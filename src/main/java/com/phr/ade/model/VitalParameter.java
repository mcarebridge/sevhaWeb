package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 3)
public class VitalParameter extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long     serialVersionUID = -881118156042267043L;
	private double                height;
	private double                weight;
	private double                temprature;
	private double                pulse;
	private double                systolicPressure;
	private double                diastolicPressure;
	private double                bloodSugar;
	private double                bloodSugarFasting;
	
	private ModelRef<CaredPerson> caredPerson      = new ModelRef<CaredPerson>(
	                                                       CaredPerson.class);
	
	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public double getTemprature()
	{
		return temprature;
	}
	
	public void setTemprature(double temprature)
	{
		this.temprature = temprature;
	}
	
	public double getPulse()
	{
		return pulse;
	}
	
	public void setPulse(double pulse)
	{
		this.pulse = pulse;
	}
	
	public double getSystolicPressure()
	{
		return systolicPressure;
	}
	
	public void setSystolicPressure(double systolicPressure)
	{
		this.systolicPressure = systolicPressure;
	}
	
	public double getDiastolicPressure()
	{
		return diastolicPressure;
	}
	
	public void setDiastolicPressure(double diastolicPressure)
	{
		this.diastolicPressure = diastolicPressure;
	}

	public double getBloodSugar()
	{
		return bloodSugar;
	}

	public void setBloodSugar(double bloodSugar)
	{
		this.bloodSugar = bloodSugar;
	}

	public double getBloodSugarFasting()
    {
	    return bloodSugarFasting;
    }

	public void setBloodSugarFasting(double bloodSugarFasting)
    {
	    this.bloodSugarFasting = bloodSugarFasting;
    }
	
	
	
	
}
