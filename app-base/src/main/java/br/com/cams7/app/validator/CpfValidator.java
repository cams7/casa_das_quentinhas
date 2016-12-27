package br.com.cams7.app.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author cesar
 *
 */
public class CpfValidator implements ConstraintValidator<CPF, String> {

	public CpfValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	public void initialize(CPF constraintAnnotation) {
	}

	/*
	 * Verifca se o CPF e valido
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		if (cpf == null || cpf.isEmpty())
			return true;

		cpf = unformatCpf(cpf);

		String[] cpfInvalidos = { "00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
				"55555555555", "66666666666", "77777777777", "88888888888", "99999999999" };

		if (cpf.length() != 11 || Arrays.asList(cpfInvalidos).contains(cpf))
			return false;

		final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

		int digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + String.valueOf(digito1) + String.valueOf(digito2));
	}

	private int calcularDigito(String cpf, int[] peso) {
		int soma = 0;
		for (int indice = cpf.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(cpf.substring(indice, indice + 1));
			soma += digito * peso[peso.length - cpf.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static String unformatCpf(String cpf) {
		return cpf.replaceAll("^(\\d{3})\\.(\\d{3})\\.(\\d{3})\\-(\\d{2})$", "$1$2$3$4");
	}

	public static String formatCpf(String cpf) {
		return cpf.replaceAll("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3-$4");
	}

}
