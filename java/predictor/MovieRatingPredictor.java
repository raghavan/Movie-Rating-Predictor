package predictor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Movie;
import util.Constants;
import csvmaker.CSVUtil;
import datahandler.DBHandler;

public class MovieRatingPredictor {

	
	public static void main(String args[]){
		DBHandler dbHandler = new  DBHandler();
		List<Movie> movies = dbHandler.getAllMoviesWithRatingVotingDistrib();
		List<Movie> traingingMovies = getRandomMoviesForTraining(movies, 70);
		List<Movie> testingMovies = new ArrayList<Movie>(movies);
		testingMovies.removeAll(traingingMovies);
		System.out.println("Training size ="+traingingMovies.size());
		System.out.println("Testing size ="+testingMovies.size());
		
		/*ArffUtil.writeArffDataToFille(traingingMovies, Constants.ARFF_TRAINING_FILE);
		System.out.println("Wrote Arff training file");
		ArffUtil.writeArffDataToFille(testingMovies, Constants.ARFF_TESTING_FILE);		
		System.out.println("Wrote Arff testing file");*/
		
		CSVUtil.writeCSVDataToFille(traingingMovies, Constants.CSV_TRAINING_FILE);
		System.out.println("Wrote csv training file");
		CSVUtil.writeCSVDataToFille(testingMovies, Constants.CSV_TESTING_FILE);
		System.out.println("Wrote csv test file");
	}
	
	
	private static List<Movie> getRandomMoviesForTraining(List<Movie> movies,int percentageForTraining) {
		List<Movie> randomMovies = new ArrayList<Movie>(movies);
		Collections.shuffle(randomMovies);	
		int endSize = (percentageForTraining * randomMovies.size()) /100;
		List<Movie> randomTweetsToReturn = randomMovies.subList(0, endSize);
		return randomTweetsToReturn;		
	}
}
