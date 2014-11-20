package com.getagain.util.imagecompare;

import java.io.BufferedInputStream;  
import java.io.FileInputStream;
import java.io.IOException;  
import java.io.InputStream;
import org.apache.pdfbox.pdfparser.PDFParser;  
import org.apache.pdfbox.util.PDFTextStripper;  


  
public class ReadPdfFile {  
   
    public static void main(String args[]) throws IOException{  
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
    //driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);  
    }    
  
}  