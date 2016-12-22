package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.model.Acesso;
import br.com.cams7.casa_das_quentinhas.model.Acesso_;

@Repository
@Transactional
public class AcessoDAOImpl extends AbstractDAO<Acesso, String> implements PersistentTokenRepository {

	static final Logger LOGGER = LoggerFactory.getLogger(AcessoDAOImpl.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		LOGGER.info("Creating Token for user : {}", token.getUsername());

		Acesso acesso = new Acesso();
		acesso.setUsername(token.getUsername());
		acesso.setId(token.getSeries());
		acesso.setToken(token.getTokenValue());
		acesso.setUltimoAcesso(token.getDate());

		persist(acesso);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		LOGGER.info("Fetch Token if any for seriesId : {}", seriesId);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Acesso> cq = cb.createQuery(ENTITY_TYPE);

		Root<Acesso> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Acesso_.id), seriesId));

		TypedQuery<Acesso> tq = getEntityManager().createQuery(cq);

		try {
			Acesso acesso = tq.getSingleResult();

			return new PersistentRememberMeToken(acesso.getUsername(), acesso.getId(), acesso.getToken(),
					acesso.getUltimoAcesso());
		} catch (NoResultException e) {
			LOGGER.info("Token not found...");
		}

		return null;
	}

	@Override
	public void removeUserTokens(String username) {
		LOGGER.info("Removing Token if any for user : {}", username);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Acesso> cq = cb.createQuery(ENTITY_TYPE);

		Root<Acesso> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Acesso_.username), username));

		TypedQuery<Acesso> tq = getEntityManager().createQuery(cq);

		try {
			Acesso acesso = tq.getSingleResult();
			delete(acesso);

			LOGGER.info("rememberMe was selected");
		} catch (NoResultException e) {
		}
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		LOGGER.info("Updating Token for seriesId : {}", seriesId);

		Acesso acesso = getByKey(seriesId);
		acesso.setToken(tokenValue);
		acesso.setUltimoAcesso(lastUsed);

		update(acesso);
	}

}
