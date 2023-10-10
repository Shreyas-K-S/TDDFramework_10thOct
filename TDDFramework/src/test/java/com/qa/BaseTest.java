package com.qa;

import org.testng.annotations.Test;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseTest {
	
	protected static AppiumDriver driver;
	protected static Properties props;	
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	protected static String platform;
	InputStream inputStream;
	InputStream stringsis;
	TestUtils utils;

    public BaseTest(){
	    PageFactory.initElements(new AppiumFieldDecorator(driver),this);     
    }
    
@Parameters({"emulator","platformName","platformVersion","deviceName","deviceUDID"})
@BeforeTest
  public void beforeTest(String emulator,String platformName,String platformVersion,String deviceName,String deviceUDID) throws Exception {
	platform = platformName;
	URL url;  
	try {
		  props = new Properties();
		  String propFileName = "config.properties"; 
		  String xmlFileName = "strings/strings.xml";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  utils = new TestUtils();
		  strings = utils.parseStringXML(stringsis);
		  
		  DesiredCapabilities caps = new DesiredCapabilities();
	      caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
	      caps.setCapability(MobileCapabilityType.DEVICE_NAME,deviceName);
	      
	      switch(platformName) {
	      case "Android":
	    	  caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
              if(emulator.equalsIgnoreCase("true")) {
	         	 // In order to launch emulator automatically use below caps
	             caps.setCapability("avd",deviceName);
	             caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
	             caps.setCapability("avdLaunchTimeout",180000);	 
	          }else {
	         	 caps.setCapability(MobileCapabilityType.UDID,deviceUDID);  
	          }	    	  
//	    	  String androidURL = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
              String androidURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
	          System.out.println("appUrl is" + androidURL);
		      caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
		      caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
		      
		     
//	        caps.setCapability("newCommandTimeout",300);
     //     caps.setCapability("appActivity","io.appium.android.apis.accessibility.CustomViewAccessibilityActivity");

//		      caps.setCapability(MobileCapabilityType.APP,appUrl);
              url = new URL(props.getProperty("appiumURL"));
              System.out.println("Driver is" + driver);
              driver = new AndroidDriver(url, caps);
             
		      System.out.println("session id: " + driver.getSessionId());
		      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtils.WAIT));
		      break;
		   
	      case "iOS":
	    	  caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("iOSAutomationName"));
		      String iOSURL = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
	          System.out.println("appUrl is" + iOSURL);
	          caps.setCapability("bundleId",props.getProperty("iOSBundleId"));
//		      String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
		     
//	        caps.setCapability("newCommandTimeout",300);

	   //     caps.setCapability("appActivity","io.appium.android.apis.accessibility.CustomViewAccessibilityActivity");

//		      caps.setCapability(MobileCapabilityType.APP,appUrl);
              url = new URL(props.getProperty("appiumURL"));
              driver = new IOSDriver(url, caps);
		      System.out.println("session id: " + driver.getSessionId() );
		      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtils.WAIT));
		      break;
		   
	      default:
	    	   throw new Exception("Invalid Platform! - " + platformName);
	      }
}catch (Exception e) {
		  e.printStackTrace();
	  }finally {
		  if(inputStream != null) {
			  inputStream.close(); 
		  }
		  if(stringsis != null) {
			  stringsis.close(); 
		  }
	  }

  }

	
public AppiumDriver getDriver() { 
	return driver; 
}
	 

public void waitForVisibility(WebElement e) {
	System.out.println("Element-->"+e);
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(TestUtils.WAIT));
	wait.until(ExpectedConditions.visibilityOf(e));
	
}

public void clear(WebElement e) {
	waitForVisibility(e);
	e.clear();
}

public void click(WebElement e) {
	waitForVisibility(e);
	e.click();
}

public void sendKeys(WebElement e,String txt) {
	waitForVisibility(e);
	e.sendKeys(txt);
}

public String getAttribute(WebElement e,String attribute) {
	waitForVisibility(e);
	return e.getAttribute(attribute);
}

public String getText(WebElement e) {
	switch(platform) {
	case "Android":
		return getAttribute(e, "text");
	case "iOS":
		return getAttribute(e, "label");
	}
	return null;
}

public void closeApp() {
	switch(platform) {
	case "Android":
		((InteractsWithApps) driver).terminateApp(props.getProperty("androidAppPackage"));
		break;
	case "iOS":
		((InteractsWithApps) driver).terminateApp(props.getProperty("iOSBundleId"));
	}
}

public void launchApp() {
	switch(platform) {
	case "Android":
		((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
		break;
	case "iOS":
		((InteractsWithApps) driver).activateApp(props.getProperty("iOSBundleId"));
	}
}

public WebElement scrollToElement() {
	/*
	 * return driver.findElement(AppiumBy.androidUIAutomator(
	 * "new UiScrollable(new UiSelector()" +
	 * ".description(\"test-Inventory item page\")).scrollIntoView(" +
	 * "new UiSelector().description(\"test-Price\"));"));
	 */
	
	return driver.findElement(AppiumBy.androidUIAutomator(
			"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
					+ "new UiSelector().description(\"test-Price\"));"));
			
	       /* return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
	                + ".scrollIntoView(new UiSelector().resourceIdMatches(\".*id.*\"))"));	*/	
}

  @AfterTest
  public void afterTest(){
	   driver.quit();
  }

}
