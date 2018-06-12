/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * @author DS5002449
 * 
 */

@Model(schemaVersion = 2)
public class Physician extends AbstractEntity implements Serializable
{
	
	/**
     * 
     */
	private static final long             serialVersionUID    = -8882450827455762468L;
	private String                        firstname;
	private String                        lastname;
	private String                        hospital;
	private ModelRef<Address>             address             = new ModelRef<Address>(
	                                                                  Address.class);
	private ModelRef<Profile>             profile             = new ModelRef<Profile>(
	                                                                  Profile.class);
	private ModelRef<PhysicianSpeciality> physicianSpeciality = new ModelRef<PhysicianSpeciality>(
	                                                                  PhysicianSpeciality.class);
	
	/**
	 * @return the firstname
	 */
	public String getFirstname()
	{
		return firstname;
	}
	
	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	/**
	 * @return the lastname
	 */
	public String getLastname()
	{
		return lastname;
	}
	
	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	/**
	 * @return the hospital
	 */
	public String getHospital()
	{
		return hospital;
	}
	
	/**
	 * @param hospital
	 *            the hospital to set
	 */
	public void setHospital(String hospital)
	{
		this.hospital = hospital;
	}
	
	public ModelRef<Profile> getProfile()
	{
		return profile;
	}
	
	public ModelRef<PhysicianSpeciality> getPhysicianSpeciality()
	{
		return physicianSpeciality;
	}
	
	public ModelRef<Address> getAddress()
	{
		return address;
	}
	
}
