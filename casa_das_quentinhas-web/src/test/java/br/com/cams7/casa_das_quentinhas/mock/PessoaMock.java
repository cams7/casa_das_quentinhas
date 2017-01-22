package br.com.cams7.casa_das_quentinhas.mock;

public class PessoaMock extends AbstractMock {

	/**
	 * Gera um CPF aleatório
	 * 
	 * @return CPF
	 */
	public static String getCpf() {
		int n = 9;
		int n1 = random(n);
		int n2 = random(n);
		int n3 = random(n);
		int n4 = random(n);
		int n5 = random(n);
		int n6 = random(n);
		int n7 = random(n);
		int n8 = random(n);
		int n9 = random(n);
		int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

		d2 = 11 - (mod(d2, 11));

		if (d2 >= 10)
			d2 = 0;

		return "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;
	}

	/**
	 * Gera um número de identidade aleatório
	 * 
	 * @return RG
	 */
	public static String getRg() {
		return String.valueOf(getBaseProducer().randomBetween(11111111l, 99999999l));
	}

	public static void main(String args[]) {
		for (int i = 0; i < 100; i++)
			System.out.println("cpf\t" + getCpf());
	}
}
