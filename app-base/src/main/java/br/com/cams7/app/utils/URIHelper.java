/**
 * 
 */
package br.com.cams7.app.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.cams7.app.AppException;
import br.com.cams7.app.ReportView;
import br.com.cams7.app.SearchParams;
import br.com.cams7.app.ReportView.Pagination;
import br.com.cams7.app.SearchParams.SortOrder;
import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
public final class URIHelper {

	private static final String PAGE_FIRST = "page_first";
	private static final String PAGE_SIZE = "page_size";
	private static final String SORT_FIELD = "sort_field";
	private static final String SORT_ORDER = "sort_order";
	private static final String FILTER_FIELD = "filter_field";
	private static final String GLOBAL_FILTER = "globalFilter";

	/**
	 * @param messages
	 * @param paramName
	 * @param paramValues
	 * @return
	 */
	private static boolean onlyOneParameter(Map<String, String> messages, String paramName, String[] paramValues) {
		if (paramValues.length > 1) {
			messages.put(paramName,
					String.format("URI inválida, porque o parâmetro foi passado %s vezes", paramValues.length));
			return false;
		}
		return true;
	}

	/**
	 * @param messages
	 * @param paramName
	 */
	private static void validParameter(Map<String, String> messages, String paramName) {
		messages.put(paramName, "URI inválida, porque o parâmetro não é válido");
	}

	public static <E extends AbstractEntity<?>> SearchParams getParams(Class<E> entityType,
			Map<String, String[]> allParams) {

		SearchParams params = new SearchParams();
		params.setFilters(new HashMap<String, Object>());

		Map<String, String> messages = new HashMap<>();

		for (Entry<String, String[]> param : allParams.entrySet()) {
			String paramName = param.getKey();
			String[] paramValues = param.getValue();

			switch (paramName) {
			case PAGE_FIRST:
				if (onlyOneParameter(messages, paramName, paramValues))
					try {
						params.setFirstPage(Integer.parseInt(paramValues[0]));
					} catch (NumberFormatException e) {
						validParameter(messages, paramName);
					}
				break;
			case PAGE_SIZE:
				if (onlyOneParameter(messages, paramName, paramValues))
					try {
						params.setSizePage(Short.parseShort(paramValues[0]));
					} catch (NumberFormatException e) {
						validParameter(messages, paramName);
					}
				break;
			case SORT_FIELD:
				if (onlyOneParameter(messages, paramName, paramValues))
					params.setSortField(paramValues[0]);
				break;
			case SORT_ORDER:
				if (onlyOneParameter(messages, paramName, paramValues))
					try {
						params.setSortOrder(SortOrder.valueOf(paramValues[0]));
					} catch (IllegalArgumentException e) {
						validParameter(messages, paramName);
					}
				break;
			case FILTER_FIELD:
				params.setGlobalFilters(paramValues);
				break;
			default:
				if (onlyOneParameter(messages, paramName, paramValues)) {
					Object value = paramValues[0];
					if (!GLOBAL_FILTER.equals(paramName))
						try {
							value = AppHelper.getFieldValue(entityType, paramName, paramValues[0]);
						} catch (AppException e) {
							validParameter(messages, paramName);
							break;
						}
					params.getFilters().put(paramName, value);
				}
				break;
			}
		}

		if (!messages.isEmpty())
			throw new AppException(messages);

		return params;
	}

	public static String getURI(SearchParams params, ReportView view) {
		StringBuffer uri = new StringBuffer();
		boolean includedQuestionMark = false;

		Pagination pagination = view.getPagination(params.getFirstPage(), params.getSizePage());
		if (pagination.isFirstAndSizePage()) {
			uri.append("?" + PAGE_FIRST + "=" + pagination.getFirstPage());
			uri.append("&" + PAGE_SIZE + "=" + pagination.getSizePage());
			includedQuestionMark = true;
		}

		if (params.getSortField() != null) {
			uri.append(getQueryDelimiter(includedQuestionMark) + SORT_FIELD + "=" + params.getSortField());
			uri.append("&" + SORT_ORDER + "=" + params.getSortOrder().name());
			includedQuestionMark = true;
		}

		if (!params.getFilters().isEmpty()) {
			if (params.getFilters().containsKey(GLOBAL_FILTER)) {
				uri.append(getQueryDelimiter(includedQuestionMark) + GLOBAL_FILTER + "="
						+ params.getFilters().get(GLOBAL_FILTER));
				for (String field : params.getGlobalFilters())
					uri.append("&" + FILTER_FIELD + "=" + field);

				includedQuestionMark = true;
			}

			for (Entry<String, Object> param : params.getFilters().entrySet()) {
				String paramName = param.getKey();
				Object paramValue = param.getValue();

				if (GLOBAL_FILTER.equals(paramName))
					continue;

				uri.append(getQueryDelimiter(includedQuestionMark) + paramName + "=" + paramValue);
				includedQuestionMark = true;
			}
		}

		return uri.toString();
	}

	private static String getQueryDelimiter(boolean isQueryDelimiter) {
		return isQueryDelimiter ? "&" : "?";
	}

}
