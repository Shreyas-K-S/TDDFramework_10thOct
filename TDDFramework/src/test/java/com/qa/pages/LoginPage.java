package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest{
	  @AndroidFindBy (accessibility = "test-Username") private WebElement usernameTxtFld;
	  @AndroidFindBy (accessibility = "test-Password") private WebElement passwordTxtFld;
	  @AndroidFindBy (accessibility = "test-LOGIN") private WebElement loginBtn;
	  @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private WebElement errTxt;

public LoginPage enterUserName(String username) {
	clear(usernameTxtFld);
	System.out.println("Login with " + username);
	sendKeys(usernameTxtFld, username);
	return this;
}

public LoginPage enterPassword(String password) {
	clear(passwordTxtFld);
	System.out.println("Password is " + password);
	sendKeys(passwordTxtFld, password);
	return this;
}

public ProductsPage pressLoginBtn() {
	System.out.println("Press login button");
	click(loginBtn);
	return new ProductsPage();
}

public ProductsPage login(String username, String password) {
	enterUserName(username);
	enterPassword(password);
	return pressLoginBtn();
}

public String getErrTxt() {
	String err = getText(errTxt);
	System.out.println("Error Text is - " + err);
	return getText(errTxt);
}

}


