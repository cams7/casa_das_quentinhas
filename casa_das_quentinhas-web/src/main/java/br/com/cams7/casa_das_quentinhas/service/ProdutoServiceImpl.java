/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO;
import br.com.cams7.casa_das_quentinhas.entity.Manutencao;
import br.com.cams7.casa_das_quentinhas.entity.Produto;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class ProdutoServiceImpl extends AbstractService<Integer, Produto, ProdutoDAO> implements ProdutoService {

	@Autowired
	private UsuarioService usuarioService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#persist(br.com.cams7.app.model.
	 * AbstractEntity, java.lang.String)
	 */
	@Override
	public void persist(Produto produto) {
		Integer usuarioId = usuarioService.getUsuarioIdByEmail(getUsername());
		produto.setUsuarioCadastro(new Usuario(usuarioId));

		produto.setManutencao(new Manutencao(new Date(), new Date()));

		super.persist(produto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Produto produto) {
		Integer id = produto.getId();
		verificaId(id);

		Object[] array = getUsuarioIdAndProdutoCadastroByProdutoId(id);

		Integer usuarioId = (Integer) array[0];
		Date cadastro = (Date) array[1];

		produto.setUsuarioCadastro(new Usuario(usuarioId));
		produto.setManutencao(new Manutencao(cadastro, new Date()));

		super.update(produto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO#getProdutoById(java.lang.
	 * Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Produto getProdutoById(Integer id) {
		return getDao().getProdutoById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO#getUsuarioIdByProdutoId(
	 * java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioIdByProdutoId(Integer produtoId) {
		return getDao().getUsuarioIdByProdutoId(produtoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO#
	 * getUsuarioIdAndProdutoCadastroByProdutoId(java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Object[] getUsuarioIdAndProdutoCadastroByProdutoId(Integer produtoId) {
		return getDao().getUsuarioIdAndProdutoCadastroByProdutoId(produtoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO#getProdutosByNomeOrId(
	 * java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<Integer, String> getProdutosByNomeOrCusto(String nomeOrCusto) {
		return getDao().getProdutosByNomeOrCusto(nomeOrCusto);
	}

}
