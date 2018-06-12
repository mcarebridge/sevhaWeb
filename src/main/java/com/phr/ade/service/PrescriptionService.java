package com.phr.ade.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.controller.health.CaredController;
import com.phr.ade.meta.CaredPersonRxLogMeta;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Substitute;
import com.phr.ade.persistence.IPrescriptionDAO;
import com.phr.ade.persistence.PrescriptionDAO;
import com.phr.ade.util.CareUtil;
import com.phr.ade.util.CaredPersonRxComplianceDTO;
import com.phr.ade.util.CaredPersonRxComplianceSnapShot;
import com.phr.ade.util.CaredPersonRxSnapShot;

public class PrescriptionService implements IPrescriptionService
{
	
	private static Logger logger = Logger.getLogger(PrescriptionService.class
	                                     .getName());
	
	private PrescriptionDAO getPrescriptionDAO() throws ClassNotFoundException,
	        InstantiationException, IllegalAccessException
	{
		return new PrescriptionDAO();
	}
	
	/**
     * 
     */
	public List<Prescription> loadPrescriptionsByCaredPerson(Long caredPersonKey)
	        throws Exception
	{
		Key _caredPersonKey = Datastore.createKey(CaredPerson.class,
		        caredPersonKey);
		
		IPrescriptionDAO _ipDAO = getPrescriptionDAO();
		
		List<Prescription> _prescriptions = _ipDAO
		        .loadPrescriptionsByCaredPerson(_caredPersonKey);
		
		return _prescriptions;
	}
	
	/**
     * 
     */
	public List<PrescriptionLines> loadPrescriptionLines(Long prescriptionKey,
	        boolean stopRxLine) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _prescriptionKey = Datastore.createKey(Prescription.class,
		        prescriptionKey);
		// 05/04/2017 - Changed this to add the functionality of
		// "Cloning not active Rx Lines"
		List<PrescriptionLines> _prescriptionLines = _prescriptionDAO
		        .loadAllPrescriptionLines(_prescriptionKey);
		List<PrescriptionLines> _prescriptionLinesListUpdated = new ArrayList<PrescriptionLines>();
		
		for (Iterator iterator = _prescriptionLines.iterator(); iterator
		        .hasNext();)
		{
			PrescriptionLines _prescriptionLinesUpd = (PrescriptionLines) iterator
			        .next();
			
			_prescriptionLinesUpd
			        .setFrequencyForDisplay(prescriptionMap(_prescriptionLinesUpd
			                .getFrequency()));
			
			Substitute _substitute = loadCurrentSubstituteForPrescriptionLine(new Long(
			        _prescriptionLinesUpd.getKey().getId()));
			
			if (_substitute != null)
			{
				_prescriptionLinesUpd.setSubstituteFound(true);
			}
			
			_prescriptionLinesListUpdated.add(_prescriptionLinesUpd);
		}
		
		return _prescriptionLinesListUpdated;
	}
	
	/**
     * 
     */
	public Key createPrescription(Prescription model) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _key = _prescriptionDAO.addModel(model);
		return _key;
	}
	
	/**
     * 
     */
	public Key updatePrescription(Prescription model) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _key = _prescriptionDAO.addModel(model);
		return _key;
	}
	
	/**
     * 
     */
	public Prescription loadPrescriptionById(Long prescriptionKey)
	        throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _prescriptionKey = Datastore.createKey(Prescription.class,
		        prescriptionKey);
		Prescription _p = _prescriptionDAO
		        .loadPrescriptionById(_prescriptionKey);
		return _p;
	}
	
	/**
     * 
     */
	public Key createPrescriptionLines(PrescriptionLines prescriptionLines)
	        throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _key = _prescriptionDAO.addModel(prescriptionLines);
		return _key;
	}
	
	/**
     * 
     */
	public Key updatePrescriptionLines(PrescriptionLines prescriptionLines)
	        throws Exception
	{
		// @todo - start the Transaction
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _key = _prescriptionDAO.addModel(prescriptionLines);
		
		// Find if any substitute is attached. If yes, we need to remove
		// as the base prescription has changed
		
		Substitute _currentSubstitute = loadCurrentSubstituteForPrescriptionLine(new Long(
		        _key.getId()).longValue());
		
		if (_currentSubstitute != null)
		{
			_currentSubstitute.setCurrentSubstitute(new Boolean(false));
			_prescriptionDAO.addModel(_currentSubstitute);
		}
		
		return _key;
	}
	
	/**
     * 
     */
	public Key createSubstitute(Substitute substitute) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Substitute _currentSubstitute = loadCurrentSubstituteForPrescriptionLine(new Long(
		        substitute.getPrescriptionLines().getKey().getId()));
		
		if (_currentSubstitute != null)
		{
			_currentSubstitute.setCurrentSubstitute(new Boolean(false));
			_prescriptionDAO.addModel(_currentSubstitute);
		}
		substitute.setCurrentSubstitute(new Boolean(true));
		Key _key = _prescriptionDAO.addModel(substitute);
		return _key;
	}
	
	/**
     * 
     */
	@Override
	public Key updateSubstitute(Substitute substitute) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _key = _prescriptionDAO.addModel(substitute);
		return _key;
	}
	
	/**
     * 
     */
	public List<Substitute> listSubstitutesForPrescriptionLine(
	        Long prescriptionLineKey) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _prescriptionLineKey = Datastore.createKey(PrescriptionLines.class,
		        prescriptionLineKey);
		List<Substitute> _substututeList = _prescriptionDAO
		        .listSubstitutesForPrescriptionLine(_prescriptionLineKey);
		return _substututeList;
	}
	
	/**
     * 
     */
	public Substitute loadCurrentSubstituteForPrescriptionLine(
	        Long prescriptionLineKey) throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _prescriptionLineKey = Datastore.createKey(PrescriptionLines.class,
		        prescriptionLineKey);
		Substitute _substutute = _prescriptionDAO
		        .loadCurrentSubstituteForPrescriptionLine(_prescriptionLineKey);
		return _substutute;
	}
	
	/**
	 * 
	 */
	@Override
	public PrescriptionLines loadPrescriptionLine(Long prescriptionLineId)
	        throws Exception
	{
		IPrescriptionDAO _prescriptionDAO = getPrescriptionDAO();
		Key _prescriptionLineKey = Datastore.createKey(PrescriptionLines.class,
		        prescriptionLineId);
		return _prescriptionDAO.loadPrescriptionLine(_prescriptionLineKey);
	}
	
	/**
	 * 
	 * @param medicationFreq
	 * @return
	 */
	private String prescriptionMap(String medicationFreq)
	{
		
		String _frequency = null;
		
		if (medicationFreq.equalsIgnoreCase("MOD"))
		{
			_frequency = "1-0-0";
		} else if (medicationFreq.equalsIgnoreCase("AOD"))
		{
			_frequency = "0-1-0";
		} else if (medicationFreq.equalsIgnoreCase("EOD"))
		{
			_frequency = "0-0-1";
		} else if (medicationFreq.equalsIgnoreCase("MATD"))
		{
			_frequency = "1-1-0";
		} else if (medicationFreq.equalsIgnoreCase("AETD"))
		{
			_frequency = "0-1-1";
		} else if (medicationFreq.equalsIgnoreCase("METD"))
		{
			_frequency = "1-0-1";
		} else if (medicationFreq.equalsIgnoreCase("TID"))
		{
			_frequency = "1-1-1";
		} else if (medicationFreq.equalsIgnoreCase("QID"))
		{
			_frequency = "1-1-1-1";
		} else if (medicationFreq.equalsIgnoreCase("STAT"))
		{
			_frequency = "STAT";
		} else if (medicationFreq.equalsIgnoreCase("QWK"))
		{
			_frequency = "1W";
		} else if (medicationFreq.equalsIgnoreCase("BIS"))
		{
			_frequency = "2W";
		} else if (medicationFreq.equalsIgnoreCase("TIW"))
		{
			_frequency = "3W";
		} else if (medicationFreq.equalsIgnoreCase("SOS"))
		{
			_frequency = "SOS";
		} else if (medicationFreq.equalsIgnoreCase("OTH"))
		{
			_frequency = "Other";
		} else
		{
			_frequency = "ERR";
		}
		
		return _frequency;
	}
	
	/**
	 * Find Rx Compliance
	 * 
	 * @throws Exception
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public Hashtable<String, Long[]> findRxComplianceForACaredPerson(
	        Long caredPersonKey, Date rangeStart, Date rangeEnd)
	        throws Exception
	{
		logger.info("--findRxComplianceForACaredPerson  caredPersonKey (Before) --"
		        + caredPersonKey);
		
		// Key _caredPersonKey = Datastore.createKey(CaredPerson.class,
		// caredPersonKey);
		
		Key _caredPersonKey = Datastore.createKey(CaredPerson.class,
		        caredPersonKey);
		
		logger.info("--findRxComplianceForACaredPerson  _caredPersonKey --"
		        + _caredPersonKey.getId());
		
		// Array of Planned Vs Actual Rx
		Long _rxPlannedAndActual[] = new Long[2];
		
		Hashtable<String, Long[]> _rxLingCompliancePair = new Hashtable<String, Long[]>();
		
		IPrescriptionDAO pDAO = getPrescriptionDAO();
		
		// Find all the Rx in a give Date Range
		List<Prescription> rxList = pDAO.loadPrescriptionByDateRange(
		        rangeStart, rangeEnd, _caredPersonKey);
		
		logger.info("--Rx  Prescribed --" + rxList.size());
		
		for (Iterator iterator = rxList.iterator(); iterator.hasNext();)
		{
			Prescription prescription = (Prescription) iterator.next();
			
			// Get Rx lines within that Given Rx
			List<PrescriptionLines> rxLinesList = pDAO
			        .loadPrescriptionLinesByDateRange(rangeStart, rangeEnd,
			                prescription.getKey(), false);
			
			for (Iterator iterator2 = rxLinesList.iterator(); iterator2
			        .hasNext();)
			{
				PrescriptionLines prescriptionLines = (PrescriptionLines) iterator2
				        .next();
				logger.info("--Rx Line Prescribed --"
				        + prescriptionLines.getDrugName() + " "
				        + prescriptionLines.getKey().getId());
				
				/*******************************************
				 * Get RxLine Start and End Date -start
				 *******************************************/
				
				Date _rxLineStartDate = prescriptionLines.getRxstartdate();
				int _rxQtyPerDay = frequencyToQtyConverter(prescriptionLines
				        .getFrequency());
				int _duartion = prescriptionLines.getDuration();
				Calendar _cStartDate = Calendar.getInstance();
				_cStartDate.setTimeInMillis(_rxLineStartDate.getTime());
				_cStartDate.add(Calendar.DATE, _duartion);
				Date _rxLineEndDate = _cStartDate.getTime();
				
				// ******************************************************
				DateFormat _df = SimpleDateFormat.getDateInstance();
				
				logger.info("--RxLine Start Date --"
				        + _df.format(_rxLineStartDate.getTime()));
				logger.info("--RxLine End Date --"
				        + _df.format(_rxLineEndDate.getTime()));
				
				// ******************************************************
				
				/********************************************
				 * Get RxLine Start and End Date -end
				 *******************************************/
				
				// Find what the range date needed for comparision
				Date _rangeStartDateCalc = null;
				Date _rangeEndDateCalc = null;
				
				if (isValidForRxCompliance(prescriptionLines.getFrequency()))
				{
					if (_rxLineEndDate.after(rangeStart))
					{
						
						Calendar _rangeEndCal = Calendar.getInstance();
						_rangeEndCal.setTime(rangeEnd);
						
						// Calc Range End Date
						if (rangeEnd.before(_rxLineEndDate))
						{
							_rangeEndDateCalc = rangeEnd;
							
						} else
						{
							_rangeEndDateCalc = _rxLineEndDate;
						}
						
						_rangeEndCal.add(Calendar.DATE, -7);
						_rangeStartDateCalc = _rangeEndCal.getTime();
						
						// Calc Range Start Date
						if (_rangeStartDateCalc.before(_rxLineStartDate))
						{
							_rangeStartDateCalc = _rxLineStartDate;
						}
						
						// ******************************************************
						logger.info("--Rx Range Start Date --"
						        + _df.format(_rangeStartDateCalc.getTime()));
						logger.info("--Rx Range End Date --"
						        + _df.format(_rangeEndDateCalc.getTime()));
						// *******************************************************
						
						long _rxTobeConsumed = rxQtyToBeConsumedInDateRange(
						        prescriptionLines, _rangeStartDateCalc,
						        _rangeEndDateCalc);
						
						logger.info("-- Rx To be Consumed --" + _rxTobeConsumed);
						
						long _actualConsumed = getActualConsumption(
						        prescriptionLines, _rangeStartDateCalc,
						        _rangeEndDateCalc);
						
						logger.info("--Rx Actual Consumed --" + _actualConsumed);
						
						_rxPlannedAndActual = new Long[2];
						// Add to be consumed
						_rxPlannedAndActual[0] = new Long(_rxTobeConsumed);
						// Add actual consumed
						_rxPlannedAndActual[1] = new Long(_actualConsumed);
						
						_rxLingCompliancePair.put(
						        prescriptionLines.getKey().toString() + "$"
						                + prescriptionLines.getDrugName(),
						        _rxPlannedAndActual);
					}
				}
			}
		}
		
		return _rxLingCompliancePair;
		
	}
	
	/**
	 * Compliance is not valid for Rx which is not at regular interval
	 * 
	 * @param frequency
	 * @return
	 */
	private boolean isValidForRxCompliance(String frequency)
	{
		boolean _isValid = false;
		frequency = frequency.trim();
		
		switch (frequency)
		{
			case "STAT":
				_isValid = false;
				break;
			case "OTH":
				_isValid = false;
				break;
			
			default:
				// Unable to resolve
				_isValid = true;
		}
		
		return _isValid;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private long getActualConsumption(PrescriptionLines prescriptionLines,
	        Date rangeStart, Date rangeEnd)
	{
		DateFormat _df = SimpleDateFormat.getDateInstance();
		
		// ******************************************************
		logger.info("-- Method -- PrescriptionService --");
		
		logger.info("--Rx Range Start Date --"
		        + _df.format(rangeStart.getTime()));
		logger.info("--Rx Range End Date --" + _df.format(rangeEnd.getTime()));
		// *******************************************************
		long _actualConsumed = 0L;
		
		CaredPersonRxLogMeta _rxLogMeta = new CaredPersonRxLogMeta();
		ArrayList<Date> _dateList = new ArrayList<Date>();
		_dateList.add(rangeEnd);
		_dateList.add(rangeStart);
		
		// List<CaredPersonRxLog> _caredPersonRxLog = Datastore
		// .query(_rxLogMeta)
		// .filter(_rxLogMeta.prescriptionLines.equal(prescriptionLines
		// .getKey()), _rxLogMeta.responseDateTime.in(_dateList))
		// .asList();
		
		List<CaredPersonRxLog> _caredPersonRxLog = Datastore
		        .query(_rxLogMeta)
		        .filter(_rxLogMeta.prescriptionLines.equal(prescriptionLines
		                .getKey()),
		                _rxLogMeta.responseDateTime
		                        .greaterThanOrEqual(rangeStart)).asList();
		
		if (_caredPersonRxLog != null)
		{
			logger.info("--_caredPersonRxLog size --"
			        + _caredPersonRxLog.size());
			_actualConsumed = _caredPersonRxLog.size();
		} else
		{
			logger.info("--_caredPersonRxLog size --" + _caredPersonRxLog);
		}
		
		return _actualConsumed;
	}
	
	/**
	 * 
	 * @param prescriptionLines
	 */
	private long rxQtyToBeConsumedInDateRange(
	        PrescriptionLines prescriptionLines, Date startDate, Date endDate)
	{
		// find end date of rxLine
		int _qtyByDay = frequencyToQtyConverter(prescriptionLines
		        .getFrequency());
		
		logger.info("--Rx Qty Per Day --" + _qtyByDay);
		
		long duration = endDate.getTime() - startDate.getTime();
		
		long diffInDays = TimeUnit.MILLISECONDS.toDays(duration) + 1;
		
		logger.info("--Days considered --" + diffInDays);
		
		long _totalQty = diffInDays * _qtyByDay;
		
		return _totalQty;
		
	}
	
	private Date findRxLineEndDate(PrescriptionLines prescriptionLines)
	
	{
		Date _rxStartDate = prescriptionLines.getRxstartdate();
		int _totalDays = prescriptionLines.getDuration().intValue();
		
		Calendar _c = Calendar.getInstance();
		_c.setTime(_rxStartDate);
		_c.add(Calendar.DATE, _totalDays);
		
		Date _rxEndDate = _c.getTime();
		return _rxEndDate;
	}
	
	/**
	 * 
	 * @param frequency
	 * @return
	 */
	private int frequencyToQtyConverter(String frequency)
	{
		frequency = frequency.trim();
		int _qty = 0;
		
		switch (frequency)
		{
			case "MOD":
				_qty = 1;
				break;
			case "AOD":
				_qty = 1;
				break;
			case "EOD":
				_qty = 1;
				break;
			case "MATD":
				_qty = 2;
				break;
			case "AETD":
				_qty = 2;
				break;
			case "METD":
				_qty = 2;
				break;
			case "TID":
				_qty = 3;
				break;
			case "QID":
				_qty = 4;
				break;
			default:
				// Unable to resolve
				_qty = -1;
		}
		
		return _qty;
	}
	
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
	
	/**
	 * 1. For a give CaredPerson and Date range, get all the Rx and all the Rx
	 * lines. Build a time base schedule for each Rx line based on the in-take
	 * frequency. For each Rx line and find if that Rx was consumed for that
	 * schedule slot
	 * 
	 * 2. Similarly find all the Symptoms reported for the given date range and
	 * grouped by Date
	 */
	
	public List<CaredPersonRxComplianceSnapShot> findRxAndSymptomsByDate(
	        String caredPersonKey, Date rangeStart, Date rangeEnd)
	        throws Exception
	{
		
		ArrayList<CaredPersonRxComplianceSnapShot> _cpRxCompSnapShotList = new ArrayList<CaredPersonRxComplianceSnapShot>();
		
		Key _caredPersonKey = Datastore.createKey(CaredPerson.class, new Long(
		        caredPersonKey));
		// Array of Planned Vs Actual Rx
		Long _rxPlannedAndActual[] = new Long[2];
		Hashtable<String, Long[]> _rxLingCompliancePair = new Hashtable<String, Long[]>();
		
		IPrescriptionDAO pDAO = getPrescriptionDAO();
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
		
		// Find All Rx for that day (making start and end date same)
		int _dayDiff = CareUtil.dateDiffinDays(rangeStart, rangeEnd);
		int i = 0;
		
		CareTransactionService _cts = new CareTransactionService();
		
		while (_dayDiff < i)
		{
			Date _rangeStart = DateUtils.addDays(rangeStart, i);
			
			List<Prescription> rxList = pDAO.loadPrescriptionByDateRange(
			        _rangeStart, _rangeStart, _caredPersonKey);
			logger.log(Level.INFO, "rxList list size = " + rxList.size());
			
			CaredPersonRxComplianceSnapShot _rxCompSnapShot = new CaredPersonRxComplianceSnapShot();
			_rxCompSnapShot.setSnapShotDate(_rangeStart);
			ArrayList<CaredPersonRxSnapShot> _cpRxSnapShotListOuter = new ArrayList<CaredPersonRxSnapShot>();
			Hashtable<PrescriptionLines, List<CaredPersonRxSnapShot>> cpRxComplianceKV = new Hashtable<PrescriptionLines, List<CaredPersonRxSnapShot>>();
			
			for (Iterator iterator = rxList.iterator(); iterator.hasNext();)
			{
				Prescription prescription = (Prescription) iterator.next();
				// Get Rx lines within that Given Rx
				List<PrescriptionLines> rxLinesList = pDAO
				        .loadPrescriptionLinesByDateRange(_rangeStart,
				                _rangeStart, prescription.getKey(), false);
				
				// Find all the Rx Lines for the Rx
				for (Iterator iterator2 = rxLinesList.iterator(); iterator2
				        .hasNext();)
				{
					PrescriptionLines prescriptionLine = (PrescriptionLines) iterator2
					        .next();
					
					logger.log(Level.INFO,
					        " Building schedule for Prescription Line  = "
					                + prescriptionLine.getDrugName());
					
					List<CaredPersonRxSnapShot> _cpRxSnapShotList = buildRxLineGridByDateRange(
					        _caredPersonKey, prescriptionLine, _rangeStart,
					        _rangeStart);
					
					cpRxComplianceKV.put(prescriptionLine, _cpRxSnapShotList);
					
					_cpRxSnapShotListOuter.addAll(_cpRxSnapShotList);
				}
			}
			
			_rxCompSnapShot.setRxSnapshot(_cpRxSnapShotListOuter);
			// Attach Key Vaue of Prescription and CaredPersonRxSnapShot
			_rxCompSnapShot.setCpRxComplianceKV(cpRxComplianceKV);
			
			// Attach SymptomLog too at this point
			List<CaredPersonSymptomLog> _caredPersonSymptomList = _cts
			        .getSymptomsReportedForDateRange(_caredPersonKey,
			                _rangeStart, _rangeStart);
			
			_rxCompSnapShot.setCaredPersonSymptomLog(_caredPersonSymptomList);
			
			_cpRxCompSnapShotList.add(_rxCompSnapShot);
			
			i--;
			
		}
		
		// Temp code to check data
		/**
		 * for (Iterator iterator = _cpRxCompSnapShotList.iterator(); iterator
		 * .hasNext();) { CaredPersonRxComplianceSnapShot
		 * _caredPersonRxComplianceSnapShot = (CaredPersonRxComplianceSnapShot)
		 * iterator .next();
		 * 
		 * // Get Rx snapshot List<CaredPersonRxSnapShot> _rxSnapshot =
		 * _caredPersonRxComplianceSnapShot .getRxSnapshot();
		 * 
		 * // Get Symptom Log List<CaredPersonSymptomLog>
		 * _caredPersonSymptomLogList = _caredPersonRxComplianceSnapShot
		 * .getCaredPersonSymptomLog();
		 * 
		 * logger.log( Level.INFO, " Show Rx Schedule for -> " +
		 * _sdf.format(_caredPersonRxComplianceSnapShot .getSnapShotDate()));
		 * 
		 * // List the Rx for (Iterator iterator3 = _rxSnapshot.iterator();
		 * iterator3 .hasNext();) { CaredPersonRxSnapShot _caredPersonRxSnapShot
		 * = (CaredPersonRxSnapShot) iterator3 .next();
		 * 
		 * logger.log( Level.INFO, " Showing Rx Schedule : -- " +
		 * _caredPersonRxSnapShot.getPrescriptionLine() .getDrugName() + " --- "
		 * + _sdf.format(_caredPersonRxSnapShot .getRxScheduledTimestamp()));
		 * 
		 * } // List the Symptoms if (_caredPersonSymptomLogList != null) { for
		 * (Iterator iterator2 = _caredPersonSymptomLogList.iterator();
		 * iterator2 .hasNext();) { CaredPersonSymptomLog _caredPersonSymptomLog
		 * = (CaredPersonSymptomLog) iterator2 .next();
		 * 
		 * logger.log(Level.INFO, " Showing Symtoms Log : " +
		 * _caredPersonSymptomLog .getPreExistingDiseaseCommonSymptom()
		 * .getModel().getSymptom());
		 * 
		 * } }
		 * 
		 * }
		 **/
		
		return _cpRxCompSnapShotList;
	}
	
	/**
	 * 
	 * @param prescriptionLines
	 * @param rangeStart
	 * @param rangeEnd
	 */
	private List<CaredPersonRxSnapShot> buildRxLineGridByDateRange(
	        Key caredPersonKey, PrescriptionLines prescriptionLine,
	        Date rangeStart, Date rangeEnd) throws Exception
	{
		ArrayList<CaredPersonRxSnapShot> _cpRxSSListDaily = new ArrayList<CaredPersonRxSnapShot>();
		
		// Build hourly schedule
		List<CaredPersonRxSnapShot> _cpRxSSListHourly = getNeededRxSnapShot(
		        caredPersonKey, prescriptionLine, rangeStart, rangeEnd);
		_cpRxSSListDaily.addAll(_cpRxSSListHourly);
		
		return _cpRxSSListDaily;
		
	}
	
	/**
	 * 
	 * @param prescriptionLines
	 * @param rxSchdlDate
	 * @param rangeEnd
	 * @return
	 */
	private List<CaredPersonRxSnapShot> getNeededRxSnapShot(Key caredPersonKey,
	        PrescriptionLines prescriptionLines, Date rangeStart, Date rangeEnd)
	        throws Exception
	{
		ArrayList<CaredPersonRxSnapShot> _cpRxSSList = new ArrayList<CaredPersonRxSnapShot>();
		
		Calendar _rxEndDate = Calendar.getInstance();
		_rxEndDate.setTime(prescriptionLines.getRxstartdate());
		_rxEndDate.add(Calendar.DATE, prescriptionLines.getDuration()
		        .intValue());
		
		int _dayDiff = CareUtil.dateDiffinDays(rangeStart, rangeEnd);
		
		// If the Rx is expiring before the RangeEnd then find the dayDiff by
		// RxEndDate
		
		/**
		 * int _dayDiffByRxEndDate = CareUtil.dateDiffinDays(rangeStart,
		 * _rxEndDate.getTime());
		 * 
		 * if (_dayDiffByRxEndDate < _dayDiff) { _dayDiff = _dayDiffByRxEndDate;
		 * }
		 **/
		logger.log(Level.INFO, "--- Building Rx Schedule for = " + _dayDiff
		        + " days.");
		
		int i = 0;
		
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
		
		while (_dayDiff > i)
		{
			Date _d = DateUtils.addDays(rangeStart, i);
			Calendar _rangeStartDate = Calendar.getInstance();
			_rangeStartDate.setTime(_d);
			
			logger.log(
			        Level.INFO,
			        "Building  Rx Data for - "
			                + _sdf.format(_rangeStartDate.getTime())
			                + " the value of i = " + i);
			
			String _rxHrsString = MobileDataExchangeHelper.getFreqInHrs(
			        prescriptionLines.getFrequency(),
			        prescriptionLines.getRxstartdate());
			
			logger.log(Level.INFO, " Build schedule for - " + _rxHrsString);
			
			// For QIWK, BIS, TIW - if it is not the right day, it will return
			// -1.
			
			if (!_rxHrsString.equalsIgnoreCase("-1"))
			{
				if (_rxHrsString.contains("-"))
				{
					StringTokenizer _st = new StringTokenizer(_rxHrsString, "-");
					while (_st.hasMoreElements())
					{
						String _rxHour = (String) _st.nextElement();
						int _rxHourInt = new Integer(_rxHour).intValue();
						
						CaredPersonRxSnapShot _cpRxSS = new CaredPersonRxSnapShot();
						_cpRxSS.setPrescriptionLine(prescriptionLines);
						
						Date _rxSchdlDate = getScheduleDate(_rangeStartDate,
						        _rxHourInt);
						
						_cpRxSS.setRxScheduledTimestamp(_rxSchdlDate);
						
						// get a snapshot of Rx Compliance
						_cpRxSS = fillRxActualStatus(caredPersonKey, _cpRxSS,
						        prescriptionLines, _rxSchdlDate);
						
						_cpRxSSList.add(_cpRxSS);
					}
				} else
				{
					String _rxHour = _rxHrsString.trim();
					int _rxHourInt = new Integer(_rxHour).intValue();
					CaredPersonRxSnapShot _cpRxSS = new CaredPersonRxSnapShot();
					_cpRxSS.setPrescriptionLine(prescriptionLines);
					
					Date _rxSchdlDate = getScheduleDate(_rangeStartDate,
					        _rxHourInt);
					
					// get a snapshot of Rx Compliance
					_cpRxSS = fillRxActualStatus(caredPersonKey, _cpRxSS,
					        prescriptionLines, _rxSchdlDate);
					
					_cpRxSS.setRxScheduledTimestamp(_rxSchdlDate);
					_cpRxSSList.add(_cpRxSS);
					
				}
			}
			
			i++;
		}
		
		return _cpRxSSList;
	}
	
	/**
	 * Fill the Actual Status of Rx
	 * 
	 * @param cpRxSnapShot
	 * @param caredPersonKey
	 * @return
	 */
	private CaredPersonRxSnapShot fillRxActualStatus(Key caredPersonKey,
	        CaredPersonRxSnapShot cpRxSnapShot,
	        PrescriptionLines prescriptionLines, Date scheduleDate)
	        throws Exception
	{
		// debug code to find the actual date passed with timezone
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a z");
		logger.log(
		        Level.INFO,
		        "======= Display scheduleDate being passed ========"
		                + _sdf.format(scheduleDate.getTime()));
		
		CareTransactionService _cts = new CareTransactionService();
		
		CaredPersonRxLog _caredPersonRxLog = _cts
		        .getRxLogByRxResponseAndRxLine(caredPersonKey,
		                prescriptionLines, scheduleDate);
		
		// If the CaredPerson has not response then it will NOT have an entry
		if (_caredPersonRxLog != null)
		{
			cpRxSnapShot.setStatusTimestamp(_caredPersonRxLog
			        .getResponseDateTime());
			cpRxSnapShot.setRxStatus(_caredPersonRxLog.getCaredPersonResponse()
			        .getModel().getAction());
		} else
		{
			cpRxSnapShot.setRxStatus("");
		}
		
		return cpRxSnapShot;
	}
	
	/**
	 * Convert the schedule into proper Date
	 * 
	 * @param schdlDate
	 * @param scheduleHrs
	 * @return
	 */
	private Date getScheduleDate(Calendar schdlDate, int scheduleHrs)
	{
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
		
		schdlDate.set(Calendar.HOUR_OF_DAY, scheduleHrs);
		schdlDate.set(Calendar.MINUTE, 0);
		schdlDate.set(Calendar.SECOND, 0);
		
		Date _rxSchdlDate = new Date();
		_rxSchdlDate.setTime(schdlDate.getTimeInMillis());
		
		// logger.log(Level.INFO,
		// "Schedule Rx Date - " + _sdf.format(_rxSchdlDate.getTime()));
		
		return _rxSchdlDate;
		
	}
	
	@Override
	public void markPrescriptionLineReordered(Long prescriptionKey)
	        throws Exception
	{
		PrescriptionLines _rxLine = loadPrescriptionLine(prescriptionKey);
		_rxLine.setReordered(true);
		updatePrescriptionLines(_rxLine);
		
	}
	
}
