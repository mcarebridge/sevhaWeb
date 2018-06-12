package com.phr.ade.service;

import com.phr.ade.values.ICareBridgeConstants;

public interface IMobileDataExchangeService extends ICareBridgeConstants
{
	
	void authenticateCaredPerson() throws Exception;
	
	void receiveCaredPersonStatus() throws Exception;
	
	StringBuffer sendCaredPersonCurrentRxPlan(Long caredPersonKey)
	        throws Exception;
	
}
