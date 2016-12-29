package br.com.cams7.casa_das_quentinhas.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

@Repository
public class UsuarioDAOImpl extends AbstractDAO<Usuario, Integer> implements UsuarioDAO {

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
			LOGGER.warn("E-mail not found...");
		}

		return null;
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
			LOGGER.warn("E-mail not found...");
		}

		return null;
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
		String senhaCriptografada = tq.getSingleResult();

		return senhaCriptografada;
	}

}
