package com.getagain.util.imagecompare;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.snowtide.pdf.OutputHandler;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.TextUnit;
import com.snowtide.pdf.layout.TextUnitImpl;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(Parameterized.class)
public class JsonExtract { 
	String oneLinePdf = "";
	String oneLineJson = "";
	private String testEnv;
	private RemoteWebDriver driver = null;
	String page[];
	String page_no[];
	
	public JsonExtract(String testEnv){
		this.testEnv = testEnv;
	  }
		 	
	  @Parameters
	   public static Collection<Object[]> data() throws Exception {
		   return (new TestEnvironement()).getEnvironment();
   }
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		//driver = TestRun.init(testEnv);	
		
		// --------- reading pages from csv
		
//		CSVHandler path = new CSVHandler("src/test/resources/paths.csv");
		CSVHandler pages = new CSVHandler("src/test/resources/pages.csv");
//		int i=0;
		//URL
//		String url = path.readCSV(2,3);
//		String filepath = path.readCSV(2,2);
//		System.out.println("Filepath : "+filepath);
//		System.out.println("URL : "+url);
//		driver.get(url);
		
		page = pages.readCSV_col(2); // PAGE FILENAME WITHOUT EXTENSION
		page_no = pages.readCSV_col(1); 
		
		
		
	}
		
	@After
	public void tearDown() throws Exception {
		TestRun.stop(driver);
	}

	
	static String json_basePath = "C:\\work\\HtmlReview\\test4\\json";
	static String pdf_basePath = "C:\\work\\HtmlReview\\test4\\pdf";
	
	static String json_fileName;
	static String pdf_fileName;
	
    static String json_filePath;
    static String pdf_filePath;
 // ----
  

//@Ignore
  @Test
    public void JsonText()throws Exception{
    {
		for(int j=1;j<page.length;j++)
		{
    	json_fileName = "\\" + page[j];
	    json_filePath = json_basePath + json_fileName + ".json";
		// -- empty existing text file
		PrintWriter writer = new PrintWriter(json_basePath + json_fileName + "_json.txt");
		writer.print("");
		writer.close();
		
		try {
			// read the json file
			FileReader reader = new FileReader(json_filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

//			// get a String from the JSON object
//			String firstName =  (String) jsonObject.get("firstname");
//			System.out.println("The first name is: " + firstName);
//
			// get a number from the JSON object
//			String id =  (String) jsonObject.get("id");
//			System.out.println("The id is: " + id);

			// get an array from the JSON object
			JSONArray txt= (JSONArray) jsonObject.get("txts");
//			
			// take the elements of the json array
//			for(int k=0; k<txt.size(); k++){
//				System.out.println(txt.get(k));
//			}
			Iterator i = txt.iterator();
			 List<String[]> AllText = new ArrayList<String[]>();
			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String hdn = innerObj.get("hdn").toString();
				if (hdn.equalsIgnoreCase("false")){
				String textjson = innerObj.get("txt").toString();
				//System.out.println(textjson);
				
// ---------
				
//				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)))) {
//				    out.println("the text");
//				}catch (IOException e) {
//				    //exception handling left as an exercise for the reader
//				}
				
				
				try {
				    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(json_basePath + json_fileName + "_json.txt", true)));
				    out.println(textjson);

				    out.close();

				} catch (IOException e) {
					System.err.println(json_fileName+ "_json.txt write not successful.");	
				    //exception handling left as an exercise for the reader
				}
				
				}

			}
			
		    System.out.println(json_fileName+ "_json.txt write successful.");	
//		    Path path = Paths.get("test.txt");
//		    Charset charset = StandardCharsets.UTF_8;
//
//		    String content = new String(Files.readAllBytes(path), charset);
//		    content = content.replaceAll("foo", "bar");
//		    Files.write(path, content.getBytes(charset));
//			// handle a structure into the json object
//			JSONObject structure = (JSONObject) jsonObject.get("job");
//			System.out.println("Into job structure, name: " + structure.get("name"));

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}
    }	
  }
	
    
    
    
 // ------------------pdf read pdfbox
@Ignore
    @Test
    public void pdfboxText() throws Exception {
  		for(int j=1;j<page.length;j++)
  		{
  		pdf_fileName = "\\" + page[j];
  	    pdf_filePath = pdf_basePath + pdf_fileName + ".pdf";
  		// -- empty existing text file
  		PrintWriter writer = new PrintWriter(pdf_basePath + pdf_fileName + "_pdf.txt");
  		writer.print("");
  		writer.close();
  	  
  		 InputStream inStream = null;
         
         inStream = new FileInputStream(pdf_filePath);
     	
   //  BufferedInputStream fileToParse=new BufferedInputStream(url.openStream());
         
         BufferedInputStream fileToParse=new BufferedInputStream(inStream);
     
     //parse()  --  This will parse the stream and populate the COSDocument object.   
     //COSDocument object --  This is the in-memory representation of the PDF document  
     
     PDFParser parser = new PDFParser(fileToParse);  
     parser.parse();  
     
     //getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
     //PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  
     
     String textpdf=new PDFTextStripper().getText(parser.getPDDocument());  
     //System.out.println(textpdf);  
     parser.getPDDocument().close(); 
     
       
  		try {
  		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pdf_basePath + pdf_fileName + "_pdf.txt", true)));
  		    out.println(textpdf);
  		    out.close();

  		} catch (IOException e) {
  			System.err.println(pdf_fileName+ "_pdf.txt write not successful.");	
  		    //exception handling left as an exercise for the reader
  		}
  		System.out.println(pdf_fileName+ "_pdf.txt write successful.");
  		
  		} 
  			
    }
 @Ignore   
    @Test
    public void pdfbox() throws IOException{  
  	  /*  
    driver.get("http://votigo.com/overview_collateral.pdf");  
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
    URL url = new URL(driver.getCurrentUrl());   
    */
        InputStream inStream = null;
              
        inStream = new FileInputStream("C:/work/HtmlReview/test3/pdf/sci_nat_4_es_bib_006.pdf");
    	
  //  BufferedInputStream fileToParse=new BufferedInputStream(url.openStream());
        
        BufferedInputStream fileToParse=new BufferedInputStream(inStream);
    
    //parse()  --  This will parse the stream and populate the COSDocument object.   
    //COSDocument object --  This is the in-memory representation of the PDF document  
    
    PDFParser parser = new PDFParser(fileToParse);  
    parser.parse();  
    
    //getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
    //PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  
    
    String output=new PDFTextStripper().getText(parser.getPDDocument());  
    System.out.println(output);  
    parser.getPDDocument().close();   
    driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);  
    }      
    

// ------------------pdf read
@Ignore
  @Test
  public void pdfText() throws Exception {
		for(int j=1;j<page.length;j++)
		{
		pdf_fileName = "\\" + page[j];
	    pdf_filePath = pdf_basePath + pdf_fileName + ".pdf";
		// -- empty existing text file
		PrintWriter writer = new PrintWriter(pdf_basePath + pdf_fileName + "_pdf.txt");
		writer.print("");
		writer.close();
	  
	  
      //String pdfFilePath = "C:\\work\\3\\wcg_tx_se_s_fm_021.pdf";//args[0];
      PDFTextStream pdfts = new PDFTextStream(pdf_filePath); 
      StringBuilder textpdf = new StringBuilder(1024);
      pdfts.pipe(new OutputTarget(textpdf));
      pdfts.close();
      //System.out.printf("The text extracted from %s is:", pdfFilePath);
      //System.out.println(textpdf);
      
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pdf_basePath + pdf_fileName + "_pdf.txt", true)));
		    out.println(textpdf);
		    out.close();

		} catch (IOException e) {
			System.err.println(pdf_fileName+ "_pdf.txt write not successful.");	
		    //exception handling left as an exercise for the reader
		}
		System.out.println(pdf_fileName+ "_pdf.txt write successful.");
		
		} 
			
  }
  
  //--------
@Ignore
  @Test
  public void readTxt() {
	  List<String[]> values = new ArrayList<String[]>();
	  values.add(new String[] { "fileName", "length_pdf", "length_json","result" });
	  
		for(int j=1;j<page.length;j++)
		{
			 oneLinePdf = "";
			 oneLineJson = "";
  	json_fileName = "\\" + page[j];
	pdf_fileName = "\\" + page[j];
	    json_filePath = json_basePath + json_fileName + "_json.txt";
	    pdf_filePath = pdf_basePath + pdf_fileName + "_pdf.txt";

	    
	    //-----------pdf	  
		
	    BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(pdf_filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				//if (sCurrentLine.contains(".indd") || sCurrentLine.contains("LIVE AREA") ){break;}
				
					//System.out.println(sCurrentLine);
				oneLinePdf = oneLinePdf + sCurrentLine;
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			//------------ json
			
			BufferedReader br1 = null;

			try {

				String sCurrentLine1;

				br1 = new BufferedReader(new FileReader(json_filePath));

				while ((sCurrentLine1 = br1.readLine()) != null) {
					//System.out.println(sCurrentLine1);
					oneLineJson = oneLineJson + sCurrentLine1;
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br1 != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			
		}
		}
		
		String printpdfText = "";
		String printjsonText = "";
		int lengthpdf = 0;
		int lengthjson = 0;
		String result;
		printpdfText = oneLinePdf.replaceAll(" ", "").replaceAll("	", "");
		System.err.println(pdf_fileName);
		System.out.println("printpdfText: " +printpdfText);
		lengthpdf = printpdfText.length();
		System.out.println("printpdfText length: " + lengthpdf);
		
		printjsonText = oneLineJson.replaceAll(" ", "").replaceAll("	", "");
		//System.err.println(json_fileName);
		System.out.println("printjsonText: " +printjsonText);
		lengthjson = printjsonText.length();
		System.out.println("printjsonText length: " + lengthjson);
		
		// updating result
		if (lengthpdf==lengthjson){result = "true";}else result = "false";
		System.out.println("Result: " + result);
		values.add(new String[] { pdf_fileName, lengthpdf+"", lengthjson+"",result });
		
		// writing result
		try {
			CSV.writeAllExample(values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}}
  
//----------------------------------------------------------------------------------------------------------------------------------------------
@Ignore
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