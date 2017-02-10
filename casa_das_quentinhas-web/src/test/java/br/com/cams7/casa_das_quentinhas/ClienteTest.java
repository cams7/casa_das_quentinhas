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
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Address;
import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class ClienteTest extends AbstractTest {

	private final String MAIN_PAGE = "cliente";
	private final String VIEW_TITLE = "Visualizar Cliente";

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

		// Pesquisa os clientes que tenha os caracteres "an" no nome, cpf,
		// e-mail, telefone ou cidade
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
		// Carrega a lista de clientes
		goToIndexPage();

		// Carrega um formulário para o cadasatro do cliente
		goToCreatePage();
		assertEquals("Adicionar Cliente", getDriver().getTitle());

		// Preenche o fomulário
		createCliente();

		// Tenta salvar os dados do cliente
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
		// Carrega a lista de clientes
		goToIndexPage();

		// Visualiza os dados do cliente
		goToViewPage();
		assertEquals(VIEW_TITLE, getDriver().getTitle());

		if (getDriver().findElements(By.xpath("//h3[@class='page-header']")).stream()
				.anyMatch(e -> "Pedidos".equals(e.getText())))
			testPedidos();

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
		// Carrega a lista de clientes
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do cliente
		goToEditPage();
		assertEquals("Editar Cliente", getDriver().getTitle());

		// Preenche o fomulário
		editCliente();

		// Tenta salva os dados do cliente ou cancela a edição
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
		return new String[] { "id", "nome", "cpf", "contato.email", "contato.telefone", "cidade.nome" };
	}

	/**
	 * Vai para a página de listagem de clientes
	 */
	private void goToIndexPage() {
		goToIndexPage("Cliente(s)");
		assertEquals("Lista de Clientes", getDriver().getTitle());
	}

	private void createCliente() {
		createOrEditCliente(true);
	}

	private void editCliente() {
		createOrEditCliente(false);
	}

	private void createOrEditCliente(boolean isCreatePage) {
		Person person = getFairy().person();
		Address address = person.getAddress();
		Long codigoIBGE = getQualquerCodigoIBGE();

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement nome = getDriver().findElement(By.name("nome"));
			nome.clear();
			nome.sendKeys(person.getFullName());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement cpf = getDriver().findElement(By.name("cpf"));
			cpf.clear();
			cpf.sendKeys(getCpf());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement nascimento = getDriver().findElement(By.name("nascimento"));
			nascimento.clear();
			nascimento.sendKeys(DateTimeFormat.forPattern("dd/MM/yyyy").print(person.getDateOfBirth()));
			sleep();
		}
		final By EMAIL = By.name("contato.email");
		getWait().until(ExpectedConditions.presenceOfElementLocated(EMAIL));
		final String CLIENTE_EMAIL = getDriver().findElement(EMAIL).getAttribute("value");

		if (isCreatePage || (getBaseProducer().trueOrFalse() && canBeChanged(CLIENTE_EMAIL))) {
			WebElement email = getDriver().findElement(EMAIL);
			email.clear();
			email.sendKeys(person.getEmail());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement telefone = getDriver().findElement(By.name("contato.telefone"));
			telefone.clear();
			telefone.sendKeys(getTelefone());
			sleep();
		}

		boolean cidadeAlterada = false;

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			final String CODIGO_IBGE = String.valueOf(codigoIBGE);

			final By CIDADE_NOME = By.name("cidade.nome");
			getWait().until(ExpectedConditions.presenceOfElementLocated(CIDADE_NOME));
			WebElement cidadeNome = getDriver().findElement(CIDADE_NOME);
			cidadeNome.clear();
			cidadeNome.sendKeys(CODIGO_IBGE);

			LOGGER.warn("create or edit cliente -> cidade: '{}'", CODIGO_IBGE);

			final By CIDADE_ID = By.name("cidade.id");

			validateId(CIDADE_ID, isCreatePage);

			final By AUTOCOMPLETE = By.cssSelector("ul.ui-autocomplete");

			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement autocomplete = driver.findElement(AUTOCOMPLETE);
					if (autocomplete.isDisplayed()) {
						autocomplete.findElements(By.cssSelector("li.ui-menu-item")).get(0).click();
						return true;
					}
					return false;
				}
			});

			getWait().until(ExpectedConditions.invisibilityOfElementLocated(AUTOCOMPLETE));
			getWait().until(ExpectedConditions.presenceOfElementLocated(CIDADE_ID));
			assertTrue(getDriver().findElement(CIDADE_ID).getAttribute("value").matches(NUMBER_REGEX));

			cidadeAlterada = true;
		}
		if (isCreatePage || (cidadeAlterada && getBaseProducer().trueOrFalse())) {
			WebElement cep = getDriver().findElement(By.name("endereco.cep"));
			cep.clear();
			cep.sendKeys(getQualquerCep(codigoIBGE));
			sleep();
		}
		if (isCreatePage || (cidadeAlterada && getBaseProducer().trueOrFalse())) {
			WebElement bairro = getDriver().findElement(By.name("endereco.bairro"));
			bairro.clear();
			bairro.sendKeys(getQualquerBairro(codigoIBGE));
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement logradouro = getDriver().findElement(By.name("endereco.logradouro"));
			logradouro.clear();
			logradouro.sendKeys(address.getStreet());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement numeroImovel = getDriver().findElement(By.name("endereco.numeroImovel"));
			numeroImovel.clear();
			numeroImovel.sendKeys(address.getStreetNumber());
			sleep();
		}
		// getDriver().findElement(By.name("endereco.complemento")).clear();
		// getDriver().findElement(By.name("endereco.complemento")).sendKeys("");
		// getDriver().findElement(By.name("endereco.pontoReferencia")).clear();
		// getDriver().findElement(By.name("endereco.pontoReferencia")).sendKeys("");
		if (isCreatePage) {
			WebElement senha = getDriver().findElement(By.name("usuarioAcesso.senha"));
			senha.clear();
			senha.sendKeys(getSenhaAcesso());
			sleep();
			WebElement confirmacaoSenha = getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha"));
			confirmacaoSenha.clear();
			confirmacaoSenha.sendKeys(getSenhaAcesso());
			sleep();
		}
	}

	private void testPedidos() {
		testList(new String[] { "id", "tipoCliente", "quantidade", "custo", "manutencao.cadastro" },
				"Visualizar Pedido", "Editar Pedido");
	}

}
