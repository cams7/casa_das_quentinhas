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
public class CelularValidator implements ConstraintValidator<Celular, String> {

	private static final String REGEX_FORMATTED_CELULAR = "^\\((\\d{2})\\) (\\d{5})\\-(\\d{4})$";
	private static final String REGEX_UNFORMATTED_CELULAR = "^(\\d{2})(\\d{5})(\\d{4})$";

	public CelularValidator() {
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
	public void initialize(Celular constraintAnnotation) {
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
		
		celular = unformatCelular(celular);

		return celular.matches("[0-9]{11}");
	}

	public static String unformatCelular(String celular) {
		return celular.replaceAll(REGEX_FORMATTED_CELULAR, "$1$2$3");
	}

	public static String formatCelular(String celular) {
		return celular.replaceAll(REGEX_UNFORMATTED_CELULAR, "($1) $2-$3");
	}

}
