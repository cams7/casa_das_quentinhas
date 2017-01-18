/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ENTREGADOR;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/entregador/report")
public class EntregadorReportController extends AbstractReportController<Integer, Funcionario, FuncionarioService> {

	private final String PDF_VIEW = "entregadorPdfView";

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
		filters.put("funcao", ENTREGADOR);
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
		return new Class<?>[] { Usuario.class };
	}

}
