package com.getagain.util.imagecompare;

import java.io.*;

import com.google.common.io.ByteStreams;

public class imageMagic {
	static String basepath = "\"C:\\work\\HtmlReview1\\test4\\imgMagic\"";
	
	public static void main(String[] args) throws Exception {
    	
    	imgMagic("edge_se_a_105");
    	
    	    }
    
    public static String imgMagic(String filename) throws IOException, InterruptedException{

//System.out.println("Start");
String output = "";

    	String command1 = "cd " + basepath + " && convert " 
    						+ filename + ".pdf_font.png -blur 3x2 " + filename + ".pdf_font.gif";
    	
    	String command2 = "cd " + basepath + " && convert " 
    						+ filename + ".pdf_image.png -blur 3x2 " + filename + ".pdf_image.gif";
    	
    	String command3 = "cd " + basepath + " && compare -fuzz 20% -compose src " 
    						+ filename + ".pdf_font.gif " + filename + ".pdf_image.gif " + filename + ".pdf_comp.gif";
    	
    	String command4 = "cd " + basepath + " && convert " 
    						+ filename + ".pdf_comp.gif -morphology Smooth Square:1.25 " + filename + ".pdf_comp-smooth.gif";
    	
 
    	String command5 = "cd " + basepath + " && compare -metric rmse " 
				+ filename + ".pdf_comp-smooth.png white.png null";
    	
        ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c",command1);
        builder1.redirectErrorStream(true);
        Process p1 = builder1.start();
        
        ProcessBuilder builder2 = new ProcessBuilder("cmd.exe", "/c",command2);
        builder2.redirectErrorStream(true);
        Process p2 = builder2.start();
        
        Thread.sleep(5000);
        ProcessBuilder builder3 = new ProcessBuilder("cmd.exe", "/c",command3);
        builder3.redirectErrorStream(true);
        Process p3 = builder3.start();
        
        Thread.sleep(5000);    
        ProcessBuilder builder4 = new ProcessBuilder("cmd.exe", "/c",command4);
        builder4.redirectErrorStream(true);
        Process p4 = builder4.start();
        
        Thread.sleep(5000); 
        ProcessBuilder builder5 = null;
        
        builder5 = new ProcessBuilder("cmd.exe", "/c",command5);
        builder5.redirectErrorStream(true);
        Process p5 = builder5.start();

        InputStream is = p5.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        while ((line = reader.readLine()) != null){
           System.out.println(line);
        	output = output+line;
        }

        //System.out.println("Done.");
        return output;

    }            
}