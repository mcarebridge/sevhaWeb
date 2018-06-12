package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class ClinicalTestForPreExisitingDisease extends AbstractEntity
        implements Serializable
{
	/**
	 * 
	 */
	private static final long          serialVersionUID = -3536109949682797547L;
	
	private ModelRef<ClinicalTestType> clinicalTestType          = new ModelRef<ClinicalTestType>(
	                                                            ClinicalTestType.class);
	private ModelRef<CommonPreExistingDiseases> commonPreExistingDisease          = new ModelRef<CommonPreExistingDiseases>(CommonPreExistingDiseases.class);

	public ModelRef<ClinicalTestType> getClinicalTestType()
    {
	    return clinicalTestType;
    }

	public ModelRef<CommonPreExistingDiseases> getCommonPreExistingDisease()
    {
	    return commonPreExistingDisease;
    }

}
