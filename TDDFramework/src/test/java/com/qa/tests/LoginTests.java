package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import io.appium.java_client.AppiumBy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{
        LoginPage loginPage;
        ProductsPage productsPage;
        InputStream datais;
        JSONObject loginUsers;
      
@BeforeClass
public void beforeClass() throws Exception {
	try {
		String dataFileName = "data/loginUsers.json";
		datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
		JSONTokener tokener = new JSONTokener(datais);
		loginUsers = new JSONObject(tokener);
	}catch(Exception e) {
		e.printStackTrace();
		throw e; 
	}finally {
		if(datais != null) {
			datais.close();
		}
	}
	   closeApp();
	    launchApp();
	
}

@AfterClass
public void afterClass() {
}
	  
@BeforeMethod
public void beforeMethod(Method m) {
	loginPage = new LoginPage();
	System.out.println("\n" + "******* starting test:" + m.getName() + "******" + "\n" );
}

@AfterMethod
public void afterMethod() {
}

@Test
public void invalidUsername() {
	  loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
	  loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
	  loginPage.pressLoginBtn();
	  
	  String actualErrTxt = loginPage.getErrTxt() + "asdf";
	  String expectedErrTxt = strings.get("err_invalid_username_or_password");
	  System.out.println("Actual error text - " + actualErrTxt + "\n" + "Expected error text - " + expectedErrTxt);
	  
	  Assert.assertEquals(actualErrTxt, expectedErrTxt); 
 }
  
  @Test
  public void invalidPassword() {
	  loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
	  loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
	  loginPage.pressLoginBtn();
	  	    
	  String actualErrTxt = loginPage.getErrTxt();
	  String expectedErrTxt = strings.get("err_invalid_username_or_password");
	  System.out.println("Actual error text - " + actualErrTxt + "\n" + "Expected error text - " + expectedErrTxt);
	 
	  Assert.assertEquals(actualErrTxt, expectedErrTxt);  
  }
  
  @Test
  public void successfulLogin() {
	  loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
	  loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
	  productsPage = loginPage.pressLoginBtn();

  	  String actualproductTitle = productsPage.getTitle();
	  String expectedproductTitle = strings.get("product_title");
	  System.out.println("Actual Title - " + actualproductTitle + "\n" + "Expected Title -" + expectedproductTitle );
	  
	  Assert.assertEquals(actualproductTitle, expectedproductTitle);	  
  }
}
