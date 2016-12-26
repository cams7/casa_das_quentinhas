package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
//import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Funcionario_;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

@Repository
public class FuncionarioDAOImpl extends AbstractDAO<Funcionario, Integer> implements FuncionarioDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioByFuncao(br.com.cams7.casa_das_quentinhas.model.Funcionario
	 * .Funcao)
	 */
	// @Override
	// public Set<Funcionario> getFuncionariosByFuncao(Funcao funcao) {
	// CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	// CriteriaQuery<Funcionario> cq = cb.createQuery(ENTITY_TYPE);
	//
	// Root<Funcionario> from = cq.from(ENTITY_TYPE);
	//
	// cq.select(from);
	// cq.where(cb.equal(from.get(Funcionario_.funcao), funcao));
	//
	// TypedQuery<Funcionario> tq = getEntityManager().createQuery(cq);
	//
	// Set<Funcionario> funcionario = new
	// HashSet<Funcionario>(tq.getResultList());
	//
	// return funcionario;
	// }

	@Override
	public List<Funcionario> search(SearchParams params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		@SuppressWarnings("unchecked")
		Join<Funcionario, Usuario> join = (Join<Funcionario, Usuario>) from.fetch(Funcionario_.usuario, JoinType.LEFT);

		TypedQuery<Funcionario> tq = getFilterAndPaginationAndSorting(cb, cq, join, params);
		List<Funcionario> funcionarios = tq.getResultList();

		return funcionarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getTotalElements(Map<String, Object> filters, String... globalFilters) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);
		Join<Funcionario, Usuario> join = from.join(Funcionario_.usuario, JoinType.LEFT);

		cq = (CriteriaQuery<Long>) getFilter(cb, cq, join, filters, globalFilters);
		long count = getCount(cb, cq, join);

		return count;
	}

	@Override
	public Funcionario getFuncionarioById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		from.join(Funcionario_.usuario);
		from.fetch(Funcionario_.usuario);

		cq.select(from);

		cq.where(cb.equal(from.get(Funcionario_.id), id));

		TypedQuery<Funcionario> tq = getEntityManager().createQuery(cq);
		Funcionario funcionario = tq.getSingleResult();

		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioFuncaoById(java.lang.Integer)
	 */
	@Override
	public Funcao getFuncionarioFuncaoById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcao> cq = cb.createQuery(Funcao.class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);
		cq.where(cb.equal(from.get(Funcionario_.id), id));
		cq.select(from.get(Funcionario_.funcao));

		TypedQuery<Funcao> tq = getEntityManager().createQuery(cq);
		Funcao funcao = tq.getSingleResult();

		return funcao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionariosByUsuarioId(java.lang.Integer)
	 */
	// @Override
	// public Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId) {
	// CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	// CriteriaQuery<Funcionario> cq = cb.createQuery(Funcionario.class);
	//
	// Root<Usuario> from = cq.from(Usuario.class);
	// cq.where(cb.equal(from.get(Usuario_.id), usuarioId));
	//
	// SetJoin<Usuario, Funcionario> join = from.join(Usuario_.funcionario);
	// cq.select(join);
	// TypedQuery<Funcionario> query = getEntityManager().createQuery(cq);
	//
	// Set<Funcionario> funcionarios = new
	// HashSet<Funcionario>(query.getResultList());
	// return funcionarios;
	// }

}
