/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
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

import java.util.List;

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
	private final String VIEW_TITLE = "Visualizar Empresa";

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

		// Pesquisa as empresas que tenha os caracteres "an" na razão social,
		// cnpj, e-mail ou cidade
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Carrega um formulário para o cadasatro da empresa
		goToCreatePage();
		assertEquals("Adicionar Empresa", getDriver().getTitle());

		createEmpresa();

		// Tenta salvar os dados da empresa
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Visualiza os dados da empresa
		goToViewPage();
		assertEquals(VIEW_TITLE, getDriver().getTitle());

		List<WebElement> headers = getDriver().findElements(By.xpath("//h3[@class='page-header']"));
		if (headers.stream().anyMatch(e -> "Pedidos".equals(e.getText())))
			testPedidos();
		else if (headers.stream().anyMatch(e -> "Entregadores".equals(e.getText())))
			testEntregadores();

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
		// Carrega a lista de empresas
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados da empresa
		goToEditPage();
		assertEquals("Editar Empresa", getDriver().getTitle());

		editEmpresa();

		// Tenta salva os dados da empresa ou cancela a edição
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
		// Carrega a lista de empresas
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
		return new String[] { "id", "razaoSocial", "cnpj", "contato.email", "contato.telefone", "tipo", "cidade.nome" };
	}

	/**
	 * Vai para a página de listagem de empresas
	 */
	private void goToIndexPage() {
		goToIndexPage("Empresa(s)");
		assertEquals("Lista de Empresas", getDriver().getTitle());
	}

	private void createEmpresa() {
		createOrEditEmpresa(true);
	}

	private void editEmpresa() {
		createOrEditEmpresa(false);
	}

	private void createOrEditEmpresa(boolean isCreatePage) {
		Person person = getFairy().person();
		Company company = person.getCompany();
		Address address = person.getAddress();

		Long codigoIBGE = getQualquerCodigoIBGE();

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement razaoSocial = getDriver().findElement(By.name("razaoSocial"));
			razaoSocial.clear();
			razaoSocial.sendKeys(company.getName());
			sleep();

			WebElement nomeFantasia = getDriver().findElement(By.name("nomeFantasia"));
			nomeFantasia.clear();
			nomeFantasia.sendKeys(company.getName());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement cnpj = getDriver().findElement(By.name("cnpj"));
			cnpj.clear();
			cnpj.sendKeys(getCnpj());
			sleep();
		}
		if (isCreatePage || (GERENTE.equals(getAcesso()) && getBaseProducer().trueOrFalse())) {
			Select tipo = new Select(getDriver().findElement(By.name("tipo")));
			tipo.deselectAll();
			tipo.selectByValue(getTipo());
			sleep();
		} else if (ATENDENTE.equals(getAcesso()))
			assertFalse(getDriver().findElement(By.name("tipo")).isDisplayed());

		final By EMAIL = By.name("contato.email");
		getWait().until(ExpectedConditions.presenceOfElementLocated(EMAIL));
		final String EMPRESA_EMAIL = getDriver().findElement(EMAIL).getAttribute("value");

		if (isCreatePage || (getBaseProducer().trueOrFalse() && canBeChanged(EMPRESA_EMAIL))) {
			WebElement email = getDriver().findElement(EMAIL);
			email.clear();
			email.sendKeys(company.getEmail());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement telefone = getDriver().findElement(By.name("contato.telefone"));
			telefone.clear();
			telefone.sendKeys(getTelefone());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			Select regimeTributario = new Select(getDriver().findElement(By.name("regimeTributario")));
			regimeTributario.deselectAll();
			regimeTributario.selectByValue(getRegimeTributario());
			sleep();
		}
		// getDriver().findElement(By.name("inscricaoEstadual")).clear();
		// getDriver().findElement(By.name("inscricaoEstadual")).sendKeys("0142315578483");
		// getDriver().findElement(By.name("inscricaoEstadualST")).clear();
		// getDriver().findElement(By.name("inscricaoEstadualST")).sendKeys("");
		// getDriver().findElement(By.name("inscricaoMuncipal")).clear();
		// getDriver().findElement(By.name("inscricaoMuncipal")).sendKeys("");
		// getDriver().findElement(By.name("codigoCnae")).clear();
		// getDriver().findElement(By.name("codigoCnae")).sendKeys("");

		boolean cidadeAlterada = false;

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			final String CODIGO_IBGE = String.valueOf(codigoIBGE);

			final By CIDADE_NOME = By.name("cidade.nome");
			getWait().until(ExpectedConditions.presenceOfElementLocated(CIDADE_NOME));
			WebElement cidadeNome = getDriver().findElement(CIDADE_NOME);
			cidadeNome.clear();
			cidadeNome.sendKeys(CODIGO_IBGE);

			LOGGER.info("create or edit empresa -> cidade: '{}'", CODIGO_IBGE);

			final By CIDADE_ID = By.name("cidade.id");

			validateIdCidade(CIDADE_ID, isCreatePage);

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

	private void validateIdCidade(final By CIDADE_ID, boolean isCreatePage) {
		getWait().until(ExpectedConditions.presenceOfElementLocated(CIDADE_ID));
		final String ID = getDriver().findElement(CIDADE_ID).getAttribute("value");
		if (isCreatePage)
			assertTrue(ID.isEmpty());
		else
			assertTrue(ID.matches(NUMBER_REGEX));
	}

	private void testPedidos() {
		testList(new String[] { "id", "tipoCliente", "quantidade", "custo", "manutencao.cadastro" },
				"Visualizar Pedido", "Editar Pedido");
	}

	private void testEntregadores() {
		testList(new String[] { "id", "nome", "cpf", "usuario.email", "celular" }, "Visualizar Entregador",
				"Editar Entregador");
	}

}
