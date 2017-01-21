package br.com.cams7.casa_das_quentinhas.mock;

public class UsuarioMock extends AbstractMock {

	private static final String[] emails;
	private static final String SENHA_ACESSO = "12345";

	static {
		emails = new String[] { "gerente@casa-das-quentinhas.com", "atendente@casa-das-quentinhas.com",
				"entregador@casa-das-quentinhas.com", "empresa@casa-das-quentinhas.com",
				"cliente@casa-das-quentinhas.com" };
	}

	/**
	 * Gera um e-mail de acesso aletório
	 * 
	 * @return E-mail de acesso
	 */
	public static String getQualquerEmailAcesso() {
		return emails[rand(0, emails.length - 1)];
	}

	/**
	 * @return Senha de acesso
	 */
	public static final String getSenhaAcesso() {
		return SENHA_ACESSO;
	}

}
