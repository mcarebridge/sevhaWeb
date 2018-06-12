package test.phr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.phr.ade.util.CareUtil;

public class TestDateConversion
{
	
	public static void main(String[] args)
	{
		String _datePart = "2017-04-21 09:00 MST";
		
		
		try
        {
	        SimpleDateFormat _dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm z");
	        
	        SimpleDateFormat _dateFormat1 = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm a z");
	        
	        Date _currDateOfMobile = _dateFormat.parse(_datePart);
	        
	        Date _datePostTZChange = CareUtil.convertTimeZoneToUTC(_currDateOfMobile, "EDT");
	   
	        System.out.println("Parsed Date ---------------> " + _dateFormat1.format(_datePostTZChange));
	                
	   
	        
	        
        } catch (ParseException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
	}
	
}
