package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;
import br.com.cams7.casa_das_quentinhas.entity.Usuario_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class UsuarioDAOImpl extends AbstractDAO<Integer, Usuario> implements UsuarioDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Usuario> from) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected List<From<?, ?>> getJoins(Root<Usuario> from) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#updateEmail(java.lang.
	 * Integer, java.lang.String)
	 */
	@Override
	public int updateEmailAndSenha(Integer id, String email, String senha) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaUpdate<Usuario> cu = cb.createCriteriaUpdate(ENTITY_TYPE);

		Root<Usuario> from = cu.from(ENTITY_TYPE);

		cu.where(cb.equal(from.get(Usuario_.id), id));

		cu.set(Usuario_.email, email);
		if (senha != null)
			cu.set(Usuario_.senhaCriptografada, senha);

		int updated = getEntityManager().createQuery(cu).executeUpdate();

		return updated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioByEmail(java.
	 * lang.String)
	 */
	@Override
	public Usuario getUsuarioByEmail(String email) {
		LOGGER.info("E-mail : {}", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Usuario> from = cq.from(ENTITY_TYPE);

		cq.select(from);

		cq.where(cb.equal(from.get(Usuario_.email), email));

		TypedQuery<Usuario> tq = getEntityManager().createQuery(cq);

		try {
			Usuario usuario = tq.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O usuário (email: %s) não foi encontrado...", email));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioIdByEmail(java.
	 * lang.String)
	 */
	@Override
	public Integer getUsuarioIdByEmail(String email) {
		LOGGER.info("E-mail : {}", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Usuario> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Usuario_.id));

		cq.where(cb.equal(from.get(Usuario_.email), email));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O id do usuário (email: %s) não foi encontrado...", email));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioSenhaById(java.
	 * lang.Integer)
	 */
	@Override
	public String getUsuarioSenhaById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);

		Root<Usuario> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Usuario_.senhaCriptografada));

		cq.where(cb.equal(from.get(Usuario_.id), id));

		TypedQuery<String> tq = getEntityManager().createQuery(cq);

		try {
			String senhaCriptografada = tq.getSingleResult();
			return senhaCriptografada;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("A senha do usuário (id: %s) não foi encontrada...", id));
		}
	}

}
