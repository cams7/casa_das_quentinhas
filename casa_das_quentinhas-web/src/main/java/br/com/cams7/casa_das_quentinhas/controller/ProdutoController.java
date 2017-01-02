/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Produto;
import br.com.cams7.casa_das_quentinhas.model.Produto.Tamanho;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/produto")
@SessionAttributes("produtoTamanhos")
public class ProdutoController extends AbstractController<ProdutoService, Produto, Integer> {

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, "custo", new MoneyEditor());
	}

	/**
	 * Possiveis tamanhos de produtos
	 */
	@ModelAttribute("produtoTamanhos")
	public Tamanho[] initializeTamanhos() {
		return Tamanho.values();
	}

	@Override
	protected String getEntityName() {
		return "produto";
	}

	@Override
	protected String getListName() {
		return "produtos";
	}

	@Override
	protected String getMainPage() {
		return "produto";
	}

	@Override
	protected String getIndexTilesPage() {
		return "produto_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "produto_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "produto_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "produto_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "produto_list";
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "custo" };
	}

	@Override
	protected Produto getEntity(Integer id) {
		return getService().getProdutoById(id);
	}

}
