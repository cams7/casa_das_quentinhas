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
public class ProdutoTest extends AbstractTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de produtos
		goToIndexPage();

		// Ordena todos os campos da tabela de produtos
		ordenaClientes();

		// Pagina a lista de produtos
		paginate();

		// Pesquisa os produtos que tenha os caracteres "an" no nome ou custo
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
		// Carrega a lista de produtos
		goToIndexPage();

		// Carrega um formulário para o cadasatro do produto
		goToCreatePage();

		// Tenta salvar os dados do produto
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
		// Carrega a lista de produtos
		goToIndexPage();

		// Visualiza os dados do produto
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
		// Carrega a lista de produtos
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do produto
		goToEditPage();

		// Tenta salvar os dados do produto
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
		// Carrega a lista de produtos
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
		getDriver().findElement(By.linkText("Produto(s)")).click();
		sleep();
	}

	private void ordenaClientes() {
		getDriver().findElement(By.id("id")).click();
		sleep();
		getDriver().findElement(By.id("nome")).click();
		sleep();
		getDriver().findElement(By.id("tamanho")).click();
		sleep();
		getDriver().findElement(By.id("custo")).click();
		sleep();
	}

}
