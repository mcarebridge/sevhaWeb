/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.logging.Logger;

import org.slim3.controller.Navigation;

/**
 * @author DS5002449
 * 
 */
public class ProfileController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(ProfileController.class
	                                      .getName());
	
	private String        fwdPage = "load/profile.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		pageTitle = "title.profile";
		System.out.println("Page Title : " + pageTitle);
		
		if (action != null && action.equalsIgnoreCase("UPDPRO"))
		{
			fwdPage = "load/careDashboard.jsp";
		}
		
		return forward(fwdPage);
	}
	
}
