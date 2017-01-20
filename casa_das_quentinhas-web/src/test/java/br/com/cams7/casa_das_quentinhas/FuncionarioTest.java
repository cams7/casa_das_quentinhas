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
		goToCreatePage();

		// Tenta salvar os dados do funcionário
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Visualiza os dados do funcionário
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
		// Carrega a lista de funcionários
		goToIndexPage();

		// Carrega um formulário para a alteração dos dados do funcionário
		goToEditPage();

		// Tenta salvar os dados do funcionário
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
		getDriver().findElement(By.linkText("Funcionário(s)")).click();
		sleep();
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
