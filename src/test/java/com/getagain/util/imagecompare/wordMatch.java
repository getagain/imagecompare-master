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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(Parameterized.class)
public class wordMatch { 
	String oneLinePdf = "";
	String oneLineJson = "";
	private String testEnv;
	private RemoteWebDriver driver = null;
	String page[];
	String page_no[];
	int passCount = 0;
	
	static String json_basePath = "C:\\work\\HtmlReview\\testsample\\json";
	static String pdf_basePath = "C:\\work\\HtmlReview\\testsample\\pdf";
	
	public wordMatch(String testEnv){
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
  public void CompareLength() throws ScriptException {
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
		printpdfText = oneLinePdf.replaceAll("\\t"," ").replaceAll("  ", " ");;
		System.err.println(pdf_fileName);
		lengthpdf = printpdfText.replaceAll(" ", "").length();
		System.out.println("printpdfText : " +printpdfText);
		System.out.println("lengthpdf: "+lengthpdf);
		
		printjsonText = oneLineJson.replaceAll("\\t"," ").replaceAll("  ", " ");
		//printjsonText = printjsonText.replaceAll("\\.|\\]|\\[|[0-9]|,|\\?|:|\\(|\\)|;|/|||’|“|”|-|!","");
		lengthjson = printjsonText.replaceAll(" ", "").length();
		System.out.println("printjsonText: " +printjsonText);
		System.out.println("lengthjson: "+lengthjson);
		String[] wordsjson = printjsonText.split("\\s+");
		String[] wordspdf = printpdfText.split("\\s+");
		
		// for extra text
		if(lengthjson>=lengthpdf){
			// You may want to check for a non-word character before blindly
		    // performing a replacement
		    // It may also be necessary to adjust the character class
		  //  words[i] = words[i].replaceAll("[^\w]", "");
						String temp1 = printpdfText;
						for (int i = 0; i < wordsjson.length; i++) {
							if(temp1.contains(wordsjson[i])){
								temp1=temp1.replaceFirst(wordsjson[i], "");
							//System.out.println(temp1);
						}else{
							passCount++;
						System.err.println("Extra text in json: " + wordsjson[i]);}
						}
						
		}
		
		// for text missing
		if(lengthjson<lengthpdf){
						String temp2 = printjsonText;
						for (int i = 0; i < wordspdf.length; i++) {
						if(temp2.contains(wordspdf[i])){
							temp2=temp2.replaceFirst(wordspdf[i], "");
						}else{
							passCount++;
						System.out.println(passCount);
						System.err.println("Text missing in json: " + wordspdf[i]);}
						}
			
			
		}

		if(passCount==0){result= "pass";}else result = "fail";	
		System.out.println("result: "+result);
		values.add(new String[] { pdf_fileName,result });
		
		// writing result
//		try {
//			CSV.writeAllExample(values);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}}
}