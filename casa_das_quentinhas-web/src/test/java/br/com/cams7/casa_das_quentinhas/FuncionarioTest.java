/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import br.com.cams7.casa_das_quentinhas.mock.ContatoMock;
import br.com.cams7.casa_das_quentinhas.mock.FuncionarioMock;
import br.com.cams7.casa_das_quentinhas.mock.PessoaMock;
import br.com.cams7.casa_das_quentinhas.mock.UsuarioMock;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class FuncionarioTest extends AbstractTest {

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
		ordenaFuncionarios();

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
		goToCreatePage("funcionario");
		assertEquals("Adicionar Funcionário", getDriver().getTitle());

		Fairy fairy = Fairy.create();
		Person person = fairy.person();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("celular")).clear();
		getDriver().findElement(By.name("celular")).sendKeys(ContatoMock.getCelular());
		new Select(getDriver().findElement(By.name("funcao"))).selectByVisibleText(FuncionarioMock.getFuncao());
		getDriver().findElement(By.name("usuario.email")).clear();
		getDriver().findElement(By.name("usuario.email")).sendKeys(person.getEmail());
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(PessoaMock.getCpf());
		getDriver().findElement(By.name("rg")).clear();
		getDriver().findElement(By.name("rg")).sendKeys(PessoaMock.getRg());
		getDriver().findElement(By.name("usuario.senha")).clear();
		getDriver().findElement(By.name("usuario.senha")).sendKeys(UsuarioMock.getSenhaAcesso());
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuario.confirmacaoSenha")).sendKeys(UsuarioMock.getSenhaAcesso());

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
		goToViewPage("funcionario");
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
		goToEditPage("funcionario");
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

	private void goToIndexPage() {
		goToIndexPage("funcionario", "Funcionário(s)");
		assertEquals("Lista de Funcionários", getDriver().getTitle());
	}

	private void ordenaFuncionarios() {
		getDriver().findElement(By.id("id")).click();
		sleep();
		getDriver().findElement(By.id("nome")).click();
		sleep();
		getDriver().findElement(By.id("cpf")).click();
		sleep();
		getDriver().findElement(By.id("usuario.email")).click();
		sleep();
		getDriver().findElement(By.id("celular")).click();
		sleep();
		getDriver().findElement(By.id("funcao")).click();
		sleep();
	}

}
