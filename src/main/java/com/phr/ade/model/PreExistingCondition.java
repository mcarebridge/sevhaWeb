package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class PreExistingCondition extends AbstractEntity implements
        Serializable
{
	
	/**
	 * 
	 */
	private static final long     serialVersionUID = -6594889809933918460L;
	
	private ModelRef<CaredPerson> caredPerson      = new ModelRef<CaredPerson>(
	                                                       CaredPerson.class);
	
	
	private ModelRef<CommonPreExistingDiseases> preExisitingDiseases = new ModelRef<CommonPreExistingDiseases>(
	                                                                         CommonPreExistingDiseases.class);


	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}


	public ModelRef<CommonPreExistingDiseases> getPreExisitingDiseases()
	{
		return preExisitingDiseases;
	}
	
	
}
