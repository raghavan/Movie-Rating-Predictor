package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mysql.jdbc.Util;

import util.Constants;
import util.Utility;

import arffmaker.ArffUtil;

public class MoviePredictorTest {

	@Test
	public void testReadAttributes() {
		List<String> attributeNames = Utility.readFromFile(Constants.ATTRIBUTE_NAME_FILE);
		assertTrue(attributeNames.size() == 11);
		System.out.println(attributeNames);
	}

	

}
