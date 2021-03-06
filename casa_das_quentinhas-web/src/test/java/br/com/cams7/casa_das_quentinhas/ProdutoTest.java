/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * @author César Magalhães
 *
 */
public class ProdutoTest extends AbstractTest {

	private final String MAIN_PAGE = "produto";
	private final String VIEW_TITLE = "Visualizar Produto";

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
		return new String[] { "id", "nome", "tamanho", "custo" };
	}

	@Override
	protected boolean canBeDeleted(int rowIndex) {
		return true;
	}

	/**
	 * Vai para a página de listagem de produtos
	 */
	private void goToIndexPage() {
		goToIndexPage("Produto(s)");
		assertEquals("Lista de Produtos", getDriver().getTitle());
	}

	private void testPedidos() {
		testList(new String[] { "id.pedidoId", "pedido.manutencao.cadastro", "quantidade" }, "Visualizar Pedido",
				"Editar Pedido");
	}

}
