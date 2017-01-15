/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Cliente;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/pedido/report")
public class PedidoReportController extends AbstractReportController<Long, Pedido, PedidoService> {

	private final String PDF_VIEW = "pedidoPdfView";

	@Override
	protected String getPdfView() {
		return PDF_VIEW;
	}

	@Override
	protected List<Pedido> getEntities(List<Pedido> pedidos) {
		pedidos = pedidos.stream().map(pedido -> {
			if (pedido.getCliente() == null) {
				Empresa empresa = pedido.getEmpresa();

				Cliente cliente = new Cliente();
				cliente.setNome(empresa.getRazaoSocial());
				cliente.setEndereco(empresa.getEndereco());
				cliente.setCidade(empresa.getCidade());

				pedido.setEmpresa(null);
				pedido.setCliente(cliente);
			}

			return pedido;
		}).sorted((p1, p2) -> {
			return p1.getCliente().getCidade().getNome().compareTo(p2.getCliente().getCidade().getNome());
		}).collect(Collectors.toList());

		return super.getEntities(pedidos);
	}

}
