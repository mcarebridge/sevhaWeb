/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Navigation;

/**
 * @author DS5002449
 * 
 */
public class LogoutController extends BaseController
{

    private static Logger logger = Logger.getLogger(LogoutController.class
	    .getName());

    @Override
    public Navigation executeLogic() throws Exception
    {

	HttpSession session = request.getSession();
	session.invalidate();
	return forward("index");
    }

}
