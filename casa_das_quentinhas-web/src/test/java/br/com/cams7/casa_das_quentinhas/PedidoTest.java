/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getFormaPagamento;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getSituacao;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getTipoAtendimento;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getTipoCliente;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * @author César Magalhães
 *
 */
public class PedidoTest extends AbstractTest {

	private final String MAIN_PAGE = "pedido";
	private final String VIEW_TITLE = "Visualizar Pedido";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Pesquisa os pedidos que tenha os caracteres "an" no cliente, empresa,
		// quantidade ou custo
		search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testCreatePage()
	 */
	@Test
	@Override
	public void testCreatePage() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Carrega um formulário para o cadasatro do pedido
		goToCreatePage();
		assertEquals("Adicionar Pedido", getDriver().getTitle());

		createPedido();

		// Tenta salvar os dados do pedido
		saveCreateOrEditPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testShowPage()
	 */
	@Test
	@Override
	public void testShowPage() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Visualiza os dados do pedido
		goToViewPage();
		assertEquals(VIEW_TITLE, getDriver().getTitle());

		if (getDriver().findElements(By.xpath("//h3[@class='page-header']")).stream()
				.anyMatch(e -> "Itens".equals(e.getText())))
			testItens();

		// Volta à página anterior
		cancelOrDeleteViewPage(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testEditPage()
	 */
	@Test
	@Override
	public void testEditPage() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do pedido
		goToEditPage();
		assertEquals("Editar Pedido", getDriver().getTitle());

		editPedido();

		// Tenta salva os dados do pedido ou cancela a edição
		saveOrCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testDestroy()
	 */
	@Test
	@Override
	public void testDestroyModal() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Exibe e fecha o modal panel de exclusão
		showAndCloseDeleteModal();
	}

	@Override
	protected String getMainPage() {
		return MAIN_PAGE;
	}

	@Override
	protected String getViewTitle() {
		return VIEW_TITLE;
	}

	@Override
	protected String[] getFields() {
		return new String[] { "id", "tipoCliente", "quantidade", "custo", "manutencao.cadastro" };
	}

	@Override
	protected boolean canBeDeleted(int rowIndex) {
		return true;
	}

	/**
	 * Vai para a página de listagem de pedidos
	 */
	private void goToIndexPage() {
		goToIndexPage("Pedido(s)");
		assertEquals("Lista de Pedidos", getDriver().getTitle());
	}

	private void createPedido() {
		createOrEditPedido(true);
	}

	private void editPedido() {
		createOrEditPedido(false);
	}

	private void createOrEditPedido(boolean isCreatePage) {
		final By AUTOCOMPLETE = By.cssSelector("ul.ui-autocomplete");

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			final String TIPO_CLIENTE_ANTERIOR = (String) getJS()
					.executeScript("return $( 'select#tipoCliente option:selected' ).val();");
			final String TIPO_CLIENTE_ATUAL = getTipoCliente();

			final By CLIENTE_ID = By.name("cliente.id");

			boolean tipoClienteAlterado = !TIPO_CLIENTE_ANTERIOR.equals(TIPO_CLIENTE_ATUAL);
			if (tipoClienteAlterado) {
				getJS().executeScript("$('select#tipoCliente').val('" + TIPO_CLIENTE_ATUAL + "').change();");

				if (!isCreatePage) {
					getWait().until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							return driver.findElement(CLIENTE_ID).getAttribute("value").isEmpty();
						}
					});
				}

				LOGGER.info("create or edit pedido -> tipo anterior: {}, tipo atual: {}", TIPO_CLIENTE_ANTERIOR,
						TIPO_CLIENTE_ATUAL);

				sleep();
			}

			validateIdCliente(CLIENTE_ID, isCreatePage || tipoClienteAlterado);

			final String LETRA_ALEATORIA = getRandomLetter();

			final By CLIENTE_NOME = By.name("cliente.nome");
			getWait().until(ExpectedConditions.presenceOfElementLocated(CLIENTE_NOME));
			WebElement clienteNome = getDriver().findElement(CLIENTE_NOME);
			clienteNome.clear();
			clienteNome.sendKeys(LETRA_ALEATORIA);

			LOGGER.warn("create or edit pedido -> cliente: '{}'", LETRA_ALEATORIA);

			try {
				getWait().until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						WebElement autocomplete = driver.findElements(AUTOCOMPLETE).get(0);
						if (autocomplete.isDisplayed()) {
							List<WebElement> itens = autocomplete.findElements(By.cssSelector("li.ui-menu-item"));
							final int TOTAL_ITENS = itens.size();
							final int INDEX = getBaseProducer().randomBetween(0, TOTAL_ITENS - 1);
							itens.get(INDEX).click();

							LOGGER.warn(
									"create or edit pedido (autocomplete - cliente) -> total itens: {}, index selected: {}",
									TOTAL_ITENS, INDEX);
							return true;
						}
						return false;
					}
				});

				getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));
				getWait().until(ExpectedConditions.presenceOfElementLocated(CLIENTE_ID));
				assertTrue(getDriver().findElement(CLIENTE_ID).getAttribute("value").matches(NUMBER_REGEX));
			} catch (TimeoutException e) {
				validateIdCliente(CLIENTE_ID, isCreatePage || tipoClienteAlterado);
			}
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			Select formaPagamento = new Select(getDriver().findElement(By.name("formaPagamento")));
			formaPagamento.deselectAll();
			formaPagamento.selectByValue(getFormaPagamento());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			Select situacao = new Select(getDriver().findElement(By.name("situacao")));
			situacao.deselectAll();
			situacao.selectByValue(getSituacao());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			Select tipoAtendimento = new Select(getDriver().findElement(By.name("tipoAtendimento")));
			tipoAtendimento.deselectAll();
			tipoAtendimento.selectByValue(getTipoAtendimento());
			sleep();
		}

		mantemItem(AUTOCOMPLETE, !isCreatePage, 0);
	}

	private void validateIdCliente(final By CLIENTE_ID, final boolean tipoClienteAlterado) {
		getWait().until(ExpectedConditions.presenceOfElementLocated(CLIENTE_ID));
		final String ID = getDriver().findElement(CLIENTE_ID).getAttribute("value");
		if (tipoClienteAlterado)
			assertTrue(ID.isEmpty());
		else
			assertTrue(ID.matches(NUMBER_REGEX));
	}

	private void mantemItem(final By AUTOCOMPLETE, boolean itemIncluido, final int countItens) {
		final By ITEM_ADD = By.id("item_add");
		getWait().until(ExpectedConditions.elementToBeClickable(ITEM_ADD));
		getDriver().findElement(ITEM_ADD).click();

		if (showItemModal(AUTOCOMPLETE, true)) {
			sleep();
			itemIncluido = true;
		}

		if (itemIncluido && getBaseProducer().trueOrFalse()) {
			sleep();

			testItens();

			if (GERENTE.equals(getAcesso())) {
				final long COUNT = Long.valueOf(getCount());

				if (COUNT > 1 && getBaseProducer().trueOrFalse()) {
					final int TOTAL_ROWS = getTotalTableRows();
					final int ROW = getRandomRow(TOTAL_ROWS);

					LOGGER.info("mantem item (delete item) -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

					final By DELETE_BUTTON = getTableDeleteButton(ROW);
					getWait().until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
					getDriver().findElement(DELETE_BUTTON).click();

					sleep();

					getWait().until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							final String CURRENT_COUNT = getCount(driver);
							return !CURRENT_COUNT.isEmpty() && COUNT > Long.valueOf(CURRENT_COUNT);
						}
					});

					final String CURRENT_COUNT = getCount();

					assertFalse(CURRENT_COUNT.isEmpty());
					assertTrue(COUNT - 1 == Long.valueOf(CURRENT_COUNT));

					LOGGER.info("mantem item (item removed) -> first count: {}, current count: {}", COUNT,
							CURRENT_COUNT);
				}
			}

			if (getBaseProducer().trueOrFalse()) {
				final int TOTAL_ROWS = getTotalTableRows();
				final int ROW = getRandomRow(TOTAL_ROWS);

				LOGGER.info("mantem item (edit item) -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

				final By EDIT_BUTTON = By.cssSelector(
						"table.dataTable > tbody > tr:nth-child(" + ROW + ") > td > button.btn.btn-warning.btn-xs");
				getWait().until(ExpectedConditions.elementToBeClickable(EDIT_BUTTON));
				getDriver().findElement(EDIT_BUTTON).click();

				if (showItemModal(AUTOCOMPLETE, false))
					sleep();
			}
		}

		if (countItens < 10 || !itemIncluido || getBaseProducer().trueOrFalse())
			mantemItem(AUTOCOMPLETE, itemIncluido, countItens + 1);

	}

	private boolean showItemModal(final By AUTOCOMPLETE, boolean newItem) {
		final By ITEM_MODAL = By.id("item_modal");

		getWait().until(ExpectedConditions.visibilityOfElementLocated(ITEM_MODAL));
		WebElement itemModal = getDriver().findElement(ITEM_MODAL);

		final By PRODUTO_ID = By.name("produto_id");

		boolean produtoSelecionado = true;
		if (newItem) {
			final String LETRA_ALEATORIA = getRandomLetter();

			final By PRODUTO = By.name("produto");
			getWait().until(ExpectedConditions.presenceOfElementLocated(PRODUTO));
			WebElement produto = itemModal.findElement(PRODUTO);
			produto.clear();
			produto.sendKeys(LETRA_ALEATORIA);

			LOGGER.warn("show item modal -> produto: '{}'", LETRA_ALEATORIA);

			getWait().until(ExpectedConditions.presenceOfElementLocated(PRODUTO_ID));
			assertTrue(itemModal.findElement(PRODUTO_ID).getAttribute("value").isEmpty());

			try {
				getWait().until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						WebElement autocomplete = driver.findElements(AUTOCOMPLETE).get(1);
						if (autocomplete.isDisplayed()) {
							List<WebElement> itens = autocomplete.findElements(By.cssSelector("li.ui-menu-item"));
							final int TOTAL_ITENS = itens.size();
							final int INDEX = getBaseProducer().randomBetween(0, TOTAL_ITENS - 1);
							itens.get(INDEX).click();

							LOGGER.warn(
									"show item modal (autocomplete - produto) -> total itens: {}, index selected: {}",
									TOTAL_ITENS, INDEX);
							return true;
						}
						return false;
					}
				});
				getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));
			} catch (TimeoutException e) {
				produtoSelecionado = false;
			}
		}

		validateIdProduto(itemModal, PRODUTO_ID, produtoSelecionado);

		WebElement quantidade = itemModal.findElement(By.name("quantidade"));
		quantidade.clear();
		quantidade.sendKeys(String.valueOf(getBaseProducer().randomBetween(1, 20)));
		sleep();

		WebElement modalSave = itemModal.findElement(By.cssSelector("div.modal-footer > input.btn.btn-primary"));
		getWait().until(ExpectedConditions.elementToBeClickable(modalSave));
		modalSave.submit();

		if (!produtoSelecionado) {
			sleep();

			WebElement modalClose = itemModal.findElement(By.cssSelector("div.modal-header > button.close"));
			getWait().until(ExpectedConditions.elementToBeClickable(modalClose));
			modalClose.click();
		}

		getWait().until(ExpectedConditions.invisibilityOfElementLocated(ITEM_MODAL));

		return produtoSelecionado;
	}

	private void validateIdProduto(WebElement itemModal, final By PRODUTO_ID, boolean produtoSelecionado) {
		getWait().until(ExpectedConditions.presenceOfElementLocated(PRODUTO_ID));
		final String ID = itemModal.findElement(PRODUTO_ID).getAttribute("value");
		if (produtoSelecionado)
			assertTrue(ID.matches(NUMBER_REGEX));
		else
			assertTrue(ID.isEmpty());
	}

	private void testItens() {
		testList("quantidade", "produto.custo", "custo", "produto.nome", "produto.tamanho");
	}

}
