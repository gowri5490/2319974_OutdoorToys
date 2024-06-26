package OutdoorToys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Browser.DriverSetup;
import dataExcel.ExcelData;



public class OutDoorToys{
	
	//Declaring static variable for implement driver and htmlReport
	static WebDriver driver;
	static String[] Elements=null;
	static int count=1;
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static ExtentTest test;
	
	//Get driver from DriverSetup class and return the Driver
	public WebDriver setupDriver()
	{
		
		//Initialize the htmlReporter path for store the HTML file
		htmlReporter = new ExtentHtmlReporter("C:\\Users\\2318300\\eclipse-workspace\\2318300_Hrm\\ExtentReport\\extentReport.html");
	    
		extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Final Report");
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		htmlReporter.config().setTheme(Theme.DARK);
	
		
		//Get driver from DriverSetup class
		driver = DriverSetup.getWebDriver();
		
		 //Using implicit wait to handle the synchronization issue
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	  
	    
	}
	
	//Implement this static method for take a Screenshot wherever needed and store it in a file
	public static void TakeScreenShot(WebDriver driver) throws IOException
	{
		//type cast web driver to TakesScreenshot 
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		File trg=new File("C:\\Users\\2318300\\eclipse-workspace\\2318300_Hrm\\ScreenShot\\img"+count+".png");
		FileUtils.copyFile(src, trg);
		count++;
				
	}
	
	//Launch the browser using this method
	public void advancePage() throws Exception
	{
		
		driver.get("https://www.ebay.com");
		
		WebElement advance=driver.findElement(By.id("gh-as-a"));
		advance.click();
		
	}
	
	public void findItems()
	{
		//Call this method to get the excel data
		Elements=ExcelData.getExcel();
		
		WebElement iName=driver.findElement(By.id("_nkw"));
		iName.sendKeys(Elements[0]);
		
		Select wrds=new Select(driver.findElement(By.name("_in_kw")));
		wrds.selectByVisibleText(Elements[1]);
		
		Select cate=new Select(driver.findElement(By.xpath("//select[@id='s0-1-17-4[0]-7[3]-_sacat']")));
		cate.selectByVisibleText(Elements[2]);
	}
	public void specialSearch()
	{
		WebElement searchIncluding=driver.findElement(By.xpath("//span//input[@name='LH_TitleDesc']"));
		searchIncluding.click();
		
		WebElement condition=driver.findElement(By.xpath("//input[@id='s0-1-17-6[4]-[0]-LH_ItemCondition']"));
		condition.click();
		
		WebElement showResults1=driver.findElement(By.xpath("//input[@id='s0-1-17-5[5]-[0]-LH_FR']"));
		showResults1.click();
		
		WebElement showResults2=driver.findElement(By.xpath("//input[@id='s0-1-17-5[5]-[1]-LH_RPA']"));
		showResults2.click();
		
		WebElement itemLocation=driver.findElement(By.xpath("//input[@id='s0-1-17-6[7]-[3]-LH_PrefLoc']"));
		itemLocation.click();
	}
	
	public void Search()
	{
		WebElement submit=driver.findElement(By.xpath("//div[@class='adv-form__actions']//button[@class='btn btn--primary']"));
		submit.click();
	}
	
	public void getItems()
	{
		List<WebElement> links=driver.findElements(By.tagName("a"));
		System.out.println("Curren page 'href' values");
		int count=1;
		for(WebElement link:links)
		{
			String toyName=link.getText();
			
			if(toyName.contains(Elements[3]))
			{
				if(!toyName.equals("Building Toys"))
				{
				String[] name=toyName.split("Opens");	
				System.out.println("Item"+count+" Name:"+name[0]);
				System.out.println("Item"+count+" Link:"+link.getAttribute("href"));
				System.out.println();
				System.out.println("-------------------");
				
				count++;
				}
			}
		}
	}
}