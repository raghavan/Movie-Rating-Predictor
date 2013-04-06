package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.InfoDetail;

public class PersonInfoMap {
	Map<Integer, List<InfoDetail>> personInfoMap = new HashMap<Integer, List<InfoDetail>>();

	public void addPersonInfoDetail(int personId, InfoDetail infoDetail){
		List<InfoDetail> infoDetails = personInfoMap.get(personId);
		if(infoDetails == null){
			infoDetails = new ArrayList<InfoDetail>();
		}
		infoDetails.add(infoDetail);
		personInfoMap.put(personId, infoDetails);
	}
	
	public List<InfoDetail> getInfoDetails(int personId){
		return personInfoMap.get(personId);
	}
	
	public Set<Integer> getAllPersonIds(){
		return personInfoMap.keySet();
	}
	
}