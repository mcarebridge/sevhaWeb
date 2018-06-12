package com.phr.ade.util;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.Years;

public class CareDateUtil
{
	public static int    year;
	public static int    month;
	public static int    day;
	public static int    hour;
	public static int    minute;
	public static String timeZoneId;
	
	/**
	 * Parses the Date String type "2017-05-08 16:50 -0400" into Year, Month,
	 * Day, Hour, Min and timezone id It used String functions to avoid any
	 * influence of System locale in date construction
	 * 
	 * @param date
	 */
	public static void parseDateString(String dateTimeString)
	{
		
		StringTokenizer _st = new StringTokenizer(dateTimeString, " ");
		
		String _dateString = (String) _st.nextElement();
		String _timeString = (String) _st.nextElement();
		String _timeZoneId = (String) _st.nextElement();
		
		StringTokenizer _st1 = new StringTokenizer(_dateString, "-");
		
		String _year = (String) _st1.nextElement();
		String _month = (String) _st1.nextElement();
		String _day = (String) _st1.nextElement();
		
		year = Integer.parseInt(_year);
		month = Integer.parseInt(_month);
		day = Integer.parseInt(_day);
		
		StringTokenizer _st2 = new StringTokenizer(_timeString, ":");
		String _hour = (String) _st2.nextElement();
		String _minute = (String) _st2.nextElement();
		
		hour = Integer.parseInt(_hour);
		minute = Integer.parseInt(_minute);
		
		timeZoneId = _timeZoneId;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDateAtZeroHrs()
	{
		Calendar _c = Calendar.getInstance();
		_c.set(Calendar.HOUR, 0);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _d = _c.getTime();
		
		return _d;
	}
	
	/**
	 * Get the latest time in UTC convert it to user's timezone and remove hr
	 * and min
	 * 
	 * @param timezoneoffset
	 * @return
	 */
	public static Date getCurrentDateAtZeroHrsWithTimeZoneOffset(
	        int timezoneoffset)
	{
		
		Date _d = new Date();
		Date _adjDate = CareUtil.convertUTCToTimeZone(_d, timezoneoffset);
		
		Calendar _c = Calendar.getInstance();
		
		_c.setTime(_adjDate);
		_c.set(Calendar.HOUR, 0);
		_c.set(Calendar.MINUTE, 0);
		_c.set(Calendar.SECOND, 0);
		
		Date _adjDateTime = _c.getTime();
		
		return _adjDateTime;
	}
	
	/**
	 * Calculate current Age in Years
	 * 
	 * @param birthDate
	 * @return
	 */
	public static int calculateCurrentAge(Date birthDate)
	{
		LocalDate _birthDateLocal = new LocalDate(birthDate.getTime());
		LocalDate _currentDateLocal = new LocalDate();
		Years _age = null;
		
		if ((_birthDateLocal != null) && (_currentDateLocal != null))
		{
			_age = Years.yearsBetween(_birthDateLocal, _currentDateLocal);
		} else
		{
			return 0;
		}
		
		return _age.getYears();
	}
}
