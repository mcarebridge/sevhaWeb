package com.phr.ade.values;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.phr.ade.model.AbstractEntity;
import com.phr.ade.model.VitalSignSubType;


@Model(schemaVersion = 2)
public class VitalSignDTO extends AbstractEntity implements Serializable
{
//	private Integer           vitalSignCategory;
	private int               gender;
	private int               age;
	private String 			  emailAddress;
	private String			  inputData;
	
	
	@Attribute(persistent=false)
	Hashtable<String, Double> vitalSignCategoryPair;
	
//	public Integer getVitalSignCategory()
//	{
//		return vitalSignCategory;
//	}
//	
//	public void setVitalSignCategory(Integer vitalSignCategory)
//	{
//		this.vitalSignCategory = vitalSignCategory;
//	}
	
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
	
	public Hashtable<String, Double> getVitalSignCategoryPair()
	{
		return vitalSignCategoryPair;
	}
	
	public void setVitalSignCategoryPair(
	        Hashtable<String, Double> vitalSignCategoryPair)
	{
		this.vitalSignCategoryPair = vitalSignCategoryPair;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getInputData()
    {
	    return inputData;
    }

	public void setInputData(String inputData)
    {
	    this.inputData = inputData;
    }
	
	
	
}
