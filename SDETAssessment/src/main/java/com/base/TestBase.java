package com.base;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	Properties prop;
	String browser;
	String url;

	// load properties in constructor
	public TestBase() {
		prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("D:\\Selenium\\CAWAssessment\\AssignmentForSDET\\config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		browser = prop.getProperty("browser");
		url = prop.getProperty("url");

	}

	// set driver config
	public WebDriver settingDriver(String browser) {

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			System.out.println("Specified browser not found");
			System.out.println("Switching to edge");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		}
		return driver;
	}

	// set synchronization
	public void setSychronisation(WebDriver driver) {

		try {
			driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// define pre condition of test
	public void preCondition() {
		driver = settingDriver(browser);
		driver.manage().window().maximize();
		driver.get(url);
		setSychronisation(driver);
	}

	//explicit wait method
	public void Wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// post test teardown method
	public void tearDown() {
		driver.close();
	}

}
