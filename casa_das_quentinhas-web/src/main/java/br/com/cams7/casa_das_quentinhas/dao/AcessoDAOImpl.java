package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Root;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.entity.Acesso;
import br.com.cams7.casa_das_quentinhas.entity.Acesso_;

/**
 * @author César Magalhães
 *
 */
@Repository
@Transactional
public class AcessoDAOImpl extends AbstractDAO<String, Acesso> implements PersistentTokenRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Acesso> from) {
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
	protected List<From<?, ?>> getJoins(Root<Acesso> from) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.rememberme.
	 * PersistentTokenRepository#createNewToken(org.springframework.security.web
	 * .authentication.rememberme.PersistentRememberMeToken)
	 */
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		LOGGER.info("Criando o token para o usuário : {}", token.getUsername());

		Acesso acesso = new Acesso();
		acesso.setEmail(token.getUsername());
		acesso.setId(token.getSeries());
		acesso.setToken(token.getTokenValue());
		acesso.setUltimoAcesso(token.getDate());

		persist(acesso);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.rememberme.
	 * PersistentTokenRepository#getTokenForSeries(java.lang.String)
	 */
	@Override
	public PersistentRememberMeToken getTokenForSeries(String id) {
		LOGGER.info("Buscando token se for o acesso (id: {})", id);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Acesso> cq = cb.createQuery(ENTITY_TYPE);

		Root<Acesso> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Acesso_.id), id));

		TypedQuery<Acesso> tq = getEntityManager().createQuery(cq);

		try {
			Acesso acesso = tq.getSingleResult();

			return new PersistentRememberMeToken(acesso.getEmail(), acesso.getId(), acesso.getToken(),
					acesso.getUltimoAcesso());
		} catch (NoResultException e) {
			LOGGER.warn("O acesso (id: {}) não foi encontrado...", id);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.rememberme.
	 * PersistentTokenRepository#removeUserTokens(java.lang.String)
	 */
	@Override
	public void removeUserTokens(String email) {
		LOGGER.info("Removendo token se for o usuário (email: {})", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);

		Root<Acesso> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Acesso_.id));
		cq.where(cb.equal(from.get(Acesso_.email), email));

		TypedQuery<String> tq = getEntityManager().createQuery(cq);

		tq.getResultList().forEach(id -> {
			delete(id);
			LOGGER.warn("O acesso (id: {}) foi removido...", id);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.rememberme.
	 * PersistentTokenRepository#updateToken(java.lang.String, java.lang.String,
	 * java.util.Date)
	 */
	@Override
	public void updateToken(String id, String tokenValue, Date lastUsed) {
		LOGGER.info("Atualizando o token para o acesso (id: {})", id);

		try {
			Acesso acesso = getById(id);
			acesso.setToken(tokenValue);
			acesso.setUltimoAcesso(lastUsed);

			update(acesso);
		} catch (AppNotFoundException e) {
			LOGGER.warn("O acesso (id: {}) não foi encontrado...", id);
		}
	}

}
