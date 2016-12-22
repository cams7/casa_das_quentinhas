/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
public final class AppHelper {

	/**
	 * Cria uma nova entidade
	 * 
	 * @param entityType
	 *            Tipo de classe Entity
	 * @return Entity
	 */
	public static <E extends AbstractEntity<?>> E getNewEntity(Class<E> entityType) {
		try {
			E entity = entityType.newInstance();
			return entity;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new AppException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * Retorna o tipo de classe
	 * 
	 * @param entityType
	 *            Tipo de classe Entity
	 * @param attributeName
	 *            Nome do atributo da entidade
	 * @return Tipo de classe
	 */
	@SuppressWarnings("unchecked")
	public static <E extends AbstractEntity<?>> FieldTypes<E> getFieldTypes(Class<E> entityType, String attributeName) {
		int index = attributeName.indexOf(".");
		String entityName = null;

		if (index > -1) {
			entityName = attributeName.substring(0, index);
			attributeName = attributeName.substring(index + 1);
		}

		try {
			if (entityName != null) {
				Field field = entityType.getDeclaredField(entityName);
				entityType = (Class<E>) field.getType();
				return getFieldTypes(entityType, attributeName);
			}

			Field field = entityType.getDeclaredField(attributeName);
			return new FieldTypes<E>(entityType, field.getType());
		} catch (NoSuchFieldException | SecurityException e) {
			throw new AppException(String.format("O atributo '%s' não foi encontrado na entidade '%s'", attributeName,
					entityType.getName()), e.getCause());
		}
	}

	/**
	 * Converte o valor para o tipo correto
	 * 
	 * @param entityType
	 *            Tipo da entidade
	 * @param fieldName
	 *            Nome do atributo
	 * @param fieldValue
	 *            Vator do atributo
	 * @return
	 */
	public static <E extends AbstractEntity<?>> Object getFieldValue(Class<E> entityType, String fieldName,
			Object fieldValue) {
		Class<?> attributeType = getFieldTypes(entityType, fieldName).getAttributeType();

		fieldValue = getCorrectValue(fieldValue);

		if (fieldValue == null)
			return null;

		if (attributeType.equals(String.class))
			return fieldValue;

		try {
			Constructor<?> constructor = attributeType.getDeclaredConstructor(String.class);
			Object value = constructor.newInstance(String.valueOf(fieldValue));
			return value;
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw new AppException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * Corrige o valor
	 * 
	 * @param value
	 * @return
	 */
	private static Object getCorrectValue(Object value) {
		if (value == null)
			return null;

		if (value instanceof String) {
			String stringValue = (String) value;
			if (stringValue.isEmpty())
				return null;

			return stringValue.trim();
		}

		return value;
	}

	/**
	 * Verifica se o tipo infomado é "Boolean"
	 * 
	 * @param type
	 *            Tipo de classe
	 * @return
	 */
	public static boolean isBoolean(Class<?> type) {
		return type.equals(Boolean.class) || type.equals(Boolean.TYPE);
	}

	/**
	 * Verifica se o tipo infomado é "Number"
	 * 
	 * @param type
	 *            Tipo de classe
	 * @return
	 */
	public static boolean isNumber(Class<?> type) {
		if (isInteger(type))
			return true;

		if (isFloat(type))
			return true;

		return false;
	}

	private static boolean isInteger(Class<?> type) {
		if (type.equals(Byte.class) || type.equals(Byte.TYPE))
			return true;

		if (type.equals(Short.class) || type.equals(Short.TYPE))
			return true;

		if (type.equals(Integer.class) || type.equals(Integer.TYPE))
			return true;

		if (type.equals(Long.class) || type.equals(Long.TYPE))
			return true;

		return false;
	}

	/**
	 * Verifica se o tipo infomado é "Float"
	 * 
	 * @param type
	 *            Tipo de classe
	 * @return
	 */
	private static boolean isFloat(Class<?> type) {
		if (type.equals(Float.class) || type.equals(Float.TYPE))
			return true;

		if (type.equals(Double.class) || type.equals(Double.TYPE))
			return true;

		return false;
	}

	/**
	 * Verifica se o tipo infomado é "Date"
	 * 
	 * @param type
	 *            Tipo de classe
	 * @return
	 */
	public static boolean isDate(Class<?> type) {
		return type.equals(Date.class);
	}

	/**
	 * Verifica se o tipo infomado é "enum"
	 * 
	 * @param type
	 *            Tipo de classe
	 * @return
	 */
	public static boolean isEnum(Class<?> type) {
		return type.isEnum();
	}

	/**
	 * @param values
	 * @return
	 */
	public static String getString(String[] values) {
		String stringValue = "";

		if (values == null)
			return stringValue;

		stringValue += "[";
		for (int i = 0; i < values.length; i++) {
			stringValue += values[i];
			if (i < values.length - 1)
				stringValue += ", ";
		}
		stringValue += "]";

		return stringValue;
	}

	public static class FieldTypes<E extends AbstractEntity<?>> {
		private Class<E> entityType;
		private Class<?> attributeType;

		private FieldTypes(Class<E> entityType, Class<?> attributeType) {
			super();
			this.entityType = entityType;
			this.attributeType = attributeType;
		}

		public Class<E> getEntityType() {
			return entityType;
		}

		public Class<?> getAttributeType() {
			return attributeType;
		}

	}
}
