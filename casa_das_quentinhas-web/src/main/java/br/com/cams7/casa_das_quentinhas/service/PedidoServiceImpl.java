/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#persist(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void persist(Pedido pedido) {
		Usuario usuario = new Usuario(usuarioService.getUsuarioIdByEmail(getUsername()));
		pedido.setUsuarioCadastro(usuario);

		Manutencao manutencao = new Manutencao();
		manutencao.setCadastro(new Date());
		manutencao.setAlteracao(new Date());

		pedido.setManutencao(manutencao);

		super.persist(pedido);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Pedido pedido) {
		pedido.getManutencao().setAlteracao(new Date());

		super.update(pedido);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getPedidoById(java.lang.
	 * Long)
	 */
	@Override
	public Pedido getPedidoById(Long id) {
		Pedido pedido = getDao().getPedidoById(id);
		return pedido;
	}

}
