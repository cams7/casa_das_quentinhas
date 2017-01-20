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
public class EmpresaTest extends AbstractTest {

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
		ordenaClientes();

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

		// Tenta salvar os dados da empresa
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Visualiza os dados da empresa
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
		// Carrega a lista de empresas
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados da empresa
		goToEditPage();

		// Tenta salvar os dados da empresa
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
		// Carrega a lista de empresas
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
		getDriver().findElement(By.linkText("Empresa(s)")).click();
		sleep();
	}

	private void ordenaClientes() {
		getDriver().findElement(By.id("id")).click();
		sleep();
		getDriver().findElement(By.id("razaoSocial")).click();
		sleep();
		getDriver().findElement(By.id("cnpj")).click();
		sleep();
		getDriver().findElement(By.id("contato.email")).click();
		sleep();
		getDriver().findElement(By.id("contato.telefone")).click();
		sleep();
		getDriver().findElement(By.id("tipo")).click();
		sleep();
		getDriver().findElement(By.id("cidade.nome")).click();
		sleep();
	}

}
