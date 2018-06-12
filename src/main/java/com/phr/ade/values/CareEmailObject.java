package com.phr.ade.values;

import java.util.Map;

public class CareEmailObject
{
	private int                                 age;
	private int                                 gender;
	private Map<String, VitalSignUserOutPutDTO> vsSIgnUserOutputDTOMapSortedInner;
	
	public void setAge(int _age)
	{
		age = _age;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setGender(int _gender)
	{
		gender = _gender;
	}
	
	public int getGender()
	{
		return gender;
	}
	
	public void setUserObject(
	        Map<String, VitalSignUserOutPutDTO> _vsSIgnUserOutputDTOMapSorted)
	{
		vsSIgnUserOutputDTOMapSortedInner = _vsSIgnUserOutputDTOMapSorted;
	};
	
	public Map<String, VitalSignUserOutPutDTO> getUserObject()
	{
		return vsSIgnUserOutputDTOMapSortedInner;
	}
}