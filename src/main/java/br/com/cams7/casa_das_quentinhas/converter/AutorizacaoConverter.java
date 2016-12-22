package br.com.cams7.casa_das_quentinhas.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.service.AutorizacaoService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class AutorizacaoConverter implements Converter<Object, Autorizacao> {

	static final Logger logger = LoggerFactory.getLogger(AutorizacaoConverter.class);

	@Autowired
	private AutorizacaoService autorizacaoService;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Autorizacao convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Autorizacao autorizacao = autorizacaoService.getById(id);
		logger.info("Profile : {}", autorizacao);
		return autorizacao;
	}

}