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

	private final String MAIN_PAGE = "produto";

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
		assertEquals("Adicionar Produto", getDriver().getTitle());

		// Tenta salva os dados do produto ou cancela o cadastro
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
		// Carrega a lista de produtos
		goToIndexPage();

		// Visualiza os dados do produto
		goToViewPage();
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
		goToEditPage();
		assertEquals("Editar Produto", getDriver().getTitle());

		// Tenta salva os dados do produto ou cancela a edição
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
		// Carrega a lista de produtos
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

	@Override
	protected String[] getFields() {
		return new String[] { "id", "nome", "tamanho", "custo" };
	}

	/**
	 * Vai para a página de listagem de produtos
	 */
	private void goToIndexPage() {
		goToIndexPage("Produto(s)");
		assertEquals("Lista de Produtos", getDriver().getTitle());
	}

}
