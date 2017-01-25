/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getTelefone;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerBairro;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCep;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCodigoIBGE;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Address;
import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class ClienteTest extends AbstractTest {

	private final String MAIN_PAGE = "cliente";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de clientes
		goToIndexPage();

		// Ordena todos os campos da tabela de clientes
		sortFields();

		// Pagina a lista de clientes
		paginate();

		// Pesquisa os clientes que tenha os caracteres "an" no nome, cpf,
		// e-mail, telefone ou cidade
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
		// Carrega a lista de clientes
		goToIndexPage();

		// Carrega um formulário para o cadasatro do cliente
		goToCreatePage();
		assertEquals("Adicionar Cliente", getDriver().getTitle());

		Person person = getFairy().person();
		Address address = person.getAddress();
		Long codigoIBGE = getQualquerCodigoIBGE();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(getCpf());
		getDriver().findElement(By.name("nascimento")).clear();
		getDriver().findElement(By.name("nascimento"))
				.sendKeys(DateTimeFormat.forPattern("dd/MM/yyyy").print(person.getDateOfBirth()));
		getDriver().findElement(By.name("contato.email")).clear();
		getDriver().findElement(By.name("contato.email")).sendKeys(person.getEmail());
		getDriver().findElement(By.name("contato.telefone")).clear();
		getDriver().findElement(By.name("contato.telefone")).sendKeys(getTelefone());

		getDriver().findElement(By.name("cidade.nome")).clear();
		getDriver().findElement(By.name("cidade.nome")).sendKeys(String.valueOf(codigoIBGE));

		final By CIDADE_ID = By.name("cidade.id");

		assertEquals(getDriver().findElement(CIDADE_ID).getAttribute("value"), "");

		WebDriverWait wait = getWait();

		if (!wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement autocomplete = driver.findElement(By.cssSelector("ul.ui-autocomplete"));
				if (autocomplete.isDisplayed()) {
					autocomplete.findElements(By.cssSelector("li.ui-menu-item")).get(0).click();
					return true;
				}
				return false;
			}
		}))
			fail("O ID da cidade não foi informado");

		assertNotEquals(getDriver().findElement(CIDADE_ID).getAttribute("value"), "");

		getDriver().findElement(By.name("endereco.cep")).clear();
		getDriver().findElement(By.name("endereco.cep")).sendKeys(getQualquerCep(codigoIBGE));
		getDriver().findElement(By.name("endereco.bairro")).clear();
		getDriver().findElement(By.name("endereco.bairro")).sendKeys(getQualquerBairro(codigoIBGE));
		getDriver().findElement(By.name("endereco.logradouro")).clear();
		getDriver().findElement(By.name("endereco.logradouro")).sendKeys(address.getStreet());
		getDriver().findElement(By.name("endereco.numeroImovel")).clear();
		getDriver().findElement(By.name("endereco.numeroImovel")).sendKeys(address.getStreetNumber());
		// getDriver().findElement(By.name("endereco.complemento")).clear();
		// getDriver().findElement(By.name("endereco.complemento")).sendKeys("");
		// getDriver().findElement(By.name("endereco.pontoReferencia")).clear();
		// getDriver().findElement(By.name("endereco.pontoReferencia")).sendKeys("");
		getDriver().findElement(By.name("usuarioAcesso.senha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.senha")).sendKeys(getSenhaAcesso());
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).sendKeys(getSenhaAcesso());

		// Tenta salvar os dados do cliente
		saveCreateAndEditPage();

		// Volta à página anterior
		// cancelCreatePage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testShowPage()
	 */
	@Test
	@Override
	public void testShowPage() {
		// Carrega a lista de clientes
		goToIndexPage();

		// Visualiza os dados do cliente
		goToViewPage();
		assertEquals("Visualizar Cliente", getDriver().getTitle());

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
		// Carrega a lista de clientes
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do cliente
		goToEditPage();
		assertEquals("Editar Cliente", getDriver().getTitle());

		// Tenta salvar os dados do cliente
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
	 * Vai para a página de listagem de clientes
	 */
	private void goToIndexPage() {
		goToIndexPage("Cliente(s)");
		assertEquals("Lista de Clientes", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela cliente
	 */
	private void sortFields() {
		sortFields("id", "nome", "cpf", "contato.email", "contato.telefone", "cidade.nome");
	}

}
