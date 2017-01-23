package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getAcessoByEmail;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getQualquerEmailAcesso;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;

public abstract class AbstractTest implements BaseTest {

	// private boolean acceptNextAlert = true;
	// private StringBuffer verificationErrors = new StringBuffer();

	protected final Logger LOGGER;

	private static WebDriver driver;
	private static String baseUrl;

	private static boolean sleepEnabled = false;

	private static Object acesso;

	private static Fairy fairy;
	private static BaseProducer baseProducer;

	private final String UNAUTHORIZED_MESSAGE = "Não autorizado";

	/**
	 * 
	 */
	public AbstractTest() {
		super();
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	@BeforeSuite
	public void setUp() throws Exception {
		// LOGGER.info("@BeforeSuite");

		setDriverAndUrl();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		fairy = Fairy.create();
		baseProducer = fairy.baseProducer();

		final String EMAIL_ACESSO = getQualquerEmailAcesso();
		login(EMAIL_ACESSO, getSenhaAcesso());

		acesso = getAcessoByEmail(EMAIL_ACESSO);

		LOGGER.info("E-mail: {}, acesso: {}", EMAIL_ACESSO, acesso);
	}

	@AfterSuite
	public void tearDown() throws Exception {
		// LOGGER.info("@AfterSuite");

		logout();

		driver.quit();
	}

	// @BeforeClass(alwaysRun = true)
	// public void begin() {
	// LOGGER.info("@BeforeClass");
	// }
	//
	// @AfterClass(alwaysRun = true)
	// public void end() {
	// LOGGER.info("@AfterClass");
	// // String verificationErrorString = verificationErrors.toString();
	// // if (!"".equals(verificationErrorString)) {
	// // fail(verificationErrorString);
	// // }
	// }

	/**
	 * Vai para página de listagem
	 * 
	 * @param mainPage
	 */
	protected void goToIndexPage(String mainPage, String menu) {
		boolean isGerente = GERENTE.equals(acesso);
		if (isGerente)
			driver.findElement(By.linkText(menu)).click();
		else
			getDriver().get(baseUrl + "/" + mainPage);

		sleep();
	}

	/**
	 * Pagina a tabela
	 */
	protected void paginate() {
		short maxResults = Short
				.valueOf((String) getJS().executeScript("return $('input#dataTable_maxResults').val();"));
		long count = Long.valueOf((String) getJS().executeScript("return $('input#dataTable_count').val();"));

		int totalPages = (int) (count / maxResults);
		if (count % maxResults > 0)
			totalPages++;

		// for (int i = 2; i <= totalPages; i++) {
		getJS().executeScript("$('ul.pagination li a').eq( " + totalPages + " ).click();");
		sleep();
		// }

		// LOGGER.info("maxResults: {}, count: {}, pages: {}", maxResults,
		// count, totalPages);
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected void search(String query) {
		getJS().executeScript("$('input#search_query').val('" + query + "');$('button#search_btn').click();");
		sleep();
	}

	/**
	 * Ordena todos os campos da tabela
	 * 
	 * @param fields
	 *            Campos da tabela
	 */
	protected void sortFields(String... fields) {
		for (String field : fields) {
			if (baseProducer.trueOrFalse()) {
				getDriver().findElement(By.id(field)).click();
				sleep();
			}
		}
	}

	/**
	 * Vai para página de cadastro
	 */
	protected void goToCreatePage(String mainPage) {
		boolean isGerente = GERENTE.equals(acesso);
		if (isGerente)
			driver.findElement(By.cssSelector("div#top > div.h2 > a.btn.btn-primary")).click();
		else {
			boolean finded = (Boolean) getJS()
					.executeScript("return $('div#top').find('div.h2 a.btn.btn-primary').length > 0;");
			assertFalse(finded);

			driver.get(baseUrl + "/" + mainPage + "/create");
		}
		sleep();

		if (!isGerente) {
			assertEquals(UNAUTHORIZED_MESSAGE, getDriver().getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Vai para a página de visualização dos dados
	 */
	protected void goToViewPage(String mainPage) {
		driver.findElement(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs")).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		driver.findElement(By.cssSelector("div#actions > div > a.btn.btn-default")).click();
		sleep();
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected void goToEditPage(String mainPage) {
		boolean isGerenteOrAtendente = GERENTE.equals(acesso) || ATENDENTE.equals(acesso);

		if (isGerenteOrAtendente)
			driver.findElement(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-warning.btn-xs")).click();
		else {
			boolean finded = (Boolean) getJS().executeScript(
					"return $('table.dataTable tbody tr td').find('a.btn.btn-warning.btn-xs').length > 0;");
			assertFalse(finded);

			driver.get(baseUrl + "/" + mainPage + "/" + getId() + "/edit");
		}
		sleep();

		if (!isGerenteOrAtendente) {
			assertEquals(UNAUTHORIZED_MESSAGE, getDriver().getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Exibe o pop-pop de exclusão
	 */
	protected void showDeleteModal() {
		boolean isGerente = GERENTE.equals(acesso);

		if (!isGerente) {
			boolean finded = ((Boolean) getJS().executeScript(
					"return $('table.dataTable tbody tr td').find('button.btn.btn-danger.btn-xs.delete').length > 0;"));
			assertFalse(finded);

			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}

		driver.findElement(By.cssSelector("table.dataTable > tbody > tr > td > button.btn.btn-danger.btn-xs.delete"))
				.click();
		sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
		getJS().executeScript("$('div.modal-footer button.btn.btn-default').click();");
		// driver.findElement(By.cssSelector("div.modal-footer >
		// button.btn.btn-default")).click();
		sleep();
	}

	/**
	 * Tenta salva dos dados do formulário
	 */
	protected void saveCreateAndEditPage() {
		driver.findElement(By.cssSelector("div#actions > div > input.btn.btn-primary")).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelCreateAndEditPage() {
		driver.findElement(By.cssSelector("div#actions > div > button.btn.btn-default")).click();
		sleep();
	}

	protected void sleep() {
		if (sleepEnabled)
			try {
				Thread.sleep(Short.valueOf(System.getProperty("sleep.millisecounds")));
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
	}

	protected static WebDriver getDriver() {
		return driver;
	}

	protected final JavascriptExecutor getJS() {
		if (!(driver instanceof JavascriptExecutor))
			throw new IllegalStateException("This driver does not support JavaScript!");

		return (JavascriptExecutor) driver;
	}

	protected static String getBaseUrl() {
		return baseUrl;
	}

	// public static Object getAcesso() {
	// return acesso;
	// }

	protected static Fairy getFairy() {
		return fairy;
	}

	protected static BaseProducer getBaseProducer() {
		return baseProducer;
	}

	private void setDriverAndUrl() {
		if (System.getProperty("webdriver.chrome.driver") != null) {
			driver = new ChromeDriver();
			sleepEnabled = true;
			LOGGER.info("You are testing in Chrome");
			LOGGER.info("webdriver.chrome.driver: {}", System.getProperty("webdriver.chrome.driver"));
		} else {
			driver = new PhantomJSDriver();
			LOGGER.info("You are testing in PhantomJS");
			LOGGER.info("phantomjs.binary.path: {}", System.getProperty("phantomjs.binary.path"));
		}

		baseUrl = System.getProperty("base.url");
		LOGGER.info("url: {}", System.getProperty("base.url"));
	}

	private void login(String username, String password) {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys(password);
		driver.findElement(By.id("lembre_me")).click();
		driver.findElement(By.xpath("//input[@value='Entrar']")).click();
	}

	private void logout() {
		driver.findElement(By.cssSelector("span.glyphicon.glyphicon-log-out")).click();
	}

	private String getId() {
		long rows = (Long) getJS().executeScript("return $('table.dataTable tbody tr').length;");
		long rowIndex = getBaseProducer().randomBetween(1, rows);
		String id = (String) getJS()
				.executeScript("return $('table.dataTable tbody tr:nth-child(" + rowIndex + ") td:first').html();");
		// LOGGER.info("rows: " + rows + ", rowIndex: " + rowIndex + ", id: " +
		// id);
		return id;
	}

	// private boolean isElementPresent(By by) {
	// try {
	// driver.findElement(by);
	// return true;
	// } catch (NoSuchElementException e) {
	// return false;
	// }
	// }
	//
	// private boolean isAlertPresent() {
	// try {
	// driver.switchTo().alert();
	// return true;
	// } catch (NoAlertPresentException e) {
	// return false;
	// }
	// }
	//
	// private String closeAlertAndGetItsText() {
	// try {
	// Alert alert = driver.switchTo().alert();
	// String alertText = alert.getText();
	// if (acceptNextAlert) {
	// alert.accept();
	// } else {
	// alert.dismiss();
	// }
	// return alertText;
	// } finally {
	// acceptNextAlert = true;
	// }
	// }

}
