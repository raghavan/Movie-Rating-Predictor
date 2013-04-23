package util;

public interface Constants {
	// DB props
	String dbDriver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:8889/imdb_new";
	String username = "root";
	String password = "root";

	// Query
	String QUERY_GET_RECORDS_WITH_RATING_DISTRIB_VOTES = "select mii1.movie_id as movieId,  mii1.info as rating,mii2.info as voteDistribution,mii3.info as votes from movie_info_idx mii1,movie_info_idx mii2,movie_info_idx mii3  " +
			"where mii1.info_type_id = 101 and  mii2.info_type_id = 99 and mii3.info_type_id = 100 and mii2.movie_id = mii1.movie_id and mii3.movie_id = mii1.movie_id";
	
	String QUERY_GET_PERSON_INFO = "select * from person_info where info_type_id = ";


	// File
	String ARFF_TRAINING_FILE = "files/trainingmodel.arff";
	String ARFF_TESTING_FILE = "files/testingmodel.arff";

	String CSV_TRAINING_FILE = "files/trainingmodel.csv";
	String CSV_TESTING_FILE = "files/testingmodel.csv";
	String CSV_PERSON_ROLE_YEAR_COUNT = "files/personRoleYearMovieCount.csv";
	
	String FILE_PERSON_ARTICLE_COUNT_UPDATE_STMT = "files/person_article_count.sql";
	String FILE_PERSON_MAGAZINE_COUNT_UPDATE_STMT = "files/person_magazine_count.sql";
	String FILE_PERSON_BIOGRAPHY_COUNT_UPDATE_STMT = "files/person_biography_count.sql";
	String FILE_PERSON_MOVIE_SALARY = "files/person_movie_salary.sql";
	String FILE_PERSON_ROLE_CUMM_COUNT_OF_MOVIE = "files/person_role_cumm_count_movies.sql";
	String FILE_PERSON_ROLE_CUMM_COUNT_OF_ARTICLE = "files/person_role_cumm_count_articles.sql";
	String FILE_PERSON_ROLE_CUMM_COUNT_OF_MAGAZINE = "files/person_role_cumm_count_magazines.sql";
	String FILE_PERSON_ROLE_CUMM_COUNT_OF_BIOGRAPHY = "files/person_role_cumm_count_biographies.sql";

	

	
	String ATTRIBUTE_NAME_FILE = "files/attributeNames.txt";

	// Symbols
	String QUOTE = "\"";
	String COMMA = ",";
	String AND = " and ";

	// Config
	int CONFIG_USE_DICT_WORD_BREAK = 0;//0-don't use;1-use	
	String CLASS_LABEL = "4.6,6.8,3.4,6.1,6.6,7.0,6.4,6.5,7.1,6.0,6.9,6.7,6.3,5.7,3.0,1.8,7.7,5.6,8.3,8.1,8.0,7.8,8.8,7.9,8.2,7.6,8.5,8.4,7.5,8.6,8.7,2.3,4.1,3.3,5.9,5.2,6.2,5.5,7.2,7.3,7.4,5.8,5.3,4.7,2.2,5.1,5.0,3.1,5.4,4.9,3.8,3.7,4.3,2.5,2.9,2.4,2.0,2.7,4.4,4.5,1.6,3.5,3.6,4.2,9.2,9.0,4.8,3.9,3.2,4.0,2.8,10.0,1.2,8.9,1.4,1.9,1.1,1.0,2.1,1.3,1.7,2.6,1.5,9.4,9.1,9.6,9.3,9.8,9.5,9.7,9.9";
	
	
	int TRAINING_THRESHOLD_YEAR = 2007;	
	int TESTING_THRESHOLD_YEAR = 2012;
	
	//Info Type	
	int INFO_MAGAZINE_COVER =  37;
	int INFO_ARTCILE =  36;
	int INFO_BIOGRAPHY_MOVIES =  31;
	int INFO_SALARY = 27;
	
	//Values
	int MIN_YEAR = 1888;
	int MAX_YEAR = 2006;
	
	
	//Column Name
	String COLUMN_PERSON_IN_MOVIE_ARTICLE="count_articles";
	String COLUMN_PERSON_IN_MOVIE_MAGAZINE = "count_of_magazine_cover";
	String COLUMN_PERSON_IN_MOVIE_BIOGRAPHY = "count_biographies";
	
	String COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_ARTICLE="cum_count_of_articles";
	String COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_MAGAZINE = "cum_count_of_magazine_covers";
	String COLUMN_CUMM_COUNT_PERSON_IN_MOVIE_BIOGRAPHY = "cum_count_of_biography_movies";
	
	
	
	
	
	
}
