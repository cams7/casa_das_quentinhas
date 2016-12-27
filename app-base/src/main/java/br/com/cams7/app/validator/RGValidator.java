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
public class RGValidator implements ConstraintValidator<RG, String> {

	public RGValidator() {
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
	public void initialize(RG constraintAnnotation) {
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
	public boolean isValid(String rg, ConstraintValidatorContext context) {
		if (rg == null || rg.isEmpty())
			return true;

		return rg.matches("[0-9]{8,9}");
	}

}
