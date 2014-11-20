package com.getagain.util.imagecompare;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
public class GetText { 
	String oneLinePdf = "";
	String oneLineJson = "";
	private String testEnv;
	private RemoteWebDriver driver = null;
	String page[];
	String page_no[];
	String textArray[] = new String[9000];
	double topleftArray[] = new double[9000]; 
	double topleftArray2[] = new double[9000];

		
	static String json_basePath = "C:\\work\\HtmlReview\\test7\\json";
	static String pdf_basePath = "C:\\work\\HtmlReview\\test7\\pdf";
	
	public GetText(String testEnv){
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
    
   	
    // ------------------ json read
@Ignore
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
//			// get a number from the JSON object
//			String id =  (String) jsonObject.get("id");
//			System.out.println("The id is: " + id);

			// get an array from the JSON object
			JSONArray txt= (JSONArray) jsonObject.get("txts");
//			
//			// take the elements of the json array
//			for(int i=0; i<txt.size(); i++){
//				System.out.println(txt.get(i));
//			}
			Iterator i = txt.iterator();
			 List<String[]> AllText = new ArrayList<String[]>();
			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String hdn = innerObj.get("hdn").toString();
				String tm = innerObj.get("tm").toString();
				if (hdn.equalsIgnoreCase("false")&& tm.equalsIgnoreCase("false")){
				String textjson = innerObj.get("txt").toString();
				
				
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

// ------------------ json read Exclude Duplicates
@Ignore
@Test
public void JsonTextExcludeDuplicates()throws Exception{
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

//		// get a String from the JSON object
//		String firstName =  (String) jsonObject.get("firstname");
//		System.out.println("The first name is: " + firstName);
//
//		// get a number from the JSON object
//		String id =  (String) jsonObject.get("id");
//		System.out.println("The id is: " + id);

		// get an array from the JSON object
		JSONArray txt= (JSONArray) jsonObject.get("txts");
//		
//		// take the elements of the json array
//		for(int i=0; i<txt.size(); i++){
//			System.out.println(txt.get(i));
//		}
		int loopcount = 0; 
	//	textArray[0] = ""; topleftArray[0] = 0;
		Iterator i = txt.iterator();
		 List<String[]> AllText = new ArrayList<String[]>();
		// take each value from the json array separately
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			String hdn = innerObj.get("hdn").toString();
			String tm = innerObj.get("tm").toString();
			String l = innerObj.get("l").toString();
			String t = innerObj.get("t").toString();
			String Innertxt = innerObj.get("txt").toString();
			topleftArray[0]=Double.parseDouble(l);
			topleftArray2[0]=Double.parseDouble(t);
			if (hdn.equalsIgnoreCase("false") && tm.equalsIgnoreCase("false")){ // check for writing

							for(int z=0;z < loopcount; z++){ // looping through previous text enteries

							if((Innertxt.equalsIgnoreCase(textArray[z]))){
								
								double leftCd = (Double.parseDouble(l) - topleftArray[z]); //
								double topCd = (Double.parseDouble(t) - topleftArray2[z]);
								//System.out.println(Double.parseDouble(l));
								//System.out.println(leftCd);
								if((Math.abs(leftCd) < 3) && (Math.abs(topCd) < 3))	// checking for left top difference
								{System.out.println(Innertxt);
									Innertxt = "";
									
									
								}}}
						textArray[loopcount] = Innertxt;
						topleftArray[loopcount] = Double.parseDouble(l);
						topleftArray2[loopcount] = Double.parseDouble(t);
						String textjson = Innertxt;
						//System.out.println(Innertxt);
			
			try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(json_basePath + json_fileName + "_json.txt", true)));
			    out.println(textjson);

			    out.close();

			} catch (IOException e) {
				System.err.println(json_fileName+ "_json.txt write not successful.");	
			    //exception handling left as an exercise for the reader
			}

			} // close writing
			loopcount++;

		}
		
	    System.out.println(json_fileName+ "_json.txt write successful.");	

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
  
 
}