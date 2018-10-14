package com.phr.ade.controller.health;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;

import validator.RegisteredEmailVaidator;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.Profile;
import com.phr.ade.service.CareService;
import com.phr.ade.service.ProfileService;
import com.phr.ade.util.CareMailUtil;
import com.phr.ade.util.NewUserEmailDTO;
import com.phr.ade.values.CareMailDTO;

public class RegisterController extends BaseController {

	private static Logger logger = Logger.getLogger(RegisterController.class.getName());

	private String fwdPage = "create/register.jsp";

	@Override
	public Navigation executeLogic() throws Exception {

		String action = requestScope("actionParam");
		String selectedPreCondition = requestScope("selectedPreCondition");

		logger.info("selectedPreCondition = " + selectedPreCondition);

		pageTitle = "title.register";
		boolean _isFormValid = false;

		System.out.println("Page Title : " + pageTitle);
		Profile _profile = null;

		if (action != null && action.equalsIgnoreCase("NEWREG")) {
			// First time load
			showErrBox = false;
			CareService _cs = new CareService();
			List<CommonPreExistingDiseases> _cpfd = _cs.loadAllPreExistingDiseases();
			requestScope("allPreExistingDiseases", _cpfd);
			fwdPage = "create/register.jsp";
		} else if (action != null && action.equalsIgnoreCase("APPLN")) {
			fwdPage = "care.jsp";
		} else if (action != null && action.equalsIgnoreCase("UPDREG")) {
			fwdPage = "update/register.jsp";
		} else if (action != null && action.equalsIgnoreCase("CREREG")) {

			Validators v = new Validators(request);
			_isFormValid = validate(v, action);
			if (!_isFormValid) {
				CareService _cs = new CareService();
				List<CommonPreExistingDiseases> _cpfd = _cs.loadAllPreExistingDiseases();
				requestScope("allPreExistingDiseases", _cpfd);
				showErrBox = true;
			} else {
				_profile = populateProfileEntity();
				// @todo - create profile entity
				ProfileService _pService = new ProfileService();
				_profile = _pService.createProfile(_profile);

				if (_profile == null) {
					v.getErrors().put("EMAILTAKEN", "This emailId is already registered with us.");
					showErrBox = true;
					fwdPage = "create/register.jsp";
				} else {
					sessionScope("profile", _profile);
					sessionScope("profileId", _profile.getKey().getId());
					// send email

					notifyNewUserByEmail(_profile);
					paintDashboard();
					fwdPage = "load/firstTimeUser.jsp";
				}
			}
		} else if (action != null && action.equalsIgnoreCase("UPDPRO")) {

			Validators v = new Validators(request);

			_isFormValid = validatePassword(v);

			if (!_isFormValid) {
				showErrBox = true;
				fwdPage = "load/profile.jsp";
			} else {

				_profile = populateProfileEntity();

				Long _profileId = sessionScope("profileId");
				Key _key = Datastore.createKey(Profile.class, _profileId);
				_profile.setKey(_key);

				ProfileService _pService = new ProfileService();
				_profile = _pService.updateProfile(_profile);

				CareService _service = new CareService();
				List<CaredPerson> _caredPersons = _service.loadCaredPersonsForProfile(_profileId);
				requestScope("caredPersons", _caredPersons);

				fwdPage = "load/careDashboard.jsp";
			}

		} else if (action != null && action.equalsIgnoreCase("DASHBOARD")) {
			paintDashboard();
			fwdPage = "load/dashboard.jsp";
		} else if (action != null && action.equalsIgnoreCase("CAREPORTAL")) {
			CareService _cs = new CareService();
			List<CommonPreExistingDiseases> _cpfd = _cs.loadAllPreExistingDiseases();
			requestScope("allPreExistingDiseases", _cpfd);
			requestScope("advmeterFlag", "none");

			pageTitle = "title.careportal";
			fwdPage = "load/carePortal.jsp";
		}

		requestScope("showErrBox ", _isFormValid);
		requestScope("pageTitle ", pageTitle);
		requestScope("profile ", _profile);

		if (_profile != null) {
			sessionScope("profile ", _profile);
		}

		return forward(fwdPage);
	}

	private void notifyNewUserByEmail(Profile profile) throws Exception {

		NewUserEmailDTO _newUserEmail = new NewUserEmailDTO();
		_newUserEmail.setFirstName(profile.getFirstname());
		_newUserEmail.setSecondName(profile.getLastname());
		_newUserEmail.setEmailAddress(profile.getEmail());
		_newUserEmail.setLaunchServer(HOST_SERVER);

		String _body = CareMailUtil.loadVelocityTemplate("emailNewUser.vm", _newUserEmail);
		CareMailDTO _mailDTO = new CareMailDTO();
		_mailDTO.setMailBody(_body);
		_mailDTO.setMailsubject("Welcome to sevha");
		_mailDTO.setRecepiantName("Hello");
		_mailDTO.setToAddress(profile.getEmail());
		CareMailUtil.sendSimpleMail(_mailDTO);

	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v, String action) {
		// v.add("location", v.required());
		RegisteredEmailVaidator _rev = new RegisteredEmailVaidator();

		v.add("firstname", v.required("First Name"), v.minlength(2), v.maxlength(30),
				v.regexp("^[a-zA-Z]+$", "First Name Use only Alphabet(a-z,A-Z) with no spaces"));
		v.add("lastname", v.required("Last Name"), v.minlength(3), v.maxlength(30),
				v.regexp("^[a-zA-Z]+$", "Last Name Use only Alphabet(a-z,A-Z) with no spaces"));

		v.add("gender", v.required("Gender"), v.byteType(), v.regexp("^[1-9]", "Select a Gender"));

		v.add("dob", v.required("Date of Birth"), v.dateType("MM/dd/yyyy", "Invalid Date of Birth"));

		v.add("city", v.required("City"), v.minlength(3), v.maxlength(30),
				v.regexp("^[a-zA-Z\\s\\.]+$", "City Name Use only Alphabet(a-z,A-Z)"));
		v.add("country", v.required("Country"), v.minlength(3), v.maxlength(30),
				v.regexp("^[a-zA-Z\\s\\.]+$", "Country Use only Alphabet(a-z,A-Z) and hypens(-) with no spaces"));
		v.add("purpose", v.required("Purpose"), v.byteType(), v.regexp("^[1-9]", "Select a purpose"));

//		if (action.equalsIgnoreCase("UPDPRO")) {
//			v.add("email", v.required("Email"), v.maxlength(40), v.minlength(6),
//					v.regexp("^[0-9a-zA-Z-@_.]+$", "Email : Invalid email address"));
//		} else {

		v.add("email", v.required("Email"), v.maxlength(40), v.minlength(6),
				v.regexp(emailRegex, "Email : Invalid email address"), _rev);

		v.add("password", v.required("Password"), v.minlength(8), v.maxlength(12),
				v.regexp(passwordRegex, passwordRegexMsg));

		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}

	/**
	 * Validate only Password
	 * 
	 * @param v
	 * @return
	 */
	private boolean validatePassword(final Validators v) {
		v.add("password", v.required("Password"), v.minlength(8), v.maxlength(12),
				v.regexp(passwordRegex, passwordRegexMsg));

		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}

	/**
	 * 
	 * @return
	 */

	// private Profile populateProfileEntity()
	// {
	//
	// Profile _profile = new Profile();
	// _profile.setFirstname((String) requestScope("firstname"));
	// _profile.setLastname((String) requestScope("lastname"));
	//
	// String _gender = ((String) requestScope("gender") != null) ? (String)
	// requestScope("gender")
	// : "0";
	//
	// _profile.setGender(new Integer(_gender));
	// // @todo - convert string to date
	// // _profile.setDateofbirth((String) requestScope("dob"));
	// _profile.setCity((String) requestScope("city"));
	// _profile.setCountry((String) requestScope("country"));
	//
	// String _purpose = ((String) requestScope("purpose") != null) ? (String)
	// requestScope("purpose")
	// : "0";
	//
	// _profile.setPurpose(new Integer(_purpose));
	// _profile.setEmail(((String) requestScope("email")).toLowerCase());
	// _profile.setPassword((String) requestScope("password"));
	//
	// return _profile;
	// }
}
