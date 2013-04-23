package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.Constants;

public class PersonRoleYearMap {
	Map<PersonRoleYear, Integer> map = new HashMap<PersonRoleYear, Integer>();

	public void addPersonInfoDetail(PersonRoleYear personRoleYr, int count) {
		int existingCount = 0;
		/*if (map.get(personRoleYr) == null) {
			existingCount = getCountBefore(personRoleYr);
		}*/
		if (map.get(personRoleYr) != null) {
			existingCount = map.get(personRoleYr);
		}
		existingCount += count;
		map.put(personRoleYr, existingCount);
	}

	public int getCount(PersonRoleYear personRoleYr) {
		if(map.get(personRoleYr) != null)
			return map.get(personRoleYr);
		
		return 0;
	}

	public Set<PersonRoleYear> getAllKeys() {
		return map.keySet();
	}

	public int getCountBefore(PersonRoleYear personRoleYear) {
		PersonRoleYear personRoleYear2 = new PersonRoleYear();
		personRoleYear2.setPersonId(personRoleYear.getPersonId());
		personRoleYear2.setRoleId(personRoleYear.getRoleId());
		personRoleYear2.setYear(personRoleYear.getYear()-1);
			
		int counter = 0;
		while (personRoleYear2.getYear() >= Constants.MIN_YEAR) {
			if(map.get(personRoleYear2) != null){
				counter += map.get(personRoleYear2);
			}
			personRoleYear2.setYear(personRoleYear2.getYear() - 1);
		}
		
		return counter;
	}
}
