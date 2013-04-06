package dataloader;

import java.util.ArrayList;
import java.util.List;

import model.InfoDetail;
import model.PersonInfoMap;
import util.Constants;
import util.Utility;
import datahandler.DBHandler;

public class DataLoader {

	public static void main(String args[]) {

		// Make Article count
		getPersonInfoCountUpdateStmts(Constants.QUERY_GET_PERSON_INFO, Constants.INFO_ARTCILE,
				Constants.COLUMN_PERSON_IN_MOVIE_ARTICLE, Constants.FILE_PERSON_ARTICLE_COUNT_UPDATE_STMT);

		// Make Magazine count
		getPersonInfoCountUpdateStmts(Constants.QUERY_GET_PERSON_INFO, Constants.INFO_MAGAZINE_COVER,
				Constants.COLUMN_PERSON_IN_MOVIE_MAGAZINE, Constants.FILE_PERSON_MAGAZINE_COUNT_UPDATE_STMT);

		// Make Biography count
		getPersonInfoCountUpdateStmts(Constants.QUERY_GET_PERSON_INFO, Constants.INFO_BIOGRAPHY_MOVIES,
				Constants.COLUMN_PERSON_IN_MOVIE_BIOGRAPHY, Constants.FILE_PERSON_BIOGRAPHY_COUNT_UPDATE_STMT);

		// Person salary insert stmt
		getPersonMovieSalaryInsertStmt();

	}

	public static List<String> getPersonMovieSalaryInsertStmt() {
		/*
		 * Ignoring the % of profit amounts and not able to get the movie id but
		 * movie year is captured
		 */
		DBHandler dbHandler = new DBHandler();
		PersonInfoMap personInfoMap = dbHandler.getPersonInfoForGivenQuery(Constants.QUERY_GET_PERSON_INFO,
				Constants.INFO_SALARY);
		List<String> insertStmts = makePersonSalaryInsertStmt(personInfoMap);
		Utility.writeDataToFile(Constants.FILE_PERSON_MOVIE_SALARY, insertStmts);
		return insertStmts;
	}

	private static List<String> makePersonSalaryInsertStmt(PersonInfoMap personInfoMap) {
		List<String> insertStmts = new ArrayList<String>();
		
		for (Integer personId : personInfoMap.getAllPersonIds()) {

			for (InfoDetail infoDetail : personInfoMap.getInfoDetails(personId)) {
				List<Integer> years = Utility.getYearFromString(infoDetail.getInfo());
				int movieYear = getAnyYearMatchingThreshold(years);
				Integer salary = Utility.getSalaryFromString(infoDetail.getInfo()); 
				if(movieYear != 0 || salary != null){
					String updateStmt = "insert into person_movie_salary(person_id,salary_unnormalized,movie_year) values ("+
							personId+Constants.COMMA+salary+Constants.COMMA+movieYear+");";
					insertStmts.add(updateStmt);					
				}
			}
		}
		return insertStmts;
	}
	
	public static List<String> getPersonInfoCountUpdateStmts(String query, int infoTypeId, String columnName,
			String fileName) {
		DBHandler dbHandler = new DBHandler();
		PersonInfoMap personInfoMap = dbHandler.getPersonInfoForGivenQuery(query, infoTypeId);
		List<String> updateStmts = makePersonInfoUpdateStmts(personInfoMap, columnName);
		Utility.writeDataToFile(fileName, updateStmts);
		return updateStmts;
	}

	private static List<String> makePersonInfoUpdateStmts(PersonInfoMap personInfoMap, String columnName) {
		List<String> updateStmts = new ArrayList<String>();
		for (Integer personId : personInfoMap.getAllPersonIds()) {
			int count = 0;
			for (InfoDetail infoDetail : personInfoMap.getInfoDetails(personId)) {
				List<Integer> years = Utility.getYearFromString(infoDetail.getInfo());
				if (getAnyYearMatchingThreshold(years) != 0) {
					count += 1;
				}
			}
			String updateStmt = makeUpdateStmt(columnName, personId, count);
			updateStmts.add(updateStmt);
		}

		return updateStmts;
	}

	private static String makeUpdateStmt(String columnName, Integer personId, int count) {
		String updateStmt = "update persons_in_movie set " + columnName + "=" + count + " where person_id=" + personId
				+ ";";
		return updateStmt;
	}

	private static int getAnyYearMatchingThreshold(List<Integer> years) {
		if (years != null && years.size() > 0) {
			for (Integer year : years) {
				if (year != 0 && year < Constants.TRAINING_THRESHOLD_YEAR) {
					return year;
				}
			}
		}
		return 0;
	}

}
