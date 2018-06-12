package com.phr.ade.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.phr.ade.meta.CaredPersonRxLogMeta;
import com.phr.ade.meta.CaredPersonRxResponseMeta;
import com.phr.ade.meta.CaredPersonSymptomLogMeta;
import com.phr.ade.meta.PrescriptionMeta;
import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.model.CaredPersonRxResponse;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.persistence.CaredPersonTransactionDAO;
import com.phr.ade.persistence.ICaredPersonTransactionDAO;
import com.phr.ade.persistence.IPrescriptionDAO;
import com.phr.ade.persistence.PrescriptionDAO;
import com.phr.ade.util.CareDateUtil;
import com.phr.ade.util.CareUtil;

public class CareTransactionService implements ICareTransactionService
{
	
	private static Logger logger           = Logger.getLogger(CareTransactionService.class
	                                               .getName());
	
	private String        _rxAction        = null;
	private List<Key>     rxKeys           = null;
	private List<Key>     symptomKeys      = null;
	private boolean       rxTaken          = false;
	private Date          currDateOfMobile = null;
	
	private int           responseYear     = -1;
	private int           responseMonth    = -1;
	private int           responseDay      = -1;
	private int           responseHour     = -1;
	private int           responseMin      = -1;
	private String        responseTimeZone = "0000";
	
	private ICaredPersonTransactionDAO getCareResponseDAO()
	        throws ClassNotFoundException, InstantiationException,
	        IllegalAccessException
	{
		return new CaredPersonTransactionDAO();
	}
	
	private IPrescriptionDAO getPrescriptionDAO()
	        throws ClassNotFoundException, InstantiationException,
	        IllegalAccessException
	{
		return new PrescriptionDAO();
	}
	
	@Override
	public void addCaredPersonRxLog(String transactionData,
	        CaredPerson caredPerson) throws Exception
	
	{
		Transaction gtx = null;
		extractRxAndSymptoms(transactionData);
		// Do this only if any Rx is take
		
		if (rxTaken)
		{
			ICaredPersonTransactionDAO iCarePersonTransDAO = getCareResponseDAO();
			// 1. Extract Action, RxKeys and SymptomKey
			
			// Fill and create CaredPersonRxResponse
			
			CaredPersonRxResponse _cpr = new CaredPersonRxResponse();
			_cpr.setAction(_rxAction);
			_cpr.getCaredPerson().setModel(caredPerson);
			_cpr.setResponseDateTime(currDateOfMobile);
			
			_cpr.setResponseYear(responseYear);
			_cpr.setResponseMonth(responseMonth);
			_cpr.setResponseDay(responseDay);
			_cpr.setResponseHour(responseHour);
			_cpr.setResponseMinute(responseMin);
			_cpr.setMobileTimeZone(responseTimeZone);
			
			// gtx = Datastore.beginTransaction();
			
			Key _cprKey = iCarePersonTransDAO.addModel(_cpr);
			_cpr.setKey(_cprKey);
			
			// Fill CaredPersonRxLog
			PrescriptionService _ps = new PrescriptionService();
			CareService _cs = new CareService();
			
			for (Iterator<Key> iterator = rxKeys.iterator(); iterator.hasNext();)
			{
				Key _key = (Key) iterator.next();
				PrescriptionLines _pl = _ps.loadPrescriptionLine(new Long(_key
				        .getId()));
				
				// 04/20/17 - Filter out the exipred Rx
				if (!_pl.isRxExpired())
				{
					CaredPersonRxLog _cprl = new CaredPersonRxLog();
					_cprl.getCaredPersonResponse().setModel(_cpr);
					_cprl.setResponseDateTime(currDateOfMobile);
					_cprl.getPrescriptionLines().setModel(_pl);
					iCarePersonTransDAO.addModel(_cprl);
				}
			}
			
			ArrayList<Key> _cpSymtomLogKeyList = new ArrayList<Key>();
			
			// Fill CaredPersonSymptomLog
			for (Iterator<Key> iterator = symptomKeys.iterator(); iterator
			        .hasNext();)
			{
				Key _key = (Key) iterator.next();
				PreExistingDiseaseCommonSymptom _symptom = _cs
				        .loadSymptomByKey(new Long(_key.getId()));
				CaredPersonSymptomLog _cpsl = new CaredPersonSymptomLog();
				_cpsl.getCaredPersonResponse().setModel(_cpr);
				_cpsl.getPreExistingDiseaseCommonSymptom().setModel(_symptom);
				Key _cpSymtomLogKey = iCarePersonTransDAO.addModel(_cpsl);
				_cpSymtomLogKeyList.add(_cpSymtomLogKey);
			}
			
			// Calculate the SymptomIndex
			
			// Calculate the Vital Sign Index
		}
		
		// gtx.commit();
	}
	
	@Override
	public void getDailyRxLog()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getDailyRxSkipped()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getDailyRxTaken()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * List CaredPersonSymptom reported by Date Range
	 */
	public List<CaredPersonSymptomLog> getSymptomsReportedForDateRange(
	        Key caredPersonKey, Date rangeStart, Date rangeEnd)
	
	{
		Calendar _c = Calendar.getInstance();
		_c.setTimeInMillis(rangeStart.getTime());
		_c.set(Calendar.HOUR, 0);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _rangeStart = _c.getTime();
		Date _rangeEnd = DateUtils.addDays(_rangeStart, 1);
		
		ArrayList<Date> _dateRange = new ArrayList<Date>();
		_dateRange.add(_rangeStart);
		_dateRange.add(_rangeEnd);
		
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
		logger.log(
		        Level.INFO,
		        "Building  Schedule for Range- "
		                + _sdf.format(_rangeStart.getTime()) + " --- "
		                + _sdf.format(_rangeEnd.getTime()));
		
		ArrayList<CaredPersonSymptomLog> caredPersonSymptomLogList = new ArrayList<CaredPersonSymptomLog>();
		
		CaredPersonRxResponseMeta _caredPersonRxResponseMeta = new CaredPersonRxResponseMeta();
		
		// List<CaredPersonRxResponse> _caredPersonRxRspList = Datastore
		// .query(_caredPersonRxResponseMeta)
		// .filter(_caredPersonRxResponseMeta.caredPerson
		// .equal(caredPersonKey),
		// _caredPersonRxResponseMeta.responseDateTime
		// .greaterThanOrEqual(_rangeStart),
		// _caredPersonRxResponseMeta.responseDateTime
		// .lessThanOrEqual(_rangeEnd)).asList();
		
		Calendar _startDate = Calendar.getInstance();
		_startDate.setTime(rangeStart);
		int _searchYear = _startDate.get(Calendar.YEAR);
		int _searchMonth = _startDate.get(Calendar.MONTH);
		
		// Add a month for real Month
		_searchMonth++;
		
		int _searchDay = _startDate.get(Calendar.DATE);
		
		List<CaredPersonRxResponse> _caredPersonRxRspList = Datastore
		        .query(_caredPersonRxResponseMeta)
		        .filter(_caredPersonRxResponseMeta.caredPerson
		                .equal(caredPersonKey),
		                _caredPersonRxResponseMeta.responseYear
		                        .equal(_searchYear),
		                _caredPersonRxResponseMeta.responseMonth
		                        .equal(_searchMonth),
		                _caredPersonRxResponseMeta.responseDay
		                        .equal(_searchDay)).asList();
		
		// -------------------------------
		logger.log(Level.INFO,
		        " Building schedule list if   =  CaredPersonRxResponse fetch size"
		                + _caredPersonRxRspList.size());
		
		for (Iterator iterator = _caredPersonRxRspList.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonRxResponse _caredPersonRxResponse = (CaredPersonRxResponse) iterator
			        .next();
			
			CaredPersonSymptomLogMeta _caredPersonSymptonLogMeta = new CaredPersonSymptomLogMeta();
			List<CaredPersonSymptomLog> _caredPersonSymptomLogList = Datastore
			        .query(_caredPersonSymptonLogMeta)
			        .filter(_caredPersonSymptonLogMeta.caredPersonResponse
			                .equal(_caredPersonRxResponse.getKey())).asList();
			
			caredPersonSymptomLogList.addAll(_caredPersonSymptomLogList);
			
		}
		
		return caredPersonSymptomLogList;
	}
	
	@Override
	public void getWeeklySymptomsReported()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void extractRxAndSymptoms(String rxData) throws Exception
	{
		StringTokenizer _st = new StringTokenizer(rxData, "()");
		String _action = (String) _st.nextElement();
		
		_rxAction = _action.substring(_action.indexOf("=") + 1,
		        _action.length());
		_rxAction = _rxAction.trim().toUpperCase();
		
		String _rx = (String) _st.nextElement();
		parseRxLog(_rx.substring(_rx.indexOf("=") + 1, _rx.length()));
		
		String _symptom = (String) _st.nextElement();
		parseSymptomLog(_symptom.substring(_symptom.indexOf("=") + 1,
		        _symptom.length()));
		
		// Received Date Format "yyyy-MM-dd hh:mm"
		// 05/08/2017 the revised format is "yyyy-MM-dd hh:mm Z"
		String _currDateStrOfMobile = (String) _st.nextElement();
		String _datePart = _currDateStrOfMobile.substring(
		        _currDateStrOfMobile.indexOf("=") + 1,
		        _currDateStrOfMobile.length());
		
		if (_currDateStrOfMobile != null)
		{
			logger.info("--- Current Date Passed extractRxAndSymptoms ---"
			        + _currDateStrOfMobile);
			
			SimpleDateFormat _dateFormat = new SimpleDateFormat(
			        "yyyy-MM-dd HH:mm Z");
			
			Date _currDateOfMobile = _dateFormat.parse(_datePart);
			TimeZone _timeZoneOfMobile = _dateFormat.getTimeZone();
			//
			// Convert all the dates to GMT
			// @ Right now hard coded to EDT
			// Date _currDateOfMobileUTC =
			// CareUtil.convertTimeZoneToUTC(_currDateOfMobile, "EDT");
			
			Date _currDateOfMobileUTC = CareUtil.convertTimeZoneToUTC(
			        _currDateOfMobile, _timeZoneOfMobile.getID());
			currDateOfMobile = _currDateOfMobileUTC;
			
			// For checking the converted Dates
			SimpleDateFormat _dateFormat4ckhn = new SimpleDateFormat(
			        "yyyy-MM-dd HH:mm a z");
			logger.info("Check Rx date before inserting in to db-- --"
			        + _dateFormat4ckhn.format(currDateOfMobile));
			
			//
			CareDateUtil.parseDateString(_datePart);
			
			responseYear = CareDateUtil.year;
			responseMonth = CareDateUtil.month;
			responseDay = CareDateUtil.day;
			responseHour = CareDateUtil.hour;
			responseMin = CareDateUtil.minute;
			responseTimeZone = CareDateUtil.timeZoneId;
			
		}
	}
	
	/**
	 * 
	 */
	private void parseRxLog(String rxLog)
	{
		
		StringTokenizer _st = new StringTokenizer(rxLog, ",");
		rxKeys = new ArrayList<Key>();
		
		while (_st.hasMoreElements())
		{
			String _rxkey = (String) _st.nextElement();
			if (!_rxkey.equalsIgnoreCase("-"))
			{
				Key _key = Datastore.createKey(PrescriptionLines.class,
				        new Long(_rxkey));
				rxKeys.add(_key);
			}
		}
		
		if (rxKeys.size() > 0)
		{
			rxTaken = true;
		}
	}
	
	/**
	 * 
	 */
	private void parseSymptomLog(String symptomLog)
	{
		
		StringTokenizer _st = new StringTokenizer(symptomLog, ",");
		symptomKeys = new ArrayList<Key>();
		
		while (_st.hasMoreElements())
		{
			String _symptomkey = (String) _st.nextElement();
			
			if (!_symptomkey.equalsIgnoreCase("-"))
			{
				Key _key = Datastore.createKey(
				        PreExistingDiseaseCommonSymptom.class, new Long(
				                _symptomkey));
				symptomKeys.add(_key);
			}
		}
	}
	
	/**
	 * Get Rx taken By Hr. The Hr is sent by Mobile device. Logic: For a
	 * caredpserson and give Hr
	 * 
	 * 
	 */
	public List<CaredPersonRxLog> getDailyRxTakenByHr(Key caredPersonKey,
	        Date reportedDate) throws Exception
	{
		Calendar _c = Calendar.getInstance();
		_c.setTime(reportedDate);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _updReportDate = _c.getTime();
		
		ICaredPersonTransactionDAO iCarePersonTransDAO = getCareResponseDAO();
		List<CaredPersonRxResponse> _rxRespList = iCarePersonTransDAO
		        .getCaredPersonRxResponseByHr(caredPersonKey, _updReportDate);
		
		// Based on the responses received. Get the RxLog for each response
		List<CaredPersonRxLog> caredPersonRxLogs = iCarePersonTransDAO
		        .getCaredPersonRxLogByResponse(_rxRespList);
		
		return caredPersonRxLogs;
	}
	
	/**
	 * 
	 * @param caredPersonKey
	 * @param reportedDate
	 * @param responseHour
	 * @return
	 * @throws Exception
	 */
	public List<CaredPersonRxLog> getDailyRxTakenInPastHr(Key caredPersonKey,
	        Date reportedDate, int responseHour) throws Exception
	{
		Calendar _c = Calendar.getInstance();
		_c.setTime(reportedDate);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _updReportDate = _c.getTime();
		
		ICaredPersonTransactionDAO iCarePersonTransDAO = getCareResponseDAO();
		List<CaredPersonRxResponse> _rxRespList = iCarePersonTransDAO
		        .getCaredPersonRxResponseTakenInAHour(caredPersonKey,
		                reportedDate, responseHour);
		
		// Based on the responses received. Get the RxLog for each response
		List<CaredPersonRxLog> caredPersonRxLogs = iCarePersonTransDAO
		        .getCaredPersonRxLogByResponse(_rxRespList);
		
		return caredPersonRxLogs;
	}
	
	/**
	 * Find all the Rx skipped by a CaredPerson in Last 1 hr
	 */
	public Collection<PrescriptionLines> getHourlyRxSkipped(Key caredPersonKey,
	        Date responseDate, int responseHour) throws Exception
	{
		// TODO Auto-generated method stub
		// 1. Load all the Rx
		// Check for the RxSkipped in last Hour
		--responseHour;
		Calendar _c = Calendar.getInstance();
		_c.setTimeInMillis(responseDate.getTime());
		_c.add(Calendar.HOUR_OF_DAY, -1);
		
		responseDate = _c.getTime();
		
		IPrescriptionDAO _iRxDAO = getPrescriptionDAO();
		
		PrescriptionService _ps = new PrescriptionService();
		List<Prescription> _prescriptionList = _ps
		        .loadPrescriptionsByCaredPerson(new Long(caredPersonKey.getId()));
		
		// This list adds only those RxLines which are supposed to be taken in a
		// given Hour
		ArrayList<PrescriptionLines> iteratorTaken = new ArrayList<PrescriptionLines>();
		Hashtable<Key, PrescriptionLines> _rxAll = new Hashtable<Key, PrescriptionLines>();
		
		for (Iterator<Prescription> iterator = _prescriptionList.iterator(); iterator
		        .hasNext();)
		{
			Prescription _prescriptionIn = (Prescription) iterator.next();
			List<PrescriptionLines> _rxLines = _iRxDAO.loadPrescriptionLines(
			        _prescriptionIn.getKey(), false);
			
			for (Iterator iterator2 = _rxLines.iterator(); iterator2.hasNext();)
			{
				PrescriptionLines _prescriptionLines = (PrescriptionLines) iterator2
				        .next();
				String _freqInHrs = MobileDataExchangeHelper
				        .getFreqInHrs(_prescriptionLines.getFrequency(), _prescriptionLines.getRxstartdate());
				// CharSequence _cs = new Integer(responseHour).toString();
				
				logger.info("--- Find Rx for Hr  ---" + _freqInHrs
				        + " Hrs --- " + responseHour + " -- "
				        + _prescriptionLines.getDrugName());
				
				if (isItRxTime(_freqInHrs, new Integer(responseHour).toString()))
				{
					// _rxLinesList.add(_prescriptionLines);
					logger.info("-- Rx match found for --"
					        + _prescriptionLines.getDrugName());
					_rxAll.put(_prescriptionLines.getKey(), _prescriptionLines);
				}
			}
			
			iterator.remove();
		}
		
		// 3. Check against the RxResponseLog
		// Get all the RxResponse Log taken in last hr
		ICaredPersonTransactionDAO iCPTransDAO = getCareResponseDAO();
		List<PrescriptionLines> _rxTakenList = iCPTransDAO
		        .getCaredPersonRxLinesTakenInAHour(caredPersonKey,
		                responseDate, responseHour);
		
		for (Iterator iterator = _rxTakenList.iterator(); iterator.hasNext();)
		{
			PrescriptionLines prescriptionLines = (PrescriptionLines) iterator
			        .next();
			logger.info("-- Rx Take at  --" + responseHour + " Hrs "
			        + prescriptionLines.getDrugName());
			if (_rxAll.containsKey(prescriptionLines.getKey()))
			{
				_rxAll.remove(prescriptionLines.getKey());
			}
		}
		
		// 4. Find from the overall Rx List what has been taken
		
		return _rxAll.values();
	}
	
	/**
	 * 
	 * @param freqInHrs
	 * @param currentHours
	 * @return
	 */
	private boolean isItRxTime(String freqInHrs, String currentHours)
	{
		StringTokenizer _stk = new StringTokenizer(freqInHrs, "-");
		ArrayList<String> _aList = new ArrayList<String>();
		
		while (_stk.hasMoreElements())
		{
			String _str = (String) _stk.nextElement();
			_aList.add(_str);
		}
		
		return _aList.contains(currentHours);
	}
	
	/**
	 * Calc Rx Skipped and return as a string id:rxname
	 * 
	 * @param caredPersonKey
	 * @param responseDate
	 * @param responseHour
	 * @return
	 * @throws Exception
	 */
	public String getHourlyRxSkippedAsString(Key caredPersonKey,
	        Date responseDate, int responseHour) throws Exception
	{
		Collection<PrescriptionLines> _rxLines = getHourlyRxSkipped(
		        caredPersonKey, responseDate, responseHour);
		Hashtable<String, String> _cgRxPair = new Hashtable<String, String>();
		
		StringBuffer _cgKeyValue = new StringBuffer();
		
		if (_rxLines.size() == 0)
		{
			_cgKeyValue.append("-");
		}
		
		for (Iterator iterator = _rxLines.iterator(); iterator.hasNext();)
		{
			PrescriptionLines prescriptionLines = (PrescriptionLines) iterator
			        .next();
			String _cgNameAndCell = getCareGiverDetailsForRx(prescriptionLines);
			
			StringBuffer _sb = new StringBuffer();
			// _sb.append(_cgNameAndCell);
			_sb.append("[");
			_sb.append(prescriptionLines.getKey().getId());
			_sb.append(":");
			_sb.append(prescriptionLines.getDrugName());
			_sb.append("]");
			
			if (_cgRxPair.containsKey(_cgNameAndCell))
			{
				String _rxString = _cgRxPair.get(_cgNameAndCell);
				logger.info("--- Reading from Hashtable ---" + _rxString);
				_sb.append(_rxString);
				_cgRxPair.put(_cgNameAndCell, _sb.toString());
			} else
			{
				logger.info("--- Adding to  Hashtable ---" + _cgNameAndCell);
				_cgRxPair.put(_cgNameAndCell, _sb.toString());
			}
		}
		
		Set _cgKey = _cgRxPair.keySet();
		
		for (Iterator iterator = _cgKey.iterator(); iterator.hasNext();)
		{
			String _key = (String) iterator.next();
			String _value = _cgRxPair.get(_key);
			_cgKeyValue.append("{");
			_cgKeyValue.append(_key);
			_cgKeyValue.append(_value);
			_cgKeyValue.append("}");
		}
		
		return _cgKeyValue.toString();
	}
	
	/**
	 * 
	 * @param prescriptionLines
	 * @return
	 */
	private String getCareGiverDetailsForRx(PrescriptionLines prescriptionLines)
	{
		String _cgNameAndCell = "";
		Prescription _rx = prescriptionLines.getPrescription().getModel();
		Caregiver _cg = _rx.getCareGiver().getModel();
		Address _address = _cg.getAddress().getModel();
		
		_cgNameAndCell = "[" + _cg.getFirstName() + ":"
		        + _address.getCellphone() + "]";
		
		return _cgNameAndCell;
	}
	
	/**
	 * Get the RxResponse based on the ScheduledHR of a Rx Line
	 */
	public List<CaredPersonRxResponse> getRxResponseByScheduledHR(
	        Key caredPersonKey, Date scheduledDateTime) throws Exception
	{
		// Convert from TimeZone to GMT. Hardcoded to EDT
		// 05/09/2017 - Not needed for TimeZone conversion
		// Date scheduledDateTimeUTC = CareUtil.convertTimeZoneToUTC(
		// scheduledDateTime, "EDT");
		
		Calendar _startDate = Calendar.getInstance();
		_startDate.setTime(scheduledDateTime);
		int _searchYear = _startDate.get(Calendar.YEAR);
		int _searchMonth = _startDate.get(Calendar.MONTH);
		
		// Add a month for real Month
		_searchMonth++;
		
		int _searchDay = _startDate.get(Calendar.DATE);
		int _searchHrOfDay_Start = _startDate.get(Calendar.HOUR_OF_DAY);
		
		//
		// We need to find the response within the 1 hr of thr scheduled Hr
		Date scheduledDateTimeAdd1hr = DateUtils.addHours(scheduledDateTime, 1);
		
		Calendar _startDate_1 = Calendar.getInstance();
		_startDate.setTime(scheduledDateTimeAdd1hr);
		int _searchHrOfDay_Start_1 = _startDate_1.get(Calendar.HOUR_OF_DAY);
		
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a z");
		
		logger.log(
		        Level.INFO,
		        " -- scheduledDateTime = -- "
		                + _sdf.format(scheduledDateTime.getTime())
		                + " --- _searchDate  --"
		                + _sdf.format(scheduledDateTimeAdd1hr.getTime()));
		
		logger.log(Level.INFO, ">-- searchYear  --" + _searchYear
		        + ">-- searchMonth  --" + _searchMonth + ">-- _searchDay  --"
		        + _searchDay + " >-- searchHour  --" + _searchHrOfDay_Start);
		
		CaredPersonRxResponseMeta _caredPersonRxResponseMeta = new CaredPersonRxResponseMeta();
		
		// 05/09/2017 : Change this logic to use to search based on split up
		// dates
		
		// List<CaredPersonRxResponse> _caredPersonRxResponseList = Datastore
		// .query(_caredPersonRxResponseMeta)
		// .filter(_caredPersonRxResponseMeta.caredPerson
		// .equal(caredPersonKey),
		// _caredPersonRxResponseMeta.responseDateTime
		// .greaterThanOrEqual(scheduledDateTime),
		// _caredPersonRxResponseMeta.responseDateTime
		// .lessThanOrEqual(_searchDate)).asList();
		
		/**
		 * 05/10/2017 : Added new Logic to find of the Rx was taken within 1 hr
		 * of the scheduled Time
		 */
		List<CaredPersonRxResponse> _caredPersonRxResponseList = Datastore
		        .query(_caredPersonRxResponseMeta)
		        .filter(_caredPersonRxResponseMeta.caredPerson
		                .equal(caredPersonKey),
		                _caredPersonRxResponseMeta.responseYear
		                        .equal(_searchYear),
		                _caredPersonRxResponseMeta.responseMonth
		                        .equal(_searchMonth),
		                _caredPersonRxResponseMeta.responseDay
		                        .equal(_searchDay),
		                _caredPersonRxResponseMeta.responseHour
		                        .equal(_searchHrOfDay_Start)).asList();
		
		logger.log(Level.INFO, " =======> Count of Rx Response found = "
		        + _caredPersonRxResponseList.size());
		
		return _caredPersonRxResponseList;
	}
	
	/**
	 * Get the CaredPersonRxLog from CaredPersonResponse
	 */
	public CaredPersonRxLog getRxLogByRxResponseAndRxLine(
	        Key caredPersonRxResponseKey, Key rxLine) throws Exception
	{
		
		CaredPersonRxLogMeta _caredPersonRxLogMeta = new CaredPersonRxLogMeta();
		CaredPersonRxLog _caredPersonRxLog = Datastore
		        .query(_caredPersonRxLogMeta)
		        .filter(_caredPersonRxLogMeta.caredPersonResponse
		                .equal(caredPersonRxResponseKey),
		                _caredPersonRxLogMeta.prescriptionLines.equal(rxLine))
		        .asSingle();
		
		return _caredPersonRxLog;
	}
	
	/**
	 * Use this method if we do not know Rxline is linked to which RxResponse
	 * 
	 * @param _caredPersonRxResponseList
	 * @param rxLine
	 * @return
	 * @throws Exception
	 */
	private CaredPersonRxLog getRxLogByRxResponseAndRxLine(
	        List<CaredPersonRxResponse> cpRxResponseList, Key rxLine)
	        throws Exception
	{
		CaredPersonRxLog _caredPersonRxLog = null;
		
		for (Iterator iterator = cpRxResponseList.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonRxResponse caredPersonRxResponse = (CaredPersonRxResponse) iterator
			        .next();
			
			_caredPersonRxLog = getRxLogByRxResponseAndRxLine(
			        caredPersonRxResponse.getKey(), rxLine);
			
			if (_caredPersonRxLog != null)
			{
				break;
			}
		}
		
		return _caredPersonRxLog;
	}
	
	/**
	 * 
	 * @param caredPersonKey
	 * @param prescriptionLines
	 * @param scheduleDate
	 * @return
	 */
	public CaredPersonRxLog getRxLogByRxResponseAndRxLine(Key caredPersonKey,
	        PrescriptionLines prescriptionLines, Date scheduleDate)
	        throws Exception
	{
		List<CaredPersonRxResponse> _caredPersonRxResponseList = getRxResponseByScheduledHR(
		        caredPersonKey, scheduleDate);
		
		CaredPersonRxLog _caredPersonRxLog = getRxLogByRxResponseAndRxLine(
		        _caredPersonRxResponseList, prescriptionLines.getKey());
		
		return _caredPersonRxLog;
	}
	
	/**
	 * Get all the Symptoms for the given CaredPersonRxResponse
	 */
	
	public List<CaredPersonSymptomLog> getSymptomLogByRxResponse(
	        Key caredPersonRxResponseKey) throws Exception
	{
		
		CaredPersonSymptomLogMeta _caredPersonSymptomLogMeta = new CaredPersonSymptomLogMeta();
		
		List<CaredPersonSymptomLog> _caredPersonSymptomLogList = Datastore
		        .query(_caredPersonSymptomLogMeta)
		        .filter(_caredPersonSymptomLogMeta.caredPersonResponse
		                .equal(caredPersonRxResponseKey)).asList();
		
		return _caredPersonSymptomLogList;
		
	}
	
	/**
	 * 
	 * @param caredPersonRxResponseKeyList
	 * @return
	 * @throws Exception
	 */
	public List<CaredPersonSymptomLog> getSymptomLogByRxResponse(
	        List<Key> caredPersonRxResponseKeyList) throws Exception
	{
		ArrayList<CaredPersonSymptomLog> caredPersonSymptomLogList = new ArrayList<CaredPersonSymptomLog>();
		
		for (Iterator iterator = caredPersonRxResponseKeyList.iterator(); iterator
		        .hasNext();)
		{
			Key key = (Key) iterator.next();
			List<CaredPersonSymptomLog> _caredPersonSymptomLogList = getSymptomLogByRxResponse(key);
			
			caredPersonSymptomLogList.addAll(_caredPersonSymptomLogList);
		}
		
		return caredPersonSymptomLogList;
	}
	
}
