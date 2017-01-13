/**
 * 
 */
package br.com.cams7.app.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.SearchParams;
import br.com.cams7.app.entity.AbstractEntity;
import br.com.cams7.app.service.BaseService;
import br.com.cams7.app.utils.URIHelper;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractReportController<PK extends Serializable, E extends AbstractEntity<PK>, S extends BaseService<PK, E>>
		extends AbstractController<PK, E, S> implements BaseReportController {

	public AbstractReportController() {
		super();
	}

	private Map<String, Object> getReportParams(SearchParams params) {
		if (getFilters() != null)
			params.getFilters().putAll(getFilters());

		setIgnoredJoins();

		List<E> entities = getService().search(params);

		if (entities.isEmpty())
			throw new AppNotFoundException("Não foi encontrada nenhuma entidade");

		Map<String, Object> reportParams = new HashMap<String, Object>();
		reportParams.put(JRParameter.REPORT_LOCALE, LOCALE);
		reportParams.put("datasource", new JRBeanCollectionDataSource(entities));
		return reportParams;
	}

	private ModelAndView getModelAndView(Map<String, Object> reportParams, String reportName) {
		return new ModelAndView(reportName, reportParams);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView generatePdfReport(HttpServletRequest request) {
		SearchParams params = URIHelper.getParams(ENTITY_TYPE, request.getParameterMap());

		return getModelAndView(getReportParams(params), getPdfView());
	}

	protected abstract String getPdfView();
}
