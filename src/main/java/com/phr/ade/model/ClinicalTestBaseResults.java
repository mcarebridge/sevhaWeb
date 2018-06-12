package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class ClinicalTestBaseResults extends AbstractEntity implements
        Serializable
{
	
	/**
	 * 
	 */
	private static final long          serialVersionUID = -1211604946766341047L;
	
	private ModelRef<ClinicalTestType> clinicalTestType = new ModelRef<ClinicalTestType>(
	                                                            ClinicalTestType.class);
	
	private double                     minValue;
	private double                     maxValue;
	// GT,LT,BT
	private String                     logicalOperator;
	private int                        age;
	private String                     gender;
	
	public ModelRef<ClinicalTestType> getClinicalTestType()
	{
		return clinicalTestType;
	}
	
	public double getMinValue()
	{
		return minValue;
	}
	
	public void setMinValue(double minValue)
	{
		this.minValue = minValue;
	}
	
	public double getMaxValue()
	{
		return maxValue;
	}
	
	public void setMaxValue(double maxValue)
	{
		this.maxValue = maxValue;
	}
	
	public String getLogicalOperator()
	{
		return logicalOperator;
	}
	
	public void setLogicalOperator(String logicalOperator)
	{
		this.logicalOperator = logicalOperator;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
}
