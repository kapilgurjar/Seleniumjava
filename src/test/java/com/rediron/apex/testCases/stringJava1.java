package com.rediron.apex.testCases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import com.rediron.apex.testBase.TestBase;
import com.rediron.apex.utils.Utility;

public class stringJava1 extends TestBase {

	public static void main(String[] args) throws Exception {
//		String dbDate="2020-04-01 00:00:00";
//		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dbDate);
//		System.out.println("Before parse date " +date1);
//		//07/14/2021
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		String strDate1 = formatter.format(date1);
//		System.out.println("after parse date " +strDate1);
//		Utility.encodedString("Devtest@1234");
		// 2022-11-07 00:00:00
//		String expDate=Utility.executeQuery("SELECT * FROM configdate where site_no=112", "CONFIG_DT");
//		System.out.println(expDate);
		//03/07/2023
		String date="2023-03-07 00:00:00";
		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		System.out.println(date1);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
		String strDate1 = formatter.format(date1);
		System.out.println(strDate1);
//      Utility.encodedString("Devtest@123456");
//	    Random r = new Random();
//	    int low = 1;
//	    int high = 30;
//	    int result = r.nextInt(high-low) + low;
//	    System.out.println(result);	
		
		//String number = "150.000";
		//System.out.println(number.replaceAll("\\.\\d+$", ""));

		//System.out.println(number.split(Pattern.quote(".")));
//		String text="jhgh";
//		System.out.println(text.length());
		
	}
}
