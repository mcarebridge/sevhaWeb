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
public class CaredPerson extends AbstractEntity implements Serializable
{
	
	private static final long          serialVersionUID = 4072815862510096419L;
	private String                     firstName;
	private String                     lastName;
	private Date                       dob;
	private Integer                    gender;
	private ModelRef<Address>          address          = new ModelRef<Address>(
	                                                            Address.class);
	
	private ModelRef<Caregiver>        caregiver        = new ModelRef<Caregiver>(
	                                                            Caregiver.class);
	
	private ModelRef<Profile>          profile          = new ModelRef<Profile>(
	                                                            Profile.class);
	@Attribute(persistent = false)
	private List<PreExistingCondition> preExistingCondition;
	
	@Attribute(persistent = false)
	private List<Prescription>         prescription;
	
	@Attribute(persistent = false)
	private EmergencyResponse          emergencyResponse;
	
	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	/**
	 * @return the caregiver
	 */
	public ModelRef<Caregiver> getCaregiver()
	{
		return caregiver;
	}
	
	/**
	 * @return the profile
	 */
	public ModelRef<Profile> getProfile()
	{
		return profile;
	}
	
	public Date getDob()
	{
		return dob;
	}
	
	public void setDob(Date dob)
	{
		this.dob = dob;
	}
	
	public Integer getGender()
	{
		return gender;
	}
	
	public void setGender(Integer gender)
	{
		this.gender = gender;
	}
	
	public ModelRef<Address> getAddress()
	{
		return address;
	}
	
	public List<PreExistingCondition> getPreExistingCondition()
	{
		return preExistingCondition;
	}
	
	public void setPreExistingCondition(
	        List<PreExistingCondition> preExistingCondition)
	{
		this.preExistingCondition = preExistingCondition;
	}
	
	public List<Prescription> getPrescription()
	{
		return prescription;
	}
	
	public void setPrescription(List<Prescription> prescription)
	{
		this.prescription = prescription;
	}
	
	public EmergencyResponse getEmergencyResponse()
	{
		return emergencyResponse;
	}
	
	public void setEmergencyResponse(EmergencyResponse emergencyResponse)
	{
		this.emergencyResponse = emergencyResponse;
	}
}
