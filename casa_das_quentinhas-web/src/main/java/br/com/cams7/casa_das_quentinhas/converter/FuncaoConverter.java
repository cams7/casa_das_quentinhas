package br.com.cams7.casa_das_quentinhas.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class FuncaoConverter implements Converter<Object, Funcao> {

	static final Logger logger = LoggerFactory.getLogger(FuncaoConverter.class);

	@Autowired
	private FuncionarioService funcionarioService;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Funcao convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Funcao funcao = funcionarioService.getFuncionarioFuncaoById(id);
		logger.info("Funcionario : {}", funcao);
		return funcao;
	}

}