package test.phr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class TestStringMatch
{
	public static void main(String[] args)
	{
		String _a = "8-16-21";
		
		String _b = "16";
		
		StringTokenizer _stk = new StringTokenizer(_a, "-");
		ArrayList<String> _aList = new ArrayList<String>();
		
		while (_stk.hasMoreElements())
		{
			String _str = (String) _stk.nextElement();
			_aList.add(_str);			
		}
		
		System.out.println(_aList.contains(_b));
		
	}
}
