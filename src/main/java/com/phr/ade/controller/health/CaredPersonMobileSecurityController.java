package com.phr.ade.controller.health;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonRxLog;
import com.phr.ade.service.CareTransactionService;
import com.phr.ade.service.MobileDataExchangeService;
import com.phr.ade.service.ProfileService;

/**
 * This class only does the authentication based on the cared person's mobile's
 * IMEI number Max two cared person under same profile can have same IMEI number
 * 
 * @author deejay
 * 
 */

public class CaredPersonMobileSecurityController extends Controller
{
	
	private static Logger logger = Logger.getLogger(CaredPersonMobileSecurityController.class
	                                     .getName());
	
	@Override
	protected Navigation run() throws Exception
	{
		String action = requestScope("actionParam");
		String imeiCode = requestScope("imeiCode");
		String currdate = requestScope("currdate");
		int currentHour = -1;
		
		logger.info("--- Current Date Passed ---" + currdate);
		
		SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date _currDateOfMobile = _dateFormat.parse(currdate);
		
		Calendar _c = Calendar.getInstance();
		_c.setTime(_currDateOfMobile);
		currentHour = _c.get(Calendar.HOUR_OF_DAY);
		
		OutputStream o = response.getOutputStream();
		
		Long _imeiNum = new Long(imeiCode);
		
		ProfileService _ps = new ProfileService();
		
		List<CaredPerson> _caredPerson = _ps
		        .authenticateCaredPersonbyIMEI(_imeiNum);
		
		String _mobileResponse = "(CAREDPERSONAUTH:";
		String _partString = "";
		
		if (_caredPerson != null & _caredPerson.size() > 0)
		{
			_partString = "(MSG:AUTH-SUCCESS)";
			response.addHeader("AUTH_MSG", _partString);
			MobileDataExchangeService _ms = new MobileDataExchangeService();
			StringBuffer _xml = _ms.sendCaredPersonCurrentRxPlan(new Long(
			        _caredPerson.get(0).getKey().getId()));
			_partString += "(DATA:" + _xml + ")";
									
			CareTransactionService _careTransSrv = new CareTransactionService();
			
			List<CaredPersonRxLog> caredPersonRxLogList = _careTransSrv
			        .getDailyRxTakenByHr(_caredPerson.get(0).getKey(),
			                _currDateOfMobile);
			
			String _rxConsumedString = getRxTakenLogList(caredPersonRxLogList);
			_partString += _rxConsumedString;
			
			String _rxSkipped = "(RXSKIPPED:";
			String _rxSkipped1 = _rxSkipped
			        + _careTransSrv.getHourlyRxSkippedAsString(_caredPerson
			                .get(0).getKey(), _currDateOfMobile, currentHour);
			_rxSkipped = _rxSkipped1 + ")";
			_partString += _rxSkipped;
			
			_partString += "(CAREDPERSON:" + _caredPerson.get(0).getFirstName()
			        + " " + _caredPerson.get(0).getLastName() + ")";
			
			// String _careGiveName = _caredPerson.get(0).getCaregiver()
			// .getModel().getFirstName();
			// String _cgCellNum = _caredPerson.get(0).getCaregiver().getModel()
			// .getAddress().getModel().getCellphone();
			
			// String _careGiverDtls = "(CAREGIVERDTLS:" + "[CGNAME:"
			// + _careGiveName + "]" + "[CGCELLNUM:" + _cgCellNum + "])";
			
			// _partString += _careGiverDtls;
			
			logger.info("--- Return String ---" + _partString);
			
		} else
		{
			_partString = "(MSG:AUTH-FAILED)";
			_partString += "(DATA:" + "none" + ")";
			_partString += "(RXCONSUMED:-)";
			_partString += "(RXSKIPPED:-)";
			logger.info("--- Return String ---" + _partString);
			response.addHeader("AUTH_MSG", _partString);
		}
		
		_mobileResponse += _partString + ")";
		
		GZIPOutputStream gz = new GZIPOutputStream(o);
		
		gz.write(_mobileResponse.getBytes());
		gz.close();
		o.close();
		// out.println(_mobileDataString.toString());
		// requestScope("mobileResponse", _mobileResponse);
		// return forward("mobileSimulator.jsp");
		return null;
	}
	
	/**
	 * Send back the RxConsumed String = (RXCONSUMED:1,2,3,)
	 * 
	 * @param caredPersonRxLogList
	 * @return
	 */
	private String getRxTakenLogList(List<CaredPersonRxLog> caredPersonRxLogList)
	{
		
		StringBuffer _sb = new StringBuffer("(RXCONSUMED:");
		
		for (Iterator iterator = caredPersonRxLogList.iterator(); iterator
		        .hasNext();)
		{
			CaredPersonRxLog caredPersonRxLog = (CaredPersonRxLog) iterator
			        .next();
			
			_sb.append(caredPersonRxLog.getPrescriptionLines().getModel()
			        .getKey().getId());
			_sb.append(",");
			
		}
		
		if (caredPersonRxLogList == null | caredPersonRxLogList.size() == 0)
		{
			_sb.append("-");
		}
		
		_sb.append(")");
		
		logger.log(Level.INFO, "RxConsumed ----- > " + _sb.toString());
		return _sb.toString();
	}
	
}
