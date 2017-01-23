package br.com.cams7.casa_das_quentinhas.mock;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;

public class FuncionarioMock extends AbstractMock {

	/**
	 * Gera, aleatoriamente, a função do funcionário que pode ser GERENTE ou
	 * ATENDENTE
	 * 
	 * @return Função do funcionário
	 */
	public static String getFuncao() {
		return getBaseProducer().randomElement(GERENTE.name(), ATENDENTE.name());
	}

}
