/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Contato;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.RegimeTributario;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Endereco;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.CidadeService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/empresa")
@SessionAttributes({ "empresaTipos", "empresaRegimesTributarios" })
public class EmpresaController extends AbstractController<EmpresaService, Empresa, Integer> {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MessageSource messageSource;

	private final short MAX_RESULTS = 5;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#store(br.com.cams7.app.
	 * model.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.lang.Integer)
	 */
	@Override
	public String store(@Valid Empresa empresa, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		Usuario usuario = empresa.getUsuarioAcesso();
		Cidade cidade = empresa.getCidade();

		if (cidade.getId() == null) {
			FieldError cidadeError = new FieldError("empresa", "cidade.id",
					messageSource.getMessage("NotNull.empresa.cidade.id", null, Locale.getDefault()));
			result.addError(cidadeError);
		}

		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError("empresa", "usuarioAcesso.senha",
					messageSource.getMessage("NotEmpty.empresa.usuarioAcesso.senha", null, Locale.getDefault()));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		setEmailNotUniqueError(empresa, result);
		setCNPJNotUniqueError(empresa, result);

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getCreateTilesPage();

		empresa.setCnpj(empresa.getUnformattedCnpj());
		empresa.getContato().setTelefone(empresa.getContato().getUnformattedTelefone());
		empresa.getEndereco().setCep(empresa.getEndereco().getUnformattedCep());

		getService().setUsername(getUsername());
		getService().persist(empresa);

		return redirectMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#show(java.io.Serializable,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String show(@PathVariable Integer id, ModelMap model) {

		Tipo tipo = getService().getEmpresaIipoById(id);

		if (tipo != null)
			switch (tipo) {
			case CLIENTE:
				getPedidos(id, model, 0, "id", SortOrder.DESCENDING);
				break;
			case ENTREGA:
				getFuncionarios(id, model, 0, "id", SortOrder.DESCENDING);
				break;
			default:
				break;
			}

		return super.show(id, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#update(br.com.cams7.app.
	 * model.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable, java.lang.Integer)
	 */
	@Override
	public String update(@Valid Empresa empresa, BindingResult result, ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		Usuario usuario = empresa.getUsuarioAcesso();

		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		setEmailNotUniqueError(empresa, result);
		setCNPJNotUniqueError(empresa, result);

		setCommonAttributes(model);
		setEditPage(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getEditTilesPage();

		empresa.setCnpj(empresa.getUnformattedCnpj());
		empresa.getContato().setTelefone(empresa.getContato().getUnformattedTelefone());
		empresa.getEndereco().setCep(empresa.getEndereco().getUnformattedCep());

		getService().update(empresa);

		return redirectMainPage();
	}

	@GetMapping(value = { "/{empresaId}/funcionarios" })
	public String getFuncionarios(@PathVariable Integer empresaId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		getFuncionarios(empresaId, model, offset, sortField, SortOrder.get(sortOrder));

		return "funcionario_list";
	}

	@GetMapping(value = { "/{empresaId}/pedidos" })
	public String getPedidos(@PathVariable Integer empresaId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		getPedidos(empresaId, model, offset, sortField, SortOrder.get(sortOrder));

		return "pedido_list";
	}

	@SuppressWarnings("unchecked")
	private void getFuncionarios(Integer empresaId, ModelMap model, Integer offset, String sortField,
			SortOrder sortOrder) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("empresa.id", empresaId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		funcionarioService.setIgnoredJoins(Empresa.class);
		List<Funcionario> funcionarios = funcionarioService.search(params);
		long count = funcionarioService.getTotalElements(filters);

		model.addAttribute("funcionarios", funcionarios);
		model.addAttribute("escondeEmpresa", true);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

	@SuppressWarnings("unchecked")
	private void getPedidos(Integer empresaId, ModelMap model, Integer offset, String sortField, SortOrder sortOrder) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("empresa.id", empresaId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		pedidoService.setIgnoredJoins(Cliente.class, Empresa.class);
		List<Pedido> pedidos = pedidoService.search(params);
		long count = pedidoService.getTotalElements(filters);

		model.addAttribute("pedidos", pedidos);
		model.addAttribute("escondeCliente", true);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

	@GetMapping(value = { "/cidades/{nomeOrIbge}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getCidades(@PathVariable String nomeOrIbge) {
		Map<Integer, String> cidades = cidadeService.getCidadesByNomeOrIbge(nomeOrIbge);

		return new ResponseEntity<Map<Integer, String>>(cidades, HttpStatus.OK);
	}

	/**
	 * Verifica se o campo de confirmação de senha não esta vazio, caso o campo
	 * senha tenha sido preenchido anteriormente
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 * @param senhaInformada
	 *            A senha foi informada
	 */
	private void setNotEmptyConfirmacaoError(Usuario usuario, BindingResult result, boolean senhaInformada) {
		if (senhaInformada && usuario.getConfirmacaoSenha().isEmpty()) {
			FieldError confirmacaoError = new FieldError("empresa", "usuarioAcesso.confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * Verifica se senha informada é mesma do campo confirmação
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 */
	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		if (result.getFieldErrorCount("usuarioAcesso.confirmacaoSenha") > 0)
			return;

		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError("empresa", "usuarioAcesso.confirmacaoSenha",
					messageSource.getMessage("NotEquals.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * Verifica se o e-mail informado não foi cadastrado anteriormente
	 * 
	 * @param empresa
	 * @param result
	 */
	private void setEmailNotUniqueError(Empresa empresa, BindingResult result) {
		if (result.getFieldErrorCount("contato.email") > 0)
			return;

		Usuario usuario = empresa.getUsuarioAcesso();
		Contato contato = empresa.getContato();

		if (!getService().isEmailUnique(empresa.getId(), usuario.getId(), contato.getEmail())) {
			FieldError emailError = new FieldError("empresa", "contato.email", messageSource.getMessage(
					"NonUnique.empresa.contato.email", new String[] { contato.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}
	}

	/**
	 * Verifica se o CPF informado não foi cadastrado anteriormente
	 * 
	 * @param empresa
	 * @param result
	 */
	private void setCNPJNotUniqueError(Empresa empresa, BindingResult result) {
		if (result.getFieldErrorCount("cnpj") > 0)
			return;

		String cnpj = empresa.getUnformattedCnpj();

		if (!getService().isCNPJUnique(empresa.getId(), cnpj)) {
			FieldError cnpjError = new FieldError("empresa", "cnpj", messageSource.getMessage("NonUnique.empresa.cnpj",
					new String[] { empresa.getCnpj() }, Locale.getDefault()));
			result.addError(cnpjError);
		}
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
		return new String[] { "razaoSocial", "cnpj", "contato.email", "contato.telefone", "cidade.nome" };
	}

	@Override
	protected Empresa getNewEntity() {
		Empresa empresa = new Empresa();
		empresa.setCidade(new Cidade());
		empresa.setUsuarioAcesso(new Usuario());
		empresa.setEndereco(new Endereco());
		empresa.setContato(new Contato());

		return empresa;
	}

	@Override
	protected Empresa getEntity(Integer id) {
		Empresa empresa = getService().getEmpresaById(id);
		return empresa;
	}

}
