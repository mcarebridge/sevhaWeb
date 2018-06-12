package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 2)
public class PreExistingDiseaseCommonSymptom extends AbstractEntity implements
        Serializable
{
	private ModelRef<CommonPreExistingDiseases> preExistingDisease = new ModelRef<CommonPreExistingDiseases>(
	                                                                       CommonPreExistingDiseases.class);
	private String                              symptom;
	
	private int                                 weightage;
	
	public String getSymptom()
	{
		return symptom;
	}
	
	public void setSymptom(String symptom)
	{
		this.symptom = symptom;
	}
	
	public ModelRef<CommonPreExistingDiseases> getPreExistingDisease()
	{
		return preExistingDisease;
	}
	
	public int getWeightage()
	{
		return weightage;
	}
	
	public void setWeightage(int weightage)
	{
		this.weightage = weightage;
	}
}
