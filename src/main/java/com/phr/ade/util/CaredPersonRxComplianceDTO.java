package com.phr.ade.util;

import java.io.Serializable;
import java.util.List;

import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PrescriptionLines;

public class CaredPersonRxComplianceDTO implements Serializable
{
	
	/**
	 * 
	 */
	private static final long           serialVersionUID = 4455952823550288499L;
	
	private PrescriptionLines           prescriptionLines;
	private List<CaredPersonRxSnapShot> rxSnapshot;
	
	public PrescriptionLines getPrescriptionLines()
	{
		return prescriptionLines;
	}
	
	public void setPrescriptionLines(PrescriptionLines prescriptionLines)
	{
		this.prescriptionLines = prescriptionLines;
	}

	public List<CaredPersonRxSnapShot> getRxSnapshot()
    {
	    return rxSnapshot;
    }

	public void setRxSnapshot(List<CaredPersonRxSnapShot> rxSnapshot)
    {
	    this.rxSnapshot = rxSnapshot;
    }
	
}
