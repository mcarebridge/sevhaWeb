package com.phr.ade.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PrescriptionLines;

public class CaredPersonRxComplianceSnapShot implements Serializable
{
	/**
	 * 
	 */
	private static final long                                         serialVersionUID = 5367682842503337152L;
	private Date                                                      snapShotDate;
	private List<CaredPersonRxSnapShot>                               rxSnapshot;
	private List<CaredPersonSymptomLog>                               caredPersonSymptomLog;
	private Hashtable<PrescriptionLines, List<CaredPersonRxSnapShot>> cpRxComplianceKV;
	
	public Date getSnapShotDate()
	{
		return snapShotDate;
	}
	
	public void setSnapShotDate(Date snapShotDate)
	{
		this.snapShotDate = snapShotDate;
	}
	
	public List<CaredPersonRxSnapShot> getRxSnapshot()
	{
		return rxSnapshot;
	}
	
	public void setRxSnapshot(List<CaredPersonRxSnapShot> rxSnapshot)
	{
		this.rxSnapshot = rxSnapshot;
	}
	
	public List<CaredPersonSymptomLog> getCaredPersonSymptomLog()
	{
		return caredPersonSymptomLog;
	}
	
	public void setCaredPersonSymptomLog(
	        List<CaredPersonSymptomLog> caredPersonSymptomLog)
	{
		this.caredPersonSymptomLog = caredPersonSymptomLog;
	}
	
	public Hashtable<PrescriptionLines, List<CaredPersonRxSnapShot>> getCpRxComplianceKV()
	{
		return cpRxComplianceKV;
	}
	
	public void setCpRxComplianceKV(
	        Hashtable<PrescriptionLines, List<CaredPersonRxSnapShot>> cpRxComplianceKV)
	{
		this.cpRxComplianceKV = cpRxComplianceKV;
	}
}
