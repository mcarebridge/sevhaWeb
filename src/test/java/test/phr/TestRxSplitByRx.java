package test.phr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class TestRxSplitByRx
{
	
	public static void main(String[] args)
	{
		try
        {
	        String rxData = "(ACTION=TAKEN)(RXTAKEN=2160001,)(SYMPTOMS=1004,)(CURRDATE=2014-11-10 08:01)";
	        
	        StringTokenizer _st = new StringTokenizer(rxData, "()");
	        

	        String _action = (String) _st.nextElement();
	        String _rx = (String) _st.nextElement();
	        String _symptom = (String) _st.nextElement();

	        // Received Date Format "yyyy-MM-dd hh:mm"		
	        String _currDateStrOfMobile = (String) _st.nextElement();
	        String _datePart = _currDateStrOfMobile.substring(_currDateStrOfMobile.indexOf("=") + 1,
	                _currDateStrOfMobile.length());
	        
	        if (_currDateStrOfMobile != null)
	        {
	        	SimpleDateFormat _dateFormat = new SimpleDateFormat(
	        	        "yyyy-MM-dd hh:mm");
	        	Date _currDateOfMobile = _dateFormat.parse(_datePart);
	        }
        } catch (ParseException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
	}
	
}
