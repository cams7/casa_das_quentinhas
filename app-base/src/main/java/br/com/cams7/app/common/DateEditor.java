/**
 * 
 */
package br.com.cams7.app.common;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author César Magalhães
 *
 */
public class DateEditor extends PropertyEditorSupport {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void setAsText(String value) throws IllegalArgumentException {
		try {
			setValue(DATE_FORMAT.parse(value));
		} catch (ParseException e) {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		if (getValue() != null)
			return DATE_FORMAT.format((Date) getValue());

		return "";
	}

}
