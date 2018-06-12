package com.phr.ade.util;

import java.io.Serializable;
import java.util.Date;

import com.phr.ade.model.PrescriptionLines;

public class CaredPersonRxSnapShot implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2271192351268499008L;
	private PrescriptionLines prescriptionLine;
	private Date              rxScheduledTimestamp;
	private String            rxStatus;
	private Date              statusTimestamp;
	
	public Date getStatusTimestamp()
	{
		return statusTimestamp;
	}
	
	public void setStatusTimestamp(Date statusTimestamp)
	{
		this.statusTimestamp = statusTimestamp;
	}
	
	public String getRxStatus()
	{
		return rxStatus;
	}
	
	public void setRxStatus(String rxStatus)
	{
		this.rxStatus = rxStatus;
	}
	
	public PrescriptionLines getPrescriptionLine()
	{
		return prescriptionLine;
	}
	
	public void setPrescriptionLine(PrescriptionLines prescriptionLine)
	{
		this.prescriptionLine = prescriptionLine;
	}
	
	public Date getRxScheduledTimestamp()
	{
		return rxScheduledTimestamp;
	}
	
	public void setRxScheduledTimestamp(Date rxScheduledTimestamp)
	{
		this.rxScheduledTimestamp = rxScheduledTimestamp;
	}
	
}
