/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getCelular;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getRg;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class EntregadorTest extends AbstractTest {

	private final String MAIN_PAGE = "entregador";
	private final String VIEW_TITLE = "Visualizar Entregador";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de entregadores
		goToIndexPage();

		// Pesquisa os entregadores que tenha os caracteres "an" no nome, cpf,
		// e-mail, celular, nome da empresa ou cnpj da empresa
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
		// Carrega a lista de entregadores
		goToIndexPage();

		// Carrega um formulário para o cadasatro do entregador
		goToCreatePage();
		assertEquals("Adicionar Entregador", getDriver().getTitle());

		// Preenche o fomulário
		createEntregador();

		// Tenta salva os dados do entregador ou cancela o cadastro
		saveOrCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testShowPage()
	 */
	@Test
	@Override
	public void testShowPage() {
		// Carrega a lista de entregadores
		goToIndexPage();

		// Visualiza os dados do entregador
		goToViewPage();
		assertEquals(VIEW_TITLE, getDriver().getTitle());

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
		// Carrega a lista de entregadores
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do entregador
		goToEditPage();
		assertEquals("Editar Entregador", getDriver().getTitle());

		// Preenche o fomulário
		editEntregador();

		// Tenta salva os dados do entregador ou cancela a edição
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
		// Carrega a lista de clientes
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
		return new String[] { "id", "nome", "cpf", "usuario.email", "celular", "empresa.razaoSocial" };
	}

	/**
	 * Vai para a página de listagem de entregadores
	 */
	private void goToIndexPage() {
		goToIndexPage("Entregador(es)");
		assertEquals("Lista de Entregadores", getDriver().getTitle());
	}

	private void createEntregador() {
		createOrEditEntregador(true);
	}

	private void editEntregador() {
		createOrEditEntregador(false);
	}

	private void createOrEditEntregador(boolean isCreatePage) {
		Person person = getFairy().person();

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			getDriver().findElement(By.name("nome")).clear();
			getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			getDriver().findElement(By.name("celular")).clear();
			getDriver().findElement(By.name("celular")).sendKeys(getCelular());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			getDriver().findElement(By.name("empresa.razaoSocial")).clear();
			getDriver().findElement(By.name("empresa.razaoSocial")).sendKeys("a");

			final By EMPRESA_ID = By.name("empresa.id");

			if (isCreatePage)
				assertTrue(getDriver().findElement(EMPRESA_ID).getAttribute("value").isEmpty());

			final By AUTOCOMPLETE = By.cssSelector("ul.ui-autocomplete");

			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement autocomplete = driver.findElement(AUTOCOMPLETE);
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

			assertFalse(getDriver().findElement(EMPRESA_ID).getAttribute("value").isEmpty());
		}
		final By EMAIL = By.name("usuario.email");
		getWait().until(ExpectedConditions.presenceOfElementLocated(EMAIL));
		if (isCreatePage || (GERENTE.equals(getAcesso()) && getBaseProducer().trueOrFalse()
				&& canBeChanged(getDriver().findElement(EMAIL).getAttribute("value")))) {
			getDriver().findElement(EMAIL).clear();
			getDriver().findElement(EMAIL).sendKeys(person.getEmail());
			sleep();
		} else if (ATENDENTE.equals(getAcesso()))
			assertTrue((Boolean) getJS().executeScript("return  $('#email').is('[readonly]');"));

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			getDriver().findElement(By.name("cpf")).clear();
			getDriver().findElement(By.name("cpf")).sendKeys(getCpf());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			getDriver().findElement(By.name("rg")).clear();
			getDriver().findElement(By.name("rg")).sendKeys(getRg());
			sleep();
		}
		if (isCreatePage) {
			getDriver().findElement(By.name("usuario.senha")).clear();
			getDriver().findElement(By.name("usuario.senha")).sendKeys(getSenhaAcesso());
			sleep();
			getDriver().findElement(By.name("usuario.confirmacaoSenha")).clear();
			getDriver().findElement(By.name("usuario.confirmacaoSenha")).sendKeys(getSenhaAcesso());
			sleep();
		}
	}
}
