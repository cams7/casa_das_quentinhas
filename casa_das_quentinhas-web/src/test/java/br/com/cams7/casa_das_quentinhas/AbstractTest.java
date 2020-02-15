package br.com.cams7.casa_das_quentinhas;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getAcessoByEmail;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getEmails;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getQualquerEmailAcesso;
import static br.com.cams7.casa_das_quentinhas.mock.UsuarioMock.getSenhaAcesso;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;

public abstract class AbstractTest implements BaseTest {

	protected final Logger LOGGER;
	protected final static String NUMBER_REGEX = "\\d+";

	private final static Fairy fairy;
	private final static BaseProducer baseProducer;

	private static String baseUrl;

	private static WebDriver driver;
	private static JavascriptExecutor js;
	private static Wait<WebDriver> wait;

	private static boolean sleep;

	private Object acesso;

	private final String UNAUTHORIZED_MESSAGE = "Não autorizado";

	static {
		sleep = true;

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

	@BeforeTest
	@Parameters({ "browser" })
	public void setUp(String browser) throws MalformedURLException {
		setDriverAndUrl(browser);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		js = getJS(driver);
		wait = new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class);
	}

	@AfterTest
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
		if (GERENTE.equals(acesso)) {
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
		paginate();
	}

	/**
	 * Pagina a tabela
	 */
	protected final void paginate() {
		final String ACTIVE_PAGE = getActivePage();

		final By PAGES = By.cssSelector("ul.pagination > li > a");
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(PAGES));
		final String SELECTED_PAGE = baseProducer.randomElement(driver.findElements(PAGES).stream().filter(link -> {
			try {
				Integer.parseInt(link.getText());
				return true;
			} catch (NumberFormatException e) {
			}
			return false;
		}).map(link -> link.getText()).toArray(size -> new String[size]));

		LOGGER.info("paginate -> active page: {}, selected page: {}", ACTIVE_PAGE, SELECTED_PAGE);

		if (!ACTIVE_PAGE.equals(SELECTED_PAGE)) {
			WebElement selectPage = driver.findElement(By.cssSelector("ul.pagination > li"))
					.findElement(By.xpath("//a[text()='" + SELECTED_PAGE + "']"));
			wait.until(ExpectedConditions.elementToBeClickable(selectPage));
			selectPage.click();

			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					final String CURRENT_PAGE = getActivePage(driver);
					return !(CURRENT_PAGE.isEmpty() || ACTIVE_PAGE.equals(CURRENT_PAGE));
				}
			});

			final String CURRENT_PAGE = getActivePage();

			assertFalse(CURRENT_PAGE.isEmpty());
			assertNotSame(ACTIVE_PAGE, CURRENT_PAGE);

			LOGGER.info("paginate -> first page: {}, current page: {}", ACTIVE_PAGE, CURRENT_PAGE);

			sleep();
		}
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected final void search() {
		final long FIRST_COUNT = Long.valueOf(getCount());

		final String QUERY = "an";

		final By SEARCH_INPUT = By.id("search_query");
		wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
		driver.findElement(SEARCH_INPUT).sendKeys(QUERY);

		final By SEARCH_BUTTON = By.id("search_btn");
		wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
		driver.findElement(SEARCH_BUTTON).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				final String CURRENT_COUNT = getCount(driver);
				return !CURRENT_COUNT.isEmpty() && FIRST_COUNT > Long.valueOf(CURRENT_COUNT);
			}
		});

		final String CURRENT_COUNT = getCount();

		assertFalse(CURRENT_COUNT.isEmpty());
		assertTrue(FIRST_COUNT > Long.valueOf(CURRENT_COUNT));

		LOGGER.info("search '{}' -> first count: {}, current count: {}", QUERY, FIRST_COUNT, CURRENT_COUNT);

		sleep();
	}

	/**
	 * Ordena todos o campo informado
	 * 
	 * @param field
	 *            Campo da tabela
	 */
	protected final void sortField(final String field) {
		final String FIRST_ORDER = getSortOrder(field);

		final By CELL = getCellByField(field);
		wait.until(ExpectedConditions.elementToBeClickable(CELL));
		driver.findElement(CELL).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				final String CURRENT_ORDER = getSortOrder(driver, field);
				return !(CURRENT_ORDER.isEmpty() || FIRST_ORDER.equals(CURRENT_ORDER));
			}
		});

		final String CURRENT_ORDER = getSortOrder(field);

		assertFalse(CURRENT_ORDER.isEmpty());
		assertNotSame(FIRST_ORDER, CURRENT_ORDER);

		LOGGER.info("sort field '{}' -> first order: {}, current order: {}", field, FIRST_ORDER, CURRENT_ORDER);

		sleep();
	}

	/**
	 * Vai para página de cadastro
	 */
	protected final void goToCreatePage() {
		final boolean IS_GERENTE = GERENTE.equals(acesso);

		if (IS_GERENTE) {
			LOGGER.info("got to create page");

			final By CREATE_LINK = getCreateLink();
			wait.until(ExpectedConditions.elementToBeClickable(CREATE_LINK));
			driver.findElement(CREATE_LINK).click();
		} else {
			assertFalse(isVisibleCreateLink());

			final String URL = baseUrl + "/" + getMainPage() + "/create";
			LOGGER.info("got to create page {}", URL);
			driver.get(URL);
		}
		sleep();

		if (!IS_GERENTE) {
			assertEquals(UNAUTHORIZED_MESSAGE, driver.getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Vai para a página de visualização dos dados
	 */
	protected final void goToViewPage() {
		final int TOTAL_ROWS = getTotalTableViewRows();
		final int ROW = getRandomRow(TOTAL_ROWS);
		LOGGER.info("go to view page -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

		By VIEW_LINK = getTableViewLink(ROW);
		wait.until(ExpectedConditions.elementToBeClickable(VIEW_LINK));
		driver.findElement(VIEW_LINK).click();

		sleep();
	}

	boolean canBeDeleted = true;

	/**
	 * Vai para a página anterior
	 */
	protected final void cancelOrDeleteViewPage(final boolean onlyCancel) {
		if (!onlyCancel && GERENTE.equals(acesso) && baseProducer.trueOrFalse()) {
			final By DELETE = getViewDeleteButton();
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
				assertFalse(isVisibleDeleteButton());

			final By CANCEL = getCancelButton();
			wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
			driver.findElement(CANCEL).click();

			sleep();
		}
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected final void goToEditPage() {
		final boolean IS_GERENTE_OR_ATENDENTE = GERENTE.equals(acesso) || ATENDENTE.equals(acesso);

		final int TOTAL_ROWS = getTotalTableEditRows();

		if (IS_GERENTE_OR_ATENDENTE) {
			final int ROW = getRandomRow(TOTAL_ROWS);
			LOGGER.info("go to edit page -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

			final By EDIT_LINK = getTableEditLink(ROW);
			wait.until(ExpectedConditions.elementToBeClickable(EDIT_LINK));
			driver.findElement(EDIT_LINK).click();
		} else {
			assertFalse(TOTAL_ROWS > 0);

			final String URL = baseUrl + "/" + getMainPage() + "/" + getId() + "/edit";
			LOGGER.info("got to edit page {}", URL);
			driver.get(URL);
		}
		sleep();

		if (!IS_GERENTE_OR_ATENDENTE) {
			assertEquals(UNAUTHORIZED_MESSAGE, driver.getTitle());
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}
	}

	/**
	 * Exibe e fecha pop-pop de exclusão
	 */
	protected final void showAndCloseDeleteModal() {
		final int TOTAL_ROWS = getTotalTableDeleteRows();

		if (!GERENTE.equals(acesso)) {
			assertFalse(TOTAL_ROWS > 0);
			throw new SkipException(UNAUTHORIZED_MESSAGE);
		}

		final int ROW = getRandomRow(TOTAL_ROWS);
		LOGGER.info("show and close delete modal -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

		final By DELETE_BUTTON = getTableDeleteButton(ROW);
		wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
		driver.findElement(DELETE_BUTTON).click();

		// Exibe o modal panel de exclusão
		showDeleteModal();

		// Fecha o modal panel de exclusão
		closeDeleteModal(true, canBeDeleted(ROW));
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
		final By CANCEL = getCancelButton();
		wait.until(ExpectedConditions.elementToBeClickable(CANCEL));
		driver.findElement(CANCEL).click();
		sleep();
	}

	protected final void testList(String... fields) {
		// Ordena, aleatoriamente, um campo da tabela
		sortField(baseProducer.randomElement(fields));

		// Vai para um pagina aleatória da tabela
		paginate();
	}

	protected final void testList(final String[] fields, final String viewTitle, final String editTitle) {
		testList(fields);

		goToViewPage();
		assertEquals(viewTitle, driver.getTitle());

		cancelOrDeleteViewPage(true);
		assertEquals(getViewTitle(), driver.getTitle());

		if (GERENTE.equals(getAcesso()) || ATENDENTE.equals(getAcesso())) {
			goToEditPage();
			assertEquals(editTitle, driver.getTitle());

			cancelCreateOrEditPage();
			assertEquals(getViewTitle(), driver.getTitle());
		} else
			assertFalse(getTotalTableEditRows() > 0);

		if (GERENTE.equals(getAcesso()))
			showAndCloseDeleteModal();
		else
			assertFalse(getTotalTableDeleteRows() > 0);

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

	protected void validateId(final By ID, final boolean isEmpty) {
		wait.until(ExpectedConditions.presenceOfElementLocated(ID));
		final String VALUE = driver.findElement(ID).getAttribute("value");
		if (isEmpty)
			assertTrue(VALUE.isEmpty());
		else
			assertTrue(VALUE.matches(NUMBER_REGEX));
	}

	protected final String getRandomLetter() {
		return String.valueOf(baseProducer.randomBetween('a', 'z'));
	}

	protected final int getRandomRow(final int totalRows) {
		return baseProducer.randomBetween(1, totalRows);
	}

	protected final By getCreateLink() {
		return By.cssSelector("div#top > div.h2 > a.btn.btn-primary");
	}

	protected final boolean isVisibleCreateLink() {
		// return (Boolean) js.executeScript("return $('div#top > div.h2 >
		// a.btn.btn-primary').length > 0;");
		final By CREATE_LINK = getCreateLink();
		return !driver.findElements(CREATE_LINK).isEmpty();
	}

	protected final By getTableViewLink(int row) {
		return By.cssSelector("table.dataTable > tbody > tr:nth-child(" + row + ") > td > a.btn.btn-success.btn-xs");
	}

	protected final int getTotalTableViewRows() {
		// return ((Long) js
		// .executeScript("return $('table.dataTable > tbody > tr > td >
		// a.btn.btn-success.btn-xs').length;"))
		// .intValue();
		final By VIEW_LINK = By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs");
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(VIEW_LINK));
		return driver.findElements(VIEW_LINK).size();

	}

	protected final By getTableEditLink(int row) {
		return By.cssSelector("table.dataTable > tbody > tr:nth-child(" + row + ") > td > a.btn.btn-warning.btn-xs");
	}

	protected final int getTotalTableEditRows() {
		// return ((Long) js
		// .executeScript("return $('table.dataTable > tbody > tr > td >
		// a.btn.btn-warning.btn-xs').length;"))
		// .intValue();
		final By EDIT_LINK = By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-warning.btn-xs");
		return driver.findElements(EDIT_LINK).size();
	}

	protected final By getTableDeleteButton(int row) {
		return By
				.cssSelector("table.dataTable > tbody > tr:nth-child(" + row + ") > td > button.btn.btn-danger.btn-xs");
	}

	protected final int getTotalTableDeleteRows() {
		// return ((Long) js
		// .executeScript("return $('table.dataTable > tbody > tr > td >
		// button.btn.btn-danger.btn-xs').length;"))
		// .intValue();
		final By DELETE_BUTTON = By.cssSelector("table.dataTable > tbody > tr > td > button.btn.btn-danger.btn-xs");
		return driver.findElements(DELETE_BUTTON).size();
	}

	protected final By getViewDeleteButton() {
		return By.cssSelector("div#actions > div > button.btn.btn-danger");
	}

	protected final boolean isVisibleDeleteButton() {
		// return (Boolean) js.executeScript("return $('div#actions > div >
		// button.btn.btn-danger').length > 0;");
		final By DELETE_BUTTON = getViewDeleteButton();
		return !driver.findElements(DELETE_BUTTON).isEmpty();
	}

	// protected final By getViewCancelLink() {
	// return By.cssSelector("div#actions > div > a.btn.btn-default");
	// }

	protected final By getCreateOrEditSubmit() {
		return By.cssSelector("div#actions > div > input.btn.btn-primary");
	}

	protected final By getCancelButton() {
		return By.cssSelector("div#actions > div > button.btn.btn-default");
	}

	protected final By getModalDeleteSubmit() {
		return By.cssSelector("div.modal-footer > input.btn.btn-primary");
	}

	protected final By getModalDeleteCancelButton() {
		return By.cssSelector("div.modal-footer > button.btn.btn-default");
	}

	protected final String getCount() {
		return getCount(driver, true);
	}

	protected final String getCount(WebDriver driver) {
		return getCount(driver, false);
	}

	protected final int getTotalTableRows() {
		// return ((Long) js.executeScript("return $('table.dataTable >
		// tbody > tr').length;")).intValue();
		final By ROWS = By.cssSelector("table.dataTable > tbody > tr");
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ROWS));
		return driver.findElements(ROWS).size();
	}

	protected final void sleep() {
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

	protected final JavascriptExecutor getJS(WebDriver driver) {
		if (!(driver instanceof JavascriptExecutor))
			throw new IllegalStateException("This driver does not support JavaScript!");

		return (JavascriptExecutor) driver;
	}

	protected static JavascriptExecutor getJS() {
		return js;
	}

	protected static Wait<WebDriver> getWait() {
		return wait;
	}

	protected final Object getAcesso() {
		return acesso;
	}

	protected abstract String getMainPage();

	protected abstract String getViewTitle();

	protected abstract String[] getFields();

	private void setDriverAndUrl(String browser) throws MalformedURLException {		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME, browser);
		dc.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

		final String WEBDRIVER_URL = System.getProperty("webdriver.url");
		URL url = new URL(WEBDRIVER_URL);
		driver = new RemoteWebDriver(url, dc);

		driver.manage().window().maximize();

		baseUrl = System.getProperty("base.url");
		LOGGER.info("webdriver: {}, url: {}", WEBDRIVER_URL, baseUrl);		
	}

	private void login(String username, String password) {
		driver.get(baseUrl);

		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys(password);

		final By LEMBRE_ME = By.id("lembre_me");
		wait.until(ExpectedConditions.elementToBeClickable(LEMBRE_ME));
		driver.findElement(LEMBRE_ME).click();

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
		final By DELETE_MODAL = By.id("delete_modal");

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
			}

		}

		sleep();
	}

	private String getId() {
		final int TOTAL_ROWS = getTotalTableRows();
		final int ROW = getRandomRow(TOTAL_ROWS);

		LOGGER.info("get id -> total rows: {}, row: {}", TOTAL_ROWS, ROW);

		final By CELL = By.cssSelector("table.dataTable > tbody > tr:nth-child(" + ROW + ") > th:first-child");
		wait.until(ExpectedConditions.textMatches(CELL, Pattern.compile(NUMBER_REGEX)));
		return driver.findElement(CELL).getText();
	}

	private String getCount(WebDriver driver, boolean wait) {
		if (wait)
			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return getCount(driver).matches(NUMBER_REGEX);
				}
			});

		return driver.findElement(By.xpath("//input[@id='dataTable_count']")).getAttribute("value");
	}

	private String getSortOrder(final String field) {
		return getSortOrder(driver, true, field);
	}

	private String getSortOrder(WebDriver driver, final String field) {
		return getSortOrder(driver, false, field);
	}

	private final List<String> SORTING_CLASSES = Arrays.asList("sorting", "sorting_asc", "sorting_desc");

	private String getSortOrder(WebDriver driver, boolean wait, final String field) {
		if (wait) {
			getWait().until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					final String CURRENT_ORDER = getSortOrder(driver, field);
					return !CURRENT_ORDER.isEmpty() && SORTING_CLASSES.contains(CURRENT_ORDER);
				}
			});
		}

		return driver.findElement(getCellByField(field)).getAttribute("class");
	}

	private By getCellByField(final String field) {
		return By.cssSelector("table.dataTable > thead > tr:first-child > th[id='" + field + "']");
	}

	private String getActivePage() {
		return getActivePage(driver, true);
	}

	private String getActivePage(WebDriver driver) {
		return getActivePage(driver, false);
	}

	private String getActivePage(WebDriver driver, boolean wait) {
		final By ACTIVE_PAGE = By.cssSelector("ul.pagination > li.active > a");
		if (wait)
			getWait().until(ExpectedConditions.textMatches(ACTIVE_PAGE, Pattern.compile(NUMBER_REGEX)));

		return driver.findElement(ACTIVE_PAGE).getText();
	}

}
