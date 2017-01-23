/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getCelular;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getRg;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.codearte.jfairy.producer.person.Person;

/**
 * @author César Magalhães
 *
 */
public class EntregadorTest extends AbstractTest {

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
		goToCreatePage("entregador");
		assertEquals("Adicionar Entregador", getDriver().getTitle());

		Person person = getFairy().person();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("celular")).clear();
		getDriver().findElement(By.name("celular")).sendKeys(getCelular());
		getDriver().findElement(By.name("empresa.razaoSocial")).clear();
		getDriver().findElement(By.name("empresa.razaoSocial")).sendKeys("Erntogra < 77.848.384/0001-21 >");
		getJS().executeScript("$('input#empresa_id').val(" + 52 + ");");
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
		// Tenta salvar os dados do entregador
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
		// Carrega a lista de entregadores
		goToIndexPage();

		// Visualiza os dados do entregador
		goToViewPage("entregador");
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
		goToEditPage("entregador");
		assertEquals("Editar Entregador", getDriver().getTitle());

		// Tenta salvar os dados do entregador
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

	/**
	 * Vai para a página de listagem de entregadores
	 */
	private void goToIndexPage() {
		goToIndexPage("entregador", "Entregador(es)");
		assertEquals("Lista de Entregadores", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela entregador
	 */
	private void sortFields() {
		sortFields("id", "nome", "cpf", "usuario.email", "celular", "empresa.razaoSocial");
	}

}
