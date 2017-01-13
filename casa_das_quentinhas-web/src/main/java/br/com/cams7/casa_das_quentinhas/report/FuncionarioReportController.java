/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/funcionario/report")
public class FuncionarioReportController extends AbstractReportController<Integer, Funcionario, FuncionarioService> {

	private final String PDF_VIEW = "funcionarioPdfView";

	@Override
	public ModelAndView generatePdfReport(HttpServletRequest request) {
		return super.generatePdfReport(request);
	}

	/*
	 * Gera relatório PDF
	 * 
	 * @URL: http://localhost:8080/casa-das-quentinhas/funcionario/report/pdf?
	 * page_first=0&page_size=10&sort_field=nome&sort_order=ASCENDING&
	 * filter_field=nome&filter_field=cpf&globalFilter=ma
	 * 
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractReportController#getPdfView()
	 */
	@Override
	protected String getPdfView() {
		return PDF_VIEW;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractController#getFilters()
	 */
	@Override
	protected Map<String, Object> getFilters() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("funcao", new Funcao[] { GERENTE, ATENDENTE });
		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractController#getIgnoredJoins()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class<?>[] getIgnoredJoins() {
		return new Class<?>[] { Empresa.class };
	}

}
