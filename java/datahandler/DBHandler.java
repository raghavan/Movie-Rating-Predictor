package datahandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.Constants;

import model.Movie;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;




public class DBHandler{
	
	
	public List<Movie> getAllMoviesWithRatingVotingDistrib() {
		String query = Constants.QUERY_GET_RECORDS_WITH_RATING_DISTRIB_VOTES;
		List<Movie> movies = new ArrayList<Movie>();
		if (query != null) {
			ResultSet results = getResults(query, DBConnect.getConnection());
			movies = makeMovieFromResultSet(results);
		}
		return movies;
	} 

	private List<Movie> makeMovieFromResultSet(ResultSet results) {
		List<Movie> movies = new ArrayList<Movie>();
		if (results != null) {
			try {
				while (results.next()) {
					Movie movie = new Movie();
					movie.setMovieId(results.getInt("movieId"));
					movie.setNumberOfVotes(results.getInt("votes"));
					movie.setVoteDistribution(results.getString("voteDistribution"));
					movie.setRating(results.getFloat("rating"));
					movies.add(movie);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					results.close();
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}
		return movies;
	}

	private ResultSet getResults(String query, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (MySQLSyntaxErrorException me) {
			System.out.println("Error in select query =" + query);
		} catch (SQLException e) {
			System.out.println("Error in select query =" + query);
			e.printStackTrace();
		}
		return rs;
	}

	
}
