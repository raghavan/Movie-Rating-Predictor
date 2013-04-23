package dataloader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.PersonRoleYear;
import model.PersonRoleYearMap;
import util.Constants;
import util.Utility;
import datahandler.DBHandler;

public class PersonRoleCummCountLoader {
	public static void main(String args[]) {
		// loadCummCountOfMovies();
		loadCummCountOfOtherInfos(Constants.COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_ARTICLE,
				Constants.FILE_PERSON_ROLE_CUMM_COUNT_OF_ARTICLE, Constants.QUERY_GET_PERSON_INFO,
				Constants.INFO_ARTCILE);
		
		loadCummCountOfOtherInfos(Constants.COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_MAGAZINE,
				Constants.FILE_PERSON_ROLE_CUMM_COUNT_OF_MAGAZINE, Constants.QUERY_GET_PERSON_INFO,
				Constants.INFO_MAGAZINE_COVER);
		
		loadCummCountOfOtherInfos(Constants.COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_BIOGRAPHY,
				Constants.FILE_PERSON_ROLE_CUMM_COUNT_OF_BIOGRAPHY, Constants.QUERY_GET_PERSON_INFO,
				Constants.INFO_BIOGRAPHY_MOVIES);
		
		
	}

	private static void loadCummCountOfMovies() {
		Utility.deleteFile(Constants.FILE_PERSON_ROLE_CUMM_COUNT_OF_MOVIE);
		PersonRoleYearMap personRoleYearMap = readFromFile(Constants.CSV_PERSON_ROLE_YEAR_COUNT);
		System.out.println("Map size =" + personRoleYearMap.getAllKeys().size());
		createMovieCountUpdateStmt(personRoleYearMap);
	}

	// This is to load Articles,magazines,biographies
	private static void loadCummCountOfOtherInfos(String columnName, String fileName, String query, int infoTypeId) {
		Utility.deleteFile(fileName);
		DBHandler dbHandler = new DBHandler();
		PersonRoleYearMap personRoleYearMap = dbHandler.getPersonRoleYearMapGivenQuery(query, infoTypeId);
		System.out.println("Map size =" + personRoleYearMap.getAllKeys().size());
		createOtherInfoUpdateStmt(columnName, fileName, personRoleYearMap);
	}

	private static void createMovieCountUpdateStmt(PersonRoleYearMap personRoleYearMap) {
		for (PersonRoleYear personRoleYear : personRoleYearMap.getAllKeys()) {
			int count = personRoleYearMap.getCount(personRoleYear);

			int countBefore = personRoleYearMap.getCountBefore(personRoleYear);
			if (countBefore != 0) {
				count += countBefore;
			}
			String updateStmt = "update person_role_cumcount_analytics set cum_count_of_movies =  " + count
					+ " where person_id = " + personRoleYear.getPersonId() + Constants.AND + " role_id = "
					+ personRoleYear.getRoleId() + Constants.AND + " year = " + personRoleYear.getYear() + ";";
			Utility.appendDataToFile(Constants.FILE_PERSON_ROLE_CUMM_COUNT_OF_MOVIE, updateStmt);
		}
		System.out.println("Written to file successfully");
	}

	private static void createOtherInfoUpdateStmt(String columnName, String fileName,
			PersonRoleYearMap personRoleYearMap) {
		List<Integer> personIdsFeeded = new ArrayList<Integer>();
		for (PersonRoleYear personRoleYear : personRoleYearMap.getAllKeys()) {
			if (!personIdsFeeded.contains(personRoleYear.getPersonId())) {
				personIdsFeeded.add(personRoleYear.getPersonId());
				for (int i = Constants.MIN_YEAR; i <= Constants.MAX_YEAR; i++) {
					personRoleYear.setYear(i);
					int count = personRoleYearMap.getCount(personRoleYear);
					int countBefore = personRoleYearMap.getCountBefore(personRoleYear);
					if (countBefore != 0) {
						count += countBefore;
					}
					if (count != 0) {
						String updateStmt = "update person_role_cumcount_analytics set " + columnName + "=  " + count
								+ " where person_id = " + personRoleYear.getPersonId() + Constants.AND + " year = "
								+ personRoleYear.getYear() + ";";
						Utility.appendDataToFile(fileName, updateStmt);
					}
				}
			}
		}
		System.out.println("Written to file successfully");
	}

	private static PersonRoleYearMap readFromFile(String fileName) {
		FileInputStream fstream = null;
		PersonRoleYearMap personRoleYearMap = new PersonRoleYearMap();
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
				if (strLine != null) {
					PersonRoleYear personRoleYr = makePersonRoleYear(strLine);
					int count = Integer.parseInt(strLine.split(",")[3]);
					personRoleYearMap.addPersonInfoDetail(personRoleYr, count);
				}
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
		return personRoleYearMap;
	}

	private static PersonRoleYear makePersonRoleYear(String strLine) {
		String[] strArr = strLine.split(",");
		PersonRoleYear personRoleYear = new PersonRoleYear();
		personRoleYear.setPersonId(Integer.parseInt(strArr[0]));
		personRoleYear.setRoleId(Integer.parseInt(strArr[1]));
		personRoleYear.setYear(Integer.parseInt(strArr[2]));
		return personRoleYear;
	}
}
