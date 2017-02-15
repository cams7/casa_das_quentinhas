/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import static br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento.PAGAMENTO_A_PRAZO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento.TELEATENDIMENTO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente.PESSOA_FISICA;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente.PESSOA_JURIDICA;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.SearchParams.SortOrder;
import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractBeanController;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import br.com.cams7.casa_das_quentinhas.entity.Cliente;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.DestinoOperacao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.entity.Produto;
import br.com.cams7.casa_das_quentinhas.facade.PedidoItemFacade;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;
import br.com.cams7.casa_das_quentinhas.service.PedidoServiceImpl;
import br.com.cams7.casa_das_quentinhas.service.ProdutoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + PedidoController.MODEL_NAME)
@SessionAttributes({ "pedidoTiposCliente", "pedidoFormasPagamento", "pedidoDestinosOperacao", "pedidoTiposAtendimento",
		"pedidoSituacoes" })
public class PedidoController extends AbstractBeanController<Long, Pedido, PedidoService> {

	public static final String MODEL_NAME = "pedido";
	public static final String LIST_NAME = "pedidos";

	private final TipoCliente CADASTRO_TIPOCLIENTE = PESSOA_FISICA;
	private final FormaPagamento CADASTRO_FORMAPAGAMENTO = PAGAMENTO_A_PRAZO;
	private final TipoAtendimento CADASTRO_TIPOATENDIMENTO = TELEATENDIMENTO;
	private final Situacao CADASTRO_SITUACAO = PedidoServiceImpl.CADASTRO_SITUACAO;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PedidoItemFacade itemFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#create(org.
	 * springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String create(ModelMap model, HttpServletRequest request) {
		setSituacao(model);
		itemFacade.initCreate();
		loadItens(0L, model);

		return super.create(model, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#store(br.com.cams7.app
	 * .entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String store(@Valid Pedido pedido, BindingResult result, ModelMap model, HttpServletRequest request) {
		setSituacao(model);
		setPreviousPageAtribute(model, request);
		setCommonAttributes(model);
		// incrementPreviousPage(model, request);

		// Carrega os intens do pedido
		loadItens(0L, model);

		// 1º validação
		setNotNullClienteError(result, pedido);

		if (result.hasErrors())
			return getCreateTilesPage();

		List<PedidoItem> itens = itemFacade.getItens();

		getService().setUsername(getUsername());
		getService().persist(pedido, itens);

		sucessMessage(model);
		return redirectToPreviousPage(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#show(java.io.
	 * Serializable, org.springframework.ui.ModelMap,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String show(@PathVariable Long id, ModelMap model, HttpServletRequest request) {
		itemFacade.initShow();
		// Carrega os intens do pedido
		loadItens(id, model);

		return super.show(id, model, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#edit(java.io.
	 * Serializable, org.springframework.ui.ModelMap,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String edit(@PathVariable Long id, ModelMap model, HttpServletRequest request) {
		itemFacade.initUpdate(id);
		// Carrega os intens do pedido
		loadItens(0L, model);

		return super.edit(id, model, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#update(br.com.cams7.
	 * app.entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String update(@Valid Pedido pedido, BindingResult result, ModelMap model, @PathVariable Long id,
			HttpServletRequest request) {
		setPreviousPageAtribute(model, request);
		setCommonAttributes(model);
		// incrementPreviousPage(model, request);
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

		sucessMessage(model, pedido);
		return redirectToPreviousPage(request);
	}

	/**
	 * Carrega os itens de pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 * @param model
	 * @param offset
	 * @param sortField
	 * @param sortOrder
	 * @param query
	 * @return
	 */
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

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @param produtoId
	 *            ID do produto
	 * @return Na requisição AJAX, carrega o item selecionado
	 */
	@GetMapping(value = "/{pedidoId}/item/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, ?>> showItem(@PathVariable Long pedidoId, @PathVariable Integer produtoId) {
		Response response;

		try {
			PedidoItem item = itemFacade.getItem(pedidoId, produtoId);
			response = getSucessResponse(item);
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, ?>>(response.getBody(), response.getStatus());
	}

	/**
	 * @param model
	 * @param produtoId
	 * @param quantidade
	 * @return Na requisição AJAX, carrega um novo item
	 */
	@PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, ?>> addItem(ModelMap model,
			@RequestParam(value = "produto_id", required = true) Integer produtoId,
			@RequestParam(value = "quantidade", required = true) Short quantidade) {
		Response response;

		try {
			PedidoItem item = new PedidoItem(null, produtoId);
			item.setQuantidade(quantidade);

			Produto produto = produtoService.getById(produtoId);

			item.setProduto(produto);
			item.setCusto(produto.getCusto() * quantidade);

			Pedido pedido = itemFacade.addItem(item);
			response = getSucessResponse(pedido);
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, ?>>(response.getBody(), response.getStatus());
	}

	/**
	 * @param produtoId
	 * @return Na requisição AJAX, carrega o pedido
	 */
	@PostMapping(value = "/item/{produtoId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, ?>> removeItem(@PathVariable Integer produtoId) {
		Response response;

		try {
			Pedido pedido = itemFacade.removeItem(produtoId);
			response = getSucessResponse(pedido);
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, ?>>(response.getBody(), response.getStatus());
	}

	/**
	 * @param nomeOrCpfOrTelefone
	 * @return Na requisição AJAX, carrega os clientes
	 */
	@GetMapping(value = "/clientes/{nomeOrCpfOrTelefone}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<Integer, String>> getClientes(@PathVariable String nomeOrCpfOrTelefone) {
		Map<Integer, String> clientes = clienteService.getClientesByNomeOrCpfOrTelefone(nomeOrCpfOrTelefone);

		return new ResponseEntity<Map<Integer, String>>(clientes, OK);
	}

	/**
	 * @param nomeOrCnpj
	 * @return Na requisição AJAX, carrega as empresas clientes
	 */
	@GetMapping(value = "/empresas/{nomeOrCnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String nomeOrCnpj) {
		Map<Integer, String> empresas = empresaService.getEmpresasByRazaoSocialOrCnpj(nomeOrCnpj, Tipo.CLIENTE);

		return new ResponseEntity<Map<Integer, String>>(empresas, OK);
	}

	/**
	 * @param nomeOrCpfOrCelular
	 * @return Na requisição AJAX, carrega os entregadores
	 */
	@GetMapping(value = "/entregadores/{nomeOrCpfOrCelular}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<Integer, String>> getEntregadores(@PathVariable String nomeOrCpfOrCelular) {
		Map<Integer, String> funcionarios = funcionarioService.getEntregadoresByNomeOrCpfOrCelular(nomeOrCpfOrCelular);

		return new ResponseEntity<Map<Integer, String>>(funcionarios, OK);
	}

	/**
	 * @param nomeOrCusto
	 * @return Na requisição AJAX, carrega os produtos
	 */
	@GetMapping(value = "/produtos/{nomeOrCusto}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<Integer, String>> getProdutos(@PathVariable String nomeOrCusto) {
		Map<Integer, String> produtos = produtoService.getProdutosByNomeOrCusto(nomeOrCusto);

		return new ResponseEntity<Map<Integer, String>>(produtos, OK);
	}

	/**
	 * @return Possiveis tipos de cliente
	 */
	@ModelAttribute("pedidoTiposCliente")
	public TipoCliente[] initializeTiposCliente() {
		return TipoCliente.values();
	}

	/**
	 * @return Possiveis formas de pagamento
	 */
	@ModelAttribute("pedidoFormasPagamento")
	public FormaPagamento[] initializeFormasPagamento() {
		return FormaPagamento.values();
	}

	/**
	 * @return Possiveis destinos de operação
	 */
	@ModelAttribute("pedidoDestinosOperacao")
	public DestinoOperacao[] initializeDestinosOperacao() {
		return DestinoOperacao.values();
	}

	/**
	 * @return Possiveis tipos de atendimento
	 */
	@ModelAttribute("pedidoTiposAtendimento")
	public TipoAtendimento[] initializeTiposAtendimento() {
		return TipoAtendimento.values();
	}

	/**
	 * @return Possiveis situações do pedido
	 */
	@ModelAttribute("pedidoSituacoes")
	public Situacao[] initializeSituacoes() {
		return Situacao.values();
	}

	/**
	 * Converte os custos formatados
	 * 
	 * @param binder
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, "custo", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoIcms", new MoneyEditor());
		binder.registerCustomEditor(Float.class, "custoSt", new MoneyEditor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#getModelName()
	 */
	@Override
	protected String getModelName() {
		return MODEL_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#getListName()
	 */
	@Override
	protected String getListName() {
		return LIST_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractController#getIgnoredJoins()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class<?>[] getIgnoredJoins() {
		return new Class<?>[] { Cidade.class };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getGlobalFilters()
	 */
	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "cliente.nome", "empresa.razaoSocial", "quantidade", "custo" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#getNewEntity()
	 */
	@Override
	protected Pedido getNewEntity() {
		Pedido pedido = new Pedido();
		pedido.setCliente(new Cliente());
		pedido.setEntregador(new Funcionario());
		pedido.setTipoCliente(CADASTRO_TIPOCLIENTE);
		pedido.setFormaPagamento(CADASTRO_FORMAPAGAMENTO);
		pedido.setTipoAtendimento(CADASTRO_TIPOATENDIMENTO);
		pedido.setSituacao(CADASTRO_SITUACAO);
		return pedido;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getEntity(java.io.
	 * Serializable)
	 */
	@Override
	protected Pedido getEntity(Long id) {
		Pedido pedido = getService().getPedidoById(id);
		Funcionario entregador = pedido.getEntregador();
		if (entregador != null)
			entregador.setNome(entregador.getNomeWithCpf());

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
					getMessageSource().getMessage(PESSOA_JURIDICA.equals(pedido.getTipoCliente())
							? "NotNull.pedido.empresa.id" : "NotNull.pedido.cliente.id", null, LOCALE));
			result.addError(clienteError);
		}
	}

	/**
	 * Carrega os itens de pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 * @param model
	 */
	private void loadItens(Long pedidoId, ModelMap model) {
		loadItens(pedidoId, model, 0, "quantidade", SortOrder.DESCENDING);
	}

	/**
	 * Carrega os itens de pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
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
	 * Define a situação como Pendente no cadastro do pedido
	 * 
	 * @param model
	 */
	private final void setSituacao(ModelMap model) {
		model.addAttribute("pedidoSituacao", CADASTRO_SITUACAO.getDescricao());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getDeleteSucessMessage
	 * ()
	 */
	@Override
	protected String getDeleteSucessMessage() {
		return getMessageSource().getMessage("pedido.successfully.removed", null, LOCALE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getStoreSucessMessage(
	 * )
	 */
	@Override
	protected String getStoreSucessMessage() {
		return getMessageSource().getMessage("pedido.successfully.registered", null, LOCALE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getUpdateSucessMessage
	 * (br.com.cams7.app.entity.AbstractEntity)
	 */
	@Override
	protected String getUpdateSucessMessage(Pedido pedido) {
		return getMessageSource().getMessage("pedido.successfully.updated", new String[] { pedido.getIdWithCadastro() },
				LOCALE);
	}

}
