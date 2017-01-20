package br.com.cams7.casa_das_quentinhas;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeSuite;

public abstract class AbstractTest implements BaseTest {

	// private boolean acceptNextAlert = true;
	// private StringBuffer verificationErrors = new StringBuffer();

	private WebDriver driver;
	private String baseUrl;

	private boolean sleepEnabled = false;

	@BeforeClass(alwaysRun = true)
	// @AfterSuite(alwaysRun = true)
	public void setUp() {
		setDriverAndUrl();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		login();
	}

	@AfterClass(alwaysRun = true)
	// @BeforeSuite(alwaysRun = true)
	public void tearDown() {
		logout();

		driver.quit();
		// String verificationErrorString = verificationErrors.toString();
		// if (!"".equals(verificationErrorString)) {
		// fail(verificationErrorString);
		// }
	}

	protected void paginate() {
		driver.findElement(By.linkText("2")).click();
		sleep();
	}

	protected void search(String query) {
		// driver.findElement(By.id("search_query")).clear();
		// driver.findElement(By.id("search_query")).sendKeys(query);
		// driver.findElement(By.id("search_btn")).click();
		// sleep();
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
	protected void trySaveCreatePage() {
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
		driver.findElement(By.linkText("Visualizar")).click();
		sleep();
	}

	/**
	 * Vai para a página anterior
	 */
	protected void cancelViewPage() {
		driver.findElement(By.linkText("Cancelar")).click();
		sleep();
	}

	/**
	 * Vai para a página de edição dos dados
	 */
	protected void goToEditPage() {
		driver.findElement(By.linkText("Alterar")).click();
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
//		driver.findElement(By.xpath("//button[@value='1']")).click();
//		sleep();
	}

	/**
	 * Fecha o pop-up
	 */
	protected void closeDeleteModal() {
//		driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-default")).click();
//		sleep();
	}

	protected void sleep() {
		if (sleepEnabled)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
	}

	protected final WebDriver getDriver() {
		return driver;
	}

	protected abstract void goToIndexPage();

	private void setDriverAndUrl() {
		if (System.getProperty("webdriver.chrome.driver") != null) {
			driver = new ChromeDriver();
			sleepEnabled = true;
		} else
			driver = new PhantomJSDriver();

		baseUrl = System.getProperty("base.url");
	}

	private void login() {
		driver.get(baseUrl + "/login");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("gerente@casa-das-quentinhas.com");
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys("12345");
		// driver.findElement(By.id("lembre_me")).click();
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
