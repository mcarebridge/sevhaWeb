package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class RxCaredMedicalSymptomResponse extends AbstractEntity implements
	Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7803909870097084559L;
    private String medicalSymptom;
    private String caredResponse;
    private ModelRef<PrescriptionLines> prescriptionLine = new ModelRef<PrescriptionLines>(
	    PrescriptionLines.class);

    public ModelRef<PrescriptionLines> getPrescriptionLine()
    {
	return prescriptionLine;
    }

    public String getMedicalSymptom()
    {
	return medicalSymptom;
    }

    public void setMedicalSymptom(String medicalSymptom)
    {
	this.medicalSymptom = medicalSymptom;
    }

    public String getCaredResponse()
    {
	return caredResponse;
    }

    public void setCaredResponse(String caredResponse)
    {
	this.caredResponse = caredResponse;
    }

}
