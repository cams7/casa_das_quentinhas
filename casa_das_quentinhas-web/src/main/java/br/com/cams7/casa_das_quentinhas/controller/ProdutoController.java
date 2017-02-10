/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.SearchParams;
import br.com.cams7.app.SearchParams.SortOrder;
import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractBeanController;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.Produto;
import br.com.cams7.casa_das_quentinhas.entity.Produto.Tamanho;
import br.com.cams7.casa_das_quentinhas.service.PedidoItemService;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + ProdutoController.MODEL_NAME)
@SessionAttributes("produtoTamanhos")
public class ProdutoController extends AbstractBeanController<Integer, Produto, ProdutoService> {

	public static final String MODEL_NAME = "produto";
	public static final String LIST_NAME = "produtos";

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
		// Carrega os pedidos
		loadPedidos(id, model, 0, "id.pedidoId", SortOrder.DESCENDING);

		return super.show(id, model);
	}

	@GetMapping(value = "/{produtoId}/pedidos")
	@ResponseStatus(OK)
	public String pedidos(@PathVariable Integer produtoId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {
		loadPedidos(produtoId, model, offset, sortField, SortOrder.get(sortOrder));

		return "produto_pedidos";
	}

	/**
	 * Possiveis tamanhos de produtos
	 */
	@ModelAttribute("produtoTamanhos")
	public Tamanho[] initializeTamanhos() {
		return Tamanho.values();
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, "custo", new MoneyEditor());
	}

	@Override
	protected String getModelName() {
		return MODEL_NAME;
	}

	@Override
	protected String getListName() {
		return LIST_NAME;
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "custo" };
	}

	@Override
	protected Produto getEntity(Integer id) {
		return getService().getProdutoById(id);
	}

	@Override
	protected String getDeleteMessage() {
		return "O produto foi removido com sucesso.";
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

	@Override
	protected String getStoreSucessMessage() {
		return "O produto foi cadastrado com sucesso!";
	}

	@Override
	protected String getUpdateSucessMessage(Produto produto) {
		return String.format("Os dados do produto (%s) foram atualizados com sucesso!", produto.getNomeWithTamanho());
	}

}
