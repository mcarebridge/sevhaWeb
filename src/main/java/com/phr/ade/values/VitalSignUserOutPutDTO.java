package com.phr.ade.values;

import java.io.Serializable;

import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.model.VitalSignSubType;

public class VitalSignUserOutPutDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long       serialVersionUID = -3653407023493152787L;
	private VitalSignCategoryValues vitalSignCatValReported;
	private VitalSignCategoryValues vitalSignCatValIDEAL;
	private Double                  vitalSignValueProvided;
	private String                  vitalSignSubType;
	
	public VitalSignCategoryValues getVitalSignCatValReported()
	{
		return vitalSignCatValReported;
	}
	
	public void setVitalSignCatValReported(
	        VitalSignCategoryValues vitalSignCatValReported)
	{
		this.vitalSignCatValReported = vitalSignCatValReported;
	}
	
	public VitalSignCategoryValues getVitalSignCatValIDEAL()
	{
		return vitalSignCatValIDEAL;
	}
	
	public void setVitalSignCatValIDEAL(
	        VitalSignCategoryValues vitalSignCatValIDEAL)
	{
		this.vitalSignCatValIDEAL = vitalSignCatValIDEAL;
	}
	
	public Double getVitalSignValueProvided()
	{
		return vitalSignValueProvided;
	}
	
	public void setVitalSignValueProvided(Double vitalSignValueProvided)
	{
		this.vitalSignValueProvided = vitalSignValueProvided;
	}
	
	public String getVitalSignSubType()
	{
		return vitalSignSubType;
	}
	
	public void setVitalSignSubType(String vitalSignSubType)
	{
		this.vitalSignSubType = vitalSignSubType;
	}
}
