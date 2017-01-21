package br.com.cams7.casa_das_quentinhas.mock;

public class ContatoMock extends AbstractMock {

	/**
	 * Gera um número de telefone aletório
	 * 
	 * @return Número de telefone
	 */
	public static String getTelefone() {
		return DDD + "3" + rand(6410000, 6430000);
	}
}
