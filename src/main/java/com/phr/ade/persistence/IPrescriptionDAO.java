package com.phr.ade.persistence;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Substitute;
import com.phr.ade.values.ICareBridgeConstants;

public interface IPrescriptionDAO extends ICareBridgeConstants, IEntityDAO
{
	/********************************************
	 * Prescription Methods
	 *******************************************/
	
	/**
	 * 
	 * @param caredPerson
	 * @return
	 */
	List<Prescription> loadPrescriptionsByCaredPerson(Key caredPerson)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 * @throws Exception
	 */
	Prescription loadPrescriptionById(Key prescriptionKey) throws Exception;
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<Prescription> loadPrescriptionByDateRange(Date startDate,
	        Date endDate, Key caredPersonKey) throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	List<PrescriptionLines> loadPrescriptionLines(Key prescriptionKey,
	        boolean stopRxLine) throws Exception;
	
	
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 * @throws Exception
	 */
	List<PrescriptionLines> loadAllPrescriptionLines(Key prescriptionKey) throws Exception;
	
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<PrescriptionLines> loadPrescriptionLinesByDateRange(Date startDate,
	        Date endDate, Key prescriptionKey, boolean stopRxLine)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	List<Substitute> listSubstitutesForPrescriptionLine(Key prescriptionLineKey)
	        throws Exception;
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	Substitute loadCurrentSubstituteForPrescriptionLine(Key prescriptionLineKey);
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	PrescriptionLines loadPrescriptionLine(Key prescriptionLineKey);
	
	/**
	 * Exprire the Rx Lines whose Rx end date has reached
	 * 
	 * @param prescriptionKey
	 */
	void expireRxLines(Key prescriptionKey) throws Exception;
	
}
