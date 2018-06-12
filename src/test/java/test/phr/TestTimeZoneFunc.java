package test.phr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

import com.phr.ade.util.CareDateUtil;
import com.phr.ade.util.CareUtil;

public class TestTimeZoneFunc
{
	private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a z";
	
	public static void main(String[] args)
	{
		Date _d = new Date();
		Date _adjDate = CareUtil.convertUTCToTimeZone(_d, 420);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		System.out.println("Date () : " + formatter.format(_adjDate));		
	}
}
