package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 4)
public class CaredPersonRxResponse extends AbstractEntity implements
        Serializable
{
	private ModelRef<CaredPerson> caredPerson = new ModelRef<CaredPerson>(
	                                                  CaredPerson.class);
	private Date                  responseDateTime;
	
	private int                   responseYear;
	private int                   responseMonth;
	private int                   responseDay;
	private int                   responseHour;
	private int                   responseMinute;
	private String                mobileTimeZone;
	
	private String                action;
	
	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public Date getResponseDateTime()
	{
		return responseDateTime;
	}
	
	public void setResponseDateTime(Date responseDateTime)
	{
		this.responseDateTime = responseDateTime;
	}
	
	public int getResponseHour()
	{
		return responseHour;
	}
	
	public void setResponseHour(int responseHour)
	{
		this.responseHour = responseHour;
	}

	public int getResponseYear()
    {
	    return responseYear;
    }

	public void setResponseYear(int responseYear)
    {
	    this.responseYear = responseYear;
    }

	public int getResponseMonth()
    {
	    return responseMonth;
    }

	public void setResponseMonth(int responseMonth)
    {
	    this.responseMonth = responseMonth;
    }

	public int getResponseDay()
    {
	    return responseDay;
    }

	public void setResponseDay(int responseDay)
    {
	    this.responseDay = responseDay;
    }

	public String getMobileTimeZone()
    {
	    return mobileTimeZone;
    }

	public void setMobileTimeZone(String mobileTimeZone)
    {
	    this.mobileTimeZone = mobileTimeZone;
    }

	public int getResponseMinute()
    {
	    return responseMinute;
    }

	public void setResponseMinute(int responseMinute)
    {
	    this.responseMinute = responseMinute;
    }
}
