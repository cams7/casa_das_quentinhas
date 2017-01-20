package br.com.cams7.casa_das_quentinhas;

/**
 * @author César Magalhães
 *
 */
public interface BaseTest {

	/**
	 * Testa página de listagem
	 */
	void testIndex();

	/**
	 * Testa a página de cadastro
	 */
	void testCreatePage();

	/**
	 * Testa a página de visualização dos dados
	 */
	void testShowPage();

	/**
	 * Testa a página de edição dos dados
	 */
	void testEditPage();

	/**
	 * Testa o pop-up de exclusão
	 */
	void testDestroyModal();
}
