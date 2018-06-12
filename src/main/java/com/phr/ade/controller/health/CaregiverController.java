/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import validator.RegisteredEmailVaidator;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Address;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.Profile;
import com.phr.ade.service.CareService;

/**
 * @author DS5002449
 * 
 */
public class CaregiverController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(CaregiverController.class
	                                      .getName());
	
	private String        fwdPage = "load/caregiver.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		pageTitle = "title.caregiver";
		Long _profileId = (Long) sessionScope("profileId");
		String _keyId = (String) requestScope("key");
		
		System.out.println("Page Title : " + pageTitle);
		
		if (action != null && action.equalsIgnoreCase("LISTCAG"))
		{
			CareService _cs = new CareService();
			List<Caregiver> _careGiverList = _cs
			        .loadCaregiversForProfile(_profileId);
			
			requestScope("careGiverList", _careGiverList);
			fwdPage = "load/caregiver.jsp";
		} else if (action != null && action.equalsIgnoreCase("NEWCAG"))
		{
			
			fwdPage = "create/caregiver.jsp";
			
		} else if (action != null && action.equalsIgnoreCase("CRECAG"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "create/caregiver.jsp";
			} else
			{
				Caregiver _careGiver = populateCaregiver();
				CareService _cs = new CareService();
				_cs.createCaregiver(_careGiver);
				
				List<Caregiver> _careGiverList = _cs
				        .loadCaregiversForProfile(_profileId);
				
				System.out.println("Caregiver List " + _careGiverList.size());
				requestScope("careGiverList", _careGiverList);
				
				fwdPage = "load/caregiver.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("LDCAGDTLS"))
		{
			CareService _cs = new CareService();
			Caregiver _cg = _cs.loadCaregiver(new Long(_keyId));
			requestScope("firstname", _cg.getFirstName());
			requestScope("lastname", _cg.getLastName());
			requestScope("gender", _cg.getGender());
			requestScope("dob", DateUtil.toString(_cg.getDob(), "MM/dd/yyyy"));
			requestScope("city", _cg.getAddress().getModel().getCity());
			requestScope("country", _cg.getAddress().getModel().getCountry());
			requestScope("email", _cg.getAddress().getModel().getEmail());
			requestScope("cellphone", _cg.getAddress().getModel()
			        .getCellphone());
			requestScope("addresskey", _cg.getAddress().getModel().getKey()
			        .getId());
			requestScope("key", _keyId);
			fwdPage = "update/caregiver.jsp";
		}
		
		else if (action != null && action.equalsIgnoreCase("UPDCAG"))
		{
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "update/caregiver.jsp";
			} else
			{
				CareService _cs = new CareService();
				
				Caregiver _careGiver = populateCaregiver();
				Key _key = Datastore.createKey(Caregiver.class,
				        new Long(_keyId));
				_careGiver.setKey(_key);
				_cs.updateCaregiver(_careGiver);
				
				List<Caregiver> _careGiverList = _cs
				        .loadCaregiversForProfile(_profileId);
				
				requestScope("careGiverList", _careGiverList);
				fwdPage = "load/caregiver.jsp";
			}
			
		} else if (action != null && action.equalsIgnoreCase("DASHBOARD"))
		{
			paintDashboard();
			fwdPage = "load/careDashboard.jsp";
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
		v.add("firstname",
		        v.required("First Name"),
		        v.maxlength(30),
		        v.minlength(2),
		        v.regexp(
		                "^[0-9a-zA-Z-]+$",
		                "First Name Use only Number(0-9),Alphabet(a-z,A-Z) and hypens(-) with no spaces"));
		v.add("lastname",
		        v.required("Last Name"),
		        v.maxlength(30),
		        v.minlength(2),
		        v.regexp("^[0-9a-zA-Z-]+$",
		                "Last Name Use only Number(0-9),Alphabet(a-z,A-Z) and hypens(-) with no spaces"));
		
		v.add("dob", v.required("Date of Birth"),
		        v.dateType("MM/dd/yyyy", "Invalid Date of Birth"));
		v.add("gender", v.required("Select your Gender"));
		v.add("city", v.required("City"), v.minlength(3), v.maxlength(30), v
		        .regexp("^[a-zA-Z\\s\\.]+$",
		                "City Name Use only Alphabet(a-z,A-Z)"));
		v.add("country",
		        v.required("Country"),
		        v.minlength(3),
		        v.maxlength(30),
		        v.regexp("^[a-zA-Z\\s\\.]+$",
		                "Country Use only Alphabet(a-z,A-Z) and hypens(-) with no spaces"));

		v.add("cellphone",
		        v.required("Cell phone"),
		        v.maxlength(15),
		        v.minlength(10),
		        v.regexp(phoneRegex,
		                "Accepted format : +countrycode, followed by phone number without spaces"));
		
		v.add("email", v.required("Email"), v.maxlength(40),
		        v.minlength(6), v.regexp(emailRegex,
		                "Email : Invalid email address"), _rev);
				
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @return
	 */
	private Caregiver populateCaregiver()
	{
		Caregiver _careGiver = new Caregiver();
		Address _address = new Address();
		
		_careGiver.setFirstName((String) requestScope("firstname"));
		_careGiver.setLastName((String) requestScope("lastname"));
		
		String _gender = ((String) requestScope("gender") != null) ? (String) requestScope("gender")
		        : "0";
		_careGiver.setGender(new Integer(_gender));
		Date _dob = DateUtil.toDate((String) requestScope("dob"), "MM/dd/yyyy");
		_careGiver.setDob(_dob);
		_address.setCity((String) requestScope("city"));
		_address.setCountry((String) requestScope("country"));
		_address.setCellphone((String) requestScope("cellphone"));
		_address.setEmail((String) requestScope("email"));
		_careGiver.getAddress().setModel(_address);
		
		Profile _profile = populateProfileFromSession();
		_careGiver.getProfile().setModel(_profile);
		return _careGiver;
	}
	
}
