
package com.automation.demoqa.utils;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import com.automation.demoqa.interfaces.IWebBrowserActions;


public class WebBrowserActions implements IWebBrowserActions {
	// Steps
	// 1. identified env properties, divided them in common and env specific
	// properties
	// 2. Using propUtils class to get properties
	// 3. Store the properties into global variables
	// 4 make driver static so that it is shared across all the page files and
	// common java classes
	static long PageLoadTime;
	static long DefaultWaitTime;
	static long ConditionalWaitTime;
	static String Browser;
	static String Environment;
	public static String URL;
	public static String Username;
	public static String Password;
	static WebDriver driver;

	static {
		InitializeCommonProperties();
		InitializeEnvironmentProperties();
		InitializeBrowser();
		maximizeBrowser();
		closeBrowser();
	}

	public static void InitializeBrowser() {
		if (Browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
					+ "\\src\\test\\resources\\driver-exes\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (Browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
					+ "\\src\\test\\resources\\driver-exes\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (Browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
					+ "\\src\\test\\resources\\driver-exes\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

	}

	public static void InitializeEnvironmentProperties() {
		String environmentPropertiesPath = System.getProperty("user.dir")
				+ "//src//main//resources//configs//" + Environment + ".properties";
		try {
			URL = PropUtils.fetchProperty("web.url", environmentPropertiesPath);
			
		} catch (Exception e) {
			System.err.println("Unable to load environment[" + Environment + "] properties");
		}
	}

	public static void InitializeCommonProperties() {
		String configFolderPath = System.getProperty("user.dir")
				+ "//src//main//resources//configs//";
		try {
			PageLoadTime = Long.parseLong(PropUtils.fetchProperty("Wait.PageLoad",
					configFolderPath + "common.properties"));
			DefaultWaitTime = Long.parseLong(PropUtils.fetchProperty("Wait.Implicit",
					configFolderPath + "common.properties"));
			ConditionalWaitTime = Long.parseLong(PropUtils.fetchProperty("Wait.Condition",
					configFolderPath + "common.properties"));
			Browser = PropUtils.fetchProperty("Web.Browser",
					configFolderPath + "common.properties");
			Environment = PropUtils.fetchProperty("Web.Environment",
					configFolderPath + "common.properties");
		} catch (Exception e) {
			System.err.println("Unable to initialize framework");
		}

	}

	public static void maximizeBrowser() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.err.println("Failed to maximize window");
			e.printStackTrace();
		}
	}

	public static void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			System.err.println("Failed to closeBrowser");
			e.printStackTrace();
		}
	}

	public static void closeAllBrowsers() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.err.println("Failed to close all browsers");
			e.printStackTrace();
		}
	}

	public static void openNewTabUsingActions() {
		try {
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).sendKeys("t").build().perform();
		} catch (Exception e) {
			System.err.println("Failed to Open New tabs");
			e.printStackTrace();
		}
	}

	public static void openNewTabUsingJSE() {
		try {
			JavascriptExecutor exec = (JavascriptExecutor) driver;
			exec.executeScript("window.open('about:blank','_blank');");
		} catch (Exception e) {
			System.err.println("Failed to open new tab");
			e.printStackTrace();
		}
	}

	public static void openNewTabUsingRobot() {
		try {
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_T);
			rob.keyRelease(KeyEvent.VK_CONTROL);
			rob.keyRelease(KeyEvent.VK_T);
		} catch (Exception e) {
			System.err.println("Failed to Open New tab");
			e.printStackTrace();
		}

	}

	public static void closeTab() {
		try {
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_W);
			rob.keyRelease(KeyEvent.VK_CONTROL);
			rob.keyRelease(KeyEvent.VK_W);
		} catch (Exception e) {
			System.err.println("Failed to closeTab");
			e.printStackTrace();
		}

	}

	public static void closeAllOtherTabs() {
		List<String> allTabs;
		try {
			allTabs = new ArrayList<String>(driver.getWindowHandles());
			int noOfTabs = allTabs.size();
			for (int i = 1; i < noOfTabs; i++) {
				driver.switchTo().window(allTabs.get(allTabs.size() - 1));
				driver.close();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (driver.getWindowHandles().size() == 1) {
				System.out.println("All Other Tabs Closed.");
			}
		} catch (Exception e) {
			System.err.println("Failed to closeAll other tabs");
			e.printStackTrace();
		}
	}

	public static void closeAllTabs() {
		List<String> allTabs;
		try {
			allTabs = new ArrayList<String>(driver.getWindowHandles());
			int noOfTabs = allTabs.size();
			for (int i = 0; i < noOfTabs; i++) {
				driver.switchTo().window(allTabs.get((allTabs.size() - i) - 1));
				driver.close();
				System.out.println("Closed tab[" + i + "]");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.err.println("Failed to close All tabs");
			e.printStackTrace();
		}
	}

	public static void switchToCurrentTab() {
		try {
			driver.switchTo().defaultContent();

		} catch (Exception e) {
			System.err.println("failed to switched to current tab");
			e.printStackTrace();
		}
	}

	// click(), doubleClick(), rightClick() or ContextClick()

	public static void doubleClick() {
		try {
			Actions action = new Actions(driver);
			action.doubleClick().build().perform();
		} catch (Exception e) {
			System.err.println("failed to double click ");
			e.printStackTrace();
		}
	}

	public static void contextClick() {
		try {
			Actions action = new Actions(driver);
			action.contextClick().build().perform();
		} catch (Exception e) {
			System.err.println("failed to perform context click");
			e.printStackTrace();
		}
	}

	public static void switchToNewTab() {
		try {
			Set<String> openTabs = driver.getWindowHandles();
			driver.switchTo().window(new ArrayList<String>(openTabs).get(1));

		} catch (Exception e) {
			System.err.println("failed to switched to new tab");
			e.printStackTrace();
		}
	}

	public static void switchToNewWindow() {
		try {
			List<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(1));
		} catch (Exception e) {
			System.err.println("failed to switched to new window");
			e.printStackTrace();
		}
	}

	public static void closeCurrentTabAndSwitchToPreviousTab() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.close();
			driver.switchTo().window(tabs.get(0));

		} catch (Exception e) {
			System.err.println("failed to close current tab and switch to previous");
			e.printStackTrace();
		}

	}

	public static void ScrollUp() {

		try {
			JavascriptExecutor jScriptObject = (JavascriptExecutor) driver;
			jScriptObject.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
			System.out.println("Scrolled Up");
		} catch (Exception e) {
			System.err.println("failed to scroll up");
			e.printStackTrace();
		}
	}

	public static void ScrollDown() {
		try {
			JavascriptExecutor jScriptObject = (JavascriptExecutor) driver;
			jScriptObject.executeScript("window.scrollBy(0, document.body.scrollHeight)"); //
		} catch (Exception e) {
			System.err.println("failed to scroll down");
			e.printStackTrace();
		}
	}

	public static void ScrollUpUsingActions() {
		try {
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).build().perform();
			System.out.println("Scrolled Up using Actions");
		} catch (Exception e) {
			System.err.println("failed to scroll up");
			e.printStackTrace();
		}
	}

	public static void ScrollDownUsingActions() {
		try {
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).sendKeys(Keys.END).build().perform();
		} catch (Exception e) {
			System.err.println("failed to scroll down");
			e.printStackTrace();
		}

	}

	public static void ScrollLeft() {
		try {
			JavascriptExecutor jScriptObject = (JavascriptExecutor) driver;
			jScriptObject.executeScript("window.scrollTo(0, document.body.scrollLeft)");
		} catch (Exception e) {
			System.err.println("failed to scroll Left");
			e.printStackTrace();
		}
	}

	public static void ScrollRight() {
		try {
			JavascriptExecutor jScriptObject = (JavascriptExecutor) driver;
			jScriptObject.executeScript("window.scrollTo(0, document.body.scrollRight)");
		} catch (Exception e) {
			System.err.println("failed to scroll right");
			e.printStackTrace();
		}
	}

	public static void ScrollIntoView(WebElement element) {
		try {
			JavascriptExecutor jScriptObject = (JavascriptExecutor) driver;
			jScriptObject.executeScript("arguments[0].scrollIntoView()", element);
		} catch (Exception e) {
			System.out.println("Could not scroll into view of element");
			e.printStackTrace();

		}
	}

	public static void GoBack() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			System.err.println("failed to navigate back");
			e.printStackTrace();
		}
	}

	public static void MoveForward() {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			System.err.println("failed to navigate forward"); // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void RefreshPage() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			System.err.println("failed to refresh page");
			e.printStackTrace();
		}
	}

	public static String GetTitle() {
		String title = "";
		try {
			title = driver.getTitle();
			System.out.println("The title of current page is " + title);
		} catch (Exception e) {
			System.err.println("failed to get title of the page");
			e.printStackTrace();
		}
		return title;
	}

	public static String FetchCurrentURL() {
		String url = "";
		try {
			url = driver.getCurrentUrl();
		} catch (Exception e) {
			System.err.println("failed to fetch current url");
			e.printStackTrace();
		}
		return url;
	}

}
