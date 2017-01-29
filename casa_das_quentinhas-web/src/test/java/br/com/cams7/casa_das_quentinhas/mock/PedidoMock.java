/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.mock;

import static br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento.PAGAMENTO_A_PRAZO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento.PAGAMENTO_A_VISTA;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao.CANCELADO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao.EM_TRANSITO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao.ENTREGUE;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao.PENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento.NAO_SE_APLICA;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento.OPERACAO_NAO_PRESENCIAL;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento.OPERACAO_PRESENCIAL;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento.TELEATENDIMENTO;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente.PESSOA_FISICA;
import static br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente.PESSOA_JURIDICA;

import br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento;

/**
 * @author César Magalhães
 *
 */
public class PedidoMock extends AbstractMock {

	/**
	 * Gera, aleatoriamente, o tipo de cliente que pode ser PESSOA_FISICA ou
	 * PESSOA_JURIDICA
	 * 
	 * @return Tipo do cliente
	 */
	public static String getTipoCliente() {
		return getBaseProducer().randomElement(PESSOA_FISICA.name(), PESSOA_JURIDICA.name());
	}

	/**
	 * Gera, aleatoriamente, a forma de pagamento que pode ser
	 * PAGAMENTO_A_VISTA, PAGAMENTO_A_PRAZO ou OUTROS
	 * 
	 * @return Forma de pagamento
	 */
	public static String getFormaPagamento() {
		return getBaseProducer().randomElement(PAGAMENTO_A_VISTA.name(), PAGAMENTO_A_PRAZO.name(),
				FormaPagamento.OUTROS.name());
	}

	/**
	 * Gera, aleatoriamente, a situação do pedido que pode ser PENDENTE,
	 * EM_TRANSITO, CANCELADO ou ENTREGUE
	 * 
	 * @return Situação do pedido
	 */
	public static String getSituacao() {
		return getBaseProducer().randomElement(PENDENTE.name(), EM_TRANSITO.name(), CANCELADO.name(), ENTREGUE.name());
	}

	/**
	 * Gera, aleatoriamente, o tipo de atendimento que pode ser NAO_SE_APLICA,
	 * OPERACAO_PRESENCIAL, OPERACAO_NAO_PRESENCIAL ou OUTROS
	 * 
	 * @return Tipo de atendimento
	 */
	public static String getTipoAtendimento() {
		return getBaseProducer().randomElement(NAO_SE_APLICA.name(), OPERACAO_PRESENCIAL.name(),
				OPERACAO_NAO_PRESENCIAL.name(), TELEATENDIMENTO.name(), TipoAtendimento.OUTROS.name());
	}

}
