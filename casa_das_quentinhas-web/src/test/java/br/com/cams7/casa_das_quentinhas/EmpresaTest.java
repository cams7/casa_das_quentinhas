/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getTelefone;
import static br.com.cams7.casa_das_quentinhas.mock.EmpresaMock.getCnpj;
import static br.com.cams7.casa_das_quentinhas.mock.EmpresaMock.getRegimeTributario;
import static br.com.cams7.casa_das_quentinhas.mock.EmpresaMock.getTipo;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerBairro;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCep;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCodigoIBGE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.company.Company;
import io.codearte.jfairy.producer.person.Address;
import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class EmpresaTest extends AbstractTest {

	private final String MAIN_PAGE = "empresa";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de empresas
		goToIndexPage();

		// Ordena todos os campos da tabela de empresas
		sortFields();

		// Pagina a lista de clientes
		paginate();

		// Pesquisa as empresas que tenha os caracteres "an" na razão social,
		// cnpj, e-mail ou cidade
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Carrega um formulário para o cadasatro da empresa
		goToCreatePage();
		assertEquals("Adicionar Empresa", getDriver().getTitle());

		Person person = getFairy().person();
		Company company = person.getCompany();
		Address address = person.getAddress();

		Long codigoIBGE = getQualquerCodigoIBGE();

		getDriver().findElement(By.name("razaoSocial")).clear();
		getDriver().findElement(By.name("razaoSocial")).sendKeys(company.getName());
		sleep();
		getDriver().findElement(By.name("nomeFantasia")).clear();
		getDriver().findElement(By.name("nomeFantasia")).sendKeys(company.getName());
		sleep();
		getDriver().findElement(By.name("cnpj")).clear();
		getDriver().findElement(By.name("cnpj")).sendKeys(getCnpj());
		sleep();
		new Select(getDriver().findElement(By.name("tipo"))).selectByValue(getTipo());
		sleep();
		getDriver().findElement(By.name("contato.email")).clear();
		getDriver().findElement(By.name("contato.email")).sendKeys(company.getEmail());
		sleep();
		getDriver().findElement(By.name("contato.telefone")).clear();
		getDriver().findElement(By.name("contato.telefone")).sendKeys(getTelefone());
		sleep();
		new Select(getDriver().findElement(By.name("regimeTributario"))).selectByValue(getRegimeTributario());
		sleep();
		// getDriver().findElement(By.name("inscricaoEstadual")).clear();
		// getDriver().findElement(By.name("inscricaoEstadual")).sendKeys("0142315578483");
		// getDriver().findElement(By.name("inscricaoEstadualST")).clear();
		// getDriver().findElement(By.name("inscricaoEstadualST")).sendKeys("");
		// getDriver().findElement(By.name("inscricaoMuncipal")).clear();
		// getDriver().findElement(By.name("inscricaoMuncipal")).sendKeys("");
		// getDriver().findElement(By.name("codigoCnae")).clear();
		// getDriver().findElement(By.name("codigoCnae")).sendKeys("");
		getDriver().findElement(By.name("cidade.nome")).clear();
		getDriver().findElement(By.name("cidade.nome")).sendKeys(String.valueOf(codigoIBGE));

		final By CIDADE_ID = By.name("cidade.id");

		assertTrue(getDriver().findElement(CIDADE_ID).getAttribute("value").isEmpty());

		final By AUTOCOMPLETE = By.cssSelector("ul.ui-autocomplete");

		if (!getWait().until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement autocomplete = driver.findElement(AUTOCOMPLETE);
				if (autocomplete.isDisplayed()) {
					autocomplete.findElements(By.cssSelector("li.ui-menu-item")).get(0).click();
					return true;
				}
				return false;
			}
		}))
			fail("O ID da cidade não foi informado");

		getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));

		assertFalse(getDriver().findElement(CIDADE_ID).getAttribute("value").isEmpty());

		getDriver().findElement(By.name("endereco.cep")).clear();
		getDriver().findElement(By.name("endereco.cep")).sendKeys(getQualquerCep(codigoIBGE));
		sleep();
		getDriver().findElement(By.name("endereco.bairro")).clear();
		getDriver().findElement(By.name("endereco.bairro")).sendKeys(getQualquerBairro(codigoIBGE));
		sleep();
		getDriver().findElement(By.name("endereco.logradouro")).clear();
		getDriver().findElement(By.name("endereco.logradouro")).sendKeys(address.getStreet());
		sleep();
		getDriver().findElement(By.name("endereco.numeroImovel")).clear();
		getDriver().findElement(By.name("endereco.numeroImovel")).sendKeys(address.getStreetNumber());
		sleep();
		// getDriver().findElement(By.name("endereco.complemento")).clear();
		// getDriver().findElement(By.name("endereco.complemento")).sendKeys("");
		// getDriver().findElement(By.name("endereco.pontoReferencia")).clear();
		// getDriver().findElement(By.name("endereco.pontoReferencia")).sendKeys("");
		getDriver().findElement(By.name("usuarioAcesso.senha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.senha")).sendKeys(getSenhaAcesso());
		sleep();
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).sendKeys(getSenhaAcesso());
		sleep();
		// Tenta salvar os dados da empresa
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Visualiza os dados da empresa
		goToViewPage();
		assertEquals("Visualizar Empresa", getDriver().getTitle());

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
		// Carrega a lista de empresas
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados da empresa
		goToEditPage();
		assertEquals("Editar Empresa", getDriver().getTitle());

		// Tenta salvar os dados da empresa
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
		// Carrega a lista de empresas
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
	 * Vai para a página de listagem de empresas
	 */
	private void goToIndexPage() {
		goToIndexPage("Empresa(s)");
		assertEquals("Lista de Empresas", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela empresa
	 */
	private void sortFields() {
		if (getBaseProducer().trueOrFalse())
			clickSortField("id");

		if (getBaseProducer().trueOrFalse())
			clickSortField("razaoSocial");

		if (getBaseProducer().trueOrFalse())
			clickSortField("cnpj");

		if (getBaseProducer().trueOrFalse())
			clickSortField("contato.email");

		if (getBaseProducer().trueOrFalse())
			clickSortField("contato.telefone");

		if (getBaseProducer().trueOrFalse())
			clickSortField("tipo");

		if (getBaseProducer().trueOrFalse())
			clickSortField("cidade.nome");
	}

}
