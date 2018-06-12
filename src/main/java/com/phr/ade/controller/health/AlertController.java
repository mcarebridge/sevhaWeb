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
public class AlertController extends BaseController
{

    private static Logger logger = Logger.getLogger(AlertController.class
	    .getName());

    private String fwdPage = "load/alert.jsp";

    @Override
    public Navigation executeLogic() throws Exception
    {

	String action = requestScope("actionParam");
	pageTitle = "title.alert";
	System.out.println("Page Title : " + pageTitle);

	if (action != null && action.equalsIgnoreCase("LISTALRT"))
	{
	    fwdPage = "load/alert.jsp";
	} else if (action != null && action.equalsIgnoreCase("NEWALRT"))
	{
	    fwdPage = "create/alert.jsp";
	} else if (action != null && action.equalsIgnoreCase("UPDALRT"))
	{
	    fwdPage = "load/alert.jsp";
	} else if (action != null && action.equalsIgnoreCase("CREALRT"))
	{
	    fwdPage = "load/alert.jsp";
	} else if (action != null && action.equalsIgnoreCase("LDUPDALRT"))
	{
	    fwdPage = "update/alert.jsp";
	} else if (action != null && action.equalsIgnoreCase("DASHBOARD"))
	{
	    fwdPage = "load/dashboard.jsp";
	}

	System.out.println("AlertController alert box :" + showErrBox);

	return forward(fwdPage);
    }

}
