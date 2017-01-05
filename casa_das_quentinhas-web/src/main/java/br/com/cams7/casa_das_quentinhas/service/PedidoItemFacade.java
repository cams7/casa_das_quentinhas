package br.com.cams7.casa_das_quentinhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoItemFacade {

	@Autowired
	private PedidoItemService itemService;

	private List<PedidoItem> itens;
	private List<PedidoItemPK> removeds;

	/**
	 * Inicializa a lista de itens na sessão do usuário
	 */
	public void init() {
		itens = new ArrayList<>();
		removeds = new ArrayList<>();
	}

	/**
	 * Adiciona um item à lista de itens
	 * 
	 * @param item
	 *            Item de pedido
	 */
	public Pedido addItem(PedidoItem item) {

		Predicate<PedidoItem> predicate = i -> item.getId().getProdutoId().equals(i.getId().getProdutoId());

		if (itens.stream().anyMatch(predicate))
			itens.stream().filter(predicate).findFirst().ifPresent(i -> i.setQuantidade(item.getQuantidade()));
		else
			itens.add(item);

		short quantidade = (short) itens.stream().mapToInt(i -> i.getQuantidade()).sum();
		float custo = (float) itens.stream().mapToDouble(i -> i.getQuantidade() * i.getProduto().getCusto()).sum();

		return new Pedido(quantidade, custo);
	}

	/**
	 * Retorna o item da lista de itens
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 * @param produtoId
	 *            ID do produto
	 * @return
	 */
	public PedidoItem getItem(Long pedidoId, Integer produtoId) {
		if (pedidoId == 0L) {
			PedidoItem item = itens.stream().filter(i -> produtoId.equals(i.getId().getProdutoId())).findFirst().get();
			return item;
		}

		return itemService.getItemById(pedidoId, produtoId);
	}

	/**
	 * Remove um item da lista de itens
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 * @param produtoId
	 *            ID do produto
	 */
	public Pedido removeItem(Long pedidoId, Integer produtoId) {
		itens.removeIf(item -> produtoId.equals(item.getId().getProdutoId()));

		if (pedidoId != 0L)
			removeds.add(new PedidoItemPK(pedidoId, produtoId));

		short quantidade = (short) itens.stream().mapToInt(i -> i.getQuantidade()).sum();
		float custo = (float) itens.stream().mapToDouble(i -> i.getQuantidade() * i.getProduto().getCusto()).sum();

		return new Pedido(quantidade, custo);
	}

	/**
	 * Remove a lista de itens na sessão do usuário
	 */
	public void removeItens() {
		itens = null;
		removeds = null;
	}

	public List<PedidoItem> searchWithIgnoredJoins(SearchParams params,
			@SuppressWarnings("unchecked") Class<? extends AbstractEntity<?>>... ignoredJoins) {
		if ((Long) params.getFilters().get("id.pedidoId") == 0L) {
			return itens.stream().sorted((i1, i2) -> {
				int compareTo = 0;

				switch (params.getSortField()) {
				case "quantidade":
					switch (params.getSortOrder()) {
					case ASCENDING:
						compareTo = i1.getQuantidade().compareTo(i2.getQuantidade());
						break;
					case DESCENDING:
						compareTo = i2.getQuantidade().compareTo(i1.getQuantidade());
						break;
					default:
						break;
					}
					break;
				case "produto.nome":
					switch (params.getSortOrder()) {
					case ASCENDING:
						compareTo = i1.getProduto().getNome().compareTo(i2.getProduto().getNome());
						break;
					case DESCENDING:
						compareTo = i2.getProduto().getNome().compareTo(i1.getProduto().getNome());
						break;
					default:
						break;
					}
					break;
				case "produto.tamanho":
					switch (params.getSortOrder()) {
					case ASCENDING:
						compareTo = i1.getProduto().getTamanho().compareTo(i2.getProduto().getTamanho());
						break;
					case DESCENDING:
						compareTo = i2.getProduto().getTamanho().compareTo(i1.getProduto().getTamanho());
						break;
					default:
						break;
					}
					break;
				default:
					switch (params.getSortOrder()) {
					case ASCENDING:
						compareTo = i1.getId().getProdutoId().compareTo(i2.getId().getProdutoId());
						break;
					case DESCENDING:
						compareTo = i2.getId().getProdutoId().compareTo(i1.getId().getProdutoId());
						break;
					default:
						break;
					}
					break;
				}

				return compareTo;
			}).skip(params.getFirstPage()).limit(params.getSizePage()).collect(Collectors.toList());
		}

		itemService.setIgnoredJoins(ignoredJoins);
		return itemService.search(params);
	}

	public long getTotalElements(Map<String, Object> filters, String... globalFilters) {
		if ((Long) filters.get("id.pedidoId") == 0L)
			return itens.size();

		return itemService.getTotalElements(filters, globalFilters);
	}
}
