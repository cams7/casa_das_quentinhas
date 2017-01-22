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
		ordenaEntregadores();

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

		// Tenta salvar os dados do entregador
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

	private void goToIndexPage() {
		goToIndexPage("entregador");
		assertEquals("Lista de Entregadores", getDriver().getTitle());
	}

	private void ordenaEntregadores() {
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
		getDriver().findElement(By.id("empresa.razaoSocial")).click();
		sleep();
	}

}
