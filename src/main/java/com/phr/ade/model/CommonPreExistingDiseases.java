package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class CommonPreExistingDiseases extends AbstractEntity implements
        Serializable
{
	private String diseaseCode;
	private String diseaseName;
	
	public String getDiseaseCode()
	{
		return diseaseCode;
	}
	
	public void setDiseaseCode(String diseaseCode)
	{
		this.diseaseCode = diseaseCode;
	}
	
	public String getDiseaseName()
	{
		return diseaseName;
	}
	
	public void setDiseaseName(String diseaseName)
	{
		this.diseaseName = diseaseName;
	}
	
}
