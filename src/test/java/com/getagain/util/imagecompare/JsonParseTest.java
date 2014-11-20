package com.getagain.util.imagecompare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParseTest {

	static String basepath = "C:\\work\\HtmlReview\\test7\\json";
	static String filename;
    static String filePath;// = ;
	
	public static void main(String[] args) throws IOException, ParseException {
		filename = "\\edge_se_f_004";
		filePath = basepath + filename + ".json";
		// -- empty existing text file
		PrintWriter writer = new PrintWriter(basepath + filename + ".txt");
		writer.print("");
		writer.close();
		

			// read the json file
			FileReader reader = new FileReader(filePath);

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
			JSONArray data= (JSONArray) jsonObject.get("data");
			//System.out.println(data);
			if(!data.isEmpty()){
			JSONObject innerObj1 = (JSONObject) data.get(0); 
			//System.out.println(innerObj1.toString());
					if(!innerObj1.isEmpty()){
					JSONObject innerObj2 = (JSONObject) innerObj1.get("operatorList");
					//System.out.println(innerObj2.toString());
							if(!innerObj2.isEmpty()){
							JSONArray innerObj3= (JSONArray) innerObj2.get("argsArray");
							//JSONObject innerObj3 = (JSONObject) innerObj2.get(0);
							System.out.println(innerObj3.toString());
							System.out.println(innerObj3.size());
							
							JSONArray jOut = iterateJson(innerObj3);
							
	//---------------------		
//							Iterator i = innerObj3.iterator();
//							 List<String[]> AllText = new ArrayList<String[]>();
//							// take each value from the json array separately
//							while (i.hasNext()) {
//								JSONObject innerObj4 = (JSONObject) i.next();
//							//	String hdn = innerObj3.get("hdn").toString();
//							}
	//---------------------
								
							}
					
					}
			
			
			}
//			// take the elements of the json array
//			for(int i=0; i<txt.size(); i++){
//				System.out.println(txt.get(i));
//			}
			

			
			
			
//			Iterator i = data.iterator();
//			 List<String[]> AllText = new ArrayList<String[]>();
//			// take each value from the json array separately
//			while (i.hasNext()) {
//				JSONObject innerObj = (JSONObject) i.next();
//				String hdn = innerObj.get("hdn").toString();
//				if (hdn.equalsIgnoreCase("false")){
//				String txt1 = innerObj.get("txt").toString();
//// ---------
//				
////				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)))) {
////				    out.println("the text");
////				}catch (IOException e) {
////				    //exception handling left as an exercise for the reader
////				}
//				
//				
				try {
				    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(basepath + filename + ".txt", true)));
				    out.println(data);
				    out.close();

				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
//				
//				}

			
		    System.err.println(filename+ ".txt write successful.");	
//		    Path path = Paths.get("test.txt");
//		    Charset charset = StandardCharsets.UTF_8;
//
//		    String content = new String(Files.readAllBytes(path), charset);
//		    content = content.replaceAll("foo", "bar");
//		    Files.write(path, content.getBytes(charset));
//			// handle a structure into the json object
//			JSONObject structure = (JSONObject) jsonObject.get("job");
//			System.out.println("Into job structure, name: " + structure.get("name"));

	
//	String containsKey(JSONObject myJsonObject, String key) {
//	    String containsHelloKey = "";
//	    try {
//	        JSONArray arr = myJsonObject.getJSONArray("unicode");
//	        for(int i=0; i<arr.length(); ++i) {
//	            if(arr.getJSONObject(i).get(key) != null) {
//			String unicode = innerObj.get
//	               containsHelloKey = true;
//	               break;
//	            }
//	        }
//	    } catch (JSONException e) {}
//
//	    return containsHelloKey;
//	}

		}
	
	public static JSONArray iterateJson(JSONArray jarr)
	{
		JSONArray jarrOut = jarr;
		Iterator i = jarrOut.iterator();
		//System.out.println(jarrOut);
		
		while (i.hasNext()) {
			JSONArray jr1 = (JSONArray) jarrOut.get(Integer.parseInt(i.toString()));
			Iterator j = jr1.iterator();
					while (j.hasNext()){
						System.out.println(jr1);
					iterateJson(jr1);
					System.out.println(jr1);
					}
				return jarrOut;
			}

		return jarrOut;
	}

}
