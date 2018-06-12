package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class ClinicalTestType extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7343717562561849448L;
	
	private String            clinicalTestName;
	private String            frequencyOfTest;
	
	public String getClinicalTestName()
	{
		return clinicalTestName;
	}
	
	public void setClinicalTestName(String clinicalTestName)
	{
		this.clinicalTestName = clinicalTestName;
	}
	
	public String getFrequencyOfTest()
	{
		return frequencyOfTest;
	}
	
	public void setFrequencyOfTest(String frequencyOfTest)
	{
		this.frequencyOfTest = frequencyOfTest;
	}
	
}
