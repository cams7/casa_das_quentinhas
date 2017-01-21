package br.com.cams7.casa_das_quentinhas;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.BeforeSuite;

public abstract class AbstractTest implements BaseTest {

	// private boolean acceptNextAlert = true;
	// private StringBuffer verificationErrors = new StringBuffer();

	private static WebDriver driver;
	private static String baseUrl;

	private static boolean sleepEnabled = false;

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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		login("gerente@casa-das-quentinhas.com", "12345");
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

		LOGGER.info("maxResults: {}, count: {}, total pages: {}", maxResults, count, totalPages);

		for (int i = 2; i <= totalPages; i++) {
			getJS().executeScript("$('ul.pagination li a').eq( " + i + " ).click();");
			sleep();
		}

		sleep();
	}

	protected void search(String query) {
		getJS().executeScript("$('input#search_query').val('" + query + "');$('button#search_btn').click();");
		sleep();
	}

	/**
	 * Vai para página de cadastro
	 */
	protected void goToCreatePage() {
		driver.findElement(By.cssSelector("a.btn.btn-primary")).click();
		sleep();
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
	protected void goToViewPage() {
		// driver.findElement(By.linkText("Visualizar")).click();
		driver.findElement(By.cssSelector("a.btn.btn-success.btn-xs")).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		// driver.findElement(By.linkText("Cancelar")).click();
		driver.findElement(By.cssSelector("a.btn.btn-default")).click();
		sleep();
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected void goToEditPage() {
		// driver.findElement(By.linkText("Alterar")).click();
		driver.findElement(By.cssSelector("a.btn.btn-warning.btn-xs")).click();
		sleep();
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
		// driver.findElement(By.xpath("//button[@value='1']")).click();
		// driver.findElement(By.cssSelector("button.btn.btn-danger.btn-xs.delete")).click();
		// sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
		// driver.findElement(By.cssSelector("div.modal-footer >
		// button.btn.btn-default")).click();
		// sleep();
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

	protected abstract void goToIndexPage();

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
