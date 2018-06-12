package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.ModelRef;

public class CaredPersonHealthIndex extends AbstractEntity implements
        Serializable
{
	
	/**
	 * 
	 */
	private static final long     serialVersionUID = -8654658352277756934L;
	private ModelRef<CaredPerson> caredPerson      = new ModelRef<CaredPerson>(
	                                                       CaredPerson.class);
	private double                rxComplianceIndex;
	private double                symptomIndex;
	private double                vitalSignIndex;
	
	public CaredPersonHealthIndex()
	{
	}
	
	public double getRxComplianceIndex()
	{
		return rxComplianceIndex;
	}
	
	public double getSymptomIndex()
	{
		return symptomIndex;
	}
	
	public void setSymptomIndex(double symptomIndex)
	{
		this.symptomIndex = symptomIndex;
	}
	
	public double getVitalSignIndex()
	{
		return vitalSignIndex;
	}
	
	public void setVitalSignIndex(double vitalSignIndex)
	{
		this.vitalSignIndex = vitalSignIndex;
	}
	
	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}
	
	public void setCaredPerson(ModelRef<CaredPerson> caredPerson)
	{
		this.caredPerson = caredPerson;
	}
	
}
