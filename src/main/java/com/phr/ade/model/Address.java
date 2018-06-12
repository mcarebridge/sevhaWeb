package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

@Model(schemaVersion = 2)
public class Address extends AbstractEntity implements Serializable
{
	private String streetAddress;
	private String city;
	private String country;
	private String zip;
	private String cellphone;
	private long imei;
	private String fixedLine;
	private String email;
	
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getZip()
	{
		return zip;
	}
	
	public void setZip(String zip)
	{
		this.zip = zip;
	}
	
	public String getCellphone()
	{
		return cellphone;
	}
	
	public void setCellphone(String cellphone)
	{
		this.cellphone = cellphone;
	}
	
	public String getFixedLine()
	{
		return fixedLine;
	}
	
	public void setFixedLine(String fixedLine)
	{
		this.fixedLine = fixedLine;
	}

	public String getEmail()
    {
	    return email;
    }

	public void setEmail(String email)
    {
	    this.email = email;
    }

	public long getImei()
    {
	    return imei;
    }

	public void setImei(long imei)
    {
	    this.imei = imei;
    }

	public String getStreetAddress()
    {
	    return streetAddress;
    }

	public void setStreetAddress(String streetAddress)
    {
	    this.streetAddress = streetAddress;
    }
	
}
