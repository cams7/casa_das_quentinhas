/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static org.junit.Assert.assertEquals;

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
		sortFields();

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
		goToCreatePage("produto");
		assertEquals("Adicionar Produto", getDriver().getTitle());

		// Tenta salvar os dados do produto
		saveCreateAndEditPage();

		// Volta à página anterior
		cancelCreateAndEditPage();
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
		goToViewPage("produto");
		assertEquals("Visualizar Produto", getDriver().getTitle());

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
		goToEditPage("produto");
		assertEquals("Editar Produto", getDriver().getTitle());

		// Tenta salvar os dados do produto
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
		// Carrega a lista de produtos
		goToIndexPage();

		// Exibe o pop-pop de exclusão
		showDeleteModal();

		// Fecha o pop-pop de exclusão
		closeDeleteModal();
	}

	/**
	 * Vai para a página de listagem de produtos
	 */
	private void goToIndexPage() {
		goToIndexPage("produto", "Produto(s)");
		assertEquals("Lista de Produtos", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela produto
	 */
	private void sortFields() {
		sortFields("id", "nome", "tamanho", "custo");
	}

}
