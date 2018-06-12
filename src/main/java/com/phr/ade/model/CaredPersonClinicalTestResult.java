package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class CaredPersonClinicalTestResult extends AbstractEntity implements Serializable
{
	
	private double testResult;
	
	private ModelRef<ClinicalTestType> clinicalTestType = new ModelRef<ClinicalTestType>(
            ClinicalTestType.class);
	private ModelRef<CaredPerson> caredPerson      = new ModelRef<CaredPerson>(
            CaredPerson.class);
	
	
	
	public ModelRef<ClinicalTestType> getClinicalTestType()
    {
	    return clinicalTestType;
    }
	

	
	public ModelRef<CaredPerson> getCaredPerson()
    {
	    return caredPerson;
    }


	public double getTestResult()
    {
	    return testResult;
    }


	public void setTestResult(double testResult)
    {
	    this.testResult = testResult;
    }
	
}
