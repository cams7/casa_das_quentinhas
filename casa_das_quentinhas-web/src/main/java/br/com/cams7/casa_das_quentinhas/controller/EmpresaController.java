/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Endereco;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Empresa.RegimeTributario;
import br.com.cams7.casa_das_quentinhas.service.CidadeService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/empresa")
@SessionAttributes({ "empresaTipos", "empresaRegimesTributarios" })
public class EmpresaController extends AbstractController<EmpresaService, Empresa, Integer> {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = { "/cidades/{nomeOrIbge}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String nomeOrIbge) {
		Map<Integer, String> cidades = cidadeService.getCidadesByNomeOrIbge(nomeOrIbge);

		return new ResponseEntity<Map<Integer, String>>(cidades, HttpStatus.OK);
	}

	/**
	 * Possiveis tipos de empresa
	 */
	@ModelAttribute("empresaTipos")
	public Empresa.Tipo[] initializeTipos() {
		Empresa.Tipo[] tipos = Empresa.Tipo.values();
		return tipos;
	}

	/**
	 * Possiveis regimes tributários
	 */
	@ModelAttribute("empresaRegimesTributarios")
	public RegimeTributario[] initializeRegimesTributarios() {
		RegimeTributario[] regimesTributarios = RegimeTributario.values();
		return regimesTributarios;
	}

	@Override
	protected String getEntityName() {
		return "empresa";
	}

	@Override
	protected String getListName() {
		return "empresas";
	}

	@Override
	protected String getMainPage() {
		return "empresa";
	}

	@Override
	protected String getIndexTilesPage() {
		return "empresa_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "empresa_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "empresa_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "empresa_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "empresa_list";
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "razaoSocial", "nomeFantasia", "cnpj", "email" };
	}

	@Override
	protected Empresa getNewEntity() {
		Empresa empresa = new Empresa();
		empresa.setUsuarioAcesso(new Usuario());
		empresa.setEndereco(new Endereco());
		empresa.getEndereco().setCidade(new Cidade());

		return empresa;
	}
	
	@Override
	protected Empresa getEntity(Integer id) {
		// TODO Auto-generated method stub
		return super.getEntity(id);
	}

}
