package com.phr.ade.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.controller.health.AlertController;
import com.phr.ade.meta.CaredPersonRxLogMeta;
import com.phr.ade.meta.CaredPersonRxResponseMeta;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.model.CaredPersonRxResponse;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PrescriptionLines;

public class CaredPersonTransactionDAO extends AbstractEntityDAO implements
        ICaredPersonTransactionDAO
{
	
	private static Logger logger = Logger.getLogger(CaredPersonTransactionDAO.class
	                                     .getName());
	
	@Override
	public void addCaredPersonRxResponse(
	        CaredPersonRxResponse caredPersonRxResponse,
	        List<CaredPersonRxLog> caredPersonRxLog,
	        List<CaredPersonSymptomLog> caredPersonSymptomLog)
	{
		Key _responseKey = addModel(caredPersonRxResponse);
		caredPersonRxResponse.setKey(_responseKey);
		
		for (Iterator iterator = caredPersonRxLog.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonRxLog caredPersonRxLog1 = (CaredPersonRxLog) iterator
			        .next();
			caredPersonRxLog1.getCaredPersonResponse().setModel(
			        caredPersonRxResponse);
			addModel(caredPersonRxLog1);
			
		}
		
		for (Iterator iterator = caredPersonSymptomLog.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonSymptomLog caredPersonSymptomLog1 = (CaredPersonSymptomLog) iterator
			        .next();
			
			caredPersonSymptomLog1.getCaredPersonResponse().setModel(
			        caredPersonRxResponse);
			addModel(caredPersonSymptomLog1);
			
		}
		
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
	
	@Override
	public void getDailySymptomsReported()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getWeeklySymptomsReported()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get all the CaredPersonRxResponse for a caredPerson for an Hour The
	 * reportedDate of mobile date with min = 0 Pick only those records which
	 * have been received in that hour
	 */
	public List<CaredPersonRxResponse> getCaredPersonRxResponseByHr(
	        Key caredPerson, Date reportedDate)
	{
		Calendar _c = Calendar.getInstance();
		_c.setTime(reportedDate);
		int _year = _c.get(Calendar.YEAR);
		int _month = _c.get(Calendar.MONTH);
		_month++;
		int _day = _c.get(Calendar.DATE);
		int _hour = _c.get(Calendar.HOUR_OF_DAY);
		
		CaredPersonRxResponseMeta _caredPersonRxMeta = new CaredPersonRxResponseMeta();
		// List<CaredPersonRxResponse> _rxResponseList = Datastore
		// .query(_caredPersonRxMeta)
		// .filter(_caredPersonRxMeta.caredPerson.equal(caredPerson),
		// _caredPersonRxMeta.action.equal("TAKEN"),
		// _caredPersonRxMeta.responseDateTime
		// .greaterThanOrEqual(reportedDate)).asList();
		
		List<CaredPersonRxResponse> _rxResponseList = Datastore
		        .query(_caredPersonRxMeta)
		        .filter(_caredPersonRxMeta.caredPerson.equal(caredPerson),
		                _caredPersonRxMeta.action.equal("TAKEN"),
		                _caredPersonRxMeta.responseYear.equal(_year),
		                _caredPersonRxMeta.responseMonth.equal(_month),
		                _caredPersonRxMeta.responseDay.equal(_day),
		                _caredPersonRxMeta.responseHour.equal(_hour)).asList();
		
		logger.log(
		        Level.INFO,
		        " -- CaredPersonTransactionDAO.getCaredPersonRxResponseByHr -- _rxResponseList size --"
		                + _rxResponseList.size());
		
		return _rxResponseList;
	}
	
	/**
	 * 
	 * @param caredPerson
	 * @param reportedDate
	 * @param responseHour
	 * @return
	 */
	public List<CaredPersonRxResponse> getCaredPersonRxResponseTakenInAHour(
	        Key caredPerson, Date reportedDate, int responseHour)
	{
		
		SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Calendar _c = Calendar.getInstance();
		_c.setTime(reportedDate);
		int _year = _c.get(Calendar.YEAR);
		int _month = _c.get(Calendar.MONTH);
		_month++;
		int _day = _c.get(Calendar.DATE);
		int _hour = responseHour;
		
		logger.log(
		        Level.INFO,
		        " -- CaredPersonTransactionDAO.getCaredPersonRxResponseTakenInPastHour -- Parameter --"
		                + caredPerson.getId()
		                + " -- "
		                + _dateFormat.format(reportedDate)
		                + " -- "
		                + +responseHour);
		
		CaredPersonRxResponseMeta _caredPersonRxMeta = new CaredPersonRxResponseMeta();
		// List<CaredPersonRxResponse> _rxResponseList = Datastore
		// .query(_caredPersonRxMeta)
		// .filter(_caredPersonRxMeta.caredPerson.equal(caredPerson),
		// _caredPersonRxMeta.action.equal("TAKEN"),
		// _caredPersonRxMeta.responseDateTime
		// .greaterThanOrEqual(reportedDate),
		// _caredPersonRxMeta.responseHour.equal(new Integer(
		// responseHour))).asList();
		
		List<CaredPersonRxResponse> _rxResponseList = Datastore
		        .query(_caredPersonRxMeta)
		        .filter(_caredPersonRxMeta.caredPerson.equal(caredPerson),
		                _caredPersonRxMeta.action.equal("TAKEN"),
		                _caredPersonRxMeta.responseYear.equal(_year),
		                _caredPersonRxMeta.responseMonth.equal(_month),
		                _caredPersonRxMeta.responseDay.equal(_day),
		                _caredPersonRxMeta.responseHour.equal(_hour)).asList();
		
		logger.log(
		        Level.INFO,
		        " -- CaredPersonTransactionDAO.getCaredPersonRxResponseTakenInPastHour -- _rxResponseList size --"
		                + _rxResponseList.size());
		
		return _rxResponseList;
	}
	
	@Override
	public List<CaredPersonRxResponse> getCaredPersonRxLinesByHr(
	        Key CaredPersonRxResponseKey, Date reportedDate)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CaredPersonRxLog> getCaredPersonRxLogByResponse(
	        List<CaredPersonRxResponse> carePersonRespList)
	{
		CaredPersonRxLogMeta _caredPersonLogMeta = new CaredPersonRxLogMeta();
		ArrayList<CaredPersonRxLog> caredPersonRxList = new ArrayList<CaredPersonRxLog>();
		
		for (Iterator iterator = carePersonRespList.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonRxResponse caredPersonRxResponse = (CaredPersonRxResponse) iterator
			        .next();
			List<CaredPersonRxLog> _caredPersonRxList1 = Datastore
			        .query(_caredPersonLogMeta)
			        .filter(_caredPersonLogMeta.caredPersonResponse
			                .equal(caredPersonRxResponse.getKey())).asList();
			caredPersonRxList.addAll(_caredPersonRxList1);
		}
		
		// TODO Auto-generated method stub
		return caredPersonRxList;
	}
	
	@Override
	public List<PrescriptionLines> getCaredPersonRxLinesTakenInAHour(
	        Key caredPerson, Date reportedDate, int responseHour)
	{
		ArrayList<PrescriptionLines> _rxTakenList = new ArrayList<PrescriptionLines>();
		
		List<CaredPersonRxResponse> rxResponseList = getCaredPersonRxResponseTakenInAHour(
		        caredPerson, reportedDate, responseHour);
		
		CaredPersonRxLogMeta _caredPersonRxLogMeta = new CaredPersonRxLogMeta();
		
		for (Iterator iterator = rxResponseList.iterator(); iterator.hasNext();)
		{
			CaredPersonRxResponse caredPersonRxResponse = (CaredPersonRxResponse) iterator
			        .next();
			List<CaredPersonRxLog> _rxLogList = Datastore
			        .query(_caredPersonRxLogMeta)
			        .filter(_caredPersonRxLogMeta.caredPersonResponse
			                .equal(caredPersonRxResponse.getKey())).asList();
			
			for (Iterator iterator2 = _rxLogList.iterator(); iterator2
			        .hasNext();)
			{
				CaredPersonRxLog caredPersonRxLog = (CaredPersonRxLog) iterator2
				        .next();
				
				_rxTakenList.add(caredPersonRxLog.getPrescriptionLines()
				        .getModel());
				
			}
		}
		
		return _rxTakenList;
	}
}
