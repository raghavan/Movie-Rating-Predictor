package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Utility {

	static Map<String, String> propContents = null;
	public static Map<String, String> readpropFile(String fileName) {
		if (propContents == null) {
			propContents = new HashMap<String, String>();
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(fileName));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				propContents.put(key.trim().toLowerCase(), props.getProperty(key).trim().toLowerCase());
			}
		}
		return propContents;
	}

	public static List<String> readFromFile(String fileName) {
		FileInputStream fstream = null;
		List<String> fileRecords = new ArrayList<String>();
		try {
			fstream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.trim();
				fileRecords.add(strLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileRecords;
	}

	

	private static String reconstructTweet(List<String> words) {
		StringBuffer newStr = new StringBuffer();
		for (String word : words) {
			newStr.append(word);
			newStr.append(" ");
		}
		return newStr.toString().trim();
	}

	public static void writeDataToFile(String fileName, List<String> content) {
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(fileName);
			out = new BufferedWriter(fstream);
			for (String str : content) {
				out.write(str);
				out.write("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
