package test.phr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.phr.ade.service.MobileDataExchangeHelper;
import com.phr.ade.util.CareUtil;

public class TestRxTwiceThriceWeek
{
	
	public TestRxTwiceThriceWeek()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String a[])
	{
		Calendar _c = Calendar.getInstance();
		_c.set(Calendar.YEAR, 2017);
		_c.set(Calendar.MONTH, 7);
		_c.set(Calendar.DATE, 10);
		
		
		
		//boolean _visible = CareUtil.ifRxEligibleDay("TIW", _c.getTime());
		String _timetoTake = MobileDataExchangeHelper.getFreqInHrs("TIW", _c.getTime());
		System.out.println("-- Visible -- " + _timetoTake);
	}
	
}
	