package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao_;

@Repository
public class AutorizacaoDAOImpl extends AbstractDAO<Autorizacao, Integer> implements AutorizacaoDAO {

	public Autorizacao findById(Integer id) {
		Autorizacao autorizacao = getByKey(id);

		return autorizacao;
	}

	public Autorizacao findByPapel(String papel) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Autorizacao> cq = cb.createQuery(ENTITY_TYPE);

		Root<Autorizacao> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Autorizacao_.papel), papel));

		TypedQuery<Autorizacao> tq = getEntityManager().createQuery(cq);

		Autorizacao autorizacao = tq.getSingleResult();

		return autorizacao;
	}

	public List<Autorizacao> findAll() {
		List<Autorizacao> autorizacoes = super.getAll();
		return autorizacoes;
	}

}
