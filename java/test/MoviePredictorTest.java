package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import util.Constants;
import util.Utility;

import arffmaker.ArffUtil;

public class MoviePredictorTest {

	@Test
	public void testReadAttributes(){
		List<String> attributeNames = Utility.readFromFile(Constants.ATTRIBUTE_NAME_FILE);
		assertTrue(attributeNames.size() == 11);
		System.out.println(attributeNames);
	}
}
