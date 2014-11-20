package com.getagain.util.imagecompare;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.compro.automation.core.TestEnvironement;
import com.compro.automation.core.TestRun;
import com.compro.automation.utils.CSVHandler;

public class getOffset {
	
	public static void main(String args[]) throws FileNotFoundException, IOException{
		float max = getmax();
		System.out.println(max);
	}
	
	static String oneLinePdf = "";
	String oneLineJson = "";
	static String page[];
	static String page_no[];
	String textMissing[] = new String[9999];
	int textMissingCount=0;

	static String pdf_basePath = "E:\\HtmlReview1\\test3\\pdf";
	
	// --------- reading pages from csv

	
	static String json_fileName;
	static String pdf_fileName;
	
    static String json_filePath;
    static String pdf_filePath;


	public static float getmax() throws FileNotFoundException, IOException{
		
		CSVHandler pages = new CSVHandler("src/test/resources/pages.csv");
		page = pages.readCSV_col(2); // PAGE FILENAME WITHOUT EXTENSION
		page_no = pages.readCSV_col(1); 
		
		float current;
		float max= 0;
	  
			for(int j=1;j<page.length;j++)
			{
				 oneLinePdf = "";
			pdf_fileName = "\\" + page[j];
		    pdf_filePath = pdf_basePath + pdf_fileName + "_pdf.txt";

		    
		    //-----------pdf	  
			
		    BufferedReader br = null;

			try {

				String sCurrentLine;

//				 br = new BufferedReader(new FileReader(pdf_filePath));
				 br = new BufferedReader(
							new InputStreamReader(new FileInputStream(pdf_filePath), "UTF-8"));

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
			}
			
			current = oneLinePdf.length();

			if(current>max){max = current;}
		}

			return max;
	}
	
public static float getCurrentlen(String filename) throws FileNotFoundException, IOException{
		
			float current = 0;
			oneLinePdf = "";
			pdf_fileName = "\\" + filename;
		    pdf_filePath = pdf_basePath + pdf_fileName + "_pdf.txt";

		    
		    //-----------pdf	  
			
		    BufferedReader br = null;

			try {

				String sCurrentLine;

//				 br = new BufferedReader(new FileReader(pdf_filePath));
				 br = new BufferedReader(
							new InputStreamReader(new FileInputStream(pdf_filePath), "UTF-8"));

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
			}
			
			current = oneLinePdf.length();

			return current;
	}

	public static float offset(float maxin, String currentin) throws FileNotFoundException, IOException{
				
		String current = currentin;
		float max= maxin;
		float currentOffset;

			oneLinePdf = "";
			pdf_fileName = "\\" + current;
		    pdf_filePath = pdf_basePath + pdf_fileName + "_pdf.txt";

		    
		    //-----------pdf	  
			
		    BufferedReader br = null;

			try {

				String sCurrentLine;

//				 br = new BufferedReader(new FileReader(pdf_filePath));
				 br = new BufferedReader(
							new InputStreamReader(new FileInputStream(pdf_filePath), "UTF-8"));

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
			}
			
			 float currentLen = oneLinePdf.length();
			 currentOffset = (currentLen/max);
			 return currentOffset;
	}
}