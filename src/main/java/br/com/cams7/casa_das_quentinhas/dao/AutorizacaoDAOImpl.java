package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

@Repository
public class AutorizacaoDAOImpl extends AbstractDAO<Integer, Autorizacao>
		implements AutorizacaoDAO {

	public Autorizacao findById(Integer id) {
		return getByKey(id);
	}

	public Autorizacao findByPapel(String papel) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("papel", papel));
		return (Autorizacao) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Autorizacao> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("papel"));
		return (List<Autorizacao>) crit.list();
	}

}
