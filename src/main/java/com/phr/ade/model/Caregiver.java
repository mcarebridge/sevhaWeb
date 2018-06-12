/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * @author DS5002449
 * 
 */
@Model(schemaVersion = 1)
public class Caregiver extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4072815862510096419L;
    private String firstName;
    private String lastName;
    private Integer gender;
    private Date dob;
    private ModelRef<Address> address = new ModelRef<Address>(Address.class);
    private ModelRef<Profile> profile = new ModelRef<Profile>(Profile.class);

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

    public ModelRef<Address> getAddress()
    {
	return address;
    }

    public Integer getGender()
    {
	return gender;
    }

    public void setGender(Integer gender)
    {
	this.gender = gender;
    }

}
