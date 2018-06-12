/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.Physician;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.Profile;
import com.phr.ade.service.CareService;
import com.phr.ade.service.PrescriptionService;

/**
 * @author DS5002449
 * 
 */
public class DashboardController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(DashboardController.class
	                                      .getName());
	
	private String        fwdPage = "load/careDashboard.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		String careneedy = (String) requestScope("careneedy");
		Profile _profile = (Profile) sessionScope("profile");
		Long _profileId = (Long) sessionScope("profileId");
		pageTitle = "title.dashboard";
		
		Validators v = new Validators(request);
		boolean _isFormValid = validate(v);
		Calendar _cStart = Calendar.getInstance();
		_cStart.set(Calendar.HOUR, 0);
		_cStart.set(Calendar.HOUR_OF_DAY, 0);
		_cStart.set(Calendar.MINUTE, 0);
		_cStart.add(Calendar.DATE, -7);
		
		Calendar _cEnd = Calendar.getInstance();
		_cEnd.set(Calendar.HOUR, 0);
		_cEnd.set(Calendar.HOUR_OF_DAY, 0);
		_cEnd.set(Calendar.MINUTE, 0);
		
		if (!_isFormValid)
		{
			showErrBox = true;
		} else
		{
			// Load Cared Person Details
			CareService _cs = new CareService();
			PrescriptionService _ps = new PrescriptionService();
			
			CaredPerson _cp = _cs.loadCaredPerson(new Long(careneedy));
			_ps.findRxComplianceForACaredPerson(_cp.getKey().getId(),
			        _cStart.getTime(), _cEnd.getTime());
			
			sessionScope("selectedCared", _cp);
			// Add key exclusively as session object was not retaining PK
			sessionScope("selectedCaredId", new Long(_cp.getKey().getId()));
			
			Long _careNeedyKey = null;
			if (careneedy != null)
			{
				_careNeedyKey = new Long(careneedy);
			} else
			{
				_careNeedyKey = sessionScope("selectedCaredId");
			}
		}
		paintDashboard();
		
		requestScope("showErrBox ", _isFormValid);
		return forward(fwdPage);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v)
	{
		v.add("careneedy", v.required("Care Person"),
		        v.longType("Select a Person to be Cared"));
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	private void setProfileInRequest(Profile _profile)
	{
		requestScope("firstname", _profile.getFirstname());
		requestScope("lastname", _profile.getLastname());
		requestScope("gender", _profile.getGender());
		requestScope("dob", _profile.getDateofbirth());
		requestScope("city", _profile.getCity());
		requestScope("country", _profile.getCountry());
		requestScope("purpose", _profile.getPurpose());
		requestScope("email", _profile.getEmail());
	}
	
}
