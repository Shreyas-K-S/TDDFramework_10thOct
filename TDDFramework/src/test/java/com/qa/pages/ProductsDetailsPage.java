package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsDetailsPage extends MenuPage{
	  @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	  private WebElement SLBTitle;
	  @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	  private WebElement SLBTxt;
	  @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS") 
	  private WebElement backToProductsBtn;
//	  @AndroidFindBy (accessibility = "test-Price") private WebElement SLBPrice;
	  
	  
public String getSLBTitle() {
	String title = getText(SLBTitle);
	System.out.println("Title is - " + title);
	return title;
}

public String getSLBTxt() {
	String txt = getText(SLBTxt);
	System.out.println("Text is - " + txt);
	return txt;
}

/*
 * public String getSLBPrice() { String price = getText(SLBPrice);
 * System.out.println("Price is - " + price); return price; }
 */

public String scrollToSLBPriceAndGetSLBPrice() {
	return getText(scrollToElement());
}

public ProductsPage pressBacktoProductsBtn() {
	System.out.println("Navigate Back to Products Page");
	click(backToProductsBtn);
	return new ProductsPage();
}
}


