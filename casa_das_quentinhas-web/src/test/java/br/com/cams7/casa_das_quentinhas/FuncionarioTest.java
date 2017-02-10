/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getCelular;
import static br.com.cams7.casa_das_quentinhas.mock.FuncionarioMock.getFuncao;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getRg;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class FuncionarioTest extends AbstractTest {

	private final String MAIN_PAGE = "funcionario";
	private final String VIEW_TITLE = "Visualizar Funcionário";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de funcionários
		goToIndexPage();

		// Pesquisa os funcionários que tenha os caracteres "an" no nome, cpf,
		// e-mail ou celular
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Carrega um formulário para o cadasatro do funcionário
		goToCreatePage();
		assertEquals("Adicionar Funcionário", getDriver().getTitle());

		// Preenche o fomulário
		createFuncionario();

		// Tenta salva os dados do funcionário ou cancela o cadastro
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Visualiza os dados do funcionário
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do funcionário
		goToEditPage();
		assertEquals("Editar Funcionário", getDriver().getTitle());

		// Preenche o fomulário
		editFuncionario();

		// Tenta salva os dados do funcionário ou cancela a edição
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
		return new String[] { "id", "nome", "cpf", "usuario.email", "celular", "funcao" };
	}

	/**
	 * Vai para a página de listagem de funcionários
	 */
	private void goToIndexPage() {
		goToIndexPage("Funcionário(s)");
		assertEquals("Lista de Funcionários", getDriver().getTitle());
	}

	private void createFuncionario() {
		createOrEditFuncionario(true);
	}

	private void editFuncionario() {
		createOrEditFuncionario(false);
	}

	private void createOrEditFuncionario(boolean isCreatePage) {
		Person person = getFairy().person();

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement nome = getDriver().findElement(By.name("nome"));
			nome.clear();
			nome.sendKeys(person.getFullName());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement celular = getDriver().findElement(By.name("celular"));
			celular.clear();
			celular.sendKeys(getCelular());
			sleep();
		}

		final By EMAIL = By.name("usuario.email");
		getWait().until(ExpectedConditions.presenceOfElementLocated(EMAIL));
		final String FUNCIONARIO_EMAIL = getDriver().findElement(EMAIL).getAttribute("value");

		if (isCreatePage || (GERENTE.equals(getAcesso()) && getBaseProducer().trueOrFalse()
				&& canBeChanged(FUNCIONARIO_EMAIL))) {
			Select funcao = new Select(getDriver().findElement(By.name("funcao")));
			funcao.deselectAll();
			funcao.selectByValue(getFuncao());
			sleep();
		} else if (ATENDENTE.equals(getAcesso()))
			assertFalse(getDriver().findElement(By.name("funcao")).isDisplayed());

		if (isCreatePage || (GERENTE.equals(getAcesso()) && getBaseProducer().trueOrFalse()
				&& canBeChanged(FUNCIONARIO_EMAIL))) {
			WebElement email = getDriver().findElement(EMAIL);
			email.clear();
			email.sendKeys(person.getEmail());
			sleep();
		} else if (ATENDENTE.equals(getAcesso()))
			assertTrue((Boolean) getJS().executeScript("return  $('#email').is('[readonly]');"));

		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement cpf = getDriver().findElement(By.name("cpf"));
			cpf.clear();
			cpf.sendKeys(getCpf());
			sleep();
		}
		if (isCreatePage || getBaseProducer().trueOrFalse()) {
			WebElement rg = getDriver().findElement(By.name("rg"));
			rg.clear();
			rg.sendKeys(getRg());
			sleep();
		}
		if (isCreatePage) {
			WebElement senha = getDriver().findElement(By.name("usuario.senha"));
			senha.clear();
			senha.sendKeys(getSenhaAcesso());
			sleep();
			WebElement confirmacaoSenha = getDriver().findElement(By.name("usuario.confirmacaoSenha"));
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
