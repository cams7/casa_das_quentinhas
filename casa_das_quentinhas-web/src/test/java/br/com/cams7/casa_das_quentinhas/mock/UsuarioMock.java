package br.com.cams7.casa_das_quentinhas.mock;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ENTREGADOR;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Usuario.Tipo.CLIENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Usuario.Tipo.EMPRESA;

public class UsuarioMock extends AbstractMock {

	private static final String[] emails = new String[] { "gerente@casa-das-quentinhas.com",
			"atendente@casa-das-quentinhas.com", "entregador@casa-das-quentinhas.com",
			"empresa@casa-das-quentinhas.com", "cliente@casa-das-quentinhas.com" };

	private static final String SENHA_ACESSO = "12345";

	/**
	 * Gera um e-mail de acesso aletório
	 * 
	 * @return E-mail de acesso
	 */
	public static String getQualquerEmailAcesso() {
		return getBaseProducer().randomElement(emails);
	}

	/**
	 * @return Senha de acesso
	 */
	public static final String getSenhaAcesso() {
		return SENHA_ACESSO;
	}

	public static Object getAcessoByEmail(String emailAcesso) {
		switch (emailAcesso) {
		case "gerente@casa-das-quentinhas.com":
			return GERENTE;
		case "atendente@casa-das-quentinhas.com":
			return ATENDENTE;
		case "entregador@casa-das-quentinhas.com":
			return ENTREGADOR;
		case "empresa@casa-das-quentinhas.com":
			return EMPRESA;
		case "cliente@casa-das-quentinhas.com":
			return CLIENTE;
		default:
			break;
		}

		return null;
	}

	public static String[] getEmails() {
		return emails;
	}

}
