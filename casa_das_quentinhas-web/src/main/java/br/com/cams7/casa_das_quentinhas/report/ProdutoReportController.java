/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
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

	@Override
	protected List<Produto> getEntities(List<Produto> produtos) {
		return produtos.stream().map(produto -> {
			List<PedidoItem> itens = getService().getItensIdByProdutoId(produto.getId());
			produto.setItens(itens);
			return produto;
		}).collect(Collectors.toList());
	}

}
