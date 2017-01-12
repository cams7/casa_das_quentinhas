/**
 * 
 */
package br.com.cams7.app.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import br.com.cams7.app.AbstractBase;
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
		extends AbstractBase<PK, E> implements BaseReportController {

	@Autowired
	private S service;

	public AbstractReportController() {
		super();
	}

	private Map<String, Object> getReportParams(SearchParams params) {
		Map<String, Object> reportParams = new HashMap<String, Object>();

		List<E> entities = service.search(params);

		if (entities.isEmpty())
			throw new AppNotFoundException("Não foi encontrada nenhuma entidade");

		reportParams.put(JRParameter.REPORT_LOCALE, LOCALE);
		reportParams.put("datasource", new JRBeanCollectionDataSource(entities));
		return reportParams;
	}

	private ModelAndView getModelAndView(Map<String, Object> reportParams, String reportName) {
		return new ModelAndView(reportName, reportParams);
	}

	/**
	 * Gera relatório PDF
	 * 
	 * @URL: http://localhost:8080/crud_sys/req/pessoa/report/pdf? page_first=
	 *       0&page_size=15&sort_field=nascimento&sort_order=DESCENDING&
	 *       filter_field=nome&filter_field=cpf&globalFilter=m&nome=a&cpf=6
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView generatePdfReport(HttpServletRequest request) {
		SearchParams params = URIHelper.getParams(ENTITY_TYPE, request.getParameterMap());
		// pdfReport foi declarado no arquivo jasper-views.xml
		return getModelAndView(getReportParams(params), getPdfView());
	}

	protected abstract String getPdfView();

	protected final S getService() {
		return service;
	}

}
