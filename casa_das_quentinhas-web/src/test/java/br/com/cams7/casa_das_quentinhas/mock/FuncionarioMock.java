package br.com.cams7.casa_das_quentinhas.mock;

public class FuncionarioMock extends AbstractMock {

	/**
	 * Gera, aleatoriamente, a função do funcionário que pode ser GERENTE ou
	 * ATENDENTE
	 * 
	 * @return Função do funcionário
	 */
	public static String getFuncao() {
		return getBaseProducer().randomElement("Gerente", "Atendente");
	}

}
