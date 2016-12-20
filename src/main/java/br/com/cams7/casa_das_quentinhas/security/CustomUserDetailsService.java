package br.com.cams7.casa_das_quentinhas.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;
import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UsuarioService userService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioEntity usuario = userService.findUsuarioByEmail(email);
		logger.info("User : {}", usuario);
		if (usuario == null) {
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), true,
				true, true, true, getGrantedAuthorities(usuario));
	}

	private List<GrantedAuthority> getGrantedAuthorities(UsuarioEntity user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (AutorizacaoEntity autorizacao : user.getAutorizacoes()) {
			logger.info("UserProfile : {}", autorizacao);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + autorizacao.getPapel()));
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	}

}
