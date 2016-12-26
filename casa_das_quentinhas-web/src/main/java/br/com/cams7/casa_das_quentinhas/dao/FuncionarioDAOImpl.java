package br.com.cams7.casa_das_quentinhas.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Funcionario_;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

@Repository
public class FuncionarioDAOImpl extends AbstractDAO<Funcionario, Integer> implements FuncionarioDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioByFuncao(br.com.cams7.casa_das_quentinhas.model.Funcionario
	 * .Funcao)
	 */
	@Override
	public Funcionario getFuncionarioByFuncao(Funcao funcao) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		cq.select(from);
		cq.where(cb.equal(from.get(Funcionario_.funcao), funcao));

		TypedQuery<Funcionario> tq = getEntityManager().createQuery(cq);

		Funcionario funcionario = tq.getSingleResult();

		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionariosByUsuarioId(java.lang.Integer)
	 */
	@Override
	public Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(Funcionario.class);

		Root<Usuario> from = cq.from(Usuario.class);
		cq.where(cb.equal(from.get(Usuario_.id), usuarioId));

		SetJoin<Usuario, Funcionario> join = from.join(Usuario_.funcionarios);
		cq.select(join);
		TypedQuery<Funcionario> query = getEntityManager().createQuery(cq);

		Set<Funcionario> funcionarios = new HashSet<Funcionario>(query.getResultList());
		return funcionarios;
	}

}
