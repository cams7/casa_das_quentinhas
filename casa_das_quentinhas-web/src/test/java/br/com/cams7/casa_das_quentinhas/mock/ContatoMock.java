package br.com.cams7.casa_das_quentinhas.mock;

public class ContatoMock extends AbstractMock {

	private static final Byte DDD = 31;

	/**
	 * Gera um número de telefone aletório
	 * 
	 * @return Número de telefone
	 */
	public static String getTelefone() {
		return DDD + "3" + getBaseProducer().randomBetween(6410000l, 6430000l);
	}

	/**
	 * Gera um número de celular aletório
	 * 
	 * @return Número de celular
	 */
	public static String getCelular() {
		// 319 91408625
		// TIM = 71000000 - 73999999, 91000000 - 97999999
		// VIVO = 96000000 - 98999999, 70000000 - 72999999
		// OI = 85000000 - 89999999
		// CLARO = 81000000 - 84999999
		final long TIM = getBaseProducer().randomElement(getBaseProducer().randomBetween(71000000l, 73999999l),
				getBaseProducer().randomBetween(91000000l, 97999999l));
		final long VIVO = getBaseProducer().randomElement(getBaseProducer().randomBetween(96000000l, 98999999l),
				getBaseProducer().randomBetween(70000000l, 72999999l));
		final long OI = getBaseProducer().randomElement(getBaseProducer().randomBetween(85000000l, 89999999l));
		final long CLARO = getBaseProducer().randomElement(getBaseProducer().randomBetween(81000000l, 84999999l));

		return DDD + "9" + getBaseProducer().randomElement(TIM, VIVO, OI, CLARO);
	}
}
