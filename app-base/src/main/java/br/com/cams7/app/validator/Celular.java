/**
 * 
 */
package br.com.cams7.app.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Valida telefone
 * 
 * @author cesar
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CelularValidator.class)
@Documented
public @interface Celular {
	String message() default "{br.com.cams7.app.validator.celular}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
