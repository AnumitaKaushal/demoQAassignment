package com.automation.demoqa.utils;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.demoqa.interfaces.IWebPageActions;

public class WebPageActions extends WebBrowserActions implements IWebPageActions {

	static String pageName = "";

	public WebPageActions(String PageName) {
		pageName = PageName;
	}

	public static void launchUrl(String url) {
		try {
			driver.get(url);
			WaitForPageToLoad(10);
			System.out.println("URL[" + url + "] launched");
		} catch (Exception e) {
			System.err.println("Unable to launch URL[" + url + "]");
			e.printStackTrace();
		}
	}

	public static WebElement FindElementOnWebPage(String propName) throws Exception {

		WebElement element = null;
		String propXpath = "";
		try {

			propXpath = PropUtils.fetchProperty(pageName + "." + propName, PropUtils.pathForOR);
			WaitUntilElementLocated(propXpath, ConditionalWaitTime);
			element = driver.findElement(By.xpath(propXpath));
			WaitUntilElementVisible(ConditionalWaitTime, element);
			ScrollIntoView(element);
			jsHighlighter(element);
		} catch (Exception e) {
			System.out.println("Element with Xpath[" + propXpath + "] not found");
			return null;
		}
		return element;
	}

	public List<WebElement> FindElementsOnWebPage() {

		return null;

	}

	public static void SelectFromDropDownByValue(String value, String propName) throws Exception {
		try {
			WebElement element = FindElementOnWebPage(propName);
			Select list = new Select(element);
			list.selectByValue(value);
			System.out.println("Selected option[" + value + "] from the list[" + propName + "]");
			WaitABit();
		} catch (Exception e) {
			System.out.println("Unable to select option[" + value + "] from the list[" + propName + "]");
		}
	}

	public static WebElement HoverToElementAndClick(String propName) throws Exception {
		WebElement element = FindElementOnWebPage(propName);
		try {
			
			Actions act = new Actions(driver);
			act.moveToElement(element).click(element).build().perform();
			System.out.println("Clicked on element [" + propName + "]");
			WaitABit();
		} catch (Exception e) {
			System.out.println("Unable to move to element[" + propName + "] ");
		}
		return element;
	}

	public static String GetTextForElement(String propName) throws Exception {
		String text = "";
		try {
			WebElement element = FindElementOnWebPage(propName);
			text = element.getText();
			System.out.println("Element [" + propName + "]" + "has text [" + text + "]");
		} catch (Exception e) {
			System.out.println("Unable to get text of element[" + propName + "]");
		}

		return text;
	}

	public static String GetAttributeForElement(String propName, String attribute) throws Exception {
		String attrValue = "";
		try {
			WebElement element = FindElementOnWebPage(propName);
			attrValue = element.getAttribute(attribute);
			System.out.println("Attribute value: " + attrValue);
		} catch (Exception e) {
			System.out.println("Unable to return attribute[" + attribute + "]");
			e.printStackTrace();
		}

		return attrValue;

	}

	public static String GetCSSPropertyForElement(String propName, String property) throws Exception {
		String attrValue = "";
		try {
			WebElement element = FindElementOnWebPage(propName);
			attrValue = element.getCssValue(property);
		} catch (Exception e) {
			System.out.println("Unable to return CSS Property[" + property + "]");
			e.printStackTrace();
		}

		return attrValue;

	}

	public static void uploadFile(String path) throws Exception {
		String filePath = System.getProperty("user.dir") + path;
		StringSelection stringPath = new StringSelection(filePath);

		try {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringPath, null);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			System.out.println("Unable to upload File at path[" + path + "]");
			e.printStackTrace();
		}

	}

	public static void SelectFromDropDownByVisibleText(String value, String propName) {
		try {
			WebElement element = FindElementOnWebPage(propName);
			Select list = new Select(element);
			list.selectByVisibleText(value);

			System.out.println("Selected option[" + value + "] from the list[" + propName + "]");
			WaitABit();
		} catch (Exception e) {
			System.out.println("Unable to select option[" + value + "] from the list[" + propName + "]");
		}

	}

	public static void SelectFromDropDownByIndex(int index, String propName) {
		try {
			WebElement element = FindElementOnWebPage(propName);
			Select list = new Select(element);
			list.selectByIndex(index);
		} catch (Exception e) {
			System.out.println("Unable to select option at index[" + index + "] from the list[" + propName + "]");
		}

	}

	public List<WebElement> GetAllOptionsFromList(String propName) throws Exception {
		List<WebElement> allOptions = null;
		try {
			WebElement list = FindElementOnWebPage(propName);
			Select optionsList = new Select(list);
			allOptions = optionsList.getOptions();
		} catch (Exception e) {
			System.out.println("Unable to get Options from list[" + propName + "]");
		}

		return allOptions;
	}

	public static void ClickOnElement(String propName) throws Exception {

		try {
			WebElement element = FindElementOnWebPage(propName);
			element.click();
			//WaitABit();
			System.out.println("Clicked on element [" + propName + "]");
		} catch (Exception e) {
			System.err.println("Unable to click element[" + propName + "]");
			e.printStackTrace();
		}
	}

	public static void EnterValueInTextField(String propName, String value) throws Exception {

		try {
			WebElement element = FindElementOnWebPage(propName);
			element.click();
			element.clear();
			element.sendKeys(value);
			System.out.println("Entered Value[" + value + "] in Textbox[" + propName + "]");
			//WaitABit();

		} catch (Exception e) {
			System.err.println("Unable to enter value[" + value + "] in textbox[" + propName + "]");
			e.printStackTrace();
		}
	}

	public static void WaitForPageToLoad(long seconds) {
		try {
			driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.err.println("Unable to load Page");
			e.printStackTrace();
		}
	}

	public static void WaitForElementsImplicitly(long seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.err.println("Unable to wait for element");
			e.printStackTrace();
		}
	}

	public static void WaitUntilElementVisible(long seconds, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.out.println("Element not visible on webPage: " + e.getMessage());
		}
	}

	public static void WaitUntilElementLocated(String xpath, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			System.out.println("Element not found on webPage: " + e.getMessage());
		}

	}

	public static void WaitABit() {
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			System.err.println("Unable to wait");
			e.printStackTrace();
		}
	}

	public static void jsHighlighter(WebElement element) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid red;');", element);
			Thread.sleep(300);
			//js.executeScript("arguments[0].setAttribute('style', 'border: none;');", element);
			js.executeScript("arguments[0].setAttribute('style', 'border: 1px solid black;');", element);
			Thread.sleep(300);
		} catch (Exception e) {
			System.err.println("Failed to highlight the page");
			e.printStackTrace();
		}
	}

	public static void switchToAlertAndAccept() {

		driver.switchTo().alert().accept();
	}

	public static void switchToAlertAndDismiss() {
		driver.switchTo().alert().dismiss();
	}

	public static void switchToAlertAndGetText() {

	}

	public static void EnterTextInAlertAndAccept(String keys) {
		try {
			driver.switchTo().alert().sendKeys(keys);
			switchToAlertAndAccept();
		} catch (Exception e) {
			System.err.println("Error caused in switch to method: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void showEncryptedValue(String value, String propName) throws Exception {
		
		WebElement inputvalue = FindElementOnWebPage(propName);
		inputvalue.click();
		inputvalue.clear();
		inputvalue.sendKeys(value);			
		String encryptData = value;
		byte[] encodedBytes = Base64.getEncoder().encode(encryptData.getBytes());
		System.out.println("Password/Text entered and succesfully encrypted:--------------->" + new String(encodedBytes));
	}

}
