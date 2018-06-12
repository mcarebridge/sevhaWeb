package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonHealthIndex;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.persistence.CareDAO;

public class CaredPersonHealthCareService implements
        ICaredPersonHealthCareService
{
	
	public CaredPersonHealthCareService()
	{
		
	}
	
	/**
	 * Logic : For the given CaredPerson find all the scheduled Rx and it it has
	 * been take or not. This works on the Rx weighted logic and rolls up for 7
	 * Days
	 * 
	 */
	public double calcRxComplianceIndexForTimeEvent(CaredPerson caredPerson,
	        Date rxTimeEvent)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Works on 7 days rolling weighted avg of VitalSign alerts
	 */
	public double calcVitalSignComplianceIndexForTimeEvent(
	        CaredPerson caredPerson, Date rxTimeEvent)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Works of 7 days of rolling weighted avg of Symptoms reported
	 */
	public double calcSymptomSeverityIndexForTimeEvent(CaredPerson caredPerson,
	        Date rxTimeEvent)
	{
		/**
		 * Logic:
		 * 
		 * When receive the Rx update. Read all the Symptoms reported Calculated
		 * the symptom severity Index
		 * 
		 * Based on the pre-existing condition, if none use default, load all
		 * the symptoms for that Pre-existing condiition Index = sum (1 X
		 * weight) / sum of all the weights If no symptom found it will be 0 x
		 * weight
		 */
		return 0;
	}
	
	/**
	 * 
	 */
	public double calcSymptomSeverityIndexForTimeEvent(CaredPerson caredPerson,
	        ArrayList<Key> preExistingSymptomsKeySubmitted)
	{
		List<PreExistingCondition> _preExistingCondList = caredPerson
		        .getPreExistingCondition();
		
		// Check if the _preExistingCond is default
		// If multiple preExistingPreCond - use the more important pre-existing
		// condition
		
		CommonPreExistingDiseases _commmonPreExisitngDisease = null;
		
		for (Iterator iterator = _preExistingCondList.iterator(); iterator
		        .hasNext();)
		{
			PreExistingCondition preExistingCondition = (PreExistingCondition) iterator
			        .next();
			
			if (preExistingCondition.getPreExisitingDiseases().getModel()
			        .getDiseaseCode().equalsIgnoreCase("DFLT"))
			{
				_commmonPreExisitngDisease = preExistingCondition
				        .getPreExisitingDiseases().getModel();
				break;
			}
		}
		
		CareDAO _careDAO = new CareDAO();
		
		List<PreExistingDiseaseCommonSymptom> _preExistingCommonSymptoms = _careDAO
		        .getCommonSymptomsForPreExistingDisease(
		                _commmonPreExisitngDisease.getKey(), true);
		
		// Load all the Symptoms for the pre-exisitng conditions
		// Store as <key><Value> in Hashtable
		
		// Sum up the weightage of all the Symptoms
		
		// Read the Hashtable and match for the submittedSymptoms
		
		return 0;
	}
	
	public void saveCaredPersonHealthIndex(CaredPersonHealthIndex cpHealthIndex)
	{
		// TODO Auto-generated method stub
		
	}
	
	public List<CaredPersonHealthIndex> getCPHealthIndexListByDate(
	        CaredPerson caredPerson, Date startDate, Date endDate)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public double calcOverallHealthIndex(CaredPerson caredPerson,
	        Date rxTimeEvent)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}
