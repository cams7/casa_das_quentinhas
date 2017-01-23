package br.com.cams7.casa_das_quentinhas.mock;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ENTREGADOR;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Usuario.Tipo.CLIENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Usuario.Tipo.EMPRESA;

public class UsuarioMock extends AbstractMock {

	private static final String[] emails;
	private static final String SENHA_ACESSO = "12345";

	static {
		emails = new String[] { "gerente@casa-das-quentinhas.com"/*, "atendente@casa-das-quentinhas.com",
				"entregador@casa-das-quentinhas.com", "empresa@casa-das-quentinhas.com",
				"cliente@casa-das-quentinhas.com"*/ };
	}

	/**
	 * Gera um e-mail de acesso aletório
	 * 
	 * @return E-mail de acesso
	 */
	public static String getQualquerEmailAcesso() {
		return emails[getBaseProducer().randomBetween(0, emails.length - 1)];
	}

	/**
	 * @return Senha de acesso
	 */
	public static final String getSenhaAcesso() {
		return SENHA_ACESSO;
	}

	public static Object getAcesso(String emailAcesso) {
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

}
