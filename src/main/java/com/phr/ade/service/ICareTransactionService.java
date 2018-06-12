package com.phr.ade.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.model.CaredPersonRxResponse;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.values.ICareBridgeConstants;

public interface ICareTransactionService extends ICareBridgeConstants
{
	
	/**
	 * Add Rx log, Rx taken , skipped , symptom reported
	 */
	void addCaredPersonRxLog(String transactionData, CaredPerson caredPerson)
	        throws Exception;
	
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
	 * Get Rx taken By Hr. The Hr is sent by Mobile device
	 */
	List<CaredPersonRxLog> getDailyRxTakenByHr(Key caredPersonKey,
	        Date reportedDate) throws Exception;
	
	/**
	 * Get CaredPersonRxResponse TAKEN , SKIPED, OR MISSED (this will be null)
	 * with in 1 hr of the schedule time
	 * 
	 * @param caredPersonKey
	 * @param scheduledDateTime
	 * @return
	 * @throws Exception
	 */
	List<CaredPersonRxResponse> getRxResponseByScheduledHR(Key caredPersonKey,
	        Date scheduledDateTime) throws Exception;
	
	/**
	 * 
	 * @param caredPersonRxResponseKey
	 * @param rxLine
	 * @return
	 * @throws Exception
	 */
	CaredPersonRxLog getRxLogByRxResponseAndRxLine(
	        Key caredPersonRxResponseKey, Key rxLine) throws Exception;
	
	
	
	/**
	 * 
	 * @param caredPersonRxResponseKeyList
	 * @return
	 */
	List<CaredPersonSymptomLog> getSymptomLogByRxResponse(
	        List<Key> caredPersonRxResponseKeyList) throws Exception;
	
	/**
	 * 
	 * @param caredPersonRxResponseKey
	 * @return
	 * @throws Exception
	 */
	List<CaredPersonSymptomLog> getSymptomLogByRxResponse(
	        Key caredPersonRxResponseKey) throws Exception;
	
	/**
	 * Get Daily symptoms reported
	 */
	List<CaredPersonSymptomLog> getSymptomsReportedForDateRange(
	        Key CaredPerson, Date rangeStart, Date rangeEnd);
	
	/**
	 * Get Weekly symptoms reported
	 */
	void getWeeklySymptomsReported();
	
	/**
	 * 
	 * @param caredPersonKey
	 * @param responseDate
	 * @param responseHour
	 * @return
	 */
	Collection<PrescriptionLines> getHourlyRxSkipped(Key caredPersonKey,
	        Date responseDate, int responseHour) throws Exception;
	
	String getHourlyRxSkippedAsString(Key caredPersonKey, Date responseDate,
	        int responseHour) throws Exception;
	
}
