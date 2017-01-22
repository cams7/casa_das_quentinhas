package br.com.cams7.casa_das_quentinhas.mock;

public class ContatoMock extends AbstractMock {

	/**
	 * Gera um número de telefone aletório
	 * 
	 * @return Número de telefone
	 */
	public static String getTelefone() {
		return DDD + "3" + getBaseProducer().randomBetween(6410000l, 6430000l);
	}
}
