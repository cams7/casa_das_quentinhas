package br.com.cams7.casa_das_quentinhas.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao_;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

@Repository
public class AutorizacaoDAOImpl extends AbstractDAO<Autorizacao, Integer> implements AutorizacaoDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO#getUsuarioByPapel(
	 * java.lang.String)
	 */
	@Override
	public Autorizacao getAutorizacaoByPapel(String papel) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Autorizacao> cq = cb.createQuery(ENTITY_TYPE);

		Root<Autorizacao> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Autorizacao_.papel), papel));

		TypedQuery<Autorizacao> tq = getEntityManager().createQuery(cq);

		Autorizacao autorizacao = tq.getSingleResult();

		return autorizacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO#
	 * getAutorizacoesByUsuarioId(java.lang.Integer)
	 */
	@Override
	public Set<Autorizacao> getAutorizacoesByUsuarioId(Integer usuarioId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Autorizacao> cq = cb.createQuery(Autorizacao.class);

		Root<Usuario> from = cq.from(Usuario.class);
		cq.where(cb.equal(from.get(Usuario_.id), usuarioId));

		SetJoin<Usuario, Autorizacao> join = from.join(Usuario_.autorizacoes);
		cq.select(join);
		TypedQuery<Autorizacao> query = getEntityManager().createQuery(cq);

		Set<Autorizacao> autorizacoes = new HashSet<Autorizacao>(query.getResultList());
		return autorizacoes;
	}

}
