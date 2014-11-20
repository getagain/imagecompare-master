package com.getagain.util.imagecompare;
import java.lang.reflect.*;

public class test1 {
	String tt = "fn2";
	public static void main(String args[]) throws Exception{
String currentTest = "1 of 114";
System.out.println("start");

currentTest = currentTest.replaceAll(".*of ", "");
System.out.println(currentTest);
				

	}
	
	public void validateLaunch(String launch) throws Exception {
		
//		Method method = getClass().getDeclaredMethod(launch);
//		Class[] parameterTypes = method.getParameterTypes();
//		Class returnType = method.getReturnType();
//	    method.invoke(this);
		
		//get method that takes a String as argument
		Method method = getClass().getMethod("video", String.class, String.class);

		method.invoke(this, "parameter-value1","p2");
	
	}
	
	public void video(String caption, String para2){
		System.out.println("video");
		System.out.println(caption);
		System.out.println(para2);
		
	}
	
	
	public void audio(){
		System.out.println("audio");
		
	}
}
