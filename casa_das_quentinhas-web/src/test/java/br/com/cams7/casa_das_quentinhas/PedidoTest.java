/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * @author César Magalhães
 *
 */
public class PedidoTest extends AbstractTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.BaseTest#testIndex()
	 */
	@Test
	@Override
	public void testIndex() {
		// Carrega a lista de pedidos
		goToIndexPage();

		// Ordena todos os campos da tabela de pedidos
		ordenaClientes();

		// Pagina a lista de pedidos
		paginate();

		// Pesquisa os pedidos que tenha os caracteres "an" no cliente, empresa,
		// quantidade ou custo
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
		// Carrega a lista de pedidos
		goToIndexPage();

		// Carrega um formulário para o cadasatro do pedido
		goToCreatePage("pedido");
		assertEquals("Adicionar Pedido", getDriver().getTitle());

		// Tenta salvar os dados do pedido
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
		// Carrega a lista de pedidos
		goToIndexPage();

		// Visualiza os dados do pedido
		goToViewPage("pedido");
		assertEquals("Visualizar Pedido", getDriver().getTitle());

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
		// Carrega a lista de pedidos
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do pedido
		goToEditPage("pedido");
		assertEquals("Editar Pedido", getDriver().getTitle());

		// Tenta salvar os dados do pedido
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
		// Carrega a lista de pedidos
		goToIndexPage();

		// Exibe o pop-pop de exclusão
		showDeleteModal();

		// Fecha o pop-pop de exclusão
		closeDeleteModal();
	}

	private void goToIndexPage() {
		goToIndexPage("pedido", "Pedido(s)");
		assertEquals("Lista de Pedidos", getDriver().getTitle());
	}

	private void ordenaClientes() {
		getDriver().findElement(By.id("id")).click();
		sleep();
		getDriver().findElement(By.id("tipoCliente")).click();
		sleep();
		getDriver().findElement(By.id("quantidade")).click();
		sleep();
		getDriver().findElement(By.id("custo")).click();
		sleep();
		getDriver().findElement(By.id("manutencao.cadastro")).click();
		sleep();
	}

}
