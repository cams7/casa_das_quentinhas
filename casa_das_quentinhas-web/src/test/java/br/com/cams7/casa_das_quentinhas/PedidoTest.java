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

import com.google.common.base.Function;

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

		// Ordena todos os campos da tabela de pedidos
		sortFields();

		// Pagina a lista de pedidos
		paginate();

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

		Select tipoCliente = new Select(getDriver().findElement(By.name("tipoCliente")));
		tipoCliente.deselectAll();
		tipoCliente.selectByValue(getTipoCliente());
		sleep();

		getDriver().findElement(By.name("cliente.nome")).clear();
		getDriver().findElement(By.name("cliente.nome")).sendKeys("a");

		final By CLIENTE_ID = By.name("cliente.id");

		assertTrue(getDriver().findElement(CLIENTE_ID).getAttribute("value").isEmpty());

		final By AUTOCOMPLETE = By.cssSelector("ul.ui-autocomplete");

		if (!getWait().until(new ExpectedCondition<Boolean>() {
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
		}))
			fail("O ID do cliente não foi informado");

		getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));

		assertFalse(getDriver().findElement(CLIENTE_ID).getAttribute("value").isEmpty());

		Select formaPagamento = new Select(getDriver().findElement(By.name("formaPagamento")));
		formaPagamento.deselectAll();
		formaPagamento.selectByValue(getFormaPagamento());
		sleep();
		Select situacao = new Select(getDriver().findElement(By.name("situacao")));
		situacao.deselectAll();
		situacao.selectByValue(getSituacao());
		sleep();
		Select tipoAtendimento = new Select(getDriver().findElement(By.name("tipoAtendimento")));
		tipoAtendimento.deselectAll();
		tipoAtendimento.selectByValue(getTipoAtendimento());
		sleep();
		do {
			final By ITEM_ADD = By.id("item_add");
			getWait().until(ExpectedConditions.elementToBeClickable(ITEM_ADD));
			getDriver().findElement(ITEM_ADD).click();

			final By ITEM_MODAL = By.id("item_modal");

			WebElement itemModal = getWait().until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					WebElement itemModal = driver.findElement(ITEM_MODAL);
					if (itemModal.isDisplayed())
						return itemModal;
					return null;
				}
			});

			if (itemModal == null)
				fail("A tela de cadastro do item de pedido não foi carregada");

			itemModal.findElement(By.name("produto")).clear();
			itemModal.findElement(By.name("produto")).sendKeys("a");

			final By PRODUTO_ID = By.name("produto_id");

			assertTrue(itemModal.findElement(PRODUTO_ID).getAttribute("value").isEmpty());

			if (!getWait().until(new ExpectedCondition<Boolean>() {
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
			}))
				fail("O ID do produto não foi informado");

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));

			assertFalse(itemModal.findElement(PRODUTO_ID).getAttribute("value").isEmpty());

			itemModal.findElement(By.name("quantidade")).clear();
			itemModal.findElement(By.name("quantidade"))
					.sendKeys(String.valueOf(getBaseProducer().randomBetween(1, 20)));
			sleep();

			WebElement modalSave = itemModal.findElement(By.cssSelector("div.modal-footer > input.btn.btn-primary"));
			getWait().until(ExpectedConditions.elementToBeClickable(modalSave));
			modalSave.click();

			// WebElement modalClose =
			// itemModal.findElement(By.cssSelector("div.modal-header >
			// button.close"));
			// wait.until(ExpectedConditions.elementToBeClickable(modalClose));
			// modalClose.click();

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(ITEM_MODAL));

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
		} while (getBaseProducer().trueOrFalse());

		// Tenta salvar os dados do pedido
		saveCreateAndEditPage();

		// Volta à página anterior
		// cancelCreateAndEditPage();
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
		cancelViewPage();
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

		// Tenta salvar os dados do pedido
		saveCreateAndEditPage();
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
		showDeleteModal();

		// Fecha o pop-pop de exclusão
		closeDeleteModal();
	}

	@Override
	protected String getMainPage() {
		return MAIN_PAGE;
	}

	/**
	 * Vai para a página de listagem de pedidos
	 */
	private void goToIndexPage() {
		goToIndexPage("Pedido(s)");
		assertEquals("Lista de Pedidos", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela pedido
	 */
	private void sortFields() {
		if (getBaseProducer().trueOrFalse())
			clickSortField("id");

		if (getBaseProducer().trueOrFalse())
			clickSortField("tipoCliente");

		if (getBaseProducer().trueOrFalse())
			clickSortField("quantidade");

		if (getBaseProducer().trueOrFalse())
			clickSortField("custo");

		if (getBaseProducer().trueOrFalse())
			clickSortField("manutencao.cadastro");

	}

}
