package datahandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.Utility;

import model.InfoDetail;
import model.Movie;
import model.PersonInfoMap;
import model.PersonRoleYear;
import model.PersonRoleYearMap;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class DBHandler {

	public List<Movie> getAllMoviesWithRatingVotingDistrib() {
		String query = Constants.QUERY_GET_RECORDS_WITH_RATING_DISTRIB_VOTES;
		List<Movie> movies = new ArrayList<Movie>();
		if (query != null) {
			ResultSet results = getResults(query, DBConnect.getConnection());
			movies = makeMovieFromResultSet(results);
		}
		return movies;
	}

	public PersonInfoMap getPersonInfoForGivenQuery(String query, int infoTypeId) {
		PersonInfoMap personInfoMap = new PersonInfoMap();
		if (query != null) {
			PreparedStatement ps;
			try {
				ps = DBConnect.getConnection().prepareStatement(query + "?;");
				ps.setInt(1, infoTypeId);
				ResultSet results = ps.executeQuery();
				personInfoMap = makePersonFromResultSet(results);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return personInfoMap;
	}

	private PersonInfoMap makePersonFromResultSet(ResultSet results) {
		PersonInfoMap personInfoMap = new PersonInfoMap();
		if (results != null) {
			try {
				while (results.next()) {
					int personId = results.getInt("person_id");
					InfoDetail infoDetail = new InfoDetail();
					infoDetail.setInfo(results.getString("info"));
					infoDetail.setInfoTypeId(results.getInt("info_type_id"));
					infoDetail.setRecordId(results.getInt("id"));
					personInfoMap.addPersonInfoDetail(personId, infoDetail);
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
		return personInfoMap;
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

	public PersonRoleYearMap getPersonRoleYearMapGivenQuery(String query, int infoTypeId) {
		PersonRoleYearMap personRoleYearMap = new PersonRoleYearMap();
		if (query != null) {
			PreparedStatement ps;
			try {
				ps = DBConnect.getConnection().prepareStatement(query + "?;");
				ps.setInt(1, infoTypeId);
				ResultSet results = ps.executeQuery();
				personRoleYearMap = makePersonRoleYearMapFromResultSet(results);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return personRoleYearMap;
	}
	
	private PersonRoleYearMap makePersonRoleYearMapFromResultSet(ResultSet results) {
		PersonRoleYearMap personRoleYearMap = new PersonRoleYearMap();
		if (results != null) {
			try {
				while (results.next()) {
					int personId = results.getInt("person_id");
					List<Integer> years = Utility.getYearFromString(results.getString("info"));
					int movieYear = Utility.getAnyYearMatchingThreshold(years);					
					PersonRoleYear personRoleYear = new PersonRoleYear();
					personRoleYear.setPersonId(personId);
					personRoleYear.setRoleId(0);
					personRoleYear.setYear(movieYear);	
					if(movieYear != 0){
						personRoleYearMap.addPersonInfoDetail(personRoleYear, 1);
					}
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
		return personRoleYearMap;
	}

}
