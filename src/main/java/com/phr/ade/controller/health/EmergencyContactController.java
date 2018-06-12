package com.phr.ade.controller.health;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import validator.RegisteredEmailVaidator;

import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.service.CareService;

public class EmergencyContactController extends BaseController
{
	private static Logger logger  = Logger.getLogger(EmergencyContactController.class
	                                      .getName());
	
	private String        fwdPage = "load/emergencyContact.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		String action = requestScope("actionParam");
		careneedy = (String) requestScope("careneedy");
		pageTitle = "title.dashboard";
		
		Validators v = new Validators(request);
		boolean _isFormValid = validate(v);
		
		logger.log(Level.INFO, "--->" + action);
		
		if (action != null && action.equalsIgnoreCase("NEWEMRCNT"))
		{
			CareService _cs = new CareService();
			// List<CaredPerson> _caredPersons = _cs
			// .loadCaredPersonsForProfile(_profileId);
			Hashtable<CaredPerson, String> _caredPersonERStatus = _cs
			        .loadCaredPersonListByEmergencyContact(_profileId);
			
			requestScope("caredPersonERStatus", _caredPersonERStatus);
			fwdPage = "create/emergencyContact.jsp";
		} else if (action != null && action.equalsIgnoreCase("CREEMR"))
		{
			paintDashboard();
			if (!_isFormValid)
			{
				showErrBox = true;
				CareService _cs = new CareService();
				Hashtable<CaredPerson, String> _caredPersonERStatus = _cs
				        .loadCaredPersonListByEmergencyContact(_profileId);
				
				requestScope("caredPersonERStatus", _caredPersonERStatus);
				fwdPage = "create/emergencyContact.jsp";
			} else
			{
				EmergencyResponse _er = populateEmergencyResponse();
				CareService _cs = new CareService();
				_cs.createEmergencyResponse(_er);
				// List<CaredPerson> _caredPersons = _cs
				// .loadCaredPersonsForProfile(_profileId);
				
				// requestScope("caredPersons", _caredPersons);
				loadEmergencyContactList();
			}
		}
		
		return forward(fwdPage);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v)
	{
		RegisteredEmailVaidator _rev = new RegisteredEmailVaidator();
		// v.add("location", v.required());
		// v.add("contactPerson", v.required("Contact Person"));
		// v.add("providerName", v.required("Hospital/Clinic Name"));
		
		v.add("contactPerson", v.required("Contact Person"), v.maxlength(30));
		v.add("providerName", v.required("Hospital/Clinic Name"),
		        v.maxlength(30));
		
		v.add("streetAddress", v.required("Street Address"));
		v.add("city", v.required("City"));
		v.add("country", v.required("Country"));
		v.add("cellphone",
		        v.regexp("^[0-9]+$", "Emergency Contact Number: Use only Number(0-9)"),				
		        v.minlength(10, "Emergency Contact Number : Requires Minimum 10 digits"),
		        v.required("Emergency Contact Number"));
		v.add("email",
		        v.required("Email"),
		        v.maxlength(40),
		        v.minlength(6),
		        v.regexp("^[0-9a-zA-Z-@_.]+$", "Email : Invalid email address"),
		        _rev);
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @return
	 */
	private EmergencyResponse populateEmergencyResponse() throws Exception
	{
		EmergencyResponse _er = new EmergencyResponse();
		
		_er.setContactPerson((String) requestScope("contactPerson"));
		_er.setProviderName((String) requestScope("providerName"));
		
		CareService _cs = new CareService();
		CaredPerson _cp = _cs.loadCaredPerson(new Long(careneedy));
		
		Address _address = new Address();
		
		_address.setStreetAddress((String) requestScope("streetAddress"));
		_address.setCity((String) requestScope("city"));
		_address.setCountry((String) requestScope("country"));
		_address.setCellphone((String) requestScope("cellphone"));
		_address.setFixedLine((String) requestScope("fixedLine"));
		_address.setEmail((String) requestScope("email"));
		_er.getAddress().setModel(_address);
		
		_er.getCaredPerson().setModel(_cp);
		
		return _er;
	}
	
}
