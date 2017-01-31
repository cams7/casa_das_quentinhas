/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getFormaPagamento;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getSituacao;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getTipoAtendimento;
import static br.com.cams7.casa_das_quentinhas.mock.PedidoMock.getTipoCliente;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.util.List;

import org.openqa.selenium.By;
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
		search("an");
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
		assertEquals("Visualizar Pedido", getDriver().getTitle());

		// Volta à página anterior
		cancelOrDeleteViewPage();
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

		// Exibe o pop-pop de exclusão
		showAndCloseDeleteModal();
	}

	@Override
	protected String getMainPage() {
		return MAIN_PAGE;
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
			Select tipoCliente = new Select(getDriver().findElement(By.name("tipoCliente")));
			tipoCliente.deselectAll();
			tipoCliente.selectByValue(getTipoCliente());
			sleep();

			getDriver().findElement(By.name("cliente.nome")).clear();
			getDriver().findElement(By.name("cliente.nome")).sendKeys("a");

			final By CLIENTE_ID = By.name("cliente.id");

			if (isCreatePage)
				assertTrue(getDriver().findElement(CLIENTE_ID).getAttribute("value").isEmpty());

			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement autocomplete = driver.findElements(AUTOCOMPLETE).get(0);
					if (autocomplete.isDisplayed()) {
						List<WebElement> itens = autocomplete.findElements(By.cssSelector("li.ui-menu-item"));
						int index = getBaseProducer().randomBetween(0, itens.size() - 1);
						itens.get(index).click();
						return true;
					}
					return false;
				}
			});

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));

			assertFalse(getDriver().findElement(CLIENTE_ID).getAttribute("value").isEmpty());
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
		boolean itemCadastrado = !isCreatePage;
		do {
			final By ITEM_ADD = By.id("item_add");
			getWait().until(ExpectedConditions.elementToBeClickable(ITEM_ADD));
			getDriver().findElement(ITEM_ADD).click();

			final By ITEM_MODAL = By.id("item_modal");

			getWait().until(ExpectedConditions.visibilityOfElementLocated(ITEM_MODAL));
			WebElement itemModal = getDriver().findElement(ITEM_MODAL);

			itemModal.findElement(By.name("produto")).clear();
			itemModal.findElement(By.name("produto"))
					.sendKeys(getBaseProducer().randomElement("bife", "frango", "ovo", "salada", "creme"));

			final By PRODUTO_ID = By.name("produto_id");

			assertTrue(itemModal.findElement(PRODUTO_ID).getAttribute("value").isEmpty());

			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement autocomplete = driver.findElements(AUTOCOMPLETE).get(1);
					if (autocomplete.isDisplayed()) {
						List<WebElement> itens = autocomplete.findElements(By.cssSelector("li.ui-menu-item"));
						int index = getBaseProducer().randomBetween(0, itens.size() - 1);
						itens.get(index).click();
						return true;
					}
					return false;
				}
			});

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));

			assertFalse(itemModal.findElement(PRODUTO_ID).getAttribute("value").isEmpty());

			itemModal.findElement(By.name("quantidade")).clear();
			itemModal.findElement(By.name("quantidade"))
					.sendKeys(String.valueOf(getBaseProducer().randomBetween(1, 20)));
			sleep();

			if (getBaseProducer().trueOrFalse()) {
				WebElement modalSave = itemModal
						.findElement(By.cssSelector("div.modal-footer > input.btn.btn-primary"));
				getWait().until(ExpectedConditions.elementToBeClickable(modalSave));
				modalSave.submit();

				itemCadastrado = true;
			} else {
				WebElement modalClose = itemModal.findElement(By.cssSelector("div.modal-header > button.close"));
				getWait().until(ExpectedConditions.elementToBeClickable(modalClose));
				modalClose.click();
			}

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(ITEM_MODAL));

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
		} while (!itemCadastrado || getBaseProducer().trueOrFalse());
	}

}
