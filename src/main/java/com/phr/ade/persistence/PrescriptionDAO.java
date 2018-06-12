package com.phr.ade.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.meta.PrescriptionLinesMeta;
import com.phr.ade.meta.PrescriptionMeta;
import com.phr.ade.meta.SubstituteMeta;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Substitute;
import com.phr.ade.service.PrescriptionService;

public class PrescriptionDAO extends AbstractEntityDAO implements
        IPrescriptionDAO
{
	
	private static Logger logger = Logger.getLogger(PrescriptionDAO.class
	                                     .getName());
	
	/**
     * 
     */
	public List<Prescription> loadPrescriptionsByCaredPerson(Key caredPersonKey)
	        throws Exception
	{
		PrescriptionMeta _presMeta = new PrescriptionMeta();
		List<Prescription> _prescriptions = Datastore.query(_presMeta)
		        .filter(_presMeta.caredPerson.equal(caredPersonKey)).asList();
		
		return _prescriptions;
	}
	
	/**
	 * Load not expired and not stoped Rx Only
	 */
	public List<PrescriptionLines> loadPrescriptionLines(Key prescriptionKey,
	        boolean stopRxLine) throws Exception
	{
		
		PrescriptionLinesMeta _presLinesMeta = new PrescriptionLinesMeta();
		List<PrescriptionLines> _prescriptionLines = Datastore
		        .query(_presLinesMeta)
		        .filter(_presLinesMeta.prescription.equal(prescriptionKey),
		                _presLinesMeta.stopRxLine.equal(stopRxLine),
		                _presLinesMeta.rxExpired.equal(false)).asList();
		
		return _prescriptionLines;
	}
	
	/**
	 * Load all the Rx Line
	 * 
	 * @param prescriptionKey
	 * @return
	 * @throws Exception
	 */
	public List<PrescriptionLines> loadAllPrescriptionLines(Key prescriptionKey)
	        throws Exception
	{
		
		PrescriptionLinesMeta _presLinesMeta = new PrescriptionLinesMeta();
		List<PrescriptionLines> _prescriptionLines = Datastore
		        .query(_presLinesMeta)
		        .filter(_presLinesMeta.prescription.equal(prescriptionKey))
		        .asList();
		
		return _prescriptionLines;
	}
	
	/**
     * 
     */
	public List<Substitute> listSubstitutesForPrescriptionLine(
	        Key prescriptionLineKey) throws Exception
	{
		
		SubstituteMeta _substituteMeta = new SubstituteMeta();
		List<Substitute> _substitutes = Datastore
		        .query(_substituteMeta)
		        .filter(_substituteMeta.prescriptionLines
		                .equal(prescriptionLineKey)).asList();
		
		return _substitutes;
	}
	
	/**
     * 
     */
	public Prescription loadPrescriptionById(Key prescriptionKey)
	        throws Exception
	{
		PrescriptionMeta _presMeta = new PrescriptionMeta();
		Prescription _prescription = Datastore.query(_presMeta)
		        .filter(_presMeta.key.equal(prescriptionKey)).asSingle();
		
		return _prescription;
	}
	
	/**
     * 
     */
	public Substitute loadCurrentSubstituteForPrescriptionLine(
	        Key prescriptionLineKey)
	{
		SubstituteMeta _substituteMeta = new SubstituteMeta();
		Substitute _substitute = Datastore
		        .query(_substituteMeta)
		        .filter(_substituteMeta.prescriptionLines
		                .equal(prescriptionLineKey),
		                _substituteMeta.currentSubstitute.equal(new Boolean(
		                        true))).asSingle();
		
		return _substitute;
	}
	
	/**
     * 
     */
	public PrescriptionLines loadPrescriptionLine(Key prescriptionLineKey)
	{
		PrescriptionLinesMeta _presLineMeta = new PrescriptionLinesMeta();
		PrescriptionLines _prescriptionLine = Datastore.query(_presLineMeta)
		        .filter(_presLineMeta.key.equal(prescriptionLineKey))
		        .asSingle();
		
		return _prescriptionLine;
	}
	
	/**
	 * Find all the Rx with in a given Date Range
	 */
	public List<Prescription> loadPrescriptionByDateRange(Date startDate,
	        Date endDate, Key caredPersonKey) throws Exception
	{
		ArrayList<Date> _rxDateRange = new ArrayList<Date>();
		_rxDateRange.add(startDate);
		_rxDateRange.add(endDate);
		
		PrescriptionMeta _presMeta = new PrescriptionMeta();
		List<Prescription> _prescriptions = Datastore
		        .query(_presMeta)
		        .filter(_presMeta.caredPerson.equal(caredPersonKey),
		                _presMeta.rxStartDate.lessThanOrEqual(endDate))
		        .asList();
		
		// List<Prescription> _prescriptions = Datastore.query(_presMeta)
		// .filter(_presMeta.caredPerson.equal(caredPersonKey)).asList();
		
		logger.info("--loadPrescriptionByDateRange  caredPersonKey --"
		        + caredPersonKey.getId());
		
		return _prescriptions;
	}
	
	/**
	 * Find all the RxLines for a Rx within a given date range
	 */
	public List<PrescriptionLines> loadPrescriptionLinesByDateRange(
	        Date startDate, Date endDate, Key prescriptionKey,
	        boolean stopRxLine) throws Exception
	{
		
		Calendar _c = Calendar.getInstance();
		_c.setTimeInMillis(endDate.getTime());
		_c.set(Calendar.HOUR, 0);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _startDate = _c.getTime();
		
		DateFormat _df = SimpleDateFormat.getDateInstance();
		logger.info("--RxLine loadPrescriptionLinesByDateRange Start Date --"
		        + _df.format(_startDate.getTime()));
		
		PrescriptionLinesMeta _presLineMeta = new PrescriptionLinesMeta();
		List<PrescriptionLines> _prescriptionsLinesList = Datastore
		        .query(_presLineMeta)
		        .filter(_presLineMeta.prescription.equal(prescriptionKey),
		                _presLineMeta.rxstartdate.lessThanOrEqual(endDate))
		        .asList();
		
		logger.info("RxLines found for Compliance :"
		        + _prescriptionsLinesList.size());
		
		// check for Rx expiry Date
		
		ArrayList<PrescriptionLines> _refinedList = new ArrayList<PrescriptionLines>();
		
		int i = 0;
		
		for (Iterator iterator = _prescriptionsLinesList.iterator(); iterator
		        .hasNext();)
		{
			PrescriptionLines prescriptionLines = (PrescriptionLines) iterator
			        .next();
			
			Date _rxStartDate = prescriptionLines.getRxstartdate();
			Integer _duration = prescriptionLines.getDuration();
			Date _rxEndDate = DateUtils.addDays(_rxStartDate, _duration);
			
			SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
			
			long _rxStartDateMillis = _rxStartDate.getTime();
			long _rxEndDateMillis = _rxEndDate.getTime();
			
			logger.log(
			        Level.INFO,
			        prescriptionLines.getDrugName() + " --- Rx End Date "
			                + _sdf.format(_rxEndDate.getTime())
			                + " --- Current Start Date --"
			                + _sdf.format(_startDate.getTime())
			                + " -decision to remove - "
			                + (_rxEndDateMillis <= _rxStartDateMillis));
			
			if (_rxEndDate.after(_startDate))
			{
				_refinedList.add(prescriptionLines);
			}
			
			// if(_rxEndDateMillis <= _rxStartDateMillis)
			// {
			// _refinedList.add(prescriptionLines);
			// }
			
			i++;
		}
		
		logger.log(Level.INFO,
		        "-- Size of the refined list --" + _refinedList.size());
		
		return _refinedList;
	}
	
	@Override
	/**
	 * 
	 */
	public void expireRxLines(Key prescriptionKey) throws Exception
	{
		List<PrescriptionLines> _rxLinesList = loadPrescriptionLines(
		        prescriptionKey, false);
		
		for (Iterator iterator = _rxLinesList.iterator(); iterator.hasNext();)
		{
			PrescriptionLines _prescriptionLines = (PrescriptionLines) iterator
			        .next();
			// Potentoal Bug : Doesn't take in to consideration Users's
			// TimeZone. Uses system TimeZone
			// @todo : Fix TimeZone issue
			Calendar _cCurr = Calendar.getInstance();
			_cCurr.set(Calendar.HOUR, 0);
			_cCurr.set(Calendar.MINUTE, 0);
			_cCurr.set(Calendar.SECOND, 0);
			
			Calendar _cRxEnd = Calendar.getInstance();
			_cRxEnd.setTime(_prescriptionLines.getRxstartdate());
			
			_cRxEnd.add(Calendar.DATE, _prescriptionLines.getDuration()
			        .intValue());
			
			// If RxLine EndDate is before Current Date
			if (_cCurr.after(_cRxEnd))
			{
				// Expire RxLine
				_prescriptionLines.setRxExpired(true);
				this.addModel(_prescriptionLines);
			}
			
		}
		
	}
}
