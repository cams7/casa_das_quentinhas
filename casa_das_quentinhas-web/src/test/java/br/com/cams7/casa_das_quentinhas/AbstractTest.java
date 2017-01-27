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
import org.openqa.selenium.support.ui.ExpectedCondition;
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

	private static boolean sleep = false;

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
	 * @param menu
	 *            Titulo do menu
	 */
	protected void goToIndexPage(String menu) {
		boolean isGerente = GERENTE.equals(acesso);
		if (isGerente) {
			WebElement link = driver.findElement(By.linkText(menu));
			getWait().until(ExpectedConditions.elementToBeClickable(link));
			link.click();
		} else
			getDriver().get(baseUrl + "/" + getMainPage());

		sleep();
	}

	/**
	 * Pagina a tabela
	 */
	protected void paginate() {
		short maxResults = Short
				.valueOf(driver.findElement(By.xpath("//input[@id='dataTable_maxResults']")).getAttribute("value"));
		long count = Long.valueOf(getCount(driver));

		final String id = getId(driver, 0);

		int totalPages = (int) (count / maxResults);
		if (count % maxResults > 0)
			totalPages++;

		WebDriverWait wait = getWait();

		WebElement selectPage = getDriver().findElements(By.cssSelector("ul.pagination > li > a")).get(totalPages);
		wait.until(ExpectedConditions.elementToBeClickable(selectPage));
		selectPage.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentId = getId(driver, 0);
				return !currentId.isEmpty() && !id.equals(currentId);
			}
		});

		LOGGER.info("First id: {}, current id: {}", id, getId(driver, 0));

		sleep();
	}

	/**
	 * Pesquisa os dados
	 * 
	 * @param query
	 */
	protected void search(String query) {
		long count = Long.valueOf(getCount(driver));

		WebDriverWait wait = getWait();

		By searchInput = By.id("search_query");
		wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
		driver.findElement(searchInput).sendKeys(query);

		By searchButton = By.id("search_btn");
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		driver.findElement(searchButton).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String currentCount = getCount(driver);
				return !currentCount.isEmpty() && count > Long.valueOf(currentCount);
			}
		});

		LOGGER.info("First count: {}, current count: {}", count, getCount(driver));

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
		// final String firstField = getSortField(driver);
		// final String firstOrder = getSortOrder(driver);

		WebDriverWait wait = getWait();
		WebElement sortField = driver.findElement(By.cssSelector("table.dataTable > thead > tr"))
				.findElement(By.id(field));
		wait.until(ExpectedConditions.elementToBeClickable(sortField));
		sortField.click();

		// wait.until(new ExpectedCondition<Boolean>() {
		// public Boolean apply(WebDriver driver) {
		// String currentField = getSortField(driver);
		// String curretOrder = getSortOrder(driver);
		// LOGGER.info("First field: {}, last field: {}", firstField,
		// currentField);
		// LOGGER.info("First order: {}, last order: {}", firstOrder,
		// curretOrder);
		// return !currentField.isEmpty() && !curretOrder.isEmpty()
		// && (!firstField.equals(currentField) ||
		// !firstOrder.equals(curretOrder));
		// }
		// });

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
			getWait().until(ExpectedConditions.elementToBeClickable(create));
			create.click();
		} else {
			assertFalse(buttons.size() > 0);
			driver.get(baseUrl + "/" + getMainPage() + "/create");
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
	protected void goToViewPage() {
		final List<WebElement> buttons = driver
				.findElements(By.cssSelector("table.dataTable > tbody > tr > td > a.btn.btn-success.btn-xs"));
		int index = getBaseProducer().randomBetween(0, buttons.size() - 1);

		WebElement view = buttons.get(index);
		getWait().until(ExpectedConditions.elementToBeClickable(view));
		view.click();

		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		WebElement cancel = driver.findElement(By.cssSelector("div#actions > div > a.btn.btn-default"));
		getWait().until(ExpectedConditions.elementToBeClickable(cancel));
		cancel.click();

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
			getWait().until(ExpectedConditions.elementToBeClickable(edit));
			edit.click();
		} else {
			assertFalse(totalButtons > 0);
			driver.get(baseUrl + "/" + getMainPage() + "/" + getId() + "/edit");
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

		WebDriverWait wait = getWait();

		WebElement delete = buttons.get(index);
		wait.until(ExpectedConditions.elementToBeClickable(delete));
		delete.click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.id("delete_modal")).isDisplayed();
			}
		});

		LOGGER.info("Foi aberto, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
		By DELETE_MODAL = By.id("delete_modal");

		WebDriverWait wait = getWait();

		WebElement cancel = driver.findElement(DELETE_MODAL)
				.findElement(By.cssSelector("div.modal-footer > button.btn.btn-default"));
		wait.until(ExpectedConditions.elementToBeClickable(cancel));
		cancel.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(DELETE_MODAL));

		LOGGER.info("Foi fechado, o modal panel de exclusão");

		sleep();
	}

	/**
	 * Tenta salva dos dados do formulário
	 */
	protected void saveCreateAndEditPage() {
		WebElement save = driver.findElement(By.cssSelector("div#actions > div > input.btn.btn-primary"));
		getWait().until(ExpectedConditions.elementToBeClickable(save));
		save.click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelCreateAndEditPage() {
		WebElement cancel = driver.findElement(By.cssSelector("div#actions > div > button.btn.btn-default"));
		getWait().until(ExpectedConditions.elementToBeClickable(cancel));
		cancel.click();
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

	protected final WebDriverWait getWait(WebDriver driver) {
		return new WebDriverWait(driver, 5);
	}

	protected final WebDriverWait getWait() {
		return getWait(getDriver());
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

	protected abstract String getMainPage();

	private void setDriverAndUrl() {
		if (System.getProperty("webdriver.chrome.driver") != null) {
			driver = new ChromeDriver();
			sleep = true;
			LOGGER.info("webdriver.chrome.driver: {}", System.getProperty("webdriver.chrome.driver"));
		} else {
			driver = new PhantomJSDriver();
			LOGGER.info("phantomjs.binary.path: {}", System.getProperty("phantomjs.binary.path"));
		}

		baseUrl = System.getProperty("base.url");
		LOGGER.info("url: {}", baseUrl);
	}

	private void login(String username, String password) {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys(password);
		driver.findElement(By.id("lembre_me")).click();
		WebElement login = driver.findElement(By.xpath("//input[@value='Entrar']"));
		getWait().until(ExpectedConditions.elementToBeClickable(login));
		login.click();
	}

	private void logout() {
		WebElement logout = driver.findElement(By.cssSelector("span.glyphicon.glyphicon-log-out"));
		getWait().until(ExpectedConditions.elementToBeClickable(logout));
		logout.click();
	}

	private String getId() {
		final By ROW = By.cssSelector("table.dataTable > tbody > tr");
		final List<WebElement> buttons = driver.findElements(ROW);
		getWait().until(ExpectedConditions.presenceOfElementLocated(ROW));
		int index = getBaseProducer().randomBetween(0, buttons.size() - 1);

		return getId(driver, index);
	}

	private String getId(WebDriver driver, int rowIndex) {
		final By ROW = By.cssSelector("table.dataTable > tbody > tr");
		WebElement cell = driver.findElements(ROW).get(rowIndex).findElements(By.tagName("td")).get(0);
		getWait(driver).until(ExpectedConditions.presenceOfElementLocated(ROW));
		return cell.getText();
	}

	private String getCount(WebDriver driver) {
		return driver.findElement(By.xpath("//input[@id='dataTable_count']")).getAttribute("value");
	}

	private String getSortField(WebDriver driver) {
		return driver.findElement(By.xpath("//input[@id='dataTable_sortField']")).getAttribute("value");
	}

	private String getSortOrder(WebDriver driver) {
		return driver.findElement(By.xpath("//input[@id='dataTable_sortOrder']")).getAttribute("value");
	}

}
