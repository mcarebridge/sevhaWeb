/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Persistent;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

/**
 * @author DS5002449
 * 
 */
@Model(schemaVersion = 2)
public class Profile extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6188089777893569028L;
	private String firstname;
	private String lastname;
	private Integer gender;
	private Date dateofbirth;
	private String city;
	private String country;
	private Integer purpose;
	private String email;
	private String password;
	private Boolean active;
	private Boolean firstTimeUser = true;
	private Boolean showHelpTips = true;
	@Attribute(persistent = false)
	private ArrayList<PreExistingCondition> preExistingConditions;

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the secondname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param secondname the secondname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the gender
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateofbirth
	 */
	public Date getDateofbirth() {
		return dateofbirth;
	}

	/**
	 * @param dateofbirth the dateofbirth to set
	 */
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the purpose
	 */
	public Integer getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getFirstTimeUser() {
		return firstTimeUser;
	}

	public void setFirstTimeUser(Boolean firstTimeUser) {
		this.firstTimeUser = firstTimeUser;
	}

	public Boolean getShowHelpTips() {
		return showHelpTips;
	}

	public void setShowHelpTips(Boolean showHelpTips) {
		this.showHelpTips = showHelpTips;
	}

	public ArrayList<PreExistingCondition> getPreExistingConditions() {
		return preExistingConditions;
	}

	public void setPreExistingConditions(ArrayList<PreExistingCondition> preExistingConditions) {
		this.preExistingConditions = preExistingConditions;
	}

}
