package com.shubham.automation.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class DataProvider {

	int max, min;
	
	boolean isException = false;
	String filepath = "src"+File.separator+"test"+File.separator+"resources"+File.separator+"testdata"+File.separator+"AP_Content_File.csv";
		
	public int generate_random_number_within_range(int max1,int min1)
	{
		Random rand = new Random();
		int num=rand.nextInt((max1 - min1) + 1) +min1;
		return num;
	}
	
	
	/*
	 * The following function
	 * gets data from content File
	 */
	
	
	
	public String getAnswerToSelect(String Token) {
		
		String AnswerToSelect=" ";
		InputStream is;
		String lineArray[];

		try {

			is = new FileInputStream(new File(filepath));
			DataInputStream in = new DataInputStream(is);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			int i = 1;

			while ((strLine = br.readLine()) != null) {

				if (i >= 1) {
		  			
					lineArray=strLine.split(",");
					
					if(lineArray[0].equals(Token))
					{
						  
						AnswerToSelect=lineArray[1];
						break;
					}
					
				}
				i++;
			}
			
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return AnswerToSelect;
	
	}

	
}
