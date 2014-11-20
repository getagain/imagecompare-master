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
import java.net.URL;
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


public class ImageComparisonJava1 {

	
	static String image1 = "E:\\HtmlReview1\\test3\\rch_sb_B1_021.pdf_font.png";
	static String image2 = "E:\\HtmlReview1\\test3\\rch_sb_B1_021.pdf_image.png";
    public static void main(String args[])
    {
      BufferedImage img1 = null;
      BufferedImage img2 = null;
      try {
//        URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
//        URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
        
        File file1= new File(image1);
        File file2= new File(image2);
        
//        img1 = ImageIO.read(url1);
//        img2 = ImageIO.read(url2);
        
        img1 = ImageIO.read(file1);
        img2 = ImageIO.read(file2);
        
      } catch (IOException e) {
        e.printStackTrace();
      }
      long width1 = img1.getWidth(null);
      long width2 = img2.getWidth(null);
      long height1 = img1.getHeight(null);
      long height2 = img2.getHeight(null);
      if ((width1 != width2) || (height1 != height2)) {
        System.err.println("Error: Images dimensions mismatch");
        System.exit(1);
      }
      long diff = 0;
      for (int i = 0; i < (int)height1; i++) {
        for (int j = 0; j < (int)width1; j++) {
          long rgb1 = img1.getRGB((int)i, (int)j);
          long rgb2 = img2.getRGB((int)i, (int)j);
          long r1 = (rgb1 >> 16) & 0xff;
          long g1 = (rgb1 >>  8) & 0xff;
          long b1 = (rgb1      ) & 0xff;
          long r2 = (rgb2 >> 16) & 0xff;
          long g2 = (rgb2 >>  8) & 0xff;
          long b2 = (rgb2      ) & 0xff;
          diff += Math.abs(r1 - r2);
          diff += Math.abs(g1 - g2);
          diff += Math.abs(b1 - b2);
        }
      }
      double n = width1 * height1 * 3;
      double p = diff / n / 255.0;
      System.out.println("diff percent: " + (p * 100.0));
    }

}
