package model;

public class PersonRoleYear {

	private int personId;
	private int roleId;
	private int year;
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + personId;
		result = prime * result + roleId;
		result = prime * result + year;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonRoleYear other = (PersonRoleYear) obj;
		if (personId != other.personId)
			return false;
		if (roleId != other.roleId)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	
	
}
