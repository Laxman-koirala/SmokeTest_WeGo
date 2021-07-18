package com.qa;

import org.testng.annotations.Test;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseTest {
	
	protected AppiumDriver driver;
	protected Properties props;
	InputStream inputStream;
	
 
  @Parameters({"platformName","deviceName","udid"})
  @BeforeTest
  public void beforeTest(String platformName, String deviceName, String udid) throws Exception {
	  try {
		  props = new Properties();
		  String propFileName = "config.properties";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  
		  DesiredCapabilities capabilities = new DesiredCapabilities();
	      capabilities.setCapability("platformName",platformName);
	      capabilities.setCapability("deviceName", deviceName);
	      capabilities.setCapability("udid", udid);
	      capabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
	      capabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
	      capabilities.setCapability("appActivity",props.getProperty("androidAppActivity"));
	      
	      URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
	      capabilities.setCapability("app",appUrl);
	      
	      URL url = new URL(props.getProperty("appiumURL"));
	      driver = new AndroidDriver(url,capabilities);
	      
	      String sessionId = driver.getSessionId().toString();
	      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


	  } catch (Exception e) {  
		  e.printStackTrace();	  
	  }
	 
  }
  
  public void waitForVisibility(MobileElement e) {
	  WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
	  wait.until(ExpectedConditions.visibilityOf(e));
  }
  
  public void click(MobileElement e) {
	  waitForVisibility(e);
	  e.click();
  }
  
  public void sendKeys(MobileElement e, String txt) {
	  waitForVisibility(e);
	  e.sendKeys(txt);
  }
  
  public void getAttribute(MobileElement e, String attribute) {
	  waitForVisibility(e);
	  e.getAttribute(attribute);
  }

  @AfterTest
  public void afterTest() {
  }

}
