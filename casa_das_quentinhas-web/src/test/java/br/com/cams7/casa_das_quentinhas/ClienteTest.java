/**
 * 
 */
package br.com.cams7.casa_das_quentinhas;

import static org.junit.Assert.assertEquals;

import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import br.com.cams7.casa_das_quentinhas.mock.ContatoMock;
import br.com.cams7.casa_das_quentinhas.mock.EnderecoMock;
import br.com.cams7.casa_das_quentinhas.mock.PessoaMock;
import br.com.cams7.casa_das_quentinhas.mock.UsuarioMock;
import io.codearte.jfairy.Fairy;
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
		goToCreatePage("cliente");
		assertEquals("Adicionar Cliente", getDriver().getTitle());

		Fairy fairy = Fairy.create();

		Person person = fairy.person();
		Address address = person.getAddress();

		// By.xpath("//input[@name='nome']")
		getDriver().findElement(By.name("nome")).clear();
		getDriver().findElement(By.name("nome")).sendKeys(person.getFullName());
		getDriver().findElement(By.name("cpf")).clear();
		getDriver().findElement(By.name("cpf")).sendKeys(PessoaMock.getCpf());
		getDriver().findElement(By.name("nascimento")).clear();
		getDriver().findElement(By.name("nascimento"))
				.sendKeys(DateTimeFormat.forPattern("dd/MM/yyyy").print(person.getDateOfBirth()));
		getDriver().findElement(By.name("contato.email")).clear();
		getDriver().findElement(By.name("contato.email")).sendKeys(person.getEmail());

		Cidade cidade = EnderecoMock.getQualquerCidade();

		getDriver().findElement(By.name("contato.telefone")).clear();
		getDriver().findElement(By.name("contato.telefone")).sendKeys(ContatoMock.getTelefone());
		getDriver().findElement(By.name("cidade.nome")).clear();
		getDriver().findElement(By.name("cidade.nome")).sendKeys(cidade.getNome());
		getJS().executeScript("$('input#cidade_id').val(" + cidade.getId() + ");");
		getDriver().findElement(By.name("endereco.cep")).clear();
		getDriver().findElement(By.name("endereco.cep")).sendKeys(EnderecoMock.getQualquerCep(cidade.getId()));
		getDriver().findElement(By.name("endereco.bairro")).clear();
		getDriver().findElement(By.name("endereco.bairro")).sendKeys(EnderecoMock.getQualquerBairro(cidade.getId()));
		getDriver().findElement(By.name("endereco.logradouro")).clear();
		getDriver().findElement(By.name("endereco.logradouro")).sendKeys(address.getStreet());
		getDriver().findElement(By.name("endereco.numeroImovel")).clear();
		getDriver().findElement(By.name("endereco.numeroImovel")).sendKeys(address.getStreetNumber());
		// getDriver().findElement(By.name("endereco.complemento")).clear();
		// getDriver().findElement(By.name("endereco.complemento")).sendKeys("");
		// getDriver().findElement(By.name("endereco.pontoReferencia")).clear();
		// getDriver().findElement(By.name("endereco.pontoReferencia")).sendKeys("");
		getDriver().findElement(By.name("usuarioAcesso.senha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.senha")).sendKeys(UsuarioMock.getSenhaAcesso());
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).clear();
		getDriver().findElement(By.name("usuarioAcesso.confirmacaoSenha")).sendKeys(UsuarioMock.getSenhaAcesso());
		sleep();

		// Tenta salvar os dados do cliente
		saveCreatePage();

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

	private void goToIndexPage() {
		goToIndexPage("cliente");
		assertEquals("Lista de Clientes", getDriver().getTitle());
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
