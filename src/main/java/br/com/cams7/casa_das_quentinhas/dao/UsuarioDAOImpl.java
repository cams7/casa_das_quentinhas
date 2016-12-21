package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.model.Usuario;

@Repository
public class UsuarioDAOImpl extends AbstractDAO<Integer, Usuario> implements UsuarioDAO {

	static final Logger logger = LoggerFactory.getLogger(UsuarioDAOImpl.class);

	public Usuario findById(Integer id) {
		Usuario usuario = getByKey(id);
		if (usuario != null) {
			Hibernate.initialize(usuario.getAutorizacoes());
		}
		return usuario;
	}

	public Usuario findByEmail(String email) {
		logger.info("E-mail : {}", email);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("email", email));

		Usuario usuario = (Usuario) crit.uniqueResult();
		if (usuario != null)
			Hibernate.initialize(usuario.getAutorizacoes());

		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Usuario> usuarios = (List<Usuario>) criteria.list();

		// No need to fetch userProfiles since we are not showing them on list
		// page. Let them lazy load.
		// Uncomment below lines for eagerly fetching of userProfiles if you
		// want.
		/*
		 * for(User user : users){ Hibernate.initialize(user.getUserProfiles());
		 * }
		 */
		return usuarios;
	}

	public void save(Usuario usuario) {
		persist(usuario);
	}

	public void deleteById(Integer id) {
		// Criteria crit = createEntityCriteria();
		// crit.add(Restrictions.eq("id", id));
		// UsuarioEntity user = (UsuarioEntity) crit.uniqueResult();
		Usuario usuario = getByKey(id);
		delete(usuario);
	}

	@Override
	public List<Usuario> list(Integer offset, Integer maxResults) {
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = createEntityCriteria().setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 10).list();
		return usuarios;
	}

	@Override
	public Long count() {
		Long count = (Long) createEntityCriteria().setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}

}
