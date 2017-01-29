package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getAcessoByEmail;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getQualquerEmailAcesso;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;

public abstract class AbstractTest implements BaseTest {

	private final static Fairy fairy;
	private final static BaseProducer baseProducer;

	protected final Logger LOGGER;

	private static String baseUrl;

	private static WebDriver driver;
	// private static JavascriptExecutor js;
	private static WebDriverWait wait;

	private static boolean sleep = true;

	private Object acesso;

	private final String UNAUTHORIZED_MESSAGE = "Não autorizado";

	static {
		fairy = Fairy.create();
		baseProducer = fairy.baseProducer();
	}

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

		// js = getJS(driver);
		wait = new WebDriverWait(driver, 5);
	}

	@AfterSuite
	public void tearDown() throws Exception {
		// LOGGER.info("@AfterSuite");
		driver.quit();
	}

	@BeforeClass(alwaysRun = true)
	public void begin() {
		// LOGGER.info("@BeforeClass");
		final String EMAIL_ACESSO = getQualquerEmailAcesso();
		login(EMAIL_ACESSO, getSenhaAcesso());

		acesso = getAcessoByEmail(EMAIL_ACESSO);

		LOGGER.info("begin -> e-mail: {}, acesso: {}", EMAIL_ACESSO, acesso);
	}

	@AfterClass(alwaysRun = true)
	public void end() {
		// LOGGER.info("@AfterClass");
		logout();
	}

	/**
	 * Vai para página de listagem
	 * 
	 * @param menu
	 *            Titulo do menu
	 */
	protected void goToIndexPage(String menu) {
		boolean isGerente = GERENTE.equals(acesso);
		if (isGerente) {
			final By MENU = By.linkText(menu);
			wait.until(ExpectedConditions.elementToBeClickable(MENU));
			driver.findElement(MENU).click();
		} else
			driver.get(baseUrl + "/" + getMainPage());

		sleep();
	}

	/**
	 * Pagina a tabela
	 */
	protected void paginate() {
		short maxResults = Short.valueOf(getMaxResults(driver));
		long count = Long.valueOf(getCount(driver));

		String firstId = getId(driver, 1);

		int totalPages = (int) (count / maxResults);
		if (count % maxResults > 0)
			totalPages++;

		LOGGER.info("paginate -> max results: {}, count: {}, total pages: {}", maxResults, count, totalPages);

		WebElement selectPage = driver.findElement(By.cssSelector("ul.pagination > li"))
				.findElement(By.xpath("//a[text()='" + totalPages + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(selectPage));
		selectPage.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentId = getId(driver, 1);
				return !(currentId.isEmpty() || firstId.equals(currentId));
			}
		});

		LOGGER.info("paginate -> first id: {}, current id: {}", firstId, getId(driver, 1));

		sleep();
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected void search(String query) {
		long firstCount = Long.valueOf(getCount(driver));

		final By SEARCH_INPUT = By.id("search_query");
		wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
		driver.findElement(SEARCH_INPUT).sendKeys(query);

		final By SEARCH_BUTTON = By.id("search_btn");
		wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
		driver.findElement(SEARCH_BUTTON).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentCount = getCount(driver);
				return !currentCount.isEmpty() && firstCount > Long.valueOf(currentCount);
			}
		});

		LOGGER.info("search '{}' -> first count: {}, current count: {}", query, firstCount, getCount(driver));

		sleep();
	}

	/**
	 * Ordena todos os campos da tabela
	 * 
	 * @param fields
	 *            Campos da tabela
	 */
	// protected void sortFields(String... fields) {
	// }
	protected void clickSortField(String field) {
		String firstOrder = getSortOrder(driver, field);

		final By CELL = By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
		wait.until(ExpectedConditions.elementToBeClickable(CELL));
		driver.findElement(CELL).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentOrder = getSortOrder(driver, field);
				return !(currentOrder.isEmpty() || firstOrder.equals(currentOrder));

			}
		});

		LOGGER.info("sort '{}' -> first order: {}, current order: {}", field, firstOrder, getSortOrder(driver, field));

		sleep();
	}

	/**
	 * Vai para página de cadastro
	 */
	protected void goToCreatePage() {
		boolean isGerente = GERENTE.equals(acesso);

		final List<WebElement> buttons = driver.findElements(By.cssSelector("div#top > div.h2 > a.btn.btn-primary"));

		if (isGerente) {
			WebElement create = buttons.get(0);
			wait.until(ExpectedConditions.elementToBeClickable(create));
			create.click();
		} else {
			assertFalse(buttons.size() > 0);
			String url = baseUrl + "/" + getMainPage() + "/create";
			LOGGER.info("create -> got to {}", url);
			driver.get(url);
		}
		sleep();

		if (!isGerente) {
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("title"),
			// UNAUTHORIZED_MESSAGE));
			assertEquals(UNAUTHORIZED_MESSAGE, driver.getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Vai para a página de visualização dos dados
	 */
	protected void goToViewPage() {
		final List<WebElement> buttons = driver
				.findElements(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs"));
		int index = getBaseProducer().randomBetween(0, buttons.size() - 1);

		WebElement view = buttons.get(index);
		wait.until(ExpectedConditions.elementToBeClickable(view));
		view.click();

		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		final By CANCEL = By.cssSelector("div#actions > div > a.btn.btn-default");
		wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
		driver.findElement(CANCEL).click();

		sleep();
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected void goToEditPage() {
		boolean isGerenteOrAtendente = GERENTE.equals(acesso) || ATENDENTE.equals(acesso);

		final List<WebElement> buttons = driver
				.findElements(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-warning.btn-xs"));
		final int totalButtons = buttons.size();

		if (isGerenteOrAtendente) {
			int index = getBaseProducer().randomBetween(0, totalButtons - 1);
			WebElement edit = buttons.get(index);
			wait.until(ExpectedConditions.elementToBeClickable(edit));
			edit.click();
		} else {
			assertFalse(totalButtons > 0);
			String url = baseUrl + "/" + getMainPage() + "/" + getId() + "/edit";
			LOGGER.info("edit -> got to {}", url);
			driver.get(url);
		}
		sleep();

		if (!isGerenteOrAtendente) {
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("title"),
			// UNAUTHORIZED_MESSAGE));
			assertEquals(UNAUTHORIZED_MESSAGE, driver.getTitle());
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

		WebElement delete = buttons.get(index);
		wait.until(ExpectedConditions.elementToBeClickable(delete));
		delete.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.id("delete_modal")).isDisplayed();
			}
		});

		LOGGER.info("showDelete -> foi aberto, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
		By DELETE_MODAL = By.id("delete_modal");

		WebElement cancel = driver.findElement(DELETE_MODAL)
				.findElement(By.cssSelector("div.modal-footer > button.btn.btn-default"));
		wait.until(ExpectedConditions.elementToBeClickable(cancel));
		cancel.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(DELETE_MODAL));

		LOGGER.info("closeDelete -> foi fechado, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Tenta salva dos dados do formulário
	 */
	protected void saveCreateAndEditPage() {
		final By SAVE = By.cssSelector("div#actions > div > input.btn.btn-primary");
		wait.until(ExpectedConditions.elementToBeClickable(SAVE));
		driver.findElement(SAVE).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelCreateAndEditPage() {
		final By CANCEL = By.cssSelector("div#actions > div > button.btn.btn-default");
		wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
		driver.findElement(CANCEL).click();
		sleep();
	}

	protected void sleep() {
		if (sleep)
			try {
				Thread.sleep(Short.valueOf(System.getProperty("sleep.millisecounds")));
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
	}

	protected static Fairy getFairy() {
		return fairy;
	}

	protected static BaseProducer getBaseProducer() {
		return baseProducer;
	}

	protected static String getBaseUrl() {
		return baseUrl;
	}

	protected static WebDriver getDriver() {
		return driver;
	}

	// private JavascriptExecutor getJS(WebDriver driver) {
	// if (!(driver instanceof JavascriptExecutor))
	// throw new IllegalStateException("This driver does not support
	// JavaScript!");
	//
	// return (JavascriptExecutor) driver;
	// }

	// protected static JavascriptExecutor getJS() {
	// return js;
	// }

	// private WebDriverWait getWait(WebDriver driver) {
	// return new WebDriverWait(driver, 5);
	// }

	protected static WebDriverWait getWait() {
		return wait;
	}

	protected abstract String getMainPage();

	private void setDriverAndUrl() {
		final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";
		final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
		final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
		final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";

		if (System.getProperty(WEBDRIVER_CHROME_DRIVER) != null) {
			driver = new ChromeDriver();
			LOGGER.info("{}: {}", WEBDRIVER_CHROME_DRIVER, System.getProperty(WEBDRIVER_CHROME_DRIVER));
		} else if (System.getProperty(WEBDRIVER_GECKO_DRIVER) != null) {
			driver = new FirefoxDriver();
			LOGGER.info("{}: {}", WEBDRIVER_GECKO_DRIVER, System.getProperty(WEBDRIVER_GECKO_DRIVER));
		} else if (System.getProperty(WEBDRIVER_IE_DRIVER) != null) {
			driver = new InternetExplorerDriver();
			LOGGER.info("{}: {}", WEBDRIVER_IE_DRIVER, System.getProperty(WEBDRIVER_IE_DRIVER));
		} else if (System.getProperty(PHANTOMJS_BINARY_PATH) != null) {
			driver = new PhantomJSDriver();
			LOGGER.info("{}: {}", PHANTOMJS_BINARY_PATH, System.getProperty(PHANTOMJS_BINARY_PATH));

			sleep = false;
		} else {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.manage().window().maximize();

		baseUrl = System.getProperty("base.url");
		LOGGER.info("url: {}", baseUrl);
	}

	private void login(String username, String password) {
		driver.get(baseUrl);

		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys(password);
		// driver.findElement(By.id("lembre_me")).click();
		final By LOGIN = By.xpath("//input[@value='Entrar']");
		wait.until(ExpectedConditions.elementToBeClickable(LOGIN));
		driver.findElement(LOGIN).click();

		sleep();
	}

	private void logout() {
		final By LOGOUT = By.cssSelector("span.glyphicon.glyphicon-log-out");
		wait.until(ExpectedConditions.elementToBeClickable(LOGOUT));
		driver.findElement(LOGOUT).click();

		sleep();
	}

	private String getId() {
		final By ROW = By.cssSelector("table.dataTable > tbody > tr");
		final List<WebElement> buttons = driver.findElements(ROW);
		wait.until(ExpectedConditions.presenceOfElementLocated(ROW));
		int rowIndex = getBaseProducer().randomBetween(1, buttons.size());

		return getId(driver, rowIndex);
	}

	private String getId(WebDriver driver, int rowIndex) {
		final By CELL = By.cssSelector("table.dataTable > tbody > tr:nth-child(" + rowIndex + ") > td:first-child");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		return driver.findElement(CELL).getText();
	}

	private String getMaxResults(WebDriver driver) {
		final By MAX_RESULTS = By.xpath("//input[@id='dataTable_maxResults']");
		wait.until(ExpectedConditions.presenceOfElementLocated(MAX_RESULTS));
		return driver.findElement(MAX_RESULTS).getAttribute("value");
	}

	private String getCount(WebDriver driver) {
		final By COUNT = By.xpath("//input[@id='dataTable_count']");
		wait.until(ExpectedConditions.presenceOfElementLocated(COUNT));
		return driver.findElement(COUNT).getAttribute("value");
	}

	private String getSortOrder(WebDriver driver, String field) {
		final By CELL = By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		return driver.findElement(CELL).getAttribute("class");
	}

}
