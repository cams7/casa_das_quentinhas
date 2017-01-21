/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.mock;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractMock {

	protected static final Byte DDD = 31;

	protected static int rand(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

}
