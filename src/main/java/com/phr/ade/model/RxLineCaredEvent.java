package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class RxLineCaredEvent extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 9057214887078662654L;
    private Date alarmDateTime;
    private String caredResponse;
    private ModelRef<PrescriptionLines> prescriptionLine = new ModelRef<PrescriptionLines>(
	    PrescriptionLines.class);

    public String getCaredResponse()
    {
	return caredResponse;
    }

    public void setCaredResponse(String caredResponse)
    {
	this.caredResponse = caredResponse;
    }

    public Date getAlarmDateTime()
    {
	return alarmDateTime;
    }

    public void setAlarmDateTime(Date alarmDateTime)
    {
	this.alarmDateTime = alarmDateTime;
    }

    public ModelRef<PrescriptionLines> getPrescriptionLine()
    {
	return prescriptionLine;
    }
}
