/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * @author DS5002449
 * 
 */
@Model(schemaVersion = 6)
public class PrescriptionLines extends AbstractEntity implements Serializable
{
	
	/**
     * 
     */
	private static final long      serialVersionUID = -1705135943397787542L;
	private String                 drugName;
	private String                 strength;
	private String                 unit;
	private String                 route;
	private String                 frequency;
	
	@Attribute(persistent = false)
	private String                 frequencyForDisplay;
	@Attribute(persistent = false)
	private boolean                substituteFound  = false;
	// private String howMuch;
	private Integer                duration;
	private Date                   rxstartdate;
	@Attribute(persistent = false)
	private Date                   rxEnddate;
	private boolean                rxExpired;
	private boolean                reordered        = false;
	private String                 mealOption;
	private Boolean                stopRxLine       = false;
	
	private ModelRef<Prescription> prescription     = new ModelRef<Prescription>(
	                                                        Prescription.class);
	
	public Date getRxEnddate()
	{
		return rxEnddate;
	}
	
	public void setRxEnddate(Date rxEnddate)
	{
		this.rxEnddate = rxEnddate;
	}
	
	/**
	 * @return the prescription
	 */
	public ModelRef<Prescription> getPrescription()
	{
		return prescription;
	}
	
	public String getDrugName()
	{
		return drugName;
	}
	
	public void setDrugName(String drugName)
	{
		this.drugName = drugName;
	}
	
	public String getStrength()
	{
		return strength;
	}
	
	public void setStrength(String strength)
	{
		this.strength = strength;
	}
	
	public String getRoute()
	{
		return route;
	}
	
	public void setRoute(String route)
	{
		this.route = route;
	}
	
	public String getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}
	
	// public String getHowMuch()
	// {
	// return howMuch;
	// }
	//
	// public void setHowMuch(String howMuch)
	// {
	// this.howMuch = howMuch;
	// }
	
	public Date getRxstartdate()
	{
		return rxstartdate;
	}
	
	public void setRxstartdate(Date rxstartdate)
	{
		this.rxstartdate = rxstartdate;
	}
	
	public String getFrequencyForDisplay()
	{
		return frequencyForDisplay;
	}
	
	public void setFrequencyForDisplay(String frequencyForDisplay)
	{
		this.frequencyForDisplay = frequencyForDisplay;
	}
	
	public boolean isSubstituteFound()
	{
		return substituteFound;
	}
	
	public void setSubstituteFound(boolean substituteFound)
	{
		this.substituteFound = substituteFound;
	}
	
	public Boolean getStopRxLine()
	{
		return stopRxLine;
	}
	
	public void setStopRxLine(Boolean stopRxLine)
	{
		this.stopRxLine = stopRxLine;
	}
	
	public Integer getDuration()
	{
		return duration;
	}
	
	public void setDuration(Integer duration)
	{
		this.duration = duration;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public boolean isRxExpired()
	{
		return rxExpired;
	}
	
	public void setRxExpired(boolean rxExpired)
	{
		this.rxExpired = rxExpired;
	}
	
	public boolean isReordered()
	{
		return reordered;
	}
	
	public void setReordered(boolean reordered)
	{
		this.reordered = reordered;
	}
	
	public String getMealOption()
	{
		return mealOption;
	}
	
	public void setMealOption(String mealOption)
	{
		this.mealOption = mealOption;
	}
}
