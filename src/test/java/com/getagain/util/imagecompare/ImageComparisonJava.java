/*
 * This file is part of the Java library imagecompare.
 *
 * Copyright © 2013, Kay Abendroth or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 *
 * imagecompare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * imagecompare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with imagecompare. If not, see <http://www.gnu.org/licenses/>.
 */
package com.getagain.util.imagecompare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.getagain.util.imagecompare.ImageComparisonModule;
import com.getagain.util.imagecompare.ImageComparisonService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Tests for {@link ImageComparisonService}.
 *
 * @author kay.abendroth@raxion.net (Kay Abendroth)
 * @since 1.0.0
 * @see ImageComparisonService
 */

public class ImageComparisonJava {


    //private static final 
    static String googleReference = "C:\\tt\\sci_nat_4_es_bib_001.pdf_image.png";
    //private static final 
    static String googleIdentical = "C:\\tt\\sci_nat_4_es_bib_001_11.png";

   
    public static void comparetest() throws IOException {

        BufferedImage googleReferenceImage;
        BufferedImage googleIdenticalImage;
        googleIdenticalImage = googleReferenceImage = null;
//--------------------
  
        File file1= new File(googleReference);
        BufferedImage image1 = ImageIO.read(file1);
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        ImageIO.write(image1,"png", os1); 
        InputStream fis1 = new ByteArrayInputStream(os1.toByteArray());
        
        File file2= new File(googleIdentical);
        BufferedImage image2 = ImageIO.read(file2);
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        ImageIO.write(image2,"png", os2); 
        InputStream fis2 = new ByteArrayInputStream(os2.toByteArray());
 
 //-------------------
 //-------------------       
        try {
         googleReferenceImage = ImageIO.read(fis1); 
       //     googleReferenceImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(googleReference));
         googleIdenticalImage = ImageIO.read(fis2);
         //   googleIdenticalImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(googleIdentical));
        } catch (final IOException ioe) {
            assertTrue(ioe.getMessage(), false);
        } catch (final IllegalArgumentException iae) {
            assertTrue(iae.getMessage(), false);
        }

        final Injector injector = Guice.createInjector(new ImageComparisonModule());
        final ImageComparisonService imageComparisonService = injector.getInstance(ImageComparisonService.class);
        List<String[]> values = new ArrayList<String[]>();
        // Test successful comparison: Pictures are 100 per cent identical.
        System.out.println(googleReference);
        final boolean result = imageComparisonService.compare(googleIdenticalImage, googleReferenceImage);
       // assertTrue("Test successful comparison: Pictures are 100 per cent identical.", result);
        System.out.println("Calculated percentage of equality is:"+SimpleImageComparisonProcessor.getpercentEquality());
        String perc1=""+ SimpleImageComparisonProcessor.getpercentEquality();
		values.add(new String[] { googleIdentical, perc1 });
        

		try {
			CSV.writeAllExample(values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static void main(String args[]) throws IOException{
    	long start = System.currentTimeMillis();
    	comparetest();
    	long stop = System.currentTimeMillis();
    	System.out.println("TIME TAKEN IS ="+(stop-start));
    }
}
