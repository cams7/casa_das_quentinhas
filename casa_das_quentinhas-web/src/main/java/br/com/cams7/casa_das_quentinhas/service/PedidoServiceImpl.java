/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class PedidoServiceImpl extends AbstractService<PedidoDAO, Pedido, Long> implements PedidoService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PedidoItemService itemService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getPedidoById(java.lang.
	 * Long)
	 */
	@Override
	public Pedido getPedidoById(Long id) {
		return getDao().getPedidoById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.PedidoService#persist(br.com.
	 * cams7.casa_das_quentinhas.model.Pedido, java.util.List)
	 */
	@Override
	public void persist(Pedido pedido, List<PedidoItem> itens) {
		Usuario usuario = new Usuario(usuarioService.getUsuarioIdByEmail(getUsername()));
		pedido.setUsuarioCadastro(usuario);

		Manutencao manutencao = new Manutencao();
		manutencao.setCadastro(new Date());
		manutencao.setAlteracao(new Date());

		pedido.setManutencao(manutencao);

		super.persist(pedido);

		itens.forEach(item -> {
			item.getId().setPedidoId(pedido.getId());
			itemService.persist(item);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.PedidoService#update(br.com.
	 * cams7.casa_das_quentinhas.model.Pedido, java.util.List)
	 */
	@Override
	public void update(Pedido pedido, List<PedidoItem> itens) {
		pedido.getManutencao().setAlteracao(new Date());

		super.update(pedido);

		/* int total = */ itemService.deleteByPedido(pedido.getId());

		itens.forEach(item -> {
			item.getId().setPedidoId(pedido.getId());
			itemService.persist(item);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.PedidoService#deleteItem(java.
	 * lang.Long, java.lang.Integer)
	 */
	@Override
	public void deleteItem(Long pedidoId, Integer produtoId) {
		itemService.delete(new PedidoItemPK(pedidoId, produtoId));
	}

}