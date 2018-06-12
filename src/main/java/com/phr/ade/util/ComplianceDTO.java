package com.phr.ade.util;

import java.io.Serializable;

public class ComplianceDTO implements Serializable
{
	private String complianceItem;
	private String complianceValue;
	private String alertIndicator;
	private String unit;
	
	public String getComplianceItem()
	{
		return complianceItem;
	}
	
	public void setComplianceItem(String complianceItem)
	{
		this.complianceItem = complianceItem;
	}
	
	public String getComplianceValue()
	{
		return complianceValue;
	}
	
	public void setComplianceValue(String complianceValue)
	{
		this.complianceValue = complianceValue;
	}
	
	public String getAlertIndicator()
	{
		return alertIndicator;
	}
	
	public void setAlertIndicator(String alertIndicator)
	{
		this.alertIndicator = alertIndicator;
	}

	public String getUnit()
    {
	    return unit;
    }

	public void setUnit(String unit)
    {
	    this.unit = unit;
    }
	
}
