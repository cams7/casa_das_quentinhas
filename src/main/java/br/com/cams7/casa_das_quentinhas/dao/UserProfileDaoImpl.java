package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, AutorizacaoEntity> implements UserProfileDao {

	public AutorizacaoEntity findById(int id) {
		return getByKey(id);
	}

	public AutorizacaoEntity findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (AutorizacaoEntity) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<AutorizacaoEntity> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<AutorizacaoEntity>) crit.list();
	}

}
