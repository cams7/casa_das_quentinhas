/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Produto;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/produto/report")
public class ProdutoReportController extends AbstractReportController<Integer, Produto, ProdutoService> {

	private final String PDF_VIEW = "produtoPdfView";

	@Override
	protected String getPdfView() {
		return PDF_VIEW;
	}

}
