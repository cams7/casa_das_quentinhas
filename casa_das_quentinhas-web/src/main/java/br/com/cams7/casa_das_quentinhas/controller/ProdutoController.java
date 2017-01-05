/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.Produto;
import br.com.cams7.casa_das_quentinhas.model.Produto.Tamanho;
import br.com.cams7.casa_das_quentinhas.service.PedidoItemService;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/produto")
@SessionAttributes("produtoTamanhos")
public class ProdutoController extends AbstractController<ProdutoService, Produto, Integer> {

	@Autowired
	private PedidoItemService itemService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#show(java.io.Serializable,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String show(@PathVariable Integer id, ModelMap model) {

		loadPedidos(id, model, 0, "id.pedidoId", SortOrder.DESCENDING);

		return super.show(id, model);
	}

	@GetMapping(value = "/{produtoId}/pedidos")
	public String pedidos(@PathVariable Integer produtoId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		loadPedidos(produtoId, model, offset, sortField, SortOrder.get(sortOrder));

		return "produto_pedidos";
	}

	@SuppressWarnings("unchecked")
	private void loadPedidos(Integer produtoId, ModelMap model, Integer offset, String sortField, SortOrder sortOrder) {
		final short MAX_RESULTS = 5;

		Map<String, Object> filters = new HashMap<>();
		filters.put("id.produtoId", produtoId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		itemService.setIgnoredJoins(Produto.class);
		List<PedidoItem> itens = itemService.search(params);
		long count = itemService.getTotalElements(filters);

		model.addAttribute("itens", itens);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

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
