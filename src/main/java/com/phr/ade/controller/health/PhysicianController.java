/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Address;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PhysicianSpeciality;
import com.phr.ade.model.Profile;
import com.phr.ade.persistence.MasterDataDAO;
import com.phr.ade.service.CareService;

/**
 * @author DS5002449
 * 
 */
public class PhysicianController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(PhysicianController.class
	                                      .getName());
	private String        fwdPage = "load/physician.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		pageTitle = "title.physician";
		String _careneedy = requestScope("careneedy");
		Profile _profile = (Profile) sessionScope("profile");
		Long _profileId = (Long) sessionScope("profileId");
		String _keyId = (String) requestScope("key");
		
		if (action != null && action.equalsIgnoreCase("NEWPHY"))
		{
			Collection<PhysicianSpeciality> _phyList = MasterDataDAO
			        .loadPhysicianSpecialities();
			requestScope("physicianList", _phyList);
			fwdPage = "create/physician.jsp";
		} else if (action != null && action.equalsIgnoreCase("CREPHY"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			if (!_isFormValid)
			{
				showErrBox = true;
				Collection<PhysicianSpeciality> _phyList = MasterDataDAO
				        .loadPhysicianSpecialities();
				requestScope("physicianList", _phyList);
				fwdPage = "create/physician.jsp";
			} else
			{
				Physician _phy = populatePhysicianEntity();
				CareService _cs = new CareService();
				_cs.createPhysician(_phy);
				
				Key _profilekey = Datastore
				        .createKey(Profile.class, _profileId);
				List<Physician> _physicians = _cs
				        .loadPhysiciansForProfile(_profilekey);
				requestScope("physicians", _physicians);
				fwdPage = "load/physician.jsp";
			}
			
		} else if (action != null && action.equalsIgnoreCase("UPDPHY"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "update/physician.jsp";
			} else
			{
				Physician _phy = populatePhysicianEntity();
				Key _phykey = Datastore.createKey(Physician.class, new Long(
				        _keyId));
				_phy.setKey(_phykey);
				CareService _cs = new CareService();
				_cs.updatePhysician(_phy);
				Key _key = Datastore.createKey(Profile.class, _profileId);
				List<Physician> _physicians = _cs
				        .loadPhysiciansForProfile(_key);
				requestScope("physicians", _physicians);
				
				// @todo Create Entity;
				fwdPage = "load/physician.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("LISPHY"))
		{
			Key _key = Datastore.createKey(Profile.class, _profileId);
			CareService _cs = new CareService();
			
			List<Physician> _physicians = _cs.loadPhysiciansForProfile(_key);
			requestScope("physicians", _physicians);
			fwdPage = "load/physician.jsp";
		} else if (action != null && action.equalsIgnoreCase("LDUPDPHY"))
		{
			CareService _cs = new CareService();
			Physician _physician = _cs.loadPhysician(new Long(_keyId));
			setPhysicianForm(_physician);
			
			Collection<PhysicianSpeciality> _phyList = MasterDataDAO
			        .loadPhysicianSpecialities();
			requestScope("physicianListSpeciality", _phyList);
			fwdPage = "update/physician.jsp";
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
		// v.add("location", v.required());
		v.add("firstname",
		        v.required("First Name"),
		        v.maxlength(30),
		        v.minlength(2),
		        v.regexp(
		                "^[0-9a-zA-Z\']+$",
		                "First Name Use only Number(0-9),Alphabet(a-z,A-Z) and apostrophe(') with no spaces"));
		
		v.add("lastname",
		        v.required("Last Name"),
		        v.maxlength(30),
		        v.minlength(2),
		        v.regexp(
		                "^[0-9a-zA-Z\']+$",
		                "Last Name Use only Number(0-9),Alphabet(a-z,A-Z) and apostrophe(')) with no spaces"));
		
		v.add("speciality", v.required("Speciality"),
		        v.longType("Select a Speciality"));
		
		v.add("hospital",
		        v.required("Hospital"),
		        v.maxlength(30),
		        v.minlength(2),
		        v.regexp(
		                "^[0-9a-zA-Z\' ]+$",
		                "Hospital Name Use only Number(0-9),Alphabet(a-z,A-Z) and apostrophe(')) with no spaces"));
		v.add("city", v.required("City"), v.maxlength(30), v.minlength(2));
		v.add("zip", v.required("PIN/ZIP"), v.maxlength(10), v.minlength(5));
		v.add("fixedphone",
		        v.required("Fixed Phone"),
		        v.maxlength(15),
		        v.minlength(10),
		        v.regexp(phoneRegex,
		                "Accepted format : +countrycode, followed by phone number without spaces"));
		
		// v.add("cellphone", v.required("Cell Phone"));
		
		String _cellphone = (String) requestScope("cellphone");
		_cellphone.trim();
		
		if (_cellphone != "")
		{
			v.add("cellphone",
			        v.required("Cell Phone"),
			        v.maxlength(15),
			        v.minlength(10),
			        v.regexp(phoneRegex,
			                "Accepted format : +countrycode, followed by phone number without spaces"));
		}
		
		String _email = (String) requestScope("email");
		_email.trim();
		
		System.out.println("Email --->" + _email + "--");
		
		if (_email != "")
		{
			v.add("email", v.required("Email"),
			        v.regexp(emailRegex, "Email : Invalid email address"),
			        v.maxlength(40), v.minlength(6));
		}
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @return
	 */
	private Physician populatePhysicianEntity()
	{
		
		Physician _physician = new Physician();
		Address _address = new Address();
		_physician.setFirstname((String) requestScope("firstname"));
		_physician.setLastname((String) requestScope("lastname"));
		
		String _speciality = ((String) requestScope("speciality") != null) ? (String) requestScope("speciality")
		        : "0";
		
		PhysicianSpeciality _phySpeciality = MasterDataDAO
		        .loadPhysicianSpecialityByKey(new Long(_speciality));
		
		_physician.getPhysicianSpeciality().setModel(_phySpeciality);
		
		Date _dob = DateUtil.toDate((String) requestScope("dob"), "MM/dd/yyyy");
		_address.setCity((String) requestScope("city"));
		_address.setCountry((String) requestScope("country"));
		_address.setCellphone((String) requestScope("cellphone"));
		_address.setFixedLine(((String) requestScope("fixedphone")));
		_address.setEmail((String) requestScope("email"));
		_address.setZip((String) requestScope("zip"));
		_physician.getAddress().setModel(_address);
		
		// _physician.setSpeciality(new Integer(_speciality));
		
		_physician.setHospital((String) requestScope("hospital"));
		
		Profile _profile = populateProfileFromSession();
		_physician.getProfile().setModel(_profile);
		
		return _physician;
	}
	
	/**
	 * 
	 * @param _physician
	 */
	private void setPhysicianForm(Physician _physician)
	{
		requestScope("firstname", _physician.getFirstname());
		requestScope("lastname", _physician.getLastname());
		requestScope("speciality", _physician.getPhysicianSpeciality()
		        .getModel().getKey().getId());
		requestScope("city", _physician.getAddress().getModel().getCity());
		requestScope("country", _physician.getAddress().getModel().getCountry());
		requestScope("zip", _physician.getAddress().getModel().getZip());
		requestScope("cellphone", _physician.getAddress().getModel()
		        .getCellphone());
		requestScope("fixedphone", _physician.getAddress().getModel()
		        .getFixedLine());
		requestScope("hospital", _physician.getHospital());
		requestScope("email", _physician.getAddress().getModel().getEmail());
	}
}
