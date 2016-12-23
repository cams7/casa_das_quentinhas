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

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UsuarioService userService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = userService.getUsuarioByEmail(email);
		LOGGER.info("User : {}", usuario);
		if (usuario == null) {
			LOGGER.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
				usuario.getSenhaCriptografada(), true, true, true, true, getGrantedAuthorities(usuario));
	}

	private List<GrantedAuthority> getGrantedAuthorities(Usuario usuario) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Autorizacao autorizacao : usuario.getAutorizacoes()) {
			LOGGER.info("UserProfile : {}", autorizacao);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + autorizacao.getPapel()));
		}
		LOGGER.info("authorities : {}", authorities);
		return authorities;
	}

}