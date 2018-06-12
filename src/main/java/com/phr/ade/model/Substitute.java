package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 2)
public class Substitute extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -58975836588498878L;

    private String substitueDrug;

    private String reasonForSubstitute;

    private String substitutionSuggestedBy;

    private Boolean currentSubstitute;

    private String strength;

    private ModelRef<PrescriptionLines> prescriptionLines = new ModelRef<PrescriptionLines>(
	    PrescriptionLines.class);

    public String getSubstitueDrug()
    {
	return substitueDrug;
    }

    public void setSubstitueDrug(String substitueDrug)
    {
	this.substitueDrug = substitueDrug;
    }

    public String getReasonForSubstitute()
    {
	return reasonForSubstitute;
    }

    public void setReasonForSubstitute(String reasonForSubstitute)
    {
	this.reasonForSubstitute = reasonForSubstitute;
    }

    public ModelRef<PrescriptionLines> getPrescriptionLines()
    {
	return prescriptionLines;
    }

    public String getSubstitutionSuggestedBy()
    {
	return substitutionSuggestedBy;
    }


    public void setSubstitutionSuggestedBy(String substitutionSuggestedBy)
    {
	this.substitutionSuggestedBy = substitutionSuggestedBy;
    }

    public String getStrength()
    {
	return strength;
    }

    public void setStrength(String strength)
    {
	this.strength = strength;
    }

	public Boolean getCurrentSubstitute()
    {
	    return currentSubstitute;
    }

	public void setCurrentSubstitute(Boolean currentSubstitute)
    {
	    this.currentSubstitute = currentSubstitute;
    }

}
