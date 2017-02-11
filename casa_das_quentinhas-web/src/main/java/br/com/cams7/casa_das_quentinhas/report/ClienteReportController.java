/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import br.com.cams7.casa_das_quentinhas.entity.Cliente;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.service.ClienteService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/cliente/report")
public class ClienteReportController extends AbstractReportController<Integer, Cliente, ClienteService> {

	private final String PDF_VIEW = "clientePdfView";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractReportController#getPdfView()
	 */
	@Override
	protected String getPdfView() {
		return PDF_VIEW;
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
	 * br.com.cams7.app.controller.AbstractReportController#getEntities(java.
	 * util.List)
	 */
	@Override
	protected List<Cliente> getEntities(List<Cliente> clientes) {
		return clientes.stream().map(cliente -> {
			List<Pedido> pedidos = getService().getPedidosIdByClienteId(cliente.getId());
			cliente.setPedidos(pedidos);
			return cliente;
		}).collect(Collectors.toList());
	}

}
