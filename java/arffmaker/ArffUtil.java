package arffmaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Movie;

import util.Utility;

import util.Constants;

public class ArffUtil {

	public static String getArffRelation() {
		String arffRelation = "@RELATION MovieRatingPrediction";
		return arffRelation;
	}
	
	public static List<String> getArffAttributes() {
		List<String> attributeResults = new ArrayList<String>();
		List<String> attributes = Utility.readFromFile(Constants.ATTRIBUTE_NAME_FILE);
		for (String attributeName : attributes) {
			attributeResults.add("@ATTRIBUTE "+attributeName+"  NUMERIC");
		}	
		attributeResults.add("@ATTRIBUTE class  {"+Constants.CLASS_LABEL+"}");
		return attributeResults;
	}

	public static List<String> getArrfData(List<Movie> movies) {
		List<String> dataRecords = new ArrayList<String>();
		dataRecords.add("@DATA");
		for (Movie movie : movies) {						
			dataRecords.add(movie.getArffStructuredData());
		}
		return dataRecords;
	}

	public static void writeArffDataToFille(List<Movie> movies,String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			print(out,getArffRelation());
			goNLines(out, 2);
			List<String> structuredAtrributeNames = getArffAttributes();
			for (String structuredAttrib : structuredAtrributeNames ) {
				print(out,structuredAttrib);
				goNLines(out, 1);
			}						
			goNLines(out, 1);
			List<String> arffDataRecords  = getArrfData(movies);
			for (String str : arffDataRecords) {
				print(out,str);
				goNLines(out, 1);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void print(BufferedWriter out,String str) throws IOException {
			out.write(str);
	}

	private static void goNLines(BufferedWriter out, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			out.write("\n");
		}
	}
	
}
