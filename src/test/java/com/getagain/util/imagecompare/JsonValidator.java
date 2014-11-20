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
public class JsonValidator { 
	String oneLinePdf = "";
	String oneLineJson = "";
	private String testEnv;
	private RemoteWebDriver driver = null;
	String page[];
	String page_no[];
	  String textMissing[] = new String[9999];
	  int textMissingCount=0;
	  String imageBase = "C:\\Users\\AbhishekK\\Pictures\\112";
	
	static String json_basePath = "C:\\work\\HtmlReview\\test6\\json";
	static String pdf_basePath = "C:\\work\\HtmlReview\\test6\\pdf";
	
	public JsonValidator(String testEnv){
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
		CSVHandler pages = new CSVHandler("src/test/resources/pages.csv");
		page = pages.readCSV_col(2); // PAGE FILENAME WITHOUT EXTENSION
		page_no = pages.readCSV_col(1); 
		
	}
		
	@After
	public void tearDown() throws Exception {
		TestRun.stop(driver);
	}
	
	static String json_fileName;
	static String pdf_fileName;
	
    static String json_filePath;
    static String pdf_filePath;
 // ----
 
  
  //--------
//@Ignore
  @Test
  public void CompareLength() throws IOException {
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

//			br = new BufferedReader(new FileReader(pdf_filePath));
			 br = new BufferedReader(
						new InputStreamReader(new FileInputStream(pdf_filePath)));

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

				//br1 = new BufferedReader(new FileReader(json_filePath));
				
				 br1 = new BufferedReader(
			new InputStreamReader(new FileInputStream(json_filePath), "UTF-8"));

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
		printpdfText = oneLinePdf.replaceAll(" ", "").replaceAll("	", "").replaceAll(" ", "");
		System.err.println(pdf_fileName);
		System.out.println("printpdfText : " +printpdfText);
		lengthpdf = printpdfText.length();
		System.out.println("printpdfText length: " + lengthpdf);
		
		printjsonText = oneLineJson.replaceAll(" ", "").replaceAll("	", "").replaceAll(" ", "");
		//System.err.println(json_fileName);
		System.out.println("printjsonText: " +printjsonText);
		lengthjson = printjsonText.length();
		System.out.println("printjsonText length: " + lengthjson);
		
		// updating result
		if (lengthpdf == lengthjson){result = "Pass";}
		else if((lengthpdf-lengthjson) < 0){result = "Hidden Text" ;}
		else if((lengthpdf-lengthjson) > 0 && (lengthjson != 0)){result = "Text Missing" ;
		textMissingCount++;
		textMissing[textMissingCount]= pdf_fileName;
		}
		else if((lengthpdf-lengthjson) > 0 && (lengthjson == 0)){result = "Text Missing - Full Page" ;
		textMissingCount++;
		textMissing[textMissingCount]= pdf_fileName;


		}
		else result = "false";
		
		// check for anagram
		/*
		boolean isAnagram = Anagram.isAnagram(printjsonText, printpdfText);
		System.out.println("isAnagram: " + isAnagram);
		if(!isAnagram){result = result + ", Anagram-string not matching";}
				*/	
		
		System.out.println("CharCount Result: " + result);
	
		values.add(new String[] { pdf_fileName, lengthpdf+"", lengthjson+"",result });
		
		// writing result
		try {
			CSV.writeAllExample(values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
//		for(int i=0;i<textMissingCount;i++){
//			System.err.println("... checking bk image for text");
//		System.out.println(textMissing[textMissingCount]);
//		Runtime rt = Runtime.getRuntime();
//        //Process pr = rt.exec("cmd /c dir");
//		String batch = "C:\\Windows\\System32\\cmd.exe "  + "tesseract" + " " + "\""+ imageBase + textMissing[textMissingCount] + ".jpg" + "\""+ 
//        " "+ "\"" + imageBase + textMissing[textMissingCount]+ "\"";
//		System.out.println(batch);
////        Process pr = rt.exec(batch);
//        
//        
//        Process p=Runtime.getRuntime().exec(batch); 
//        try {
//			p.waitFor();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//        BufferedReader reader=new BufferedReader(
//            new InputStreamReader(p.getInputStream())
//        ); 
//        String line; 
//        while((line = reader.readLine()) != null) 
//        { 
//            System.out.println(line);
//        } 
//        
//		}

  }
}