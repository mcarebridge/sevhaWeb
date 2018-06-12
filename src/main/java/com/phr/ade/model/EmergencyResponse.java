package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class EmergencyResponse extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long     serialVersionUID = -8831350496316151535L;
	private String                providerName;
	private String                contactPerson;
	
	private ModelRef<Address>     address          = new ModelRef<Address>(
	                                                       Address.class);
	
	private ModelRef<CaredPerson> caredPerson      = new ModelRef<CaredPerson>(
	                                                       CaredPerson.class);
	
	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}

	public String getProviderName()
	{
		return providerName;
	}
	
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}
	
	public String getContactPerson()
	{
		return contactPerson;
	}
	
	public void setContactPerson(String contactPerson)
	{
		this.contactPerson = contactPerson;
	}
	
	public ModelRef<Address> getAddress()
	{
		return address;
	}
	
}
