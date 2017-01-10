/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.DateEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Cliente.TipoContribuinte;
import br.com.cams7.casa_das_quentinhas.model.Contato;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Endereco;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.CidadeService;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + ClienteController.MODEL_NAME)
@SessionAttributes("clienteTiposContribuintes")
public class ClienteController extends AbstractController<ClienteService, Cliente, Integer> {

	public static final String MODEL_NAME = "cliente";
	public static final String LIST_NAME = "clientes";

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#store(br.com.cams7.app.
	 * model.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.lang.Integer)
	 */
	@Override
	public String store(@Valid Cliente cliente, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		Usuario usuario = cliente.getUsuarioAcesso();
		Cidade cidade = cliente.getCidade();

		// 1º validação
		if (cidade.getId() == null) {
			FieldError cidadeError = new FieldError(getModelName(), "cidade.id",
					messageSource.getMessage("NotNull.cliente.cidade.id", null, LOCALE));
			result.addError(cidadeError);
		}

		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError(getModelName(), "usuarioAcesso.senha",
					messageSource.getMessage("NotEmpty.cliente.usuarioAcesso.senha", null, LOCALE));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);

		if (result.hasErrors())
			return getCreateTilesPage();

		// 2º validação
		setSenhaNotEqualsConfirmacaoError(usuario, result);
		setCPFNotUniqueError(cliente, result);
		setEmailNotUniqueError(cliente, result);

		if (result.hasErrors())
			return getCreateTilesPage();

		cliente.setCpf(cliente.getUnformattedCpf());
		cliente.getContato().setTelefone(cliente.getContato().getUnformattedTelefone());
		cliente.getEndereco().setCep(cliente.getEndereco().getUnformattedCep());

		getService().setUsername(getUsername());
		getService().persist(cliente);

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

		loadPedidos(id, model, 0, "id", SortOrder.DESCENDING);

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
	public String update(@Valid Cliente cliente, BindingResult result, ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setEditPage(model);

		Usuario usuario = cliente.getUsuarioAcesso();

		// 1º validação
		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());

		if (result.hasErrors())
			return getEditTilesPage();

		// 2º validação
		setSenhaNotEqualsConfirmacaoError(usuario, result);
		setCPFNotUniqueError(cliente, result);
		setEmailNotUniqueError(cliente, result);

		if (result.hasErrors())
			return getCreateTilesPage();

		cliente.setCpf(cliente.getUnformattedCpf());
		cliente.getContato().setTelefone(cliente.getContato().getUnformattedTelefone());
		cliente.getEndereco().setCep(cliente.getEndereco().getUnformattedCep());

		getService().update(cliente);

		return redirectMainPage();
	}

	@GetMapping(value = "/{clienteId}/pedidos")
	public String pedidos(@PathVariable Integer clienteId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		loadPedidos(clienteId, model, offset, sortField, SortOrder.get(sortOrder));

		return "pedido_list";
	}

	@SuppressWarnings("unchecked")
	private void loadPedidos(Integer clienteId, ModelMap model, Integer offset, String sortField, SortOrder sortOrder) {
		final short MAX_RESULTS = 5;

		Map<String, Object> filters = new HashMap<>();
		filters.put("cliente.id", clienteId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		pedidoService.setIgnoredJoins(Cliente.class, Empresa.class);
		List<Pedido> pedidos = pedidoService.search(params);
		long count = pedidoService.getTotalElements(filters);

		model.addAttribute("pedidos", pedidos);
		model.addAttribute("escondeCliente", true);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

	@GetMapping(value = "/cidades/{nomeOrIbge}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getCidades(@PathVariable String nomeOrIbge) {
		Map<Integer, String> cidades = cidadeService.getCidadesByNomeOrIbge(nomeOrIbge);

		return new ResponseEntity<Map<Integer, String>>(cidades, HttpStatus.OK);
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "nascimento", new DateEditor());
	}

	/**
	 * 1º validação
	 * 
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
			FieldError confirmacaoError = new FieldError(getModelName(), "usuarioAcesso.confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, LOCALE));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se senha informada é mesma do campo confirmação
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 */
	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError(getModelName(), "usuarioAcesso.confirmacaoSenha",
					messageSource.getMessage("NotEquals.usuario.confirmacaoSenha", null, LOCALE));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se o e-mail informado não foi cadastrado anteriormente
	 * 
	 * @param cliente
	 * @param result
	 */
	private void setEmailNotUniqueError(Cliente cliente, BindingResult result) {
		Usuario usuario = cliente.getUsuarioAcesso();
		Contato contato = cliente.getContato();

		if (!getService().isEmailUnique(cliente.getId(), usuario.getId(), contato.getEmail())) {
			FieldError emailError = new FieldError(getModelName(), "contato.email", messageSource
					.getMessage("NonUnique.cliente.contato.email", new String[] { contato.getEmail() }, LOCALE));
			result.addError(emailError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se o CPF informado não foi cadastrado anteriormente
	 * 
	 * @param cliente
	 * @param result
	 */
	private void setCPFNotUniqueError(Cliente cliente, BindingResult result) {
		String cpf = cliente.getUnformattedCpf();

		if (!getService().isCPFUnique(cliente.getId(), cpf)) {
			FieldError cpfError = new FieldError(getModelName(), "cpf",
					messageSource.getMessage("NonUnique.cliente.cpf", new String[] { cliente.getCpf() }, LOCALE));
			result.addError(cpfError);
		}
	}

	/**
	 * Possiveis tipos de contribuintes
	 */
	@ModelAttribute("clienteTiposContribuintes")
	public TipoContribuinte[] initializeTipos() {
		TipoContribuinte[] tipos = TipoContribuinte.values();
		return tipos;
	}

	@Override
	protected String getModelName() {
		return MODEL_NAME;
	}

	@Override
	protected String getListName() {
		return LIST_NAME;
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "cpf", "contato.email", "contato.telefone", "cidade.nome" };
	}

	@Override
	protected Cliente getNewEntity() {
		Cliente cliente = new Cliente();
		cliente.setCidade(new Cidade());
		cliente.setUsuarioAcesso(new Usuario());
		cliente.setEndereco(new Endereco());
		cliente.setContato(new Contato());

		return cliente;
	}

	@Override
	protected Cliente getEntity(Integer id) {
		Cliente cliente = getService().getClienteById(id);
		cliente.setUsuarioAcesso(new Usuario());
		return cliente;
	}

}
