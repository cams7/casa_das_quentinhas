/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getCelular;
import static br.com.cams7.casa_das_quentinhas.mock.FuncionarioMock.getFuncao;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getRg;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class FuncionarioTest extends AbstractTest {

	private final String MAIN_PAGE = "funcionario";

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

		// Ordena todos os campos da tabela de funcionários
		sortFields();

		// Pagina a lista de funcionários
		paginate();

		// Pesquisa os funcionários que tenha os caracteres "an" no nome, cpf,
		// e-mail ou celular
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Carrega um formulário para o cadasatro do funcionário
		goToCreatePage();
		assertEquals("Adicionar Funcionário", getDriver().getTitle());

		Person person = getFairy().person();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("celular")).clear();
		getDriver().findElement(By.name("celular")).sendKeys(getCelular());
		new Select(getDriver().findElement(By.name("funcao"))).selectByValue(getFuncao());
		getDriver().findElement(By.name("usuario.email")).clear();
		getDriver().findElement(By.name("usuario.email")).sendKeys(person.getEmail());
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(getCpf());
		getDriver().findElement(By.name("rg")).clear();
		getDriver().findElement(By.name("rg")).sendKeys(getRg());
		getDriver().findElement(By.name("usuario.senha")).clear();
		getDriver().findElement(By.name("usuario.senha")).sendKeys(getSenhaAcesso());
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).sendKeys(getSenhaAcesso());

		// Tenta salvar os dados do funcionário
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Visualiza os dados do funcionário
		goToViewPage();
		assertEquals("Visualizar Funcionário", getDriver().getTitle());

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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do funcionário
		goToEditPage();
		assertEquals("Editar Funcionário", getDriver().getTitle());

		// Tenta salvar os dados do funcionário
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
	 * Vai para a página de listagem de funcionários
	 */
	private void goToIndexPage() {
		goToIndexPage("Funcionário(s)");
		assertEquals("Lista de Funcionários", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela funcionário
	 */
	private void sortFields() {
		sortFields("id", "nome", "cpf", "usuario.email", "celular", "funcao");
	}

}
