/**
 * 
 */
package br.com.cams7.app.common;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author César Magalhães
 *
 */
public class MoneyEditor extends PropertyEditorSupport {

	public static final NumberFormat NUMBER_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		try {
			setValue(NUMBER_FORMAT.parse(value));
		} catch (ParseException e) {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		if (getValue() != null)
			return NUMBER_FORMAT.format((Float) getValue());

		return "";
	}

}
