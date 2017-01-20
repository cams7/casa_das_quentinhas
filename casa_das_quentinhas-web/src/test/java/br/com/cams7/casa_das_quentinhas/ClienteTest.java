/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * @author César Magalhães
 *
 */
public class ClienteTest extends AbstractTest {

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
		ordenaClientes();

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

		// Tenta salvar os dados do cliente
		trySaveCreatePage();

		// Volta à página anterior
		cancelCreatePage();
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

		// Tenta salvar os dados do cliente
		saveEditPage();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.AbstractTest#goToIndexPage()
	 */
	@Override
	protected void goToIndexPage() {
		getDriver().findElement(By.linkText("Cliente(s)")).click();
		sleep();
	}

	private void ordenaClientes() {
		getDriver().findElement(By.id("id")).click();
		sleep();
		getDriver().findElement(By.id("nome")).click();
		sleep();
		getDriver().findElement(By.id("cpf")).click();
		sleep();
		getDriver().findElement(By.id("contato.email")).click();
		sleep();
		getDriver().findElement(By.id("contato.telefone")).click();
		sleep();
		getDriver().findElement(By.id("cidade.nome")).click();
		sleep();
	}

}
