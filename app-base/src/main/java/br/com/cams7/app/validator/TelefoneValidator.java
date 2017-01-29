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
public class TelefoneValidator implements ConstraintValidator<Telefone, String> {

	private static final String REGEX_FORMATTED_TELEFONE = "^\\((\\d{2})\\) (\\d{4})\\-(\\d{4})$";
	private static final String REGEX_UNFORMATTED_TELEFONE = "^(\\d{2})(\\d{4})(\\d{4})$";

	public TelefoneValidator() {
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
	public void initialize(Telefone constraintAnnotation) {
	}

	/*
	 * Verifica se o telefone e valido
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String telefone, ConstraintValidatorContext context) {
		if (telefone == null || telefone.isEmpty())
			return true;

		telefone = unformatTelefone(telefone);

		return telefone.matches("[0-9]{10}");
	}

	public static String unformatTelefone(String telefone) {
		return telefone.replaceAll(REGEX_FORMATTED_TELEFONE, "$1$2$3");
	}

	public static String formatTelefone(String telefone) {
		return telefone.replaceAll(REGEX_UNFORMATTED_TELEFONE, "($1) $2-$3");
	}

}
