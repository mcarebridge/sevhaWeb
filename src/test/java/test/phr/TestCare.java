package test.phr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.phr.ade.util.CareUtil;

public class TestCare
{
	
	private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a z";
	
	public static void main(String[] args) throws Exception
	{
		// method2();
		Date _d = CareUtil.convertUTCtoTimeZone(new Date(), "America/New_York");
			
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		System.out.println("Date with new TimeZone : "
		        + formatter.format(_d));
		
	}
	
	private static void method2()
	{
		DateTimeZone zoneParis = DateTimeZone.forID("Europe/Paris");
		DateTimeZone zoneMontréal = DateTimeZone.forID("America/Montreal");
		DateTimeZone zoneAuckland = DateTimeZone.forID("Pacific/Auckland");
		
		// DateTime dateTimeParis = new DateTime("2015-07-09T05:10:00+02:00",
		// zoneParis);
		
		DateTime dateTimeParis = new DateTime(new Date().getTime(), zoneParis);
		
		DateTime dateTimeMontréal = dateTimeParis.withZone(zoneMontréal);
		DateTime dateTimeAuckland = dateTimeParis.withZone(zoneAuckland);
		
		DateTimeFormatter formatterMontréal = DateTimeFormat.forStyle("FF")
		        .withZone(zoneAuckland).withLocale(Locale.ENGLISH);
		
		String output = formatterMontréal.print(dateTimeMontréal);
		System.out.println("output: " + output);
		
	}
	
	private static void method1()
	{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		
		Date date = new Date();
		
		TimeZone tz = TimeZone.getDefault(); // From TimeZone GMT
		System.out.println("TimeZone : " + tz.getID() + " - "
		        + tz.getDisplayName());
		System.out.println("TimeZone : " + tz);
		System.out.println("Date (GMT) : " + formatter.format(date));
		
		// ToTimeZone America/New_York
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
		
		DateTime dt = new DateTime(date);
		
		DateTimeZone dtZone = DateTimeZone.forID("America/Phoenix");
		DateTime dtus = dt.withZone(dtZone);
		
		TimeZone tzInAmerica = dtZone.toTimeZone();
		// Date dateInAmerica = dtus.toLocalDateTime().toDate();
		Date dateInAmerica = dtus.toLocalDateTime().toDate();
		
		// Convert to LocalDateTime first
		
		sdfAmerica.setTimeZone(tzInAmerica);
		
		System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - "
		        + tzInAmerica.getDisplayName());
		System.out.println("TimeZone : " + tzInAmerica);
		System.out.println("DateTimeZone : " + dtZone);
		System.out.println("DateTime : " + dtus);
		
		// Date dateInAmerica = CareUtil.convertUTCtoTimeZone(date,
		// "America/Toronto");
		System.out.println("dateInAmerica (Formatter) : "
		        + formatter.format(dateInAmerica));
		System.out.println("dateInAmerica (Object) : " + dateInAmerica);
		
	}
}
