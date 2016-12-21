package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Acesso;

@Repository
@Transactional
public class AcessoDAOImpl extends AbstractDAO<String, Acesso>
		implements PersistentTokenRepository {

	static final Logger logger = LoggerFactory.getLogger(AcessoDAOImpl.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		logger.info("Creating Token for user : {}", token.getUsername());
		Acesso acesso = new Acesso();
		acesso.setUsername(token.getUsername());
		acesso.setId(token.getSeries());
		acesso.setToken(token.getTokenValue());
		acesso.setUltimoAcesso(token.getDate());
		persist(acesso);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		logger.info("Fetch Token if any for seriesId : {}", seriesId);
		try {
			Criteria crit = createEntityCriteria();
			crit.add(Restrictions.eq("series", seriesId));
			Acesso acesso = (Acesso) crit.uniqueResult();

			return new PersistentRememberMeToken(acesso.getUsername(), acesso.getId(), acesso.getToken(),
					acesso.getUltimoAcesso());
		} catch (Exception e) {
			logger.info("Token not found...");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String username) {
		logger.info("Removing Token if any for user : {}", username);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", username));
		Acesso acesso = (Acesso) crit.uniqueResult();
		if (acesso != null) {
			logger.info("rememberMe was selected");
			delete(acesso);
		}

	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		logger.info("Updating Token for seriesId : {}", seriesId);
		Acesso acesso = getByKey(seriesId);
		acesso.setToken(tokenValue);
		acesso.setUltimoAcesso(lastUsed);
		update(acesso);
	}

}
