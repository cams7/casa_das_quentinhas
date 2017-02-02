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

	private static boolean isPhantomjs = false;

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
	protected final void goToIndexPage(final String menu) {
		boolean isGerente = GERENTE.equals(acesso);
		if (isGerente) {
			final By MENU = By.linkText(menu);
			wait.until(ExpectedConditions.elementToBeClickable(MENU));
			driver.findElement(MENU).click();
		} else {
			final String URL = baseUrl + "/" + getMainPage();
			LOGGER.info("got to index page {}", URL);
			driver.get(URL);
		}

		sleep();

		// Ordena, aleatoriamente, um campo da tabela
		sortField(baseProducer.randomElement(getFields()));

		// Vai para um pagina aleatória da tabela
		paginate(new byte[] { 1 });
	}

	/**
	 * Pagina a tabela
	 */
	protected final void paginate(final byte[] cells) {
		final By PAGES = By.cssSelector("ul.pagination > li > a");
		wait.until(ExpectedConditions.presenceOfElementLocated(PAGES));
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

		String firstValue = getValueByCellAndRow((short) 1, cells);

		WebElement selectPage = driver.findElement(By.cssSelector("ul.pagination > li"))
				.findElement(By.xpath("//a[text()='" + page + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(selectPage));
		selectPage.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentValue = getValueByCellAndRow(driver, (short) 1, cells);
				return !(currentValue.isEmpty() || firstValue.equals(currentValue));
			}
		});

		LOGGER.info("paginate -> first value: {}, current value: {}", firstValue,
				getValueByCellAndRow((short) 1, cells));

		sleep();
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected final void search(final String query) {
		long firstCount = Long.valueOf(getCount());

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

		LOGGER.info("search '{}' -> first count: {}, current count: {}", query, firstCount, getCount());

		sleep();
	}

	/**
	 * Ordena todos o campo informado
	 * 
	 * @param field
	 *            Campo da tabela
	 */
	protected final void sortField(final String field) {
		String firstOrder = getSortOrder(field);

		final By CELL = getCellByField(field);
		wait.until(ExpectedConditions.elementToBeClickable(CELL));
		driver.findElement(CELL).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentOrder = getSortOrder(driver, field);
				return !(currentOrder.isEmpty() || firstOrder.equals(currentOrder));
			}
		});

		LOGGER.info("sort field '{}' -> first order: {}, current order: {}", field, firstOrder, getSortOrder(field));

		sleep();
	}

	/**
	 * Vai para página de cadastro
	 */
	protected final void goToCreatePage() {
		boolean isGerente = GERENTE.equals(acesso);

		final List<WebElement> buttons = driver.findElements(getCreateLink());

		if (isGerente) {
			WebElement create = buttons.get(0);
			wait.until(ExpectedConditions.elementToBeClickable(create));
			create.click();
		} else {
			assertTrue(buttons.isEmpty());

			final String URL = baseUrl + "/" + getMainPage() + "/create";
			LOGGER.info("got to create page {}", URL);
			driver.get(URL);
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
	protected final void goToViewPage() {
		final List<WebElement> buttons = driver.findElements(getTableViewLink());
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
	protected final void cancelOrDeleteViewPage(final boolean onlyCancel) {
		final By DELETE = getViewDeleteButton();

		if (!onlyCancel && GERENTE.equals(acesso) && baseProducer.trueOrFalse()) {
			wait.until(ExpectedConditions.elementToBeClickable(DELETE));
			driver.findElement(DELETE).click();

			// Exibe o modal panel de exclusão
			showDeleteModal();

			driver.findElements(By.cssSelector("div.row > div > p > strong")).stream()
					.filter(label -> "E-mail".equals(label.getText())).findFirst().ifPresent(label -> {
						String email = label.findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*"))
								.findElement(By.cssSelector("p:nth-child(2)")).getText();
						canBeDeleted = canBeChanged(email);
					});

			// Fecha o modal panel de exclusão
			closeDeleteModal(false, canBeDeleted);
		} else {
			if (!GERENTE.equals(acesso))
				assertTrue(driver.findElements(DELETE).isEmpty());

			final By CANCEL = getViewCancelLink();
			wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
			driver.findElement(CANCEL).click();

			sleep();
		}
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected final void goToEditPage() {
		boolean isGerenteOrAtendente = GERENTE.equals(acesso) || ATENDENTE.equals(acesso);

		final List<WebElement> buttons = driver.findElements(getTableEditLink());
		final int totalButtons = buttons.size();

		if (isGerenteOrAtendente) {
			int index = getBaseProducer().randomBetween(0, totalButtons - 1);
			WebElement edit = buttons.get(index);
			wait.until(ExpectedConditions.elementToBeClickable(edit));
			edit.click();
		} else {
			assertTrue(buttons.isEmpty());

			final String URL = baseUrl + "/" + getMainPage() + "/" + getId() + "/edit";
			LOGGER.info("got to edit page {}", URL);
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
	protected final void showAndCloseDeleteModal() {
		boolean isGerente = GERENTE.equals(acesso);

		final List<WebElement> buttons = driver.findElements(getTableDeleteButton());
		final int totalButtons = buttons.size();

		if (!isGerente) {
			assertTrue(buttons.isEmpty());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}

		int index = getBaseProducer().randomBetween(0, totalButtons - 1);

		WebElement delete = buttons.get(index);
		wait.until(ExpectedConditions.elementToBeClickable(delete));
		delete.click();

		// Exibe o modal panel de exclusão
		showDeleteModal();

		// Fecha o modal panel de exclusão
		closeDeleteModal(true, canBeDeleted(index + 1));
	}

	/**
	 * Salva os dados ou cancela
	 */
	protected final void saveOrCancel() {
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
	protected final void saveCreateOrEditPage() {
		final By SAVE = getCreateOrEditSubmit();
		wait.until(ExpectedConditions.elementToBeClickable(SAVE));
		driver.findElement(SAVE).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected final void cancelCreateOrEditPage() {
		final By CANCEL = getCreateOrEditCancelButton();
		wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
		driver.findElement(CANCEL).click();
		sleep();
	}

	protected final void testList(final String[] fields, final byte[] cells) {
		// Ordena, aleatoriamente, um campo da tabela
		sortField(getBaseProducer().randomElement(fields));

		// Vai para um pagina aleatória da tabela
		paginate(cells);
	}

	protected final void testList(final String[] fields, final String viewTitle, final String editTitle) {
		testList(fields, new byte[] { 1 });

		goToViewPage();
		assertEquals(viewTitle, getDriver().getTitle());

		cancelOrDeleteViewPage(true);
		assertEquals(getViewTitle(), getDriver().getTitle());

		if (GERENTE.equals(getAcesso()) || ATENDENTE.equals(getAcesso())) {
			goToEditPage();
			assertEquals(editTitle, getDriver().getTitle());

			cancelCreateOrEditPage();
			assertEquals(getViewTitle(), getDriver().getTitle());
		} else
			assertTrue(getDriver().findElements(getTableEditLink()).isEmpty());

		if (GERENTE.equals(getAcesso()))
			showAndCloseDeleteModal();
		else
			assertTrue(getDriver().findElements(getTableDeleteButton()).isEmpty());

	}

	protected boolean canBeDeleted(final int row) {
		final By CELL = By.cssSelector("table.dataTable > tbody > tr:nth-child(" + row + ") > td:nth-child(4)");
		wait.until(ExpectedConditions.presenceOfElementLocated(CELL));
		String email = driver.findElement(CELL).getText();

		return canBeChanged(email);
	}

	protected final boolean canBeChanged(String email) {
		return !Arrays.asList(getEmails()).contains(email);
	}

	protected final String getRandomLetter() {
		return baseProducer.randomElement("a", "e", "o", "A", "E", "O");
	}

	protected final By getCreateLink() {
		return By.cssSelector("div#top > div.h2 > a.btn.btn-primary");
	}

	protected final By getTableViewLink() {
		return By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs");
	}

	protected final By getTableEditLink() {
		return By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-warning.btn-xs");
	}

	protected final By getTableDeleteButton() {
		return By.cssSelector("table.dataTable > tbody > tr > td > button.btn.btn-danger.btn-xs");
	}

	protected final By getViewDeleteButton() {
		return By.cssSelector("div#actions > div > button.btn.btn-danger");
	}

	protected final By getViewCancelLink() {
		return By.cssSelector("div#actions > div > a.btn.btn-default");
	}

	protected final By getCreateOrEditSubmit() {
		return By.cssSelector("div#actions > div > input.btn.btn-primary");
	}

	protected final By getCreateOrEditCancelButton() {
		return By.cssSelector("div#actions > div > button.btn.btn-default");
	}

	protected final By getModalDeleteSubmit() {
		return By.cssSelector("div.modal-footer > input.btn.btn-primary");
	}

	protected final By getModalDeleteCancelButton() {
		return By.cssSelector("div.modal-footer > button.btn.btn-default");
	}

	protected final String getCount() {
		return getCount(driver);
	}

	protected final String getCount(WebDriver driver) {
		final By COUNT = By.xpath("//input[@id='dataTable_count']");
		if (isPhantomjs)
			getWait().until(ExpectedConditions.presenceOfElementLocated(COUNT));
		return driver.findElement(COUNT).getAttribute("value");
	}

	protected final short getRandomTableRowIndex() {
		final By ROWS = By.cssSelector("table.dataTable > tbody > tr");
		getWait().until(ExpectedConditions.presenceOfElementLocated(ROWS));
		int totalRows = getDriver().findElements(ROWS).size();
		return (short) getBaseProducer().randomBetween(0, totalRows - 1);
	}

	protected final void sleep() {
		if (!isPhantomjs)
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

	protected final JavascriptExecutor getJS(WebDriver driver) {
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

	protected abstract String getViewTitle();

	protected abstract String[] getFields();

	private void setDriverAndUrl() {
		final String PHANTOMJS_BINARY_PATH = "phantomjs.binary.path";
		final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
		final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
		final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";

		if (System.getProperty(PHANTOMJS_BINARY_PATH) != null) {
			driver = new PhantomJSDriver();
			LOGGER.info("{}: {}", PHANTOMJS_BINARY_PATH, System.getProperty(PHANTOMJS_BINARY_PATH));

			isPhantomjs = true;
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

	/**
	 * Exibe o modal panel de exclusão
	 */
	private void showDeleteModal() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete_modal")));

		LOGGER.info("show delete modal -> foi aberto, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Fecha o modal panel de exclusão
	 */
	private void closeDeleteModal(final boolean isListPage, final boolean canBeDeleted) {
		By DELETE_MODAL = By.id("delete_modal");

		WebElement modal = driver.findElement(DELETE_MODAL);
		boolean deleted = false;
		if (canBeDeleted && baseProducer.trueOrFalse()) {
			WebElement delete = modal.findElement(getModalDeleteSubmit());
			wait.until(ExpectedConditions.elementToBeClickable(delete));
			delete.submit();

			deleted = true;
		} else {
			WebElement cancel = modal.findElement(getModalDeleteCancelButton());
			wait.until(ExpectedConditions.elementToBeClickable(cancel));
			cancel.click();
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(DELETE_MODAL));

		LOGGER.info("close delete modal -> foi fechado, o modal panel de exclusão");

		if (deleted) {
			if (isListPage) {
				final By ALERT = By.cssSelector("div.alert");
				wait.until(ExpectedConditions.visibilityOfElementLocated(ALERT));

				String message = driver.findElement(ALERT).findElement(By.tagName("span")).getText();
				assertFalse(message.isEmpty());

				LOGGER.info("close delete modal -> message: {}", message);
			} // else wait.until(ExpectedConditions.titleContains("Lista de"));

		}

		sleep();
	}

	private String getId() {
		return getValueByCellAndRow(getRandomTableRowIndex(), new byte[] { 1 });
	}

	private String getValueByCellAndRow(final short row, final byte[] cells) {
		return getValueByCellAndRow(driver, row, cells);
	}

	private String getValueByCellAndRow(WebDriver driver, final short row, final byte[] cells) {
		return getValueByCellAndRow(driver, row, cells, (byte) 0);
	}

	private String getValueByCellAndRow(WebDriver driver, final short row, final byte[] cells, final byte cellIndex) {
		final By CELL = By.cssSelector(
				"table.dataTable > tbody > tr:nth-child(" + row + ") > td:nth-child(" + cells[cellIndex] + ")");
		if (isPhantomjs)
			getWait().until(ExpectedConditions.presenceOfElementLocated(CELL));

		String value = driver.findElement(CELL).getText();

		if (cellIndex + 1 == cells.length)
			return value;

		return value + "_" + getValueByCellAndRow(driver, row, cells, (byte) (cellIndex + 1));

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

	private String getSortOrder(final String field) {
		return getSortOrder(driver, field);
	}

	private String getSortOrder(WebDriver driver, final String field) {
		final By CELL = getCellByField(field);
		if (isPhantomjs)
			getWait().until(ExpectedConditions.presenceOfElementLocated(CELL));
		return driver.findElement(CELL).getAttribute("class");
	}

	private By getCellByField(final String field) {
		return By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
	}

}
