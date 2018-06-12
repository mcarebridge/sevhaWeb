package com.phr.ade.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.phr.ade.controller.health.CaredPersonMobileSecurityController;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;

public class CareUtil
{
	
	private static Logger logger = Logger.getLogger(CareUtil.class.getName());
	
	/**
	 * Difference in two dates in days
	 * 
	 * @param rangeStart
	 * @param rangeEnd
	 * @return
	 */
	public static int dateDiffinDays(Date rangeStart, Date rangeEnd)
	{
		// Get msec from each, and subtract.
		long _diff = rangeEnd.getTime() - rangeStart.getTime();
		
		int _dayDiff = (int) _diff / (1000 * 60 * 60 * 24);
		// The dayDiff is in -ve;
		
		_dayDiff++;
		
		return _dayDiff;
		
	}
	
	/**
	 * This method uses JODATIme to convert UTC to given timezone,
	 * 
	 * @param utcDate
	 * @param timeZone
	 * @return
	 */
	public static Date convertUTCtoTimeZone(Date utcDate, String timeZone)
	{
		DateTimeZone zoneNewTZ = DateTimeZone.forID(timeZone);
		
		DateTime dateTimeNewTZ = new DateTime(utcDate.getTime(), zoneNewTZ);
		
		DateTimeFormatter formatterNewTZ = DateTimeFormat.forStyle("FF")
		        .withZone(zoneNewTZ).withLocale(Locale.ENGLISH);
		
		String output = formatterNewTZ.print(dateTimeNewTZ);
		// This will print converted time with right timezone
		System.out.println("output: " + output);
		
		// @TODO - The date time get converted by it still bears the TimeZone as
		// GMT
		Date _d = dateTimeNewTZ.toLocalDateTime().toDate();
		return _d;
	}
	
	/**
	 * 
	 * @param utcDate
	 * @param timeZone
	 * @return
	 */
	public static Date convertTimeZoneToUTC(Date timeZoneDate, String timeZone)
	{
		DateTimeZone zoneUTC = DateTimeZone.forID("GMT");
		
		DateTime dateTimeETC = new DateTime(timeZoneDate.getTime(), zoneUTC);
		
		DateTimeFormatter formatterETC = DateTimeFormat.forStyle("FF")
		        .withZone(zoneUTC).withLocale(Locale.ENGLISH);
		
		String output = formatterETC.print(dateTimeETC);
		// This will print converted time with right timezone
		System.out.println("output: " + output);
		
		// @TODO - The date time get converted by it still bears the TimeZone as
		// GMT
		Date _d = dateTimeETC.toLocalDateTime().toDate();
		return _d;
	}
	
	/**
	 * Adjust timezone to GMT. Adjusting factor is in mins -ve mins is +GMT
	 * 
	 * @param timeZoneDate
	 * @param timeAdjustmentInMins
	 * @return
	 */
	public static Date convertUTCToTimeZone(Date timeZoneDate,
	        int timeAdjustmentInMins)
	{
		DateTime dateTimeETC = new DateTime(timeZoneDate.getTime());
		DateTime dateTimeNewTimeZone = dateTimeETC
		        .minusMinutes(timeAdjustmentInMins);
		
		return dateTimeNewTimeZone.toDate();
	}
	
	/**
	 * This method transforms ####.## to ###
	 * 
	 * @param value
	 * @return
	 */
	public static String convertDoubletoString(Double value)
	{
		String _valueString = Double.toString(value);
		_valueString = _valueString.substring(0, _valueString.indexOf('.'));
		return _valueString;
	}
	
	/**
	 * Find the RAG status for the Alert
	 * 
	 * @param caredPersonSymptomList
	 * @return
	 */
	public static String getSymptomAlertLevel(
	        List<CaredPersonSymptomLog> caredPersonSymptomList)
	{
		
		int _initWeight = -1;
		String _alertlevel = "";
		
		for (Iterator iterator = caredPersonSymptomList.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonSymptomLog caredPersonSymptomLog = (CaredPersonSymptomLog) iterator
			        .next();
			
			PreExistingDiseaseCommonSymptom _preExistingDiseaseCommonSymptom = caredPersonSymptomLog
			        .getPreExistingDiseaseCommonSymptom().getModel();
			
			int weightage = _preExistingDiseaseCommonSymptom.getWeightage();
			
			if (weightage > _initWeight)
			{
				_initWeight = weightage;
			}
		}
		
		if (_initWeight <= 2 && _initWeight > 0)
		{
			_alertlevel = "GREEN";
		} else if (_initWeight > 2 && _initWeight <= 4)
		{
			_alertlevel = "ORANGE";
		} else if (_initWeight > 4)
		{
			_alertlevel = "RED";
		}
		
		return _alertlevel;
		
	}
	
	/**
	 * @deprecated
	 * @param medicationFreq
	 * @return
	 */
	String prescriptionMap(String medicationFreq, Date rxLineStartDate)
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
	 * 
	 * @param medicationFreq
	 * @param rxLineStartDate
	 * @return
	 */
	public static boolean ifRxEligibleDay(String medicationFreq,
	        Date rxLineStartDate)
	{
		boolean _isEligibleDay = false;
		
		Calendar _c = Calendar.getInstance();
		_c.setTime(rxLineStartDate);
		int _rxStartdayOfWeek = _c.get(Calendar.DAY_OF_WEEK);
		
		Calendar _cCurrent = Calendar.getInstance();
		int _currentDateOfWeek = _cCurrent.get(Calendar.DAY_OF_WEEK);
		
		if (medicationFreq != null & !medicationFreq.equals(""))
		{
			logger.log(Level.INFO, "medicationFreq - > " + medicationFreq
			        + " -- " + " _rxStartdayOfWeek " + _rxStartdayOfWeek
			        + " _currentDateOfWeek " + _currentDateOfWeek);
			
			if (_currentDateOfWeek == _rxStartdayOfWeek)
			{
				if (medicationFreq.equals("QWK") | medicationFreq.equals("BIS")
				        | medicationFreq.equals("TIW"))
					_isEligibleDay = true;
			} else
			{
				int _elgDay = _rxStartdayOfWeek;
				
				// Twice a week
				if (medicationFreq.equals("BIS"))
				{
					_isEligibleDay = bisDays(_rxStartdayOfWeek,
					        _currentDateOfWeek);
				}
				// Three times a week
				else if (medicationFreq.equals("TIW"))
				{
					_isEligibleDay = tiwDays(_rxStartdayOfWeek,
					        _currentDateOfWeek);
				}
			}
			
			logger.log(Level.INFO, "medicationFreq - > " + medicationFreq
			        + " -- " + _isEligibleDay);
		}
		
		return _isEligibleDay;
	}
	
	// Check for Twice a week Tx
	private static boolean bisDays(int dayOfRxStart, int currentDay)
	{
		boolean _found = false;
		int[] _bisarray = new int[2];
		int _elgDay = dayOfRxStart;
		
		for (int i = 0; i < 2; i++)
		{
			_elgDay += 3;
			if (_elgDay > 7)
			{
				_elgDay -= 7;
			}
			
			_bisarray[i] = _elgDay;
		}
		
		for (int i = 0; i < _bisarray.length; i++)
		{
			
			if (currentDay == _bisarray[i])
			{
				_found = true;
				break;
			}
		}
		
		return _found;
	}
	
	// Check for Thrice a week Tx
	private static boolean tiwDays(int dayOfRxStart, int currentDay)
	{
		boolean _found = false;
		int[] _biwarray = new int[3];
		int _elgDay = dayOfRxStart;
		
		for (int i = 0; i < 3; i++)
		{
			_elgDay += 2;
			if (_elgDay > 7)
			{
				_elgDay -= 7;
			}
			
			_biwarray[i] = _elgDay;
		}
		
		for (int i = 0; i < _biwarray.length; i++)
		{
			
			if (currentDay == _biwarray[i])
			{
				_found = true;
				break;
			}
		}
		
		return _found;
	}
	
	/**
	 * Convert email address to partial address like timaaah@gmail.com to
	 * timxxx@xxail.com
	 * 
	 * @param emailAddress
	 * @return
	 */
	public static String partialMaskEmail(String emailAddress)
	{
		
		/**
		 * Substring from 1 to 3 Sustring from 4 to (length-4) -> make xxxxx
		 * Subtring from length-3 to length
		 */
		
		String _start = emailAddress.substring(0, 3);
		
		String _mid = emailAddress.substring(4, (emailAddress.length() - 5));
		
		_mid = "xxxxxxxxxxx";
		
		String _last = emailAddress.substring((emailAddress.length() - 4),
		        emailAddress.length());
		
		String _maskedEmail = _start + _mid + _last;
		
		return _maskedEmail;
	}
	
}
