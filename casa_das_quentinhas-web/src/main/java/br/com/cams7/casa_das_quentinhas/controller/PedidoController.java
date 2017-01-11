/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import static br.com.cams7.casa_das_quentinhas.model.Pedido.TipoCliente.PESSOA_JURIDICA;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.app.utils.AppException;
import br.com.cams7.app.utils.SearchParams.SortOrder;
import br.com.cams7.casa_das_quentinhas.facade.PedidoItemFacade;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Pedido.DestinoOperacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoAtendimento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoCliente;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.model.Produto;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + PedidoController.MODEL_NAME)
@SessionAttributes({ "pedidoTiposCliente", "pedidoFormasPagamento", "pedidoDestinosOperacao", "pedidoTiposAtendimento",
		"pedidoSituacoes" })
public class PedidoController extends AbstractController<PedidoService, Pedido, Long> {

	public static final String MODEL_NAME = "pedido";
	public static final String LIST_NAME = "pedidos";

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PedidoItemFacade itemFacade;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#create(org.springframework
	 * .ui.ModelMap)
	 */
	@Override
	public String create(ModelMap model) {
		itemFacade.initCreate();
		loadItens(0L, model);

		return super.create(model);
	}

	@Override
	public String store(@Valid Pedido pedido, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		// Carrega os intens do pedido
		loadItens(0L, model);

		// 1º validação
		setNotNullClienteError(result, pedido);

		if (result.hasErrors())
			return getCreateTilesPage();

		List<PedidoItem> itens = itemFacade.getItens();

		getService().setUsername(getUsername());
		getService().persist(pedido, itens);

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
		itemFacade.initShow();
		// Carrega os intens do pedido
		loadItens(id, model);

		return super.show(id, model);
	}

	@Override
	public String edit(@PathVariable Long id, ModelMap model) {
		itemFacade.initUpdate(id);
		// Carrega os intens do pedido
		loadItens(0L, model);

		return super.edit(id, model);
	}

	@Override
	public String update(@Valid Pedido pedido, BindingResult result, ModelMap model, @PathVariable Long id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setEditPage(model);

		// Carrega os intens do pedido
		loadItens(0L, model);

		// 1º validação
		setNotNullClienteError(result, pedido);

		if (result.hasErrors())
			return getEditTilesPage();

		List<PedidoItem> itens = itemFacade.getItens();
		List<PedidoItemPK> removedItens = itemFacade.getRemovedItens();

		getService().update(pedido, itens, removedItens);

		return redirectMainPage();
	}

	@GetMapping(value = "/{pedidoId}/itens")
	@ResponseStatus(OK)
	public String itens(@PathVariable Long pedidoId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		loadItens(pedidoId, model, offset, sortField, SortOrder.get(sortOrder));

		return "pedido_itens";
	}

	@GetMapping(value = "/{pedidoId}/item/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PedidoItem> showItem(@PathVariable Long pedidoId, @PathVariable Integer produtoId) {
		try {
			PedidoItem item = itemFacade.getItem(pedidoId, produtoId);
			return new ResponseEntity<PedidoItem>(item, OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return getItemResponse(e);
		}
	}

	@PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pedido> addItem(ModelMap model,
			@RequestParam(value = "produto_id", required = true) Integer produtoId,
			@RequestParam(value = "quantidade", required = true) Short quantidade) {
		try {
			PedidoItem item = new PedidoItem(null, produtoId);
			item.setQuantidade(quantidade);

			Produto produto = produtoService.getById(produtoId);

			item.setProduto(produto);

			Pedido pedido = itemFacade.addItem(item);
			return new ResponseEntity<Pedido>(pedido, OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return getPedidoResponse(e);
		}
	}

	@PostMapping(value = "/item/{produtoId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pedido> removeItem(@PathVariable Integer produtoId) {
		try {
			Pedido pedido = itemFacade.removeItem(produtoId);
			return new ResponseEntity<Pedido>(pedido, OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return getPedidoResponse(e);
		}
	}

	@GetMapping(value = "/clientes/{nomeOrCpfOrTelefone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getClientes(@PathVariable String nomeOrCpfOrTelefone) {
		Map<Integer, String> clientes = clienteService.getClientesByNomeOrCpfOrTelefone(nomeOrCpfOrTelefone);

		return new ResponseEntity<Map<Integer, String>>(clientes, OK);
	}

	@GetMapping(value = "/empresas/{nomeOrCnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String nomeOrCnpj) {
		Map<Integer, String> empresas = empresaService.getEmpresasByRazaoSocialOrCnpj(nomeOrCnpj, Tipo.CLIENTE);

		return new ResponseEntity<Map<Integer, String>>(empresas, OK);
	}

	@GetMapping(value = "/produtos/{nomeOrCusto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getProdutos(@PathVariable String nomeOrCusto) {
		Map<Integer, String> produtos = produtoService.getProdutosByNomeOrCusto(nomeOrCusto);

		return new ResponseEntity<Map<Integer, String>>(produtos, OK);
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

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, "custo", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoIcms", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoSt", new MoneyEditor());
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

	@Override
	protected String getDeleteMessage() {
		return "O pedido foi removido com sucesso.";
	}

	/**
	 * Verifica se foi informado o id do cliente ou da empresa
	 * 
	 * @param result
	 * @param pedido
	 */
	private void setNotNullClienteError(BindingResult result, Pedido pedido) {
		Cliente cliente = pedido.getCliente();
		if (cliente.getId() == null) {
			FieldError clienteError = new FieldError(getModelName(), "cliente.id",
					messageSource.getMessage(PESSOA_JURIDICA.equals(pedido.getTipoCliente())
							? "NotNull.pedido.empresa.id" : "NotNull.pedido.cliente.id", null, LOCALE));
			result.addError(clienteError);
		}
	}

	/**
	 * @param model
	 */
	private void loadItens(Long pedidoId, ModelMap model) {
		loadItens(pedidoId, model, 0, "quantidade", SortOrder.DESCENDING);
	}

	/**
	 * @param model
	 * @param offset
	 * @param sortField
	 * @param sortOrder
	 */
	private void loadItens(Long pedidoId, ModelMap model, Integer offset, String sortField, SortOrder sortOrder) {
		final short MAX_RESULTS = 5;

		List<PedidoItem> itens = itemFacade.search(pedidoId, offset, MAX_RESULTS, sortField, sortOrder);
		long count = itemFacade.getTotalElements(pedidoId);

		model.addAttribute("itens", itens);
		if (pedidoId != 0L)
			model.addAttribute("escondeAcoes", true);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

	/**
	 * @param e
	 * @return
	 */
	private ResponseEntity<Pedido> getPedidoResponse(Exception e) {
		if (e instanceof AppException)
			return new ResponseEntity<Pedido>(((AppException) e).getStatus());

		return new ResponseEntity<Pedido>(INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param e
	 * @return
	 */
	private ResponseEntity<PedidoItem> getItemResponse(Exception e) {
		if (e instanceof AppException)
			return new ResponseEntity<PedidoItem>(((AppException) e).getStatus());

		return new ResponseEntity<PedidoItem>(INTERNAL_SERVER_ERROR);
	}

}
