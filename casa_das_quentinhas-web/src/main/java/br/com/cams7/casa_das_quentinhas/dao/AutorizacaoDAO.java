package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Set;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

public interface AutorizacaoDAO extends BaseDAO<Autorizacao, Integer> {

	Autorizacao getAutorizacaoByPapel(String papel);

	Set<Autorizacao> getAutorizacoesByUsuarioId(Integer usuarioId);
}
