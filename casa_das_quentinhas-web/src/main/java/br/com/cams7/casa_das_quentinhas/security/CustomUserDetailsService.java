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

import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			Usuario usuario = usuarioService.getUsuarioByEmail(email);
			LOGGER.info(usuario.toString());

			return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
					usuario.getSenhaCriptografada(), true, true, true, true, getGrantedAuthorities(usuario));
		} catch (AppNotFoundException e) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities(Usuario usuario) {
		String role = "ROLE_";
		try {
			Funcao funcao = funcionarioService.getFuncionarioFuncaoById(usuario.getId());

			role += funcao.name();
		} catch (AppNotFoundException e) {
			role += usuario.getTipo().name();
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));

		LOGGER.info("authorities: {}", authorities);

		return authorities;
	}

}
