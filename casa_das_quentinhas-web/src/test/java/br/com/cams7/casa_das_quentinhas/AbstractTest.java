package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import br.com.cams7.casa_das_quentinhas.mock.UsuarioMock;

public abstract class AbstractTest implements BaseTest {

	// private boolean acceptNextAlert = true;
	// private StringBuffer verificationErrors = new StringBuffer();

	private static WebDriver driver;
	private static String baseUrl;

	private static boolean sleepEnabled = false;
	private static Object acesso;

	protected final Logger LOGGER;

	/**
	 * 
	 */
	public AbstractTest() {
		super();
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	@BeforeSuite
	public void setUp() throws Exception {
		LOGGER.info("@BeforeSuite");

		setDriverAndUrl();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		final String EMAIL_ACESSO = UsuarioMock.getQualquerEmailAcesso();
		login(EMAIL_ACESSO, UsuarioMock.getSenhaAcesso());

		acesso = UsuarioMock.getAcesso(EMAIL_ACESSO);

		LOGGER.info("E-mail: {}, acesso: {}", EMAIL_ACESSO, acesso);
	}

	@AfterSuite
	public void tearDown() throws Exception {
		LOGGER.info("@AfterSuite");

		logout();

		driver.quit();
	}

	@BeforeClass(alwaysRun = true)
	public void begin() {
		LOGGER.info("@BeforeClass");
	}

	@AfterClass(alwaysRun = true)
	public void end() {
		LOGGER.info("@AfterClass");
		// String verificationErrorString = verificationErrors.toString();
		// if (!"".equals(verificationErrorString)) {
		// fail(verificationErrorString);
		// }
	}

	protected void paginate() {
		short maxResults = Short
				.valueOf((String) getJS().executeScript("return $('input#dataTable_maxResults').val();"));
		long count = Long.valueOf((String) getJS().executeScript("return $('input#dataTable_count').val();"));

		int totalPages = (int) (count / maxResults);
		if (count % maxResults > 0)
			totalPages++;

		for (int i = 2; i <= totalPages; i++) {
			getJS().executeScript("$('ul.pagination li a').eq( " + i + " ).click();");
			sleep();
		}

		LOGGER.info("maxResults: {}, count: {}, pages: {}", maxResults, count, totalPages);

		sleep();
	}

	protected void search(String query) {
		getJS().executeScript("$('input#search_query').val('" + query + "');$('button#search_btn').click();");
		sleep();
	}

	/**
	 * Vai para página de listagem
	 * 
	 * @param mainPage
	 */
	protected void goToIndexPage(String mainPage) {
		// getDriver().findElement(By.linkText("Cliente(s)")).click();
		getDriver().get(baseUrl + "/" + mainPage);
		sleep();
	}

	/**
	 * Vai para página de cadastro
	 */
	protected boolean goToCreatePage(String mainPage) {
		// driver.findElement(By.cssSelector("a.btn.btn-primary")).click();
		driver.get(baseUrl + "/" + mainPage + "/create");
		sleep();

		boolean isGererente = GERENTE.equals(acesso);
		if (!isGererente)
			assertEquals("Não autorizado", getDriver().getTitle());

		return isGererente;
	}

	/**
	 * Tenta salva dos dados do formulário
	 */
	protected void saveCreatePage() {
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelCreatePage() {
		driver.findElement(By.id("cancelar")).click();
		sleep();
	}

	/**
	 * Vai para a página de visualização dos dados
	 */
	protected void goToViewPage(String mainPage) {
		// driver.findElement(By.cssSelector("a.btn.btn-success.btn-xs")).click();
		driver.get(baseUrl + "/" + mainPage + "/" + getId());
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		driver.findElement(By.cssSelector("a.btn.btn-default")).click();
		sleep();
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected boolean goToEditPage(String mainPage) {
		// driver.findElement(By.cssSelector("a.btn.btn-warning.btn-xs")).click();
		driver.get(baseUrl + "/" + mainPage + "/" + getId() + "/edit");
		sleep();

		boolean isGererenteOrAtendente = GERENTE.equals(acesso) || ATENDENTE.equals(acesso);
		if (!isGererenteOrAtendente)
			assertEquals("Não autorizado", getDriver().getTitle());

		return isGererenteOrAtendente;
	}

	/**
	 * Salva os dados do formulário
	 */
	protected void saveEditPage() {
		driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
		sleep();
	}

	/**
	 * Exibe o pop-pop de exclusão
	 */
	protected void showDeleteModal() {
		try {
			driver.findElement(By.cssSelector("button.btn.btn-danger.btn-xs.delete")).click();
			sleep();
		} catch (NoSuchElementException e) {
			assertTrue(!GERENTE.equals(acesso));
		}
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
		getJS().executeScript("$('div.modal-footer button.btn.btn-default').click();");
		sleep();
	}

	protected void sleep() {
		if (sleepEnabled)
			try {
				Thread.sleep(1000);
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

	public static Object getAcesso() {
		return acesso;
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
		return (String) getJS().executeScript("return $('table.dataTable tbody tr:first td:first').html();");
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
