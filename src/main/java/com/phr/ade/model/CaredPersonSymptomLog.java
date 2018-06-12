package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class CaredPersonSymptomLog extends AbstractEntity implements
        Serializable
{
	private ModelRef<CaredPersonRxResponse>           caredPersonResponse             = new ModelRef<CaredPersonRxResponse>(
	                                                                                          CaredPersonRxResponse.class);
	
	private ModelRef<PreExistingDiseaseCommonSymptom> preExistingDiseaseCommonSymptom = new ModelRef<PreExistingDiseaseCommonSymptom>(
	                                                                                          PreExistingDiseaseCommonSymptom.class);
	
	public ModelRef<CaredPersonRxResponse> getCaredPersonResponse()
	{
		return caredPersonResponse;
	}
	
	public ModelRef<PreExistingDiseaseCommonSymptom> getPreExistingDiseaseCommonSymptom()
	{
		return preExistingDiseaseCommonSymptom;
	}
	
}
