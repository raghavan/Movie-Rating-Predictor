package csvmaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Movie;

public class CSVUtil {
	public static void writeCSVDataToFille(List<Movie> movies,String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			for (Movie movie : movies) {
				print(out,movie.getArffStructuredData());
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
