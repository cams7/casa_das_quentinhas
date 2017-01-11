package br.com.cams7.casa_das_quentinhas.facade;

import static br.com.cams7.casa_das_quentinhas.service.PedidoServiceImpl.getCustoTotal;
import static br.com.cams7.casa_das_quentinhas.service.PedidoServiceImpl.getQuantidadeTotal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import br.com.cams7.app.utils.AppInvalidDataException;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.service.PedidoItemService;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoItemFacade {

	@Autowired
	private PedidoItemService itemService;

	private List<PedidoItem> itens;
	private List<PedidoItemPK> removedItens;

	/**
	 * Inicializa a lista de itens na sessão do usuário
	 */
	public void initCreate() {
		itens = new ArrayList<>();
		removedItens = new ArrayList<>();
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void initShow() {
		itens = null;
		removedItens = null;

		itemService.setIgnoredJoins(Pedido.class);
	}

	/**
	 * @param params
	 * @param ignoredJoins
	 */
	@SuppressWarnings("unchecked")
	public void initUpdate(Long pedidoId) {
		itemService.setIgnoredJoins(Pedido.class);

		SearchParams params = new SearchParams(0, null, null, null, getFilterByPedidoId(pedidoId));

		itens = itemService.search(params);
		removedItens = new ArrayList<>();
	}

	/**
	 * Adiciona um item à lista de itens
	 * 
	 * @param item
	 *            Item de pedido
	 */
	public Pedido addItem(PedidoItem item) {
		if (itens == null)
			throw new AppInvalidDataException("A lista de itens de pedido não foi criada");

		Predicate<PedidoItem> predicate = i -> item.getId().getProdutoId().equals(i.getId().getProdutoId());

		if (itens.stream().anyMatch(predicate))
			itens.stream().filter(predicate).findFirst().ifPresent(i -> i.setQuantidade(item.getQuantidade()));
		else
			itens.add(item);

		return getPedido();
	}

	/**
	 * Busca um item pelo id do produdo na lista de itens
	 * 
	 * @param produtoId
	 *            ID do produto
	 * @return Item do pedido
	 */
	public PedidoItem getItem(Long pedidoId, Integer produtoId) {
		if (itens == null)
			return itemService.getItemById(pedidoId, produtoId);

		Predicate<PedidoItem> predicate = i -> produtoId.equals(i.getId().getProdutoId());
		if (!itens.stream().anyMatch(predicate))
			throw new AppNotFoundException(
					String.format("O item de pedido (produto:%s) não foi encontrado...", produtoId));

		return itens.stream().filter(predicate).findFirst().get();
	}

	/**
	 * Remove um item da lista de itens
	 * 
	 * @param produtoId
	 *            ID do pedido
	 * @return Pedido
	 */
	public Pedido removeItem(Integer produtoId) {
		if (itens == null)
			throw new AppInvalidDataException("A lista de itens de pedido não foi criada");

		Predicate<PedidoItem> predicate = item -> produtoId.equals(item.getId().getProdutoId());

		if (!itens.stream().anyMatch(predicate))
			throw new AppNotFoundException(
					String.format("O item de pedido (produto:%s) não foi encontrado...", produtoId));

		itens.stream().filter(predicate).filter(item -> item.getId().getPedidoId() != null).findFirst()
				.ifPresent(item -> removedItens.add(item.getId()));
		itens.removeIf(predicate);

		return getPedido();
	}

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @param firstPage
	 * @param sizePage
	 * @param sortField
	 * @param sortOrder
	 * @return Itens do pedido
	 */
	public List<PedidoItem> search(Long pedidoId, Integer firstPage, Short sizePage, String sortField,
			SortOrder sortOrder) {
		SearchParams params = new SearchParams(firstPage, sizePage, sortField, sortOrder,
				getFilterByPedidoId(pedidoId));

		if (itens == null)
			return itemService.search(params);

		return itens.stream().sorted(getSorted(params)).skip(params.getFirstPage()).limit(params.getSizePage())
				.collect(Collectors.toList());

	}

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @return Total de itens do pedido
	 */
	public long getTotalElements(Long pedidoId) {
		if (itens == null)
			return itemService.getTotalElements(getFilterByPedidoId(pedidoId));

		return itens.size();
	}

	public List<PedidoItem> getItens() {
		return itens;
	}

	/**
	 * @return IDs dos itens removidos
	 */
	public List<PedidoItemPK> getRemovedItens() {
		return removedItens;
	}

	/**
	 * @return Pedido
	 */
	private Pedido getPedido() {
		return new Pedido(getQuantidadeTotal(itens), getCustoTotal(itens));
	}

	/**
	 * @param pedidoId
	 * @return
	 */
	private Map<String, Object> getFilterByPedidoId(Long pedidoId) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("id.pedidoId", pedidoId);
		return filters;
	}

	/**
	 * @param params
	 *            Parametros
	 * @return
	 */
	private Comparator<PedidoItem> getSorted(SearchParams params) {
		Comparator<PedidoItem> comparator = (item1, item2) -> {
			int compareTo = 0;

			switch (params.getSortField()) {
			case "quantidade":
				switch (params.getSortOrder()) {
				case ASCENDING:
					compareTo = item1.getQuantidade().compareTo(item2.getQuantidade());
					break;
				case DESCENDING:
					compareTo = item2.getQuantidade().compareTo(item1.getQuantidade());
					break;
				default:
					break;
				}
				break;
			case "produto.nome":
				switch (params.getSortOrder()) {
				case ASCENDING:
					compareTo = item1.getProduto().getNome().compareTo(item2.getProduto().getNome());
					break;
				case DESCENDING:
					compareTo = item2.getProduto().getNome().compareTo(item1.getProduto().getNome());
					break;
				default:
					break;
				}
				break;
			case "produto.tamanho":
				switch (params.getSortOrder()) {
				case ASCENDING:
					compareTo = item1.getProduto().getTamanho().compareTo(item2.getProduto().getTamanho());
					break;
				case DESCENDING:
					compareTo = item2.getProduto().getTamanho().compareTo(item1.getProduto().getTamanho());
					break;
				default:
					break;
				}
				break;
			case "produto.custo":
				switch (params.getSortOrder()) {
				case ASCENDING:
					compareTo = item1.getProduto().getCusto().compareTo(item2.getProduto().getCusto());
					break;
				case DESCENDING:
					compareTo = item2.getProduto().getCusto().compareTo(item1.getProduto().getCusto());
					break;
				default:
					break;
				}
				break;
			default:
				switch (params.getSortOrder()) {
				case ASCENDING:
					compareTo = item1.getId().getProdutoId().compareTo(item2.getId().getProdutoId());
					break;
				case DESCENDING:
					compareTo = item2.getId().getProdutoId().compareTo(item1.getId().getProdutoId());
					break;
				default:
					break;
				}
				break;
			}

			return compareTo;
		};
		return comparator;
	}

}
