package com.phr.ade.controller.health;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.exception.MCareUserSessionException;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.Profile;
import com.phr.ade.model.User;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.security.Encrypt;
import com.phr.ade.service.CareService;
import com.phr.ade.service.PrescriptionService;
import com.phr.ade.service.VitalParameterService;
import com.phr.ade.util.CareUtil;
import com.phr.ade.util.CaredPersonRxComplianceSnapShot;
import com.phr.ade.util.UtilCommonConstants;
import com.phr.ade.values.ICareBridgeConstants;

public abstract class BaseController extends Controller implements ICareBridgeConstants, UtilCommonConstants {

	private static Logger logger = Logger.getLogger(BaseController.class.getName());
	String pageTitle = "title.welcome";
	String pageHeader = "page.Header";
	String systemError = "system.error";
	boolean showHeader = true;
	boolean showErrBox = false;
	protected User user = null;
	protected Profile profile = null;
	String actionParam = null;
	String careneedy = null;
	Profile _profile = null;
	Long _profileId = null;

	@Override
	protected Navigation run() throws Exception {
		Validators v = null;
		try {
			actionParam = (String) requestScope("actionParam");

			v = new Validators(request);
			validateSession(v);

			// Always get it from session.
			Long _careNeedyKey = extractSelectedCareId();
			careneedy = (_careNeedyKey != null) ? _careNeedyKey.toString() : null;

			_profile = (Profile) sessionScope("profile");
			_profileId = (Long) sessionScope("profileId");

			// user = (User) sessionScope("currentUser");
			// temp fix
			// user = new User();
			profile = (Profile) sessionScope("profile");
			if (null == profile) {
				if (actionParam != null) {
					Navigation _navg = executeLogic();
					requestScope("pageTitle", pageTitle);
					requestScope("pageHeader", pageHeader);
					requestScope("showHeader", showHeader);
					requestScope("showErrBox", new Boolean(showErrBox).toString());
					return _navg;
				} else {
					return forward("index");
				}
			} else {
				if (actionParam.equalsIgnoreCase("LOGOUT")) {
					HttpSession session = request.getSession();
					session.invalidate();
					return forward("/");
				} else if (actionParam.equalsIgnoreCase("LOADCARED") | actionParam.equalsIgnoreCase("LOADCAREGVR")
						| actionParam.equalsIgnoreCase("LOADPRES") | actionParam.equalsIgnoreCase("LISTPHY")
						| actionParam.equalsIgnoreCase("MNGPROFILE") | actionParam.equalsIgnoreCase("VITALPARA")
						| actionParam.equalsIgnoreCase("DASHBOARD") | actionParam.equalsIgnoreCase("EMERGENCY")
						| actionParam.equalsIgnoreCase("HELPME") | actionParam.equalsIgnoreCase("RXSYMPDTLS")
						| actionParam.equalsIgnoreCase("SLTNEEDYCP") | actionParam.equalsIgnoreCase("NEWCAD")) {
					String _fwdPage = menuControl();
					paintDashboard();
					requestScope("pageTitle", pageTitle);
					System.out.println("-----> Error Box : " + showErrBox);
					requestScope("showErrBox", new Boolean(showErrBox).toString());

					return forward(_fwdPage);
				} else {
					Navigation _navg = executeLogic();
					requestScope("pageTitle", pageTitle);
					requestScope("pageHeader", pageHeader);
					requestScope("showHeader", showHeader);
					requestScope("showErrBox", new Boolean(showErrBox).toString());
					return _navg;
				}
			}
		} catch (MCareUserSessionException muse) {
			logger.log(Level.SEVERE, muse.getMessage(), muse);
			String fwdPage = "/health/index.jsp";

			try {
				manageUserSessionException(actionParam);
			} catch (Exception e) {
				throw new Exception(e);
			}

			// v.add("username",
			// v.required("User session expired. Please login"));
			// boolean _b = v.validate();
			//
			// requestScope("errorSize", new Integer(v.getErrors().size()));
			// requestScope("showErrBox", new Boolean(true).toString());
			return forward(fwdPage);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			requestScope("systemError", systemError);
			requestScope("pageTitle", "page.error");
			response.setHeader("Cache-Control", "no-cache");
//			sessionScope("currentUser", null); 
			HttpSession session = request.getSession();
			session.invalidate();
			return forward("/health/error.jsp");
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Navigation executeLogic() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	protected Profile populateProfileEntity() throws Exception {

		Profile _profile = new Profile();
		_profile.setFirstname((String) requestScope("firstname"));
		_profile.setLastname((String) requestScope("lastname"));

		String _gender = ((String) requestScope("gender") != null) ? (String) requestScope("gender") : "0";

		_profile.setGender(new Integer(_gender));
		Date _dob = DateUtil.toDate((String) requestScope("dob"), "MM/dd/yyyy");
		_profile.setDateofbirth(_dob);
		_profile.setCity((String) requestScope("city"));
		_profile.setCountry((String) requestScope("country"));

		String _purpose = ((String) requestScope("purpose") != null) ? (String) requestScope("purpose") : "0";

		_profile.setPurpose(new Integer(_purpose));
		_profile.setEmail(((String) requestScope("email")).toLowerCase());

		String _password = (String) requestScope("password");
		String _passwordEnc = Encrypt.encodeString(_password);
		_profile.setPassword(_passwordEnc);

		ArrayList<PreExistingCondition> _preCondList = parsePreExistingCondition();
		_profile.setPreExistingConditions(_preCondList);

		return _profile;
	}

	/**
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private ArrayList<PreExistingCondition> parsePreExistingCondition() throws NumberFormatException, Exception {

		String selectedPreCondition = (String) requestScope("selectedPreCondition");
		ArrayList<PreExistingCondition> _preExistingList = new ArrayList<PreExistingCondition>();
		CareService _cs = new CareService();

		if (selectedPreCondition != null) {
			StringTokenizer _st = new StringTokenizer(selectedPreCondition, ",");

			while (_st.hasMoreElements()) {
				String _criticalIllnessKey = (String) _st.nextElement();

				CommonPreExistingDiseases _cpfd = _cs.loadPreExistingDisease(new Long(_criticalIllnessKey));
				PreExistingCondition _preExisitingCond = new PreExistingCondition();
				_preExisitingCond.getPreExisitingDiseases().setModel(_cpfd);
				_preExistingList.add(_preExisitingCond);

			}
		}

		return _preExistingList;
	}

	/**
	 * 
	 * @return
	 */
	protected Profile populateProfileFromSession() {

		Profile _profile = (Profile) sessionScope("profile");
		Long _profileId = sessionScope("profileId");
		Key _key = Datastore.createKey(Profile.class, _profileId);

		_profile.setKey(_key);

		return _profile;
	}

	/**
	 * @throws Exception
	 * 
	 */
	protected void paintDashboard() throws Exception {
		Long _profileId = sessionScope("profileId");

		if (_profileId == null) {
			throw new MCareUserSessionException("User session expired");
		}

		CareService _cs = new CareService();
		List<CaredPerson> _caredPersons = _cs.loadCaredPersonsForProfile(_profileId);

		CaredPerson _selectedCP = (CaredPerson) sessionScope("selectedCared");
		if (_selectedCP != null) {
			requestScope("careneedy", sessionScope("selectedCaredId"));
		}
		requestScope("caredPersons", _caredPersons);
	}

	/**
	 * 
	 */
	protected String menuControl() throws Exception {
		String action = requestScope("actionParam");
		// String careneedy = (String) requestScope("careneedy");
		String selectCareneedy = (String) requestScope("selectedCaredId");

		logger.log(Level.INFO, "  menuControl careneedy " + careneedy);

		// START
		/**
		 * if (careneedy == null) { Long _careNeedyKey =
		 * sessionScope("selectedCaredId"); careneedy = (_careNeedyKey != null) ?
		 * _careNeedyKey.toString() : null; }
		 **/
		// END

		Profile _profile = (Profile) sessionScope("profile");
		Long _profileId = (Long) sessionScope("profileId");
		pageTitle = "title.dashboard";
		String fwdPage = "load/careDashboard.jsp";

		Validators v = new Validators(request);
		boolean _isFormValid = validate(v);

		if (action.equalsIgnoreCase("SLTNEEDYCP")) {
			// This is the common code to set the CaredPerson to Session
			if (selectCareneedy.equals("S")) {
				removeSessionScope("selectedCaredId");
				removeSessionScope("selectedCaredPerson");
			} else {

				CareService _cs = new CareService();
				CaredPerson _caredPerson = _cs.loadCaredPerson(new Long(selectCareneedy));

				sessionScope("selectedCaredPerson", _caredPerson);
				sessionScope("selectedCaredId", selectCareneedy);
			}

			fwdPage = "load/careDashboard.jsp";

		} else if (action.equalsIgnoreCase("LOADCARED")) {
			pageTitle = "title.cared";
			CareService _service = new CareService();
			List<CaredPerson> _caredPersons = _service.loadCaredPersonsForProfile(_profileId);
			requestScope("caredPersons", _caredPersons);

			fwdPage = "load/cared.jsp";
		} else if (action.equalsIgnoreCase("NEWCAD")) {
			pageTitle = "title.cared";
			CareService _cs = new CareService();
			List<CommonPreExistingDiseases> _cpfd = _cs.loadAllPreExistingDiseases();
			requestScope("allPreExistingDiseases", _cpfd);
			fwdPage = "create/cared.jsp";
		} else if (action.equalsIgnoreCase("MNGPROFILE")) {
			pageTitle = "title.profile";
			setProfileInRequest(_profile);
			fwdPage = "load/profile.jsp";
		} else if (action != null && action.equalsIgnoreCase("LOADCAREGVR")) {
			CareService _cs = new CareService();
			List<Caregiver> _careGiverList = _cs.loadCaregiversForProfile(_profileId);
			pageTitle = "title.caregiver";
			requestScope("careGiverList", _careGiverList);
			fwdPage = "load/caregiver.jsp";
		} else if (action != null && action.equalsIgnoreCase("LISTPHY")) {
			CareService _cs = new CareService();
			Key _key = Datastore.createKey(Profile.class, _profileId);
			List<Physician> _physicians = _cs.loadPhysiciansForProfile(_key);
			pageTitle = "title.physician";
			requestScope("physicians", _physicians);
			fwdPage = "load/physician.jsp";
		} else if (action != null && action.equalsIgnoreCase("LOADPRES")) {
			pageTitle = "title.prescription";

			if (!_isFormValid) {
				showErrBox = true;
			} else {
				// Load Cared Person Details
				CareService _cs = new CareService();
				Key _profilekey = Datastore.createKey(Profile.class, _profileId);
				List<Physician> _phyList = _cs.loadPhysiciansForProfile(_profilekey);

				boolean _phyFound = false;

				System.out.println("list size of phy --->" + _phyList.size());

				if (_phyList.size() > 0) {
					_phyFound = true;
				}

				sessionScope("physicianFound", _phyFound);

				CaredPerson _cp = _cs.loadCaredPerson(new Long(careneedy));
				// sessionScope("selectedCared", _cp);
				// Add key exclusively as session object was not retaining PK
				// sessionScope("selectedCaredId", new
				// Long(_cp.getKey().getId()));

				/**
				 * Long _careNeedyKey = null; if (careneedy != null) { _careNeedyKey = new
				 * Long(careneedy); } else { _careNeedyKey = sessionScope("selectedCaredId"); }
				 **/

				PrescriptionService _psService = new PrescriptionService();
				List<Prescription> _prescriptions = _psService.loadPrescriptionsByCaredPerson(new Long(careneedy));
				requestScope("prescriptionList", _prescriptions);
				fwdPage = "load/prescription.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("VITALPARA")) {
			pageTitle = "title.vitalPara";
			if (!_isFormValid) {
				showErrBox = true;
			} else {
				VitalParameterService _vps = new VitalParameterService();
				VitalParameter _vitalPara = _vps.lastTakenVitalParameter(new Long(careneedy));
				logger.log(Level.INFO, "---Last loaded VitalParameter " + _vitalPara);

				if (_vitalPara != null) {
					requestScope("height", CareUtil.convertDoubletoString(_vitalPara.getHeight()));
					requestScope("weight", CareUtil.convertDoubletoString(_vitalPara.getWeight()));
					requestScope("temperature", _vitalPara.getTemprature());
					requestScope("pulse", CareUtil.convertDoubletoString(_vitalPara.getPulse()));
					requestScope("systolicPressure", CareUtil.convertDoubletoString(_vitalPara.getSystolicPressure()));
					requestScope("diastolicPressure",
							CareUtil.convertDoubletoString(_vitalPara.getDiastolicPressure()));
					requestScope("bloodSugar", CareUtil.convertDoubletoString(_vitalPara.getBloodSugar()));
					requestScope("bloodSugarFasting",
							CareUtil.convertDoubletoString(_vitalPara.getBloodSugarFasting()));
					requestScope("createdDate", CareUtil.convertUTCtoTimeZone(_vitalPara.getCreatedDate(), "EST"));
				} else {
					// @TODO : Hard Coded to EST
					requestScope("createdDate", CareUtil.convertUTCtoTimeZone(new Date(), "EST"));
				}

				fwdPage = "create/vitalParameters.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("DASHBOARD")) {
			CareService _service = new CareService();
			List<CaredPerson> _caredPersons = _service.loadCaredPersonsForProfile(_profileId);
			requestScope("caredPersons", _caredPersons);
		} else if (action != null && action.equalsIgnoreCase("HELPME")) {
			pageTitle = "title.helpme";
			fwdPage = "load/helpme.jsp";

		} else if (action != null && action.equalsIgnoreCase("EMERGENCY")) {
			pageTitle = "title.emergencycontact";
			loadEmergencyContactList();

			fwdPage = "load/emergencyContact.jsp";
		} else if (action != null && action.equalsIgnoreCase("RXSYMPDTLS")) {

			Validators v1 = new Validators(request);
			boolean _isFormValid1 = validate(v1);
			if (!_isFormValid1) {
				showErrBox = true;
				CareService _cs = new CareService();
				List<CaredPerson> _caredPersons = _cs.loadCaredPersonsForProfile(_profileId);
				requestScope("caredPersons", _caredPersons);
				fwdPage = "load/careDashboard.jsp";

			} else {
				logger.log(Level.INFO, "  RXSYMPDTLS careneedy " + careneedy);

				PrescriptionService _ps = new PrescriptionService();

				Date _startDate = new Date();

				// @todo - for now hardcoding to EST, later need to intake
				// TimeZone
				// Date _starteDateWithTZ = CareUtil.convertUTCtoTimeZone(
				// _startDate, "America/New_York");

				// 05-10-2017 : Omiting Date logic for converting timezone

				Date _endDate = DateUtils.addDays(_startDate, -8);

				List<CaredPersonRxComplianceSnapShot> _caredPersonRxComplianceSnapShot = _ps
						.findRxAndSymptomsByDate(careneedy, _startDate, _endDate);

				requestScope("rxAndSymptomsList", _caredPersonRxComplianceSnapShot);

				fwdPage = "load/rxSymptomComplianceDtls.jsp";
			}
		}
		return fwdPage;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v) {
		Long _careNeedyKey = extractSelectedCareId();

		// start - 02/12/17 adding careneedy in the request scope to
		// avoid form invalidation
		if (_careNeedyKey == null) {
			v.add("selectedCaredId", v.required("Select a Cared Person"));
		}
		// end - 02/12/17

		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}

	private void setProfileInRequest(Profile _profile) {
		requestScope("firstname", _profile.getFirstname());
		requestScope("lastname", _profile.getLastname());
		requestScope("gender", _profile.getGender());
		requestScope("dob", DateUtil.toString(_profile.getDateofbirth(), "MM/dd/yyyy"));
		requestScope("city", _profile.getCity());
		requestScope("country", _profile.getCountry());
		requestScope("purpose", _profile.getPurpose());
		requestScope("email", _profile.getEmail());
	}

	/**
	 * 
	 * @param v
	 * @return
	 * @throws MCareUserSessionException
	 */
	private boolean validateSession(final Validators v) throws MCareUserSessionException {

		HttpSession usersession = request.getSession(false);
		boolean valid = false;

		if (usersession == null) {
			v.add("username", v.required("User session has expired. Please login again."));
			valid = v.validate();
			requestScope("errorSize", new Integer(v.getErrors().size()));
			requestScope("showErrBox", new Boolean(true).toString());

			throw new MCareUserSessionException("User session expired");
		}

		return valid;
	}

	/**
	 * 
	 */
	void loadEmergencyContactList() throws Exception {
		CareService _cs = new CareService();
		List<CaredPerson> _caredPersons = _cs.loadCaredPersonsForProfile(_profileId);

		Hashtable<CaredPerson, EmergencyResponse> _emergencyContactList = new Hashtable<CaredPerson, EmergencyResponse>();

		for (CaredPerson caredPerson : _caredPersons) {
			EmergencyResponse _er = _cs.loadEmergemcyResponseForCaredPerson(new Long(caredPerson.getKey().getId()));
			if (_er != null) {
				_emergencyContactList.put(caredPerson, _er);
			}
		}

		requestScope("caredPersons", _caredPersons);
		requestScope("emergencyContactList", _emergencyContactList);
	}

	/**
	 * 
	 * @return
	 */
	Long extractSelectedCareId() {
		Object _selectedCaredIdObj = sessionScope("selectedCaredId");

		logger.log(Level.INFO, "-- session._selectedCaredIdObj---> " + _selectedCaredIdObj);

		Long _careNeedyKey = null;

		if (_selectedCaredIdObj != null) {

			String _selectedCaredIdStr = (String) sessionScope("selectedCaredId");

			if (_selectedCaredIdStr != null) {
				_careNeedyKey = new Long(Long.parseLong(_selectedCaredIdStr));
				logger.log(Level.INFO, "-- session._selectedCaredIdStr---> " + _selectedCaredIdStr);
			}
		}
		return _careNeedyKey;

	}

	/**
	 * @throws Exception
	 * 
	 */

	String manageUserSessionException(String action) throws Exception {
		String fwdPage = null;

		if (action != null && action.equalsIgnoreCase("REGENPASS")) {
			String _userNameEnc = (String) requestScope("u");
			String _userName = Encrypt.decodeString(_userNameEnc);
			request.setAttribute("username", _userName);

			fwdPage = "update/updatepassword.jsp";
		}
		return fwdPage;
	}

}
