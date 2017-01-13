/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/funcionario/report")
public class FuncionarioReportController extends AbstractReportController<Integer, Funcionario, FuncionarioService> {

	private final String PDF_VIEW = "funcionarioPdfView";

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView generatePdfReport(HttpServletRequest request) {
		getService().setIgnoredJoins(Empresa.class);
		return super.generatePdfReport(request);
	}

	@Override
	protected String getPdfView() {
		return PDF_VIEW;
	}
	

}
