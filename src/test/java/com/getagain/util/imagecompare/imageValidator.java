package com.getagain.util.imagecompare;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import com.getagain.util.imagecompare.ImageComparisonModule;
import com.getagain.util.imagecompare.ImageComparisonService;
import com.getagain.util.imagecompare.SimpleImageComparisonProcessor;
import com.google.inject.Guice;
import com.google.inject.Injector;

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

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(Parameterized.class)
public class imageValidator { 
	String oneLinePdf = "";
	String oneLineJson = "";
	private String testEnv;
	private RemoteWebDriver driver = null;
	String page[];
	String page_no[];
	
    String basepathReference = "E:\\HtmlReview1\\test3\\imgtest\\";
    String basepathIdentical = "E:\\HtmlReview1\\test3\\imgtest\\";
	
	public imageValidator(String testEnv){
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
		CSVHandler pages = new CSVHandler("src/test/resources/images.csv");
		page = pages.readCSV_col(2); // PAGE FILENAME WITHOUT EXTENSION
		page_no = pages.readCSV_col(1); 
		
	}
		
	@After
	public void tearDown() throws Exception {
		TestRun.stop(driver);
	}

	// Image Magic Test
	@Test
	public void imageMagicTest() throws IOException, InterruptedException {
    	long lStartTime = System.nanoTime();
    	 List<String[]> values = new ArrayList<String[]>();
    	 values.add(new String[] { "File Name", "Result"});
    	for(int j=1;j<page.length;j++)
    	{
    		String result = "";
    	System.out.println(page[j]);	
    		result = imageMagic.imgMagic(page[j]);
    		
    		values.add(new String[] { page[j], result});


    		//
    		try {
    			CSV.writeAll("C:\\work\\imageValidator.csv",values);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}
        	long lEndTime = System.nanoTime();
        	long difference = lEndTime - lStartTime;
        	System.out.println("Elapsed Minutes: " + (difference/1000000)*0.600);
    		
    

	}
	
	
	// Java Image Compare
    @Ignore
    //@Test
    public void comparetest() throws IOException {
    	long lStartTime = System.nanoTime();
    	float maxText = getOffset.getmax();
    	 List<String[]> values = new ArrayList<String[]>();
    	 values.add(new String[] { "File Name", "Percentage Similarity", "Text Length", "Similarity Index" });
    	for(int j=1;j<page.length;j++)
    	{
    	
        String imageReference = basepathReference + page[j] + ".pdf_image" + ".png";
        //System.out.println(imageReference);
        String imageIdentical = basepathIdentical + page[j] + ".pdf_font" + ".png";
        
        BufferedImage imageReferenceImage;
        BufferedImage imageIdenticalImage;
        imageIdenticalImage = imageReferenceImage = null;
//--------------------
        try {
        File file1= new File(imageReference);
        BufferedImage image1 = ImageIO.read(file1);
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        ImageIO.write(image1,"png", os1); 
        InputStream fis1 = new ByteArrayInputStream(os1.toByteArray());
        
        File file2= new File(imageIdentical);
        BufferedImage image2 = ImageIO.read(file2);
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        ImageIO.write(image2,"png", os2); 
        InputStream fis2 = new ByteArrayInputStream(os2.toByteArray());
 
 //-------------------
 //-------------------       

         imageReferenceImage = ImageIO.read(fis1); 
       //     imageReferenceImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imageReference));
         imageIdenticalImage = ImageIO.read(fis2);
         //   imageIdenticalImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imageIdentical));
        } catch (final IOException ioe) {
            assertTrue(ioe.getMessage(), false);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage(), false);
        }

        final Injector injector = Guice.createInjector(new ImageComparisonModule());
        final ImageComparisonService imageComparisonService = injector.getInstance(ImageComparisonService.class);
       
        // Test successful comparison: Pictures are 100 per cent identical.
        System.err.println(page[j]);
        final boolean result = imageComparisonService.compare(imageIdenticalImage, imageReferenceImage);
       // assertTrue("Test successful comparison: Pictures are 100 per cent identical.", result);
       System.out.println("Calculated percentage of similarity is : "+SimpleImageComparisonProcessor.getpercentEquality());
       String perc1=""+ SimpleImageComparisonProcessor.getpercentEquality();
        

			        // Applying Offset Logic
        			float currentLength = getOffset.getCurrentlen(page[j]);
			        float currentOffset = getOffset.offset(maxText, page[j]);
			        //System.out.println("currentOffset: "+ currentOffset);
			        float index = Float.parseFloat(perc1) + currentOffset;
			        

		values.add(new String[] { page[j], perc1, currentLength+"", index +""});

		
		//
		try {
			CSV.writeAll("C:\\work\\imageValidator.csv",values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	long lEndTime = System.nanoTime();
    	long difference = lEndTime - lStartTime;
    	System.out.println("Elapsed Minutes: " + (difference/1000000)*0.600);

    }
    
//    public void main(String args[]) throws IOException{
//    	long start = System.currentTimeMillis();
//    	comparetest();
//    	long stop = System.currentTimeMillis();
//    	System.out.println("TIME TAKEN IS ="+(stop-start));
//    }
}