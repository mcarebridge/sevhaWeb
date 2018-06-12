package com.phr.ade.controller.health;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.phr.ade.service.MobileDataExchangeService;

public class MobileDataExchangeController extends Controller
{
	
	@Override
	protected Navigation run() throws Exception
	{
		MobileDataExchangeService _ms = new MobileDataExchangeService();
		StringBuffer _xml = _ms.sendCaredPersonCurrentRxPlan(new Long(2352));
		System.out.println(_xml);
		
		return forward("/health/dataexchange.jsp");
	}
	
}
