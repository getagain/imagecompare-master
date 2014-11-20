package com.getagain.util.imagecompare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSV {

	public static void readLineByLineExample() throws IOException {
		System.out.println("\n**** readLineByLineExample ****");
		String csvFilename = "C:\\work\\sample.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		String[] row = null;
		while ((row = csvReader.readNext()) != null) {
			System.out.println(row[0] + " # " + row[1] + " #  " + row[2]);
		}

		csvReader.close();
	}

	public static void readAllExample() throws IOException {
		System.out.println("\n**** readAllExample ****");
		String[] row = null;
		String csvFilename = "C:\\work\\sample.csv";

		CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		List content = csvReader.readAll();

		for (Object object : content) {
			row = (String[]) object;

			System.out.println(row[0] + " # " + row[1] + " #  " + row[2]);
		}

		csvReader.close();

	}

	public static void writeCSVExample() throws IOException {
		System.out.println("\n**** writeCSVExample ****");

		String csv = "C:\\work\\output.csv";

		CSVWriter writer = new CSVWriter(new FileWriter(csv));

		String[] country = "India#China#United States".split("#");
		writer.writeNext(country);
		System.out.println("CSV written successfully.");
		writer.close();
	}

	public static void writeAllExample(List<String[]> values) throws IOException {
		//System.out.println("\n**** writeAll ****");

		String csv = "C:\\work\\JsonValidator.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(csv));

		List<String[]> data = new ArrayList<String[]>();
        data = values;

		writer.writeAll(data);
		System.out.println("CSV written successfully.");
		writer.close();
	}
	
	public static void writeAll(String csv1, List<String[]> values) throws IOException {
		//System.out.println("\n**** writeAll ****");

		String csv = csv1;//"C:\\work\\JsonValidator.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(csv));

		List<String[]> data = new ArrayList<String[]>();
        data = values;

		writer.writeAll(data);
		System.out.println("CSV written successfully.");
		writer.close();
	}

}
