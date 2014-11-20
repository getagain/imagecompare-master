package com.getagain.util.imagecompare;

import java.util.Collection;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.compro.automation.utils.*;
import com.compro.automation.core.*;
import org.openqa.selenium.Keys;

@RunWith(Parameterized.class)
public class TestScreenshot { 
	private String testEnv;
	private RemoteWebDriver driver = null;
	
	public TestScreenshot(String testEnv){
		this.testEnv = testEnv;
	  }
		 	
	  @Parameters
	   public static Collection<Object[]> data() throws Exception {
		   return (new TestEnvironement()).getEnvironment();
   }
	
	@Before
	public void setUp() {
		driver = TestRun.init(testEnv);	
	}
		
	@After
	public void tearDown() throws Exception {
		TestRun.stop(driver);
	}
//----------------------------------------------------------------------------------------------------------------------------------------------
@Test
public void Screenshot()throws Exception{
	CSVHandler path = new CSVHandler("src/test/resources/paths.csv");
	CSVHandler pages = new CSVHandler("src/test/resources/pages.csv");
	int i=0;
	//URL
	String url = path.readCSV(2,3);
	String filepath = path.readCSV(2,2);
	System.out.println("Filepath : "+filepath);
	System.out.println("URL : "+url);
	driver.get(url);
	
	String[] page = pages.readCSV_col(2);
	String[] page_no = pages.readCSV_col(1);  
//	for(i=0;i<page.length;i++)
//	System.out.println("Path : "+page[i]);
	
	//Single Page
	driver.findElement(By.xpath("/html/body/div[2]/div/div")).click();
	//driver.findElement(By.xpath("/html/body/div/div/div/div/div[4]/div")).click();
	synchronized (driver){driver.wait(8000);}
	//WebElement ele = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div[2]/div/div/div/div"));
	WebElement ele = driver.findElement(By.id("eeditionpagedivA"));
	
	driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/form/input")).click();	
	
	//Zoom In
	//driver.findElement(By.xpath("/html/body/div/div/div/div/div[4]/div[6]")).click();
	driver.findElement(By.xpath("/html/body/div[2]/div/div[6]")).click();
	synchronized (driver){driver.wait(3000);}
	driver.findElement(By.xpath("/html/body/div[2]/div/div[6]")).click();
	synchronized (driver){driver.wait(4000);}
	driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/form/input")).click();
	int count =0;
	for(i=1;i<=page.length;i++)
	{
	System.out.println("Executing for Page : "+page[i]);
	System.out.println("Page NO: "+page_no[i]);
	
	//------------------------------------------Enter page------------------------------
		//driver.findElement(By.xpath("/html/body/div/div/div/div/div[4]/div[4]/form/input")).clear();
		driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/form/input")).clear();
		driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/form/input")).sendKeys(page_no[i]);
		driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/form/input")).sendKeys(Keys.RETURN);
		
		//Wait till loading bar is displayed
		while(driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div")).isDisplayed())
		{
			System.out.println(count++);
			synchronized (driver){driver.wait(200);}
		}
		//----------------Take Screenshot of book container area --(Edit file name)----------
		//synchronized (driver){driver.wait(3000);}
		System.out.println(page[i]);
		Screenshot.takeElementScreenshot(driver, ele, ele.getLocation(), filepath+page[i]);
	}
	
	/*	
	//URL
	driver.get("http://192.168.1.237/engine/dist/index-dev.html?book=wcg_tx_new&align=center");

    //-------------------------------eEdition viewer(Application)--------------------------------------------
    driver.findElement(By.id("j_username")).clear();
	driver.findElement(By.id("j_username")).sendKeys("student@texas");
	driver.findElement(By.id("passwordField")).clear();
    driver.findElement(By.id("passwordField")).sendKeys("password");
    driver.findElement(By.id("imgLogin")).click();

    driver.manage().window().maximize();
    driver.findElement(By.cssSelector("html.desktop body div.row-fluid div#dashboardViewStackContainer.span12 div#dashboardViewStack.opaque-background div.module-container div.wcg-student-home section#row1.row-fluid div.unit-chooser")).click(); //Launch eEdition
	synchronized (driver){driver.wait(8000);}
	//Single Page
	driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div/div/div[4]/div")).click();
	synchronized (driver){driver.wait(8000);}
	
	//Zoom In 
	driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div/div/div[4]/div[6]")).click();
	synchronized (driver){driver.wait(8000);}
	driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div/div/div[4]/div[6]")).click();
	synchronized (driver){driver.wait(8000);}
	driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div/div/div[4]/div[6]")).click();
	synchronized (driver){driver.wait(8000);}
	driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div/div/div[4]/div[6]")).click();
	synchronized (driver){driver.wait(8000);}
	
	//Goto Page
	driver.findElement(By.cssSelector("html.desktop body div.row-fluid div#dashboardViewStackContainer.span12 div#dashboardViewStack.opaque-background div#student-eEdition.module-container div#seContentContainer div.eEdition-tools-topband-wrapper div.eEdition-tools-topband div.topband-middle-section div.pull-left form input.jump-to-page")).click();
	driver.findElement(By.cssSelector("html.desktop body div.row-fluid div#dashboardViewStackContainer.span12 div#dashboardViewStack.opaque-background div#student-eEdition.module-container div#seContentContainer div.eEdition-tools-topband-wrapper div.eEdition-tools-topband div.topband-middle-section div.pull-left form input.jump-to-page")).clear();
	driver.findElement(By.cssSelector("html.desktop body div.row-fluid div#dashboardViewStackContainer.span12 div#dashboardViewStack.opaque-background div#student-eEdition.module-container div#seContentContainer div.eEdition-tools-topband-wrapper div.eEdition-tools-topband div.topband-middle-section div.pull-left form input.jump-to-page")).sendKeys(page);
	driver.findElement(By.cssSelector("html.desktop body div.row-fluid div#dashboardViewStackContainer.span12 div#dashboardViewStack.opaque-background div#student-eEdition.module-container div#seContentContainer div.eEdition-tools-topband-wrapper div.eEdition-tools-topband div.topband-middle-section div.pull-left form input.jump-to-page")).sendKeys(Keys.RETURN);;
	synchronized (driver){driver.wait(15000);}
		
	Actions action = new Actions(driver);
	WebElement we = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div[2]/div[2]/div[2]/div/div/div/div"));
	action.moveToElement(we).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div[2]/div[2]/div[2]/div/div/div/div"))).click().build().perform();
	synchronized (driver){driver.wait(5000);}
	
	//Take Screenshot
	WebElement ele = driver.findElement(By.xpath("/html/body/div/div/div/div[3]/div/div[2]/div[2]/div[2]/div/div/div/div"));
	Screenshot.takeElementScreenshot(driver, ele, ele.getLocation(), page+"_qa2_font");

	//WebElement ele = driver.findElement(By.xpath("/html/body/div/div/div/div/div"));
	
	//-------------------------------Local viewer-----------------------------------------------------------
	synchronized (driver){driver.wait(8000);}
	
	//Single Page
	driver.findElement(By.xpath("/html/body/div[2]/div[4]/input")).click();
	synchronized (driver){driver.wait(8000);}
	WebElement ele = driver.findElement(By.xpath("/html/body/div/div/div/div/div"));
	
	//Zoom In
	driver.findElement(By.xpath("/html/body/div[2]/div[3]/input")).click();
	synchronized (driver){driver.wait(5000);}
	driver.findElement(By.xpath("/html/body/div[2]/div[3]/input")).click();
	synchronized (driver){driver.wait(5000);}
	
	/*
	//Zoom Out
	driver.findElement(By.xpath("/html/body/div[2]/div[3]/input[2]")).click();
	synchronized (driver){driver.wait(5000);}
	driver.findElement(By.xpath("/html/body/div[2]/div[3]/input[2]")).click();
	synchronized (driver){driver.wait(5000);}

	for(int i=1;i<=883;i++)
	{
	//------------------------------------------Enter page------------------------------
	driver.findElement(By.xpath("/html/body/div[2]/div[6]/input")).clear();
	driver.findElement(By.xpath("/html/body/div[2]/div[6]/input")).sendKeys(page[i]);
	driver.findElement(By.xpath("/html/body/div[2]/div[6]/input[2]")).click();
	synchronized (driver){driver.wait(10000);}
	
	//----------------Take Screenshot of book container area --(Edit file name)----------
	synchronized (driver){driver.wait(5000);}
	System.out.println(page[i]);
	Screenshot.takeElementScreenshot(driver, ele, ele.getLocation(), page[i]+"_wcg_tx_font");
	}
	*/
}

@Ignore
@Test
public void Screenshot_Compare()throws Exception{
	//Screenshot.compareScreenshots("wcg_tx_se_s_u04_055", "wcg_tx_se_s_u04_055_win7");
	Screenshot.compareScreenshots("wcg_tx_se_s_u07_046", "wcg_tx_se_s_u07_046_win7");
}
}