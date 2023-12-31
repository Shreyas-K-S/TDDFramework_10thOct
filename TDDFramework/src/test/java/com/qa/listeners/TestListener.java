package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.BaseTest;

public class TestListener implements ITestListener{

	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			 StringWriter sw = new StringWriter();
			 PrintWriter pw = new PrintWriter(sw);
			 result.getThrowable().printStackTrace(pw);
			 System.out.println(sw.toString());
		}
		
		
		  BaseTest base = new BaseTest(); 
		  File file = base.getDriver().getScreenshotAs(OutputType.FILE);
		  
		  try { 
			  FileUtils.copyFile(file,new File ("SampleScr.png")); 
			  } catch(IOException e) 
		      { // TODO Auto-generated catch block 
			  e.printStackTrace(); 
			  }
		  }
		 
	}

