/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

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

		// Ordena todos os campos da tabela de entregadores
		sortFields();

		// Pagina a lista de entregadores
		paginate();

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

		Person person = getFairy().person();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		sleep();
		getDriver().findElement(By.name("celular")).clear();
		getDriver().findElement(By.name("celular")).sendKeys(getCelular());
		sleep();
		getDriver().findElement(By.name("empresa.razaoSocial")).clear();
		getDriver().findElement(By.name("empresa.razaoSocial")).sendKeys("a");

		final By EMPRESA_ID = By.name("empresa.id");

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

		getDriver().findElement(By.name("usuario.email")).clear();
		getDriver().findElement(By.name("usuario.email")).sendKeys(person.getEmail());
		sleep();
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(getCpf());
		sleep();
		getDriver().findElement(By.name("rg")).clear();
		getDriver().findElement(By.name("rg")).sendKeys(getRg());
		sleep();
		getDriver().findElement(By.name("usuario.senha")).clear();
		getDriver().findElement(By.name("usuario.senha")).sendKeys(getSenhaAcesso());
		sleep();
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).sendKeys(getSenhaAcesso());
		sleep();

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
		assertEquals("Visualizar Entregador", getDriver().getTitle());

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
		// Carrega a lista de entregadores
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do entregador
		goToEditPage();
		assertEquals("Editar Entregador", getDriver().getTitle());

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
	 * Vai para a página de listagem de entregadores
	 */
	private void goToIndexPage() {
		goToIndexPage("Entregador(es)");
		assertEquals("Lista de Entregadores", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela entregador
	 */
	private void sortFields() {
		clickSortField("id");
		clickSortField("nome");
		clickSortField("cpf");
		clickSortField("usuario.email");
		clickSortField("celular");
		clickSortField("empresa.razaoSocial");
	}

}
