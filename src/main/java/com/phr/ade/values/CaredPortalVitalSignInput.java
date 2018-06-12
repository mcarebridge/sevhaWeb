package com.phr.ade.values;

import java.io.Serializable;
import java.util.Hashtable;

public class CaredPortalVitalSignInput implements Serializable
{
	/**
	 * 
	 */
	private static final long       serialVersionUID = -7472455787880609240L;
	private int                     gender;
	private int                     age;
	private Hashtable<Long, Double> vitalSignSubTypeValuePair;
	
	public int getGender()
	{
		return gender;
	}
	
	public void setGender(int gender)
	{
		this.gender = gender;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public Hashtable<Long, Double> getVitalSignSubTypeValuePair()
	{
		return vitalSignSubTypeValuePair;
	}
	
	public void setVitalSignSubTypeValuePair(
	        Hashtable<Long, Double> vitalSignSubTypeValuePair)
	{
		this.vitalSignSubTypeValuePair = vitalSignSubTypeValuePair;
	}
	
}
