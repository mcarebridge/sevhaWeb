package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;


@Model(schemaVersion = 1)
public class VitalSignCategoryValues extends AbstractEntity implements
        Serializable
{
	/**
	 * 
	 */
    private static final long serialVersionUID = -8277956304599632358L;
	
	private int gender;
	private int minAge;
	private int maxAge;
	private double minTargetValue;
	private double maxTargetValue;
    private ModelRef<VitalSignCategory> vitalSignCategory      = new ModelRef<VitalSignCategory>(
    		VitalSignCategory.class);
	
	

	public ModelRef<VitalSignCategory> getVitalSignCategory()
    {
	    return vitalSignCategory;
    }

	public int getGender()
    {
	    return gender;
    }
	public void setGender(int gender)
    {
	    this.gender = gender;
    }
	public int getMinAge()
    {
	    return minAge;
    }
	public void setMinAge(int minAge)
    {
	    this.minAge = minAge;
    }
	public int getMaxAge()
    {
	    return maxAge;
    }
	public void setMaxAge(int maxAge)
    {
	    this.maxAge = maxAge;
    }
	public double getMinTargetValue()
    {
	    return minTargetValue;
    }
	public void setMinTargetValue(double minTargetValue)
    {
	    this.minTargetValue = minTargetValue;
    }
	public double getMaxTargetValue()
    {
	    return maxTargetValue;
    }
	public void setMaxTargetValue(double maxTargetValue)
    {
	    this.maxTargetValue = maxTargetValue;
    }

	
	
	
	
	
}
