package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getAcessoByEmail;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getQualquerEmailAcesso;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

	protected final Logger LOGGER;

	private static WebDriver driver;
	private static String baseUrl;

	private static boolean isChrome = false;

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
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

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
				.valueOf(driver.findElement(By.xpath("//input[@id='dataTable_maxResults']")).getAttribute("value"));
		long count = Long.valueOf(driver.findElement(By.xpath("//input[@id='dataTable_count']")).getAttribute("value"));

		int totalPages = (int) (count / maxResults);
		if (count % maxResults > 0)
			totalPages++;

		WebDriverWait wait = getWait();
		WebElement selectPage = getDriver().findElements(By.cssSelector("ul.pagination > li > a")).get(totalPages);
		wait.until(ExpectedConditions.elementToBeClickable(selectPage));
		selectPage.click();

		sleep();
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

		final List<WebElement> buttons = driver.findElements(By.cssSelector("div#top > div.h2 > a.btn.btn-primary"));

		if (isGerente)
			buttons.get(0).click();
		else {
			assertFalse(buttons.size() > 0);
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
		final List<WebElement> buttons = driver
				.findElements(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs"));
		int index = getBaseProducer().randomBetween(0, buttons.size() - 1);
		buttons.get(index).click();

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

		final List<WebElement> buttons = driver
				.findElements(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-warning.btn-xs"));
		final int totalButtons = buttons.size();

		if (isGerenteOrAtendente) {
			int index = getBaseProducer().randomBetween(0, totalButtons - 1);
			buttons.get(index).click();
		} else {
			assertFalse(totalButtons > 0);
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

		final List<WebElement> buttons = driver.findElements(
				By.cssSelector("table.dataTable > tbody > tr > td > button.btn.btn-danger.btn-xs.delete"));
		final int totalButtons = buttons.size();

		if (!isGerente) {
			assertFalse(totalButtons > 0);
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}

		int index = getBaseProducer().randomBetween(0, totalButtons - 1);
		buttons.get(index).click();
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
		if (isChrome)
			try {
				Thread.sleep(Short.valueOf(System.getProperty("sleep.millisecounds")));
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
	}

	protected static WebDriver getDriver() {
		return driver;
	}

	protected final JavascriptExecutor getJS(WebDriver driver) {
		if (!(driver instanceof JavascriptExecutor))
			throw new IllegalStateException("This driver does not support JavaScript!");

		return (JavascriptExecutor) driver;
	}

	protected final JavascriptExecutor getJS() {
		return getJS(driver);
	}

	protected final WebDriverWait getWait() {
		return new WebDriverWait(getDriver(), 5);
	}

	protected static String getBaseUrl() {
		return baseUrl;
	}

	protected static Fairy getFairy() {
		return fairy;
	}

	protected static BaseProducer getBaseProducer() {
		return baseProducer;
	}

	private void setDriverAndUrl() {
		if (System.getProperty("webdriver.chrome.driver") != null) {
			driver = new ChromeDriver();
			isChrome = true;
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
		final List<WebElement> buttons = driver.findElements(By.cssSelector("table.dataTable > tbody > tr"));
		int index = getBaseProducer().randomBetween(0, buttons.size() - 1);

		return buttons.get(index).findElements(By.tagName("td")).get(0).getText();
	}

}
