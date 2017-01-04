/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Pedido.DestinoOperacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoAtendimento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoCliente;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.PedidoItemService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/pedido")
@SessionAttributes({ "pedidoTiposCliente", "pedidoFormasPagamento", "pedidoDestinosOperacao", "pedidoTiposAtendimento",
		"pedidoSituacoes" })
public class PedidoController extends AbstractController<PedidoService, Pedido, Long> {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private PedidoItemService itemService;

	@Autowired
	private MessageSource messageSource;

	@Override
	public String store(@Valid Pedido pedido, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		Cliente cliente = pedido.getCliente();

		if (cliente.getId() == null) {
			FieldError clienteError = new FieldError("pedido", "cliente.id",
					messageSource.getMessage("NotNull.pedido.cliente.id", null, Locale.getDefault()));
			result.addError(clienteError);
		}

		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getCreateTilesPage();

		getService().setUsername(getUsername());
		getService().persist(pedido, new ArrayList<>());

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
	public String show(@PathVariable Long id, ModelMap model) {

		getItens(id, model, 0, "id", SortOrder.DESCENDING);

		return super.show(id, model);
	}

	@Override
	public String update(@Valid Pedido pedido, BindingResult result, ModelMap model, @PathVariable Long id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setEditPage(model);
		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getEditTilesPage();

		getService().update(pedido, new ArrayList<>());

		return redirectMainPage();
	}

	@GetMapping(value = { "/{pedidoId}/itens" })
	public String getItens(@PathVariable Long pedidoId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		getItens(pedidoId, model, offset, sortField, SortOrder.get(sortOrder));

		return "pedido_itens";
	}

	private void getItens(Long pedidoId, ModelMap model, Integer offset, String sortField, SortOrder sortOrder) {
		final short MAX_RESULTS = 5;

		Map<String, Object> filters = new HashMap<>();
		filters.put("id.pedidoId", pedidoId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		List<PedidoItem> itens = itemService.search(params);
		long count = itemService.getTotalElements(filters);

		model.addAttribute("itens", itens);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

	@GetMapping(value = { "/clientes/{nomeOrCpfOrTelefone}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getClientes(@PathVariable String nomeOrCpfOrTelefone) {
		Map<Integer, String> clientes = clienteService.getClientesByNomeOrCpfOrTelefone(nomeOrCpfOrTelefone);

		return new ResponseEntity<Map<Integer, String>>(clientes, HttpStatus.OK);
	}

	@GetMapping(value = { "/empresas/{nomeOrCnpj}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String nomeOrCnpj) {
		Map<Integer, String> empresas = empresaService.getEmpresasByRazaoSocialOrCnpj(nomeOrCnpj);

		return new ResponseEntity<Map<Integer, String>>(empresas, HttpStatus.OK);
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, "custo", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoIcms", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoSt", new MoneyEditor());
	}

	/**
	 * Possiveis tipos de cliente
	 */
	@ModelAttribute("pedidoTiposCliente")
	public TipoCliente[] initializeTiposCliente() {
		return TipoCliente.values();
	}

	/**
	 * Possiveis formas de pagamento
	 */
	@ModelAttribute("pedidoFormasPagamento")
	public FormaPagamento[] initializeFormasPagamento() {
		return FormaPagamento.values();
	}

	/**
	 * Possiveis destinos de operação
	 */
	@ModelAttribute("pedidoDestinosOperacao")
	public DestinoOperacao[] initializeDestinosOperacao() {
		return DestinoOperacao.values();
	}

	/**
	 * Possiveis tipos de atendimento
	 */
	@ModelAttribute("pedidoTiposAtendimento")
	public TipoAtendimento[] initializeTiposAtendimento() {
		return TipoAtendimento.values();
	}

	/**
	 * Possiveis situações do pedido
	 */
	@ModelAttribute("pedidoSituacoes")
	public Situacao[] initializeSituacoes() {
		return Situacao.values();
	}

	@Override
	protected String getEntityName() {
		return "pedido";
	}

	@Override
	protected String getListName() {
		return "pedidos";
	}

	@Override
	protected String getMainPage() {
		return "pedido";
	}

	@Override
	protected String getIndexTilesPage() {
		return "pedido_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "pedido_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "pedido_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "pedido_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "pedido_list";
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "cliente.nome", "empresa.razaoSocial", "quantidade", "custo" };
	}

	@Override
	protected Pedido getNewEntity() {
		Pedido pedido = new Pedido();
		pedido.setCliente(new Cliente());
		pedido.setTipoCliente(TipoCliente.PESSOA_FISICA);
		pedido.setFormaPagamento(FormaPagamento.PAGAMENTO_A_PRAZO);
		pedido.setTipoAtendimento(TipoAtendimento.TELEATENDIMENTO);
		pedido.setSituacao(Situacao.PENDENTE);
		return pedido;
	}

	@Override
	protected Pedido getEntity(Long id) {
		Pedido pedido = getService().getPedidoById(id);

		switch (pedido.getTipoCliente()) {
		case PESSOA_FISICA: {
			Cliente cliente = pedido.getCliente();
			cliente.setNome(cliente.getNomeWithCpf());
			break;
		}
		case PESSOA_JURIDICA: {
			Empresa empresa = pedido.getEmpresa();

			Cliente cliente = new Cliente(empresa.getId());
			cliente.setNome(empresa.getRazaoSocialWithCnpj());

			pedido.setCliente(cliente);
			break;
		}
		default:
			break;
		}

		return pedido;
	}

}
