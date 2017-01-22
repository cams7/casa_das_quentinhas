/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.mock;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractMock {

	protected static final Byte DDD = 31;

	private static final Fairy fairy;
	private static final BaseProducer baseProducer;

	static {
		fairy = Fairy.create();
		baseProducer = fairy.baseProducer();
	}

	protected static Fairy getFairy() {
		return fairy;
	}

	protected static BaseProducer getBaseProducer() {
		return baseProducer;
	}

	/**
	 * @param n
	 * @return Numero randomico
	 */
	protected static int random(int n) {
		return (int) (Math.random() * n);
	}

	/**
	 * @param dividendo
	 * @param divisor
	 * @return Resto da divisão
	 */
	protected static int mod(int dividendo, int divisor) {
		return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
	}

}
