/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * @author DS5002449
 * 
 */
@Model(schemaVersion = 1)
public class Prescription extends AbstractEntity implements Serializable
{
	
	/**
     * 
     */
	private static final long       serialVersionUID = 5782223057444243008L;
	
	private String                  prescriptionTag;
	private String                  medicalCondition;
	private Date                    rxStartDate;
	private ModelRef<Physician>     physician        = new ModelRef<Physician>(
	                                                         Physician.class);
	private ModelRef<CaredPerson>   caredPerson      = new ModelRef<CaredPerson>(
	                                                         CaredPerson.class);
	private ModelRef<Caregiver>     careGiver        = new ModelRef<Caregiver>(
	                                                         Caregiver.class);
	
	@Attribute(persistent = false)
	private List<PrescriptionLines> prescriptionLines;
	
	/**
	 * @return the physician
	 */
	public ModelRef<Physician> getPhysician()
	{
		return physician;
	}
	
	/**
	 * @return the prescriptionTag
	 */
	public String getPrescriptionTag()
	{
		return prescriptionTag;
	}
	
	/**
	 * @param prescriptionTag
	 *            the prescriptionTag to set
	 */
	public void setPrescriptionTag(String prescriptionTag)
	{
		this.prescriptionTag = prescriptionTag;
	}
	
	/**
	 * @return the medicalCondition
	 */
	public String getMedicalCondition()
	{
		return medicalCondition;
	}
	
	/**
	 * @param medicalCondition
	 *            the medicalCondition to set
	 */
	public void setMedicalCondition(String medicalCondition)
	{
		this.medicalCondition = medicalCondition;
	}
	
	public ModelRef<CaredPerson> getCaredPerson()
	{
		return caredPerson;
	}
	
	public ModelRef<Caregiver> getCareGiver()
	{
		return careGiver;
	}
	
	public List<PrescriptionLines> getPrescriptionLines()
	{
		return prescriptionLines;
	}
	
	public void setPrescriptionLines(List<PrescriptionLines> prescriptionLines)
	{
		this.prescriptionLines = prescriptionLines;
	}

	public Date getRxStartDate()
    {
	    return rxStartDate;
    }

	public void setRxStartDate(Date rxStartDate)
    {
	    this.rxStartDate = rxStartDate;
    }
	
}
