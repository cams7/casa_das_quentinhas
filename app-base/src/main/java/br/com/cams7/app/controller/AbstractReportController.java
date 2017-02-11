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

	/**
	 * 
	 */
	public AbstractReportController() {
		super();
	}

	/**
	 * Carrega os parametros para gerar o relatório
	 * 
	 * @param params
	 * @return
	 */
	private Map<String, Object> getReportParams(final SearchParams params) {
		if (getFilters() != null)
			params.getFilters().putAll(getFilters());

		setIgnoredJoins();

		List<E> entities = getService().search(params);
		entities = getEntities(entities);

		if (entities.isEmpty())
			throw new AppNotFoundException("Não foi encontrada nenhuma entidade");

		final String REPORT_PATH = getClass().getClassLoader().getResource("/META-INF/report/").getPath();

		final Map<String, Object> REPORT_PARAMS = new HashMap<String, Object>();
		REPORT_PARAMS.put(JRParameter.REPORT_LOCALE, LOCALE);
		REPORT_PARAMS.put("SUBREPORT_DIR", REPORT_PATH);
		REPORT_PARAMS.put("datasource", new JRBeanCollectionDataSource(entities));

		return REPORT_PARAMS;
	}

	/**
	 * Gera o relatório
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView generatePdfReport(final HttpServletRequest request) {
		final SearchParams PARAMS = URIHelper.getParams(ENTITY_TYPE, request.getParameterMap());

		return new ModelAndView(getPdfView(), getReportParams(PARAMS));
	}

	/**
	 * @return Nome do arquivo
	 */
	protected abstract String getPdfView();

	/**
	 * @param entities
	 *            Entidades
	 * @return Entidades
	 */
	protected List<E> getEntities(List<E> entities) {
		return entities;
	}
}
