package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.persistence.CareDAO;
import com.phr.ade.persistence.ICareDAO;
import com.phr.ade.persistence.IPrescriptionDAO;
import com.phr.ade.persistence.PrescriptionDAO;

public class MobileDataExchangeService implements IMobileDataExchangeService
{
	
	/**
	 * Component Loader
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private CareDAO getCareDAO() throws ClassNotFoundException,
	        InstantiationException, IllegalAccessException
	{
		return new CareDAO();
	}
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private PrescriptionDAO getPrescriptionDAO() throws ClassNotFoundException,
	        InstantiationException, IllegalAccessException
	{
		return new PrescriptionDAO();
	}
	
	/**
	 * Authenticate a Cared Person based on the registered IMEI#
	 */
	
	public void authenticateCaredPerson() throws Exception
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The cared person will send the current state of the health and rx
	 * compliance. This will be sent as csv file. Receive the CSV record and
	 * store the Rx Compliance and HealthStatus
	 * 
	 */
	
	public void receiveCaredPersonStatus() throws Exception
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Send the Cared Person current Plan as XML. The method
	 * receciedCaredPersonStatus and this method will be a synchronous call.
	 * Logic :
	 * 
	 * 1. ReCalc the Rx State and send the updated Rx Snapshot to CaredPerson 2.
	 * Assess the CaredPerson health status a. Alert CareGiver b. Alert
	 * Physician c. Send a message to CaredPerson - something how they are doing
	 * on their progress.
	 * 
	 */
	
	public StringBuffer sendCaredPersonCurrentRxPlan(Long caredPersonKey)
	        throws Exception
	{
		
		ICareDAO _careDAO = getCareDAO();
		Key _key = Datastore.createKey(CaredPerson.class, caredPersonKey);
		CaredPerson _caredPerson = _careDAO.loadCaredPerson(_key);
		
		IPrescriptionDAO _iRxDAO = getPrescriptionDAO();
		List<Prescription> _prescriptionList = _iRxDAO
		        .loadPrescriptionsByCaredPerson(_caredPerson.getKey());
		ArrayList<Prescription> _updPrescriptionList = new ArrayList<Prescription>();
		
		for (Iterator<Prescription> iterator = _prescriptionList.iterator(); iterator
		        .hasNext();)
		{
			Prescription _prescriptionIn = (Prescription) iterator.next();
			
			//Expire the Rx Lines before use
			
			_iRxDAO.expireRxLines(_prescriptionIn.getKey());
			
			List<PrescriptionLines> _rxLines = _iRxDAO.loadPrescriptionLines(
			        _prescriptionIn.getKey(), false);
			_prescriptionIn.setPrescriptionLines(_rxLines);
			_updPrescriptionList.add(_prescriptionIn);
			iterator.remove();
		}
		
		_caredPerson.setPrescription(_updPrescriptionList);
		
		/**
		 * Temp fix to show only default symptoms List<PreExistingCondition>
		 * _preExCondList = _careDAO
		 * .loadAllPreExistingConditonsForCaredPerson(_caredPerson .getKey());
		 **/
		
		// @todo Temp fix start : Hard coded to show only default predcondition
		CommonPreExistingDiseases _defaultCommonDisease = _careDAO
		        .loadDefaultCommonPreExistingDisease();
		PreExistingCondition _preExCond = new PreExistingCondition();
		// All the default condition to have a hard key of 999999
		// It is not store in the database
		Key _preconditionkey = Datastore.createKey(PreExistingCondition.class,
		        new Long(999999));
		_preExCond.setKey(_preconditionkey);
		_preExCond.getPreExisitingDiseases().setModel(_defaultCommonDisease);
		_preExCond.getCaredPerson().setModel(_caredPerson);
		
		ArrayList<PreExistingCondition> _preExCondList = new ArrayList<PreExistingCondition>();
		_preExCondList.add(_preExCond);
		// @todo Temp Fix End
		
		_caredPerson.setPreExistingCondition(_preExCondList);
		
		EmergencyResponse _emResponse = _careDAO
		        .loadEmergemcyResponseForCaredPerson(_caredPerson.getKey());
		_caredPerson.setEmergencyResponse(_emResponse);
		
		StringBuffer _xml = MobileDataExchangeHelper
		        .fillCaredPersonData(_caredPerson);
		
		return _xml;
		
	}
}
