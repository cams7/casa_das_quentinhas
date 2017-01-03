/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.common.MoneyEditor;
import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Pedido.DestinoOperacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoAtendimento;
import br.com.cams7.casa_das_quentinhas.model.Pedido.TipoCliente;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
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
		return getService().getPedidoById(id);
	}

}
