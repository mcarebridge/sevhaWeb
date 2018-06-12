package com.phr.ade.values;

import java.io.Serializable;

public class CareMailDTO implements Serializable
{
	
	private String toAddress;
	private String mailsubject;
	private String mailBody;
	private String recepiantName;
	
	public String getToAddress()
	{
		return toAddress;
	}
	
	public void setToAddress(String toAddress)
	{
		this.toAddress = toAddress;
	}
	
	public String getMailsubject()
	{
		return mailsubject;
	}
	
	public void setMailsubject(String mailsubject)
	{
		this.mailsubject = mailsubject;
	}
	
	public String getMailBody()
	{
		return mailBody;
	}
	
	public void setMailBody(String mailBody)
	{
		this.mailBody = mailBody;
	}

	public String getRecepiantName()
    {
	    return recepiantName;
    }

	public void setRecepiantName(String recepiantName)
    {
	    this.recepiantName = recepiantName;
    }
}
