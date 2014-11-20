package com.getagain.util.imagecompare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReplace {
	public static void main(String args[]) throws IOException{
		
	
//		File file = new File("C:\\work\\wcg_tx_se_s_fm_021ff.txt");
//
//		try {
//		    Scanner scanner = new Scanner(file);
//
//		    //now read the file line by line...
//		    int lineNum = 0;
//		    while (scanner.hasNextLine()) {
//		        String line = scanner.nextLine();
//		        lineNum++;
//		        
//		        Scanner sc = new Scanner(file);        
//		        String validationResult = sc.findInLine("[^0-9^a-z^A-Z�#*$',:()\">{}_ &.=/]"); // except �
//		        if (validationResult != null) {
//		            // Invalid character found.
//		           System.out.println("Extra text found " + validationResult);
//		           System.out.println("Ascii value: "+ toAscii(validationResult));
//		        }else System.out.println("none");
//		        
//				try {
//				    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\work\\wcg_tx_se_s_fm_021fflog.txt", true)));
//				    out.println(validationResult);
//				    out.close();
//
//				} catch (IOException e) {
//				    //exception handling left as an exercise for the reader
//				}
//
//		    }
//		} catch(FileNotFoundException e) { 
//		    //handle this
//			System.out.println("file not found");
//		}
		
		System.out.println(toAscii(""));
		/*
		
		BufferedReader reader = new BufferedReader(new FileReader("C:/work/Student.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    // ...
			System.out.println(line);
		}
*/
}
	
	public static long toAscii(String s){
        StringBuilder sb = new StringBuilder();
        String ascString = null;
        long asciiInt;
                for (int i = 0; i < s.length(); i++){
                    sb.append((int)s.charAt(i));
                    char c = s.charAt(i);
                }
                ascString = sb.toString();
                asciiInt = Long.parseLong(ascString);
                return asciiInt;
    }
}