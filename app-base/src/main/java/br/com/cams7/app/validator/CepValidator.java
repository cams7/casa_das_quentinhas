/**
 * 
 */
package br.com.cams7.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author cesar
 *
 */
public class CepValidator implements ConstraintValidator<CEP, String> {

	private static final String REGEX_FORMATTED_CEP = "^(\\d{5})\\-(\\d{3})$";
	private static final String REGEX_UNFORMATTED_CEP = "^(\\d{5})(\\d{3})$";

	public CepValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(CEP constraintAnnotation) {
	}

	/*
	 * Verifica se o celular é valido
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String celular, ConstraintValidatorContext context) {
		if (celular == null || celular.isEmpty())
			return true;

		celular = unformatCep(celular);

		return celular.matches("[0-9]{8}");
	}

	public static String unformatCep(String celular) {
		return celular.replaceAll(REGEX_FORMATTED_CEP, "$1$2");
	}

	public static String formatCep(String celular) {
		return celular.replaceAll(REGEX_UNFORMATTED_CEP, "$1-$2");
	}

}
