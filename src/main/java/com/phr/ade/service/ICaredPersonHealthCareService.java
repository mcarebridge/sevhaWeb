package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonHealthIndex;
import com.phr.ade.values.ICareBridgeConstants;

/**
 * This class calculates the Health Index for Rx Compliance, Symptoms and Vital
 * Signs And overall affect due to this.
 */

public interface ICaredPersonHealthCareService extends ICareBridgeConstants
{
	
	/**
	 * For all the Rx (Active at this moment)
	 * 
	 * @param rxTimeEvent
	 * @return
	 */
	double calcRxComplianceIndexForTimeEvent(CaredPerson caredPerson,
	        Date rxTimeEvent);
	
	/**
	 * Calc the vital sign compliance Index
	 * 
	 * @param rxTimeEvent
	 * @return
	 */
	double calcVitalSignComplianceIndexForTimeEvent(CaredPerson caredPerson,
	        Date rxTimeEvent);
	
	/**
	 * Calc the Index for the severity of the Symptom
	 * 
	 * @param rxTimeEvent
	 * @return
	 */
	double calcSymptomSeverityIndexForTimeEvent(CaredPerson caredPerson,
	        Date rxTimeEvent);
	
	
	/**
	 * 
	 * @param caredPerson
	 * @param preExistingSymptomsKeySubmitted
	 * @return
	 */
	double calcSymptomSeverityIndexForTimeEvent(CaredPerson caredPerson, ArrayList<Key> preExistingSymptomsKeySubmitted);
	
	
	/**
	 * 
	 * @param caredPerson
	 * @param rxTimeEvent
	 * @return
	 */
	double calcOverallHealthIndex(CaredPerson caredPerson, Date rxTimeEvent);
	
	/**
	 * 
	 * @param cpHealthIndex
	 */
	void saveCaredPersonHealthIndex(CaredPersonHealthIndex cpHealthIndex);
	
	/**
	 * Get the list of CaredPersonHealthIndex within the given Date Time range
	 */
	List<CaredPersonHealthIndex> getCPHealthIndexListByDate(
	        CaredPerson caredPerson, Date startDate, Date endDate);
	
}
