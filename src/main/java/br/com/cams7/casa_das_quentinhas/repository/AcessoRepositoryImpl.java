package br.com.cams7.casa_das_quentinhas.repository;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.entity.AcessoEntity;
import br.com.cams7.casa_das_quentinhas.repository.AbstractRepository;

@Repository
@Transactional
public class AcessoRepositoryImpl extends AbstractRepository<String, AcessoEntity>
		implements PersistentTokenRepository {

	static final Logger logger = LoggerFactory.getLogger(AcessoRepositoryImpl.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		logger.info("Creating Token for user : {}", token.getUsername());
		AcessoEntity acesso = new AcessoEntity();
		acesso.setUsername(token.getUsername());
		acesso.setSeries(token.getSeries());
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
			AcessoEntity acesso = (AcessoEntity) crit.uniqueResult();

			return new PersistentRememberMeToken(acesso.getUsername(), acesso.getSeries(), acesso.getToken(),
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
		AcessoEntity acesso = (AcessoEntity) crit.uniqueResult();
		if (acesso != null) {
			logger.info("rememberMe was selected");
			delete(acesso);
		}

	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		logger.info("Updating Token for seriesId : {}", seriesId);
		AcessoEntity acesso = getByKey(seriesId);
		acesso.setToken(tokenValue);
		acesso.setUltimoAcesso(lastUsed);
		update(acesso);
	}

}
