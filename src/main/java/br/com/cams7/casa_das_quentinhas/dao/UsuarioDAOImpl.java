package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
//import javax.persistence.criteria.SetJoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;
import br.com.cams7.casa_das_quentinhas.utils.SearchParams;

@Repository
public class UsuarioDAOImpl extends AbstractDAO<Usuario, Integer> implements UsuarioDAO {

	static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDAOImpl.class);

	public Usuario findById(Integer id) {
		// CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		// CriteriaQuery<Autorizacao> cq = cb.createQuery(Autorizacao.class);
		//
		// Root<Usuario> from = cq.from(Usuario.class);
		// cq.where(cb.equal(from.get(Usuario_.id), id));
		//
		// SetJoin<Usuario, Autorizacao> join =
		// from.join(Usuario_.autorizacoes);
		// cq.select(join);
		// TypedQuery<Autorizacao> query = getEntityManager().createQuery(cq);
		//
		// Set<Autorizacao> autorizacoes = new
		// HashSet<Autorizacao>(query.getResultList());

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Usuario> from = cq.from(ENTITY_TYPE);
		cq.where(cb.equal(from.get(Usuario_.id), id));

		from.join(Usuario_.autorizacoes);
		from.fetch(Usuario_.autorizacoes);

		cq.select(from);

		TypedQuery<Usuario> tq = getEntityManager().createQuery(cq);
		Usuario usuario = tq.getSingleResult();

		return usuario;
	}

	public Usuario findByEmail(String email) {
		LOGGER.info("E-mail : {}", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Usuario> from = cq.from(ENTITY_TYPE);
		cq.where(cb.equal(from.get(Usuario_.email), email));
		cq.select(from);

		TypedQuery<Usuario> tq = getEntityManager().createQuery(cq);

		try {
			Usuario usuario = tq.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			LOGGER.warn("E-mail not found...");
		}

		return null;
	}

	public List<Usuario> findAll() {
		List<Usuario> usuarios = super.getAll();

		return usuarios;
	}

	public void save(Usuario usuario) {
		persist(usuario);
	}

	public void deleteById(Integer id) {
		Usuario usuario = getByKey(id);
		delete(usuario);
	}

	@Override
	public List<Usuario> list(Integer offset, Integer maxResults) {
		SearchParams params = new SearchParams(offset, maxResults.shortValue(), null, null, null);

		List<Usuario> usuarios = super.search(params);
		return usuarios;
	}

	@Override
	public Long count() {
		Long count = super.count();
		return count;
	}

}
