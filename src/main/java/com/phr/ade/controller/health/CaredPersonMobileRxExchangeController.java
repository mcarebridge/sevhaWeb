package com.phr.ade.controller.health;

import java.io.OutputStream;
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
 * This class is executed moment "Taken" or "Skip" is pressed on the mobile
 * phone. It will receive and send Rx data
 * 
 * @author deejay
 * 
 */
public class CaredPersonMobileRxExchangeController extends Controller
{
	
	private static Logger logger = Logger.getLogger(CaredPersonMobileRxExchangeController.class
	                                     .getName());
	
	@Override
	protected Navigation run() throws Exception
	{
		String imeiCode = requestScope("imeiCode");
		String rxData = requestScope("rxData");
		logger.log(Level.INFO, rxData);
		
		OutputStream o = response.getOutputStream();
		
		Long _imeiNum = new Long(imeiCode);
		
		ProfileService _ps = new ProfileService();
		
		List<CaredPerson> _caredPerson = _ps
		        .authenticateCaredPersonbyIMEI(_imeiNum);
		
		String _mobileResponse = "(CAREDPERSONAUTH:";
		String _partString = "";
		
		if (_caredPerson != null & _caredPerson.size() > 0)
		{
			// @todo : How do we know which caredperson we are talking about.
			// Must get it from the mobile phone too
			CareTransactionService _careTransSrv = new CareTransactionService();
			_careTransSrv.addCaredPersonRxLog(rxData, _caredPerson.get(0));
			
			
			_partString = "(MSG:AUTH-SUCCESS)";
			response.addHeader("AUTH_MSG", _partString);
			StringBuffer _xml = new StringBuffer("--Test Data -- ");
			_partString += "(DATA:" + _xml + ")";
			
		} else
		{
			_partString = "(MSG:AUTH-FAILED)";
			_partString += "(DATA:" + "none" + ")";
			response.addHeader("AUTH_MSG", _partString);
		}
		
		_mobileResponse += _partString + ")";
		
		GZIPOutputStream gz = new GZIPOutputStream(o);
		
		gz.write(_mobileResponse.getBytes());
		gz.close();
		o.close();
		return null;
		
	}
	
	
	
}
