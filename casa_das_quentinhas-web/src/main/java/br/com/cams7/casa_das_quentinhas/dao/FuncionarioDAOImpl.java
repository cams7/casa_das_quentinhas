package br.com.cams7.casa_das_quentinhas.dao;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ENTREGADOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario_;
import br.com.cams7.casa_das_quentinhas.entity.Manutencao_;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;
import br.com.cams7.casa_das_quentinhas.entity.Usuario_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class FuncionarioDAOImpl extends AbstractDAO<Integer, Funcionario> implements FuncionarioDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Funcionario> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		final boolean IS_NULL = getIgnoredJoins() == null;

		boolean noneMatch = IS_NULL || getIgnoredJoins().stream().noneMatch(type -> type.equals(Usuario.class));
		if (noneMatch) {
			Join<Funcionario, Usuario> joinUsuario = (Join<Funcionario, Usuario>) from.fetch(Funcionario_.usuario,
					JoinType.INNER);
			fetchJoins.add(joinUsuario);
		}
		noneMatch = IS_NULL || getIgnoredJoins().stream().noneMatch(type -> type.equals(Empresa.class));
		if (noneMatch) {
			Join<Funcionario, Empresa> joinEmpresa = (Join<Funcionario, Empresa>) from.fetch(Funcionario_.empresa,
					JoinType.INNER);
			fetchJoins.add(joinEmpresa);
		}

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected List<From<?, ?>> getJoins(Root<Funcionario> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		final boolean IS_NULL = getIgnoredJoins() == null;

		boolean noneMatch = IS_NULL || getIgnoredJoins().stream().noneMatch(type -> type.equals(Usuario.class));
		if (noneMatch) {
			Join<Funcionario, Usuario> joinUsuario = (Join<Funcionario, Usuario>) from.join(Funcionario_.usuario,
					JoinType.INNER);
			fetchJoins.add(joinUsuario);
		}

		noneMatch = IS_NULL || getIgnoredJoins().stream().noneMatch(type -> type.equals(Empresa.class));
		if (noneMatch) {
			Join<Funcionario, Empresa> joinEmpresa = (Join<Funcionario, Empresa>) from.join(Funcionario_.empresa,
					JoinType.INNER);
			fetchJoins.add(joinEmpresa);
		}

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioByIdAndFuncoes(java.lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao[])
	 */
	@Override
	public Funcionario getFuncionarioByIdAndFuncoes(Integer id, Funcao... funcoes) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(ENTITY_TYPE);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		from.fetch(Funcionario_.usuario, JoinType.INNER);
		from.fetch(Funcionario_.empresa, JoinType.INNER);

		cq.select(from);

		cq.where(cb.equal(from.get(Funcionario_.id), id), cb.or(Arrays.asList(funcoes).stream()
				.map(funcao -> cb.equal(from.get(Funcionario_.funcao), funcao)).toArray(Predicate[]::new)));

		TypedQuery<Funcionario> tq = getEntityManager().createQuery(cq);

		try {
			Funcionario funcionario = tq.getSingleResult();
			return funcionario;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O funcionário (id:%s, função: [%s]) não foi encontrado...",
					id, Arrays.asList(funcoes).stream().map(funcao -> funcao.getDescricao())
							.collect(Collectors.joining(", "))));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#getFuncionarioByCpf(
	 * java.lang.String)
	 */
	@Override
	public Integer getFuncionarioIdByCpf(String cpf) {
		LOGGER.info("CPF : {}", cpf);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Funcionario_.id));

		cq.where(cb.equal(from.get(Funcionario_.cpf), cpf));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer funcionarioId = tq.getSingleResult();
			return funcionarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O id do funcionário (cpf: %s) não foi encontrado...", cpf));
		}
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

		try {
			Funcao funcao = tq.getSingleResult();
			return funcao;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("A função do funcionário (id: %s) não foi encontrada...", id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getUsuarioIdByFuncionarioId(java.lang.Integer)
	 */
	@Override
	public Integer getUsuarioIdByFuncionarioId(Integer funcionarioId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		cq.select(from.join(Funcionario_.usuarioCadastro, JoinType.INNER).get(Usuario_.id));

		cq.where(cb.equal(from.get(Funcionario_.id), funcionarioId));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(
					String.format("Do funcionário (id: %s), o id do usuário não foi encontrado...", funcionarioId));
		}
	}

	@Override
	public Object[] getUsuarioIdAndFuncionarioCadastroByFuncionarioId(Integer funcionarioId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);
		cq.select(cb.array(from.join(Funcionario_.usuarioCadastro, JoinType.INNER).get(Usuario_.id),
				from.get(Funcionario_.manutencao).get(Manutencao_.cadastro)));
		cq.where(cb.equal(from.get(Funcionario_.id), funcionarioId));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);

		try {
			Object[] funcionario = tq.getSingleResult();
			return funcionario;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format(
					"Do funcionário (id: %s), o id do usuário de cadastro e a data de cadastro do funcionário não foram encontrados...",
					funcionarioId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getEntregadoresByNomeOrCpfOrCelular(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getEntregadoresByNomeOrCpfOrCelular(String nomeOrCpfOrCelular) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Funcionario> from = cq.from(ENTITY_TYPE);

		cq.select(cb.array(from.get(Funcionario_.id), from.get(Funcionario_.nome), from.get(Funcionario_.cpf)));

		nomeOrCpfOrCelular = "%" + nomeOrCpfOrCelular.toLowerCase() + "%";

		cq.where(cb.and(
				cb.or(cb.like(cb.lower(from.get(Funcionario_.nome)), nomeOrCpfOrCelular),
						cb.like(from.get(Funcionario_.cpf), nomeOrCpfOrCelular),
						cb.like(from.get(Funcionario_.celular), nomeOrCpfOrCelular)),
				cb.equal(from.get(Funcionario_.funcao), ENTREGADOR)));

		cq.orderBy(cb.asc(from.get(Funcionario_.nome)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> funcionarios = tq.getResultList().stream()
				.collect(Collectors.toMap(funcionario -> (Integer) funcionario[0],
						funcionario -> Funcionario.getNomeWithCpf((String) funcionario[1], (String) funcionario[2])));

		return funcionarios;
	}

}
