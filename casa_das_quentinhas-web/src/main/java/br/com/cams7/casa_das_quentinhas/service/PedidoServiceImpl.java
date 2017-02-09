/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo.CLIENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao.PENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente.PESSOA_JURIDICA;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.AppInvalidDataException;
import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Manutencao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;
import br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class PedidoServiceImpl extends AbstractService<Long, Pedido, PedidoDAO> implements PedidoService {

	public static final Situacao CADASTRO_SITUACAO = PENDENTE;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private PedidoItemService itemService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.PedidoService#persist(br.com.
	 * cams7.casa_das_quentinhas.model.Pedido, java.util.List)
	 */
	@Override
	public void persist(Pedido pedido, List<PedidoItem> itens) {
		verificaSituacao(pedido.getSituacao());
		verificaQuantidadeAndCusto(pedido.getQuantidade(), pedido.getCusto(), itens);

		setEmpresa(pedido);

		Integer usuarioId = usuarioService.getUsuarioIdByEmail(getUsername());
		pedido.setUsuarioCadastro(new Usuario(usuarioId));

		pedido.setManutencao(new Manutencao(new Date(), new Date()));

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
	 * cams7.casa_das_quentinhas.model.Pedido, java.util.List, java.util.List)
	 */
	@Override
	public void update(Pedido pedido, List<PedidoItem> itens, List<PedidoItemPK> removedItens) {
		Long id = pedido.getId();
		verificaId(id);

		verificaQuantidadeAndCusto(pedido.getQuantidade(), pedido.getCusto(), itens);

		setEmpresa(pedido);

		Object[] array = getUsuarioIdAndPedidoCadastroByPedidoId(id);

		Integer usuarioId = (Integer) array[0];
		Date cadastro = (Date) array[1];

		pedido.setUsuarioCadastro(new Usuario(usuarioId));
		pedido.setManutencao(new Manutencao(cadastro, new Date()));

		super.update(pedido);

		removedItens.forEach(itemId -> {
			itemService.delete(itemId);
		});

		itens.stream().filter(item -> item.getId().getPedidoId() != null).forEach(item -> {
			item.getId().setPedidoId(pedido.getId());
			itemService.update(item);
		});

		itens.stream().filter(item -> item.getId().getPedidoId() == null).forEach(item -> {
			item.getId().setPedidoId(pedido.getId());
			itemService.persist(item);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Long id) {
		itemService.deleteByPedido(id);

		super.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getPedidoById(java.lang.
	 * Long)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Pedido getPedidoById(Long id) {
		return getDao().getPedidoById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getUsuarioIdByPedidoId(
	 * java.lang.Long)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioIdByPedidoId(Long pedidoId) {
		return getDao().getUsuarioIdByPedidoId(pedidoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#
	 * getUsuarioIdAndPedidoCadastroByPedidoId(java.lang.Long)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Object[] getUsuarioIdAndPedidoCadastroByPedidoId(Long pedidoId) {
		return getDao().getUsuarioIdAndPedidoCadastroByPedidoId(pedidoId);
	}

	/**
	 * Atribui a empresa caso o cliente seja uma pessoa juridica
	 * 
	 * @param pedido
	 */
	private void setEmpresa(Pedido pedido) {
		if (pedido.getTipoCliente() == PESSOA_JURIDICA) {
			Integer empresaId = pedido.getCliente().getId();

			verificaEmpresa(empresaId);

			pedido.setEmpresa(new Empresa(empresaId));
			pedido.setCliente(null);
		}
	}

	/**
	 * Verifica se a empresa é valida
	 * 
	 * @param empresaId
	 *            ID da empresa
	 */
	private void verificaEmpresa(Integer empresaId) {
		String errorMessage = String.format("A empresa (id: %s) não é válida...", empresaId);

		if (empresaId.equals(1))
			throw new AppInvalidDataException(errorMessage);

		Tipo tipo = empresaService.getEmpresaIipoById(empresaId);
		if (!CLIENTE.equals(tipo))
			throw new AppInvalidDataException(errorMessage);

	}

	private void verificaQuantidadeAndCusto(short quantidade, float custo, List<PedidoItem> itens) {
		if (quantidade != getQuantidadeTotal(itens))
			throw new AppInvalidDataException("A quantidade total não é valida");

		if (custo != getCustoTotal(itens))
			throw new AppInvalidDataException("O custo total não é valido");
	}

	/**
	 * Verifica, no cadastro, se a situação do pedido esta PENDENTE
	 * 
	 * @param situacao
	 */
	private void verificaSituacao(Situacao situacao) {
		if (!CADASTRO_SITUACAO.equals(situacao))
			throw new AppInvalidDataException("A situação do pedido não é valida");
	}

	/**
	 * @param itens
	 * @return Soma da quantidade total
	 */
	public static short getQuantidadeTotal(List<PedidoItem> itens) {
		return (short) itens.stream().mapToInt(i -> i.getQuantidade()).sum();
	}

	/**
	 * @param itens
	 * @return Soma dos valores da multiplação do custo do produto com sua
	 *         respectiva quantidade
	 */
	public static float getCustoTotal(List<PedidoItem> itens) {
		return (float) itens.stream().mapToDouble(i -> i.getCusto()).sum();
	}

}
