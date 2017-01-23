/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.mock.ContatoMock.getTelefone;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerBairro;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCep;
import static br.com.cams7.casa_das_quentinhas.mock.EnderecoMock.getQualquerCidade;
import static br.com.cams7.casa_das_quentinhas.mock.PessoaMock.getCpf;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;

import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import io.codearte.jfairy.producer.person.Address;
import io.codearte.jfairy.producer.person.Person;

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
		sortFields();

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
		goToCreatePage("cliente");
		assertEquals("Adicionar Cliente", getDriver().getTitle());

		Person person = getFairy().person();
		Address address = person.getAddress();
		Cidade cidade = getQualquerCidade();

		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(getCpf());
		getDriver().findElement(By.name("nascimento")).clear();
		getDriver().findElement(By.name("nascimento"))
				.sendKeys(DateTimeFormat.forPattern("dd/MM/yyyy").print(person.getDateOfBirth()));
		getDriver().findElement(By.name("contato.email")).clear();
		getDriver().findElement(By.name("contato.email")).sendKeys(person.getEmail());
		getDriver().findElement(By.name("contato.telefone")).clear();
		getDriver().findElement(By.name("contato.telefone")).sendKeys(getTelefone());
		getDriver().findElement(By.name("cidade.nome")).clear();
		getDriver().findElement(By.name("cidade.nome")).sendKeys(cidade.getNome());
		getJS().executeScript("$('input#cidade_id').val(" + cidade.getId() + ");");
		getDriver().findElement(By.name("endereco.cep")).clear();
		getDriver().findElement(By.name("endereco.cep")).sendKeys(getQualquerCep(cidade.getId()));
		getDriver().findElement(By.name("endereco.bairro")).clear();
		getDriver().findElement(By.name("endereco.bairro")).sendKeys(getQualquerBairro(cidade.getId()));
		getDriver().findElement(By.name("endereco.logradouro")).clear();
		getDriver().findElement(By.name("endereco.logradouro")).sendKeys(address.getStreet());
		getDriver().findElement(By.name("endereco.numeroImovel")).clear();
		getDriver().findElement(By.name("endereco.numeroImovel")).sendKeys(address.getStreetNumber());
		// getDriver().findElement(By.name("endereco.complemento")).clear();
		// getDriver().findElement(By.name("endereco.complemento")).sendKeys("");
		// getDriver().findElement(By.name("endereco.pontoReferencia")).clear();
		// getDriver().findElement(By.name("endereco.pontoReferencia")).sendKeys("");
		getDriver().findElement(By.name("usuarioAcesso.senha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.senha")).sendKeys(getSenhaAcesso());
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).sendKeys(getSenhaAcesso());

		// Tenta salvar os dados do cliente
		saveCreateAndEditPage();

		// Volta à página anterior
		// cancelCreatePage();
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
		goToViewPage("cliente");
		assertEquals("Visualizar Cliente", getDriver().getTitle());

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
		goToEditPage("cliente");
		assertEquals("Editar Cliente", getDriver().getTitle());

		// Tenta salvar os dados do cliente
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
	 * Vai para a página de listagem de clientes
	 */
	private void goToIndexPage() {
		goToIndexPage("cliente", "Cliente(s)");
		assertEquals("Lista de Clientes", getDriver().getTitle());
	}

	/**
	 * Ordena, aletoriamente, os campos da tabela cliente
	 */
	private void sortFields() {
		sortFields("id", "nome", "cpf", "contato.email", "contato.telefone", "cidade.nome");
	}

}
