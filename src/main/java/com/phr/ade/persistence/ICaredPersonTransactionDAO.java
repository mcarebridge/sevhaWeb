package com.phr.ade.persistence;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.model.CaredPersonRxResponse;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.values.ICareBridgeConstants;

public interface ICaredPersonTransactionDAO extends ICareBridgeConstants,
        IEntityDAO
{
	/**
	 * Add Rx log, Rx taken , skipped , symptom reported
	 */
	void addCaredPersonRxResponse(CaredPersonRxResponse caredPersonRxResponse,
	        List<CaredPersonRxLog> caredPersonRxLog,
	        List<CaredPersonSymptomLog> caredPersonSymptomLog);
	
	/**
	 * Get Daily Rx Log (Includes Taken and Skipped)
	 */
	void getDailyRxLog();
	
	/**
	 * Get Daily Skipped Rx Log
	 */
	void getDailyRxSkipped();
	
	
	/**
	 * Get Daily Taken Rx Log
	 */
	void getDailyRxTaken();
	
	/**
	 * Get Daily symptoms reported
	 */
	void getDailySymptomsReported();
	
	/**
	 * Get Weekly symptoms reported
	 */
	void getWeeklySymptomsReported();
	
	/**
	 * 
	 * @param caredPerson
	 * @param hour
	 * @return
	 */
	List<CaredPersonRxResponse> getCaredPersonRxResponseByHr(Key caredPerson,
			Date reportedDate);
	
	
	/**
	 * 
	 * @param CaredPersonRxResponseKey
	 * @param reportedDate
	 * @return
	 */
	List<CaredPersonRxResponse> getCaredPersonRxLinesByHr(
	        Key CaredPersonRxResponseKey, Date reportedDate);
	
	/**
	 * 
	 * @param carePersonRespList
	 * @return
	 */
	List<CaredPersonRxLog> getCaredPersonRxLogByResponse(
	        List<CaredPersonRxResponse> carePersonRespList);
	
	/**
	 * 
	 * @param caredPerson
	 * @param reportedDate
	 * @param responseHour
	 * @return
	 */
	List<CaredPersonRxResponse> getCaredPersonRxResponseTakenInAHour(
	        Key caredPerson, Date reportedDate, int responseHour);
	
	
	List<PrescriptionLines> getCaredPersonRxLinesTakenInAHour(
	        Key caredPerson, Date reportedDate, int responseHour);
	
}
