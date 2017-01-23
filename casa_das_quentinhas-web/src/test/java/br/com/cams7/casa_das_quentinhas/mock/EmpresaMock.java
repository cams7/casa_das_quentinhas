/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.mock;

import static br.com.cams7.casa_das_quentinhas.entity.Empresa.RegimeTributario.REGIME_NORMAL;
import static br.com.cams7.casa_das_quentinhas.entity.Empresa.RegimeTributario.SIMPLES_NACIONAL;
import static br.com.cams7.casa_das_quentinhas.entity.Empresa.RegimeTributario.SUBLIMITE_RECEITA;
import static br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo.CLIENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo.ENTREGA;

/**
 * @author César Magalhães
 *
 */
public class EmpresaMock extends AbstractMock {

	/**
	 * Gera, aleatoriamente, o tipo de empresa que pode ser CLIENTE ou ENTREGA
	 * 
	 * @return Tipo de empresa
	 */
	public static String getTipo() {
		return getBaseProducer().randomElement(CLIENTE.name(), ENTREGA.name());
	}

	/**
	 * Gera, aleatoriamente, o regime tributário
	 * 
	 * @return Regime tributário
	 */
	public static String getRegimeTributario() {
		return getBaseProducer().randomElement(SIMPLES_NACIONAL.name(), SUBLIMITE_RECEITA.name(), REGIME_NORMAL.name());
	}

	/**
	 * Gera um CNPJ aleatório
	 * 
	 * @return CNPJ
	 */
	public static String getCnpj() {
		int n = 9;
		int n1 = random(n);
		int n2 = random(n);
		int n3 = random(n);
		int n4 = random(n);
		int n5 = random(n);
		int n6 = random(n);
		int n7 = random(n);
		int n8 = random(n);
		int n9 = 0; // randomiza(n);
		int n10 = 0; // randomiza(n);
		int n11 = 0; // randomiza(n);
		int n12 = 1; // randomiza(n);
		int d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4
				+ n1 * 5;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4
				+ n2 * 5 + n1 * 6;

		d2 = 11 - (mod(d2, 11));

		if (d2 >= 10)
			d2 = 0;

		return "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + d1 + d2;
	}

	public static void main(String args[]) {
		for (int i = 0; i < 100; i++)
			System.out.println("cnpj\t" + getCnpj());
	}

}
