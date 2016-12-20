package br.com.cams7.casa_das_quentinhas.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;

@Repository
public class UsuarioRepositoryImpl extends AbstractRepository<Integer, UsuarioEntity> implements UsuarioRepository {

	static final Logger logger = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);

	public UsuarioEntity findById(Integer id) {
		UsuarioEntity usuario = getByKey(id);
		if (usuario != null) {
			Hibernate.initialize(usuario.getAutorizacoes());
		}
		return usuario;
	}

	public UsuarioEntity findByEmail(String email) {
		logger.info("E-mail : {}", email);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("email", email));

		UsuarioEntity usuario = (UsuarioEntity) crit.uniqueResult();
		if (usuario != null)
			Hibernate.initialize(usuario.getAutorizacoes());

		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioEntity> findAll() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<UsuarioEntity> usuarios = (List<UsuarioEntity>) criteria.list();

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

	public void save(UsuarioEntity usuario) {
		persist(usuario);
	}

	public void deleteById(Integer id) {
		// Criteria crit = createEntityCriteria();
		// crit.add(Restrictions.eq("id", id));
		// UsuarioEntity user = (UsuarioEntity) crit.uniqueResult();
		UsuarioEntity usuario = getByKey(id);
		delete(usuario);
	}

}
