package com.phr.ade.service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Substitute;
import com.phr.ade.util.CaredPersonRxComplianceSnapShot;
import com.phr.ade.values.ICareBridgeConstants;

public interface IPrescriptionService extends ICareBridgeConstants
{
	
	/********************************************
	 * Prescription Methods
	 *******************************************/
	
	/**
	 * 
	 * @param caredPerson
	 * @return
	 */
	List<Prescription> loadPrescriptionsByCaredPerson(Long caredPersonKey)
	        throws Exception;
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Key createPrescription(Prescription model) throws Exception;
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Key updatePrescription(Prescription model) throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 * @throws Exception
	 */
	Prescription loadPrescriptionById(Long prescriptionKey) throws Exception;
	
	/********************************************
	 * PrescriptionLine Methods
	 *******************************************/
	
	/**
	 * 
	 * @param prescriptionLines
	 * @return
	 */
	Key createPrescriptionLines(PrescriptionLines prescriptionLines)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionLines
	 * @return
	 */
	Key updatePrescriptionLines(PrescriptionLines prescriptionLines)
	        throws Exception;
	
	/**
	 * 
	 * @param substitute
	 * @return
	 */
	Key createSubstitute(Substitute substitute) throws Exception;
	
	/**
	 * 
	 * @param substitute
	 * @return
	 */
	Key updateSubstitute(Substitute substitute) throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	List<PrescriptionLines> loadPrescriptionLines(Long prescriptionKey,
	        boolean stopRxLine) throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 * @throws Exception
	 */
	PrescriptionLines loadPrescriptionLine(Long prescriptionLineId)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	List<Substitute> listSubstitutesForPrescriptionLine(Long prescriptionLineKey)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	Substitute loadCurrentSubstituteForPrescriptionLine(Long prescriptionLineKey)
	        throws Exception;
	
	/**
	 * Find Rx compliance
	 * 
	 * @param caredPersonKey
	 * @param rangeStart
	 * @param rangeEnd
	 */
//	Hashtable<String, Long[]> findRxComplianceForACaredPerson(
//	        Key caredPersonKey, Date rangeStart, Date rangeEnd)
//	        throws Exception;
	
	
	
	 Hashtable<String, Long[]> findRxComplianceForACaredPerson(
		        Long caredPersonKey, Date rangeStart, Date rangeEnd)
		        throws Exception;
	
	
	
	/**
	 * Find the status of each prescription and Symptoms reported between a
	 * given Date Range
	 * 
	 * @param caredPersonKey
	 * @param rangeStart
	 * @param rangeEnd
	 * @return
	 * @throws Exception
	 */
	
	List<CaredPersonRxComplianceSnapShot> findRxAndSymptomsByDate(
	        String caredPersonKey, Date rangeStart, Date rangeEnd)
	        throws Exception;
	
	
	
	/**
	 * Update the prescriptionline as reordered. This will avoid reordering the same prescriptionline.
	 * @param prescriptionKey
	 */
	void markPrescriptionLineReordered(Long prescriptionKey) throws Exception;
	
}
