package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import dataloader.DataLoader;

import util.Constants;
import util.Utility;

public class MovieDataLoaderTest {
	@Test
	public void testYearCount() {
		List<String> years = Utility.readFromFile("files/query_result.csv");
		Map<Integer, Integer> yearCount = new HashMap<Integer, Integer>();
		for (String str : years) {
			str = str.replace("\"", "");
			str = str.replace(":", "");
			Integer yearInt = -1;
			try{
				yearInt = Integer.parseInt(str);
			}catch(NumberFormatException e){
				System.out.println(str);
			}
			int count = 0;
			if (yearCount.containsKey(yearInt)) {
				count = yearCount.get(yearInt);
			}
			count += 1;
			yearCount.put(yearInt, count);
		}
		
		for(Integer yr : yearCount.keySet()){
			System.out.println(yr + " "+yearCount.get(yr));
		}
	}
	
	@Test
	public void testGetYearFromString(){
		String info = "_\"The Abbott and Costello Show\" (1952)_ (qv)::$1534,00/episode";
		System.out.println(Utility.getYearFromString(info));
	}
	
	
	
	@Test
	public void testMakeUpdateStmtsForArticleCount(){
		List<String> updateStmts = DataLoader.getPersonInfoCountUpdateStmts(Constants.QUERY_GET_PERSON_INFO, Constants.INFO_ARTCILE,
				Constants.COLUMN_PERSON_IN_MOVIE_ARTICLE, Constants.FILE_PERSON_ARTICLE_COUNT_UPDATE_STMT);
	}
	
	@Test
	public void testGetSalaryRegex(){
		String info = "_\"The Abbott and Costello Show\" (1952)_ (qv)::$15,000/episode";
		System.out.println(Utility.getSalaryFromString(info));
	}
	
	@Test
	public void testMakeInsertStmtsForPersonMovie(){
		List<String> insertStmts = DataLoader.getPersonMovieSalaryInsertStmt();
		System.out.println(insertStmts.size());
	}
}
