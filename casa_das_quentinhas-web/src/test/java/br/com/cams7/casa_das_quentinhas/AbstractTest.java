package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getAcessoByEmail;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getEmails;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getQualquerEmailAcesso;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.util.Arrays;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;

public abstract class AbstractTest implements BaseTest {

	private final static Fairy fairy;
	private final static BaseProducer baseProducer;

	protected final Logger LOGGER;

	private static String baseUrl;

	private static WebDriver driver;
	private static JavascriptExecutor js;
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

	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		setDriverAndUrl();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		js = getJS(driver);
		wait = new WebDriverWait(driver, 5);
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@BeforeClass(alwaysRun = true)
	public void begin() {
		final String EMAIL_ACESSO = getQualquerEmailAcesso();
		login(EMAIL_ACESSO, getSenhaAcesso());

		acesso = getAcessoByEmail(EMAIL_ACESSO);

		LOGGER.info("begin -> e-mail: {}, acesso: {}", EMAIL_ACESSO, acesso);
	}

	@AfterClass(alwaysRun = true)
	public void end() {
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
		} else {
			final String URL = baseUrl + "/" + getMainPage();
			LOGGER.info("create -> got to {}", URL);
			driver.get(URL);
		}

		sleep();

		// Ordena, aleatoriamente, um campo da tabela
		sortField(baseProducer.randomElement(getFields()));

		// Vai para um pagina aleatória da tabela
		paginate();
	}

	/**
	 * Pagina a tabela
	 */
	protected void paginate() {
		String firstId = getId(1);

		final By PAGES = By.cssSelector("ul.pagination > li > a");
		// wait.until(ExpectedConditions.presenceOfElementLocated(PAGES));
		String[] pages = driver.findElements(PAGES).stream().filter(link -> {
			try {
				Integer.parseInt(link.getText());
				return true;
			} catch (NumberFormatException e) {
			}
			return false;
		}).map(link -> link.getText()).toArray(size -> new String[size]);

		String currentPage = getCurrentPage();
		String page = baseProducer.randomElement(pages);
		if (currentPage.equals(page))
			return;

		// LOGGER.info("paginate -> current page: {}, page: {}", currentPage,
		// page);

		WebElement selectPage = driver.findElement(By.cssSelector("ul.pagination > li"))
				.findElement(By.xpath("//a[text()='" + page + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(selectPage));
		selectPage.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentId = getId(driver,
						By.cssSelector("table.dataTable > tbody > tr:nth-child(1) > td:first-child"));
				return !(currentId.isEmpty() || firstId.equals(currentId));
			}
		});

		LOGGER.info("paginate -> first id: {}, current id: {}", firstId, getId(1));

		sleep();
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected void search(String query) {
		long firstCount = Long.valueOf(getCount());

		final By SEARCH_INPUT = By.id("search_query");
		wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
		driver.findElement(SEARCH_INPUT).sendKeys(query);

		final By SEARCH_BUTTON = By.id("search_btn");
		wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
		driver.findElement(SEARCH_BUTTON).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentCount = getCount(driver, By.xpath("//input[@id='dataTable_count']"));
				return !currentCount.isEmpty() && firstCount > Long.valueOf(currentCount);
			}
		});

		LOGGER.info("search '{}' -> first count: {}, current count: {}", query, firstCount, getCount());

		sleep();
	}

	/**
	 * Ordena todos o campo informado
	 * 
	 * @param field
	 *            Campo da tabela
	 */
	protected void sortField(String field) {
		String firstOrder = getSortOrder(field);

		final By CELL = By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
		wait.until(ExpectedConditions.elementToBeClickable(CELL));
		driver.findElement(CELL).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentOrder = getSortOrder(driver,
						By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']"));
				return !(currentOrder.isEmpty() || firstOrder.equals(currentOrder));

			}
		});

		LOGGER.info("sort '{}' -> first order: {}, current order: {}", field, firstOrder, getSortOrder(field));

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

	boolean canBeDeleted = true;

	/**
	 * Vai para a página anterior
	 */
	protected void cancelOrDeleteViewPage() {
		final By DELETE = By.cssSelector("div#actions > div > button.btn.btn-danger");

		if (GERENTE.equals(acesso) /* && baseProducer.trueOrFalse() */) {
			wait.until(ExpectedConditions.elementToBeClickable(DELETE));
			driver.findElement(DELETE).click();

			// Exibe o pop-pop de exclusão
			showDeleteModal();

			driver.findElements(By.cssSelector("div.row > div > p > strong")).stream()
					.filter(label -> "E-mail".equals(label.getText())).findFirst().ifPresent(label -> {
						String email = label.findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*"))
								.findElement(By.cssSelector("p:nth-child(2)")).getText();
						LOGGER.info("cancelOrDeleteViewPage -> email: {}", email);
						canBeDeleted = canBeChanged(email);
					});

			// Fecha o pop-pop de exclusão
			closeDeleteModal(false, canBeDeleted);
		} else {
			if (!GERENTE.equals(acesso))
				assertTrue(driver.findElements(DELETE).isEmpty());

			final By CANCEL = By.cssSelector("div#actions > div > a.btn.btn-default");
			wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
			driver.findElement(CANCEL).click();
		}

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
			final String URL = baseUrl + "/" + getMainPage() + "/" + getId() + "/edit";
			LOGGER.info("edit -> got to {}", URL);
			driver.get(URL);
		}
		sleep();

		if (!isGerenteOrAtendente) {
			assertEquals(UNAUTHORIZED_MESSAGE, driver.getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Exibe e fecha pop-pop de exclusão
	 */
	protected void showAndCloseDeleteModal() {
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

		// Exibe o pop-pop de exclusão
		showDeleteModal();

		// Fecha o pop-pop de exclusão
		closeDeleteModal(true, canBeDeleted(index + 1));
	}

	private void showDeleteModal() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete_modal")));

		LOGGER.info("showDeleteModal -> foi aberto, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	private void closeDeleteModal(boolean isListPage, boolean canBeDeleted) {
		By DELETE_MODAL = By.id("delete_modal");

		WebElement modal = driver.findElement(DELETE_MODAL);
		boolean deleted = false;
		if (canBeDeleted && baseProducer.trueOrFalse()) {
			WebElement delete = modal.findElement(By.cssSelector("div.modal-footer > input.btn.btn-primary"));
			wait.until(ExpectedConditions.elementToBeClickable(delete));
			delete.submit();

			deleted = true;
		} else {
			WebElement cancel = modal.findElement(By.cssSelector("div.modal-footer > button.btn.btn-default"));
			wait.until(ExpectedConditions.elementToBeClickable(cancel));
			cancel.click();
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(DELETE_MODAL));

		LOGGER.info("closeDeleteModal -> foi fechado, o modal panel de exclusão");

		if (deleted) {
			if (isListPage) {
				final By ALERT = By.cssSelector("div.alert");
				wait.until(ExpectedConditions.visibilityOfElementLocated(ALERT));

				String message = driver.findElement(ALERT).findElement(By.tagName("span")).getText();
				assertFalse(message.isEmpty());

				LOGGER.info("closeDeleteModal -> message: {}", message);
			} //else wait.until(ExpectedConditions.titleContains("Lista de"));

		}

		sleep();
	}

	/**
	 * Salva os dados ou cancela
	 */
	protected void saveOrCancel() {
		if (baseProducer.trueOrFalse())
			// Tenta salvar os dados do cliente
			saveCreateOrEditPage();
		else
			// Volta à página anterior
			cancelCreateOrEditPage();
	}

	/**
	 * Tenta salva dos dados do formulário
	 */
	protected void saveCreateOrEditPage() {
		final By SAVE = By.cssSelector("div#actions > div > input.btn.btn-primary");
		wait.until(ExpectedConditions.elementToBeClickable(SAVE));
		driver.findElement(SAVE).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelCreateOrEditPage() {
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

	private JavascriptExecutor getJS(WebDriver driver) {
		if (!(driver instanceof JavascriptExecutor))
			throw new IllegalStateException("This driver does not support JavaScript!");

		return (JavascriptExecutor) driver;
	}

	protected static JavascriptExecutor getJS() {
		return js;
	}

	protected static WebDriverWait getWait() {
		return wait;
	}

	protected final Object getAcesso() {
		return acesso;
	}

	protected abstract String getMainPage();

	protected abstract String[] getFields();

	private void setDriverAndUrl() {
		final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";
		final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
		final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
		final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";

		if (System.getProperty(PHANTOMJS_BINARY_PATH) != null) {
			driver = new PhantomJSDriver();
			LOGGER.info("{}: {}", PHANTOMJS_BINARY_PATH, System.getProperty(PHANTOMJS_BINARY_PATH));

			sleep = false;
		} else if (System.getProperty(WEBDRIVER_CHROME_DRIVER) != null) {
			driver = new ChromeDriver();
			LOGGER.info("{}: {}", WEBDRIVER_CHROME_DRIVER, System.getProperty(WEBDRIVER_CHROME_DRIVER));
		} else if (System.getProperty(WEBDRIVER_GECKO_DRIVER) != null) {
			driver = new FirefoxDriver();
			LOGGER.info("{}: {}", WEBDRIVER_GECKO_DRIVER, System.getProperty(WEBDRIVER_GECKO_DRIVER));
		} else {
			if (System.getProperty(WEBDRIVER_IE_DRIVER) == null)
				System.setProperty(WEBDRIVER_IE_DRIVER, "MicrosoftWebDriver.exe");

			driver = new InternetExplorerDriver();

			LOGGER.info("{}: {}", WEBDRIVER_IE_DRIVER, System.getProperty(WEBDRIVER_IE_DRIVER));
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
		driver.findElement(By.id("lembre_me")).click();
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

		return getId(rowIndex);
	}

	private String getId(int rowIndex) {
		final By CELL = By.cssSelector("table.dataTable > tbody > tr:nth-child(" + rowIndex + ") > td:first-child");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		return getId(driver, CELL);
	}

	private String getId(WebDriver driver, By cell) {
		return driver.findElement(cell).getText();
	}

	private String getCurrentPage() {
		final By OFFSET = By.xpath("//input[@id='dataTable_offset']");
		wait.until(ExpectedConditions.presenceOfElementLocated(OFFSET));
		String offset = driver.findElement(OFFSET).getAttribute("value");

		final By MAX_RESULTS = By.xpath("//input[@id='dataTable_maxResults']");
		wait.until(ExpectedConditions.presenceOfElementLocated(MAX_RESULTS));
		String maxResults = driver.findElement(MAX_RESULTS).getAttribute("value");

		int currentPage = Integer.valueOf(offset) / Integer.valueOf(maxResults) + 1;
		return String.valueOf(currentPage);

	}

	private String getCount() {
		final By COUNT = By.xpath("//input[@id='dataTable_count']");
		wait.until(ExpectedConditions.presenceOfElementLocated(COUNT));
		return getCount(driver, COUNT);
	}

	private String getCount(WebDriver driver, By count) {
		return driver.findElement(count).getAttribute("value");
	}

	private String getSortOrder(String field) {
		final By CELL = By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		return getSortOrder(driver, CELL);
	}

	private String getSortOrder(WebDriver driver, By cell) {
		return driver.findElement(cell).getAttribute("class");
	}

	protected boolean canBeDeleted(int rowIndex) {
		final By CELL = By.cssSelector("table.dataTable > tbody > tr:nth-child(" + rowIndex + ") > td:nth-child(4)");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		String email = driver.findElement(CELL).getText();

		return canBeChanged(email);
	}

	protected boolean canBeChanged(String email) {
		return !Arrays.asList(getEmails()).contains(email);
	}

}
