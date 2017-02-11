/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.report;

import static br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo.CLIENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo.ENTREGA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cams7.app.controller.AbstractReportController;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/empresa/report")
public class EmpresaReportController extends AbstractReportController<Integer, Empresa, EmpresaService> {

	private final String PDF_VIEW = "empresaPdfView";

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
	 * @see br.com.cams7.app.controller.AbstractController#getFilters()
	 */
	@Override
	protected Map<String, Object> getFilters() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("tipo", new Tipo[] { CLIENTE, ENTREGA });
		return filters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractReportController#getEntities(java.
	 * util.List)
	 */
	@Override
	protected List<Empresa> getEntities(List<Empresa> empresas) {
		return empresas.stream().map(empresa -> {
			empresa.setPedidos(null);
			empresa.setFuncionarios(null);

			List<Pedido> pedidos = getService().getPedidosIdByEmpresaId(empresa.getId());

			if (!pedidos.isEmpty()) {
				empresa.setPedidos(pedidos);
			} else {
				List<Funcionario> funcionarios = getService().getFuncionariosIdByEmpresaId(empresa.getId());
				if (!funcionarios.isEmpty())
					empresa.setFuncionarios(funcionarios);
			}
			return empresa;
		}).collect(Collectors.toList());
	}

}
