package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class RxLineCaredAction extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8181835613349231442L;

    private ModelRef<PrescriptionLines> prescriptionLine = new ModelRef<PrescriptionLines>(
	    PrescriptionLines.class);
    private boolean nextDosageDue;
    private Date nextAlarmTime;
    private Integer remainingQuantity;

    public ModelRef<PrescriptionLines> getPrescriptionLine()
    {
	return prescriptionLine;
    }

    public boolean isNextDosageDue()
    {
	return nextDosageDue;
    }

    public void setNextDosageDue(boolean nextDosageDue)
    {
	this.nextDosageDue = nextDosageDue;
    }

    public Date getNextAlarmTime()
    {
	return nextAlarmTime;
    }

    public void setNextAlarmTime(Date nextAlarmTime)
    {
	this.nextAlarmTime = nextAlarmTime;
    }

    public Integer getRemainingQuantity()
    {
	return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity)
    {
	this.remainingQuantity = remainingQuantity;
    }
}
