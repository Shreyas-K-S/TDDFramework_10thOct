package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

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

public class ProductTests extends BaseTest{
        LoginPage loginPage;
        ProductsPage productsPage;
        SettingsPage settingsPage;
        ProductsDetailsPage productsDetailspage;
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
	
	 productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
			  loginUsers.getJSONObject("validUser").getString("password")); 
}

@AfterMethod
public void afterMethod() {
	
       closeApp();
       launchApp();
		/*
		 * settingsPage = productsPage.pressSettingsBtn(); loginPage =
		 * settingsPage.presslogoutBtn();
		 */
}

@Test
public void validateProductonProductsPage() {
	  SoftAssert sa = new SoftAssert();
	 	  
	  String SLBTitle = productsPage.getSLBTitle();
	  sa.assertEquals(SLBTitle, strings.get("products_page_slb_title"));
	  
	  String SLBPrice = productsPage.getSLBPrice();
	  sa.assertEquals(SLBPrice, strings.get("products_page_slb_price"));
	
	  sa.assertAll();
   }

@Test
public void validateProductonProductDetailsPage() throws InterruptedException {
	  SoftAssert sa = new SoftAssert();
	  
	  productsDetailspage = productsPage.pressSLBTitle();
	  
	  String SLBTitle = productsDetailspage.getSLBTitle();
	  sa.assertEquals(SLBTitle, strings.get("product_details_page_slb_title"));
	  
	  String SLBTxt = productsDetailspage.getSLBTxt();
	  sa.assertEquals(SLBTxt, strings.get("product_details_page_slb_txt"));
	  
//	  productsDetailspage.scrollToSLBPriceAndGetSLBPrice();
	  
	  Thread.sleep(3000);
	  
	  String SLBPrice = productsDetailspage.scrollToSLBPriceAndGetSLBPrice();
	  sa.assertEquals(SLBPrice, strings.get("product_details_page_slb_price"));
	   
	  productsPage = productsDetailspage.pressBacktoProductsBtn();

	  sa.assertAll();
   }
}
