/**
 * 
 */
package com.phr.ade.controller.health;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import validator.RegisteredEmailVaidator;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.CaredPersonSymptomLog;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.Profile;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.service.CareService;
import com.phr.ade.service.CareTransactionService;
import com.phr.ade.service.CaredPersonHealthHelper;
import com.phr.ade.service.PrescriptionService;
import com.phr.ade.util.CareDateUtil;
import com.phr.ade.util.CareUtil;
import com.phr.ade.util.ComplianceDTO;

/**
 * @author DS5002449
 * 
 */
public class CaredController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(CaredController.class
	                                      .getName());
	
	private String        fwdPage = "load/cared.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		String origin = requestScope("origin");
		Long _profileId = (Long) sessionScope("profileId");
		String _keyId = (String) requestScope("key");
		// String _careneedyId = (String) requestScope("careneedy");
		Long _careneedyId = extractSelectedCareId();
		// Get the user timezoneoffset in min
		String _timezoneoffset = (String) sessionScope("timezoneoffset");
		
		String _addresskeyId = (String) requestScope("addresskey");
		logger.log(Level.INFO, "--->" + action);
		
		pageTitle = "title.cared";
		System.out.println("Page Title : " + pageTitle);
		System.out.println("key : " + _keyId);
		System.out.println("_careneedy : " + _careneedyId);
		
		if (action != null && action.equalsIgnoreCase("LISTCAD"))
		{
			CareService _cs = new CareService();
			List<CaredPerson> _caredPersons = _cs
			        .loadCaredPersonsForProfile(_profileId);
			requestScope("caredPersons", _caredPersons);
			fwdPage = "load/cared.jsp";
		} 
		//@todo : This block has been moved to basecontroller. It cane be taken out.
		/**
		else if (action != null && action.equalsIgnoreCase("NEWCAD"))
		{
			CareService _cs = new CareService();
			List<CommonPreExistingDiseases> _cpfd = _cs
			        .loadAllPreExistingDiseases();
			requestScope("allPreExistingDiseases", _cpfd);
			fwdPage = "create/cared.jsp";
		} 
		**/
		else if (action != null && action.equalsIgnoreCase("UPDCAD"))
		{
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				
				CareService _cs = new CareService();
				CaredPerson _cp = _cs.loadCaredPerson(new Long(_keyId));
				
				List<CommonPreExistingDiseases> _cpfd = _cs
				        .loadAllPreExistingDiseases();
				
				requestScope("allPreExistingDiseases", _cpfd);
				requestScope("selectedPreExisitingDiseases",
				        _cp.getPreExistingCondition());
				
				fwdPage = "update/cared.jsp";
			} else
			{
				CaredPerson _cp = populateCaredPerson(_keyId);
				Key _key = Datastore.createKey(CaredPerson.class, new Long(
				        _keyId));
				
				if (_addresskeyId.equalsIgnoreCase(""))
				{
					Key _addresskey = Datastore.createKey(Address.class,
					        new Long(_addresskeyId));
					_cp.getAddress().setKey(_addresskey);
				}
				
				_cp.setKey(_key);
				
				CareService _cs = new CareService();
				_cs.updateCaredPerson(_cp);
				List<CaredPerson> _caredPersons = _cs
				        .loadCaredPersonsForProfile(_profileId);
				requestScope("caredPersons", _caredPersons);
				fwdPage = "load/cared.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("DASHBOARD"))
		{
			paintDashboard();
			fwdPage = "load/careDashboard.jsp";
		} else if (action != null && action.equalsIgnoreCase("LDUPDCAD"))
		{
			CareService _cs = new CareService();
			CaredPerson _cp = _cs.loadCaredPerson(new Long(_keyId));
			requestScope("firstname", _cp.getFirstName());
			requestScope("lastname", _cp.getLastName());
			requestScope("gender", _cp.getGender());
			requestScope("dob", DateUtil.toString(_cp.getDob(), "MM/dd/yyyy"));
			
			requestScope("city", _cp.getAddress().getModel().getCity());
			requestScope("country", _cp.getAddress().getModel().getCountry());
			requestScope("email", _cp.getAddress().getModel().getEmail());
			requestScope("cellphone", _cp.getAddress().getModel()
			        .getCellphone());
			requestScope("imei", _cp.getAddress().getModel().getImei());
			requestScope("addresskey", _cp.getAddress().getModel().getKey()
			        .getId());
			requestScope("key", _keyId);
			
			List<CommonPreExistingDiseases> _cpfd = _cs
			        .loadAllPreExistingDiseases();
			requestScope("allPreExistingDiseases", _cpfd);
			requestScope("selectedPreExisitingDiseases",
			        _cp.getPreExistingCondition());
			
			fwdPage = "update/cared.jsp";
			
		} else if (action != null && action.equalsIgnoreCase("CRECAD"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				CareService _cs = new CareService();
				List<CommonPreExistingDiseases> _cpfd = _cs
				        .loadAllPreExistingDiseases();
				requestScope("allPreExistingDiseases", _cpfd);
				fwdPage = "create/cared.jsp";
			} else
			{
				CaredPerson _person = populateCaredPerson(_keyId);
				CareService _cs = new CareService();
				_cs.createCaredPerson(_person);
				
				List<CaredPerson> _caredPersons = _cs
				        .loadCaredPersonsForProfile(_profileId);
				requestScope("caredPersons", _caredPersons);
				fwdPage = "load/cared.jsp";
			}
		} else if (action != null && action.equalsIgnoreCase("UPDVITALPARA"))
		{
			requestScope("caredPersonKey", _keyId);
			fwdPage = "create/vitalParameters.jsp";
		} else if (action != null && action.equalsIgnoreCase("GENDASHBOARD"))
		{
			Validators v = new Validators(request);
			boolean _isFormValid = validateRxCompliance(v);
			if (!_isFormValid)
			{
				showErrBox = true;
				CareService _cs = new CareService();
				List<CaredPerson> _caredPersons = _cs
				        .loadCaredPersonsForProfile(_profileId);
				requestScope("caredPersons", _caredPersons);
				fwdPage = "load/careDashboard.jsp";
			} else
			{
				Date _endDate = new Date();
				// @todo - for now hardcoding to EST, later need to intake
				// TimeZone
				Date _endDateWithTZ = CareUtil.convertUTCtoTimeZone(_endDate,
				        "America/New_York");
				
				Date _startDate = DateUtils.addDays(_endDateWithTZ, -7);
				
				Calendar _cStart = Calendar.getInstance();
				_cStart.setTime(_startDate);
				_cStart.set(Calendar.HOUR, 0);
				_cStart.set(Calendar.HOUR_OF_DAY, 0);
				_cStart.set(Calendar.MINUTE, 0);
				// _cStart.add(Calendar.DATE, -7);
				
				Calendar _cEnd = Calendar.getInstance();
				_cEnd.setTime(_endDateWithTZ);
				_cEnd.set(Calendar.HOUR, 0);
				_cEnd.set(Calendar.HOUR_OF_DAY, 0);
				_cEnd.set(Calendar.MINUTE, 0);
				
				DateFormat _df = SimpleDateFormat.getDateInstance();
				_df.setCalendar(_cStart);
				logger.info("--Start Date --" + _df.format(_cStart.getTime()));
				
				_df.setCalendar(_cEnd);
				logger.info("--End Date --" + _df.format(_cEnd.getTime()));
				
				CareService _cs = new CareService();
				CaredPerson _cp = _cs.loadCaredPerson(new Long(_careneedyId));
				PrescriptionService _ps = new PrescriptionService();
				Hashtable<String, Long[]> _rxComplianceHTable = _ps
				        .findRxComplianceForACaredPerson(_careneedyId,
				                _cStart.getTime(), _cEnd.getTime());
				
				Set _keySet = _rxComplianceHTable.keySet();
				Hashtable<String, Double> _rxComplinace = new Hashtable<String, Double>();
				ArrayList<ComplianceDTO> _rxComplianceList = new ArrayList<ComplianceDTO>();
				ArrayList<ComplianceDTO> _vitalSignComplianceList = new ArrayList<ComplianceDTO>();
				
				for (Iterator iterator = _keySet.iterator(); iterator.hasNext();)
				{
					String _key = (String) iterator.next();
					String _rxName = _key.substring(_key.indexOf("$") + 1,
					        _key.length());
					
					Long[] _actualAndNeedRx = _rxComplianceHTable.get(_key);
					Long _tobeConsumed = _actualAndNeedRx[0];
					Long _actualConsumed = _actualAndNeedRx[1];
					
					if (_tobeConsumed > 0)
					{
						ComplianceDTO _cDTO = CaredPersonHealthHelper
						        .rxComplianceAlert(_rxName, _tobeConsumed,
						                _actualConsumed);
						_rxComplianceList.add(_cDTO);
					}
					
					// double _compliance = (_actualConsumed.longValue() * 100)
					// / _tobeConsumed.longValue();
					//
					// _rxComplinace.put(_rxName, new Double(_compliance));
				}
				
				logger.info("Size if _rxCompliance  " + _rxComplinace.size());
				
				// Find all the reported Symptons for the day and its importance
				
				CareTransactionService _cts = new CareTransactionService();
				
				int _timezoneoffsetNum = Integer.parseInt(_timezoneoffset);
				
				Date _rangeStart = CareDateUtil
				        .getCurrentDateAtZeroHrsWithTimeZoneOffset(_timezoneoffsetNum);
				
				// Attach SymptomLog too at this point
				List<CaredPersonSymptomLog> _caredPersonSymptomList = _cts
				        .getSymptomsReportedForDateRange(_cp.getKey(),
				                _rangeStart, _rangeStart);
				
				logger.info("Size of Symptoms "
				        + _caredPersonSymptomList.size());
				
				// Find SymtomAlert Level
				String _symptomAlertLevel = CareUtil
				        .getSymptomAlertLevel(_caredPersonSymptomList);
				logger.info("Symptom level Alert " + _symptomAlertLevel);
				
				List<CaredPerson> _caredPersons = _cs
				        .loadCaredPersonsForProfile(_profileId);
				
				// Evaluate VitalParameters
				VitalParameter _vPara = _cs.listLatestVitalParameter(new Long(
				        _careneedyId));
				
				if (_vPara != null)
				{
					ArrayList<ComplianceDTO> _vitalParaCompList = CaredPersonHealthHelper
					        .vitalParameterAlert(_vPara, _cp);
					
					_vitalSignComplianceList.addAll(_vitalParaCompList);
					
					double _lastHeight = _vPara.getHeight();
					double _lastWeight = _vPara.getWeight();
					double _bmi = CaredPersonHealthHelper.calculateBMI(
					        _lastHeight, _lastWeight);
					ComplianceDTO _cDTO = CaredPersonHealthHelper
					        .bmiAlert(_bmi);
					_vitalSignComplianceList.add(_cDTO);
					// requestScope("bmi", _bmi);
				}
				
				requestScope("caredPersons", _caredPersons);
				requestScope("rxCompliance", _rxComplinace);
				requestScope("cpSymtomLogList", _caredPersonSymptomList);
				requestScope("symptomAlertLevel", _symptomAlertLevel);
				requestScope("vitalParameter", _vPara);
				requestScope("rxcomplianceData", _rxComplianceList);
				requestScope("vitalcomplianceData", _vitalSignComplianceList);
				sessionScope("selectedCared", _cp);
				
				fwdPage = "load/careDashboard.jsp";
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
		v.add("city", v.required("City"), v.minlength(2));
		v.add("country", v.required("Country"), v.minlength(2));
		v.add("cellphone",
		        v.required("Cell phone"),
		        v.maxlength(15),
		        v.minlength(10),
		        v.regexp(phoneRegex,
		                "Accepted format : +countrycode, followed by phone number without spaces"));
		v.add("imei", v.required("IMEI"), v.doubleRange(100000000000000L,
		        9999999999999999L, "IMEI should be a vailid number"));
		
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
	 * @param v
	 * @return
	 */
	private boolean validateRxCompliance(final Validators v)
	{
		Long _careNeedyKey = extractSelectedCareId();
		if (_careNeedyKey == null)
		{
			v.add("selectedCaredId", v.required("Select a Cared Person"));
		}
		v.add("careneedy", v.required("Select a Cared Person"));
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private CaredPerson populateCaredPerson(String caredPersonKeyId)
	        throws NumberFormatException, Exception
	{
		CaredPerson _caredPerson = new CaredPerson();
		Address _address = new Address();
		_caredPerson.setFirstName((String) requestScope("firstname"));
		_caredPerson.setLastName((String) requestScope("lastname"));
		String _gender = ((String) requestScope("gender") != null) ? (String) requestScope("gender")
		        : "0";
		_caredPerson.setGender(new Integer(_gender));
		Date _dob = DateUtil.toDate((String) requestScope("dob"), "MM/dd/yyyy");
		_caredPerson.setDob(_dob);
		_address.setCity((String) requestScope("city"));
		_address.setCountry((String) requestScope("country"));
		_address.setCellphone((String) requestScope("cellphone"));
		_address.setEmail((String) requestScope("email"));
		_address.setImei(new Long((String) requestScope("imei")).longValue());
		_caredPerson.getAddress().setModel(_address);
		Profile _profile = populateProfileFromSession();
		_caredPerson.getProfile().setModel(_profile);
		
		// @todo : doesn't handle multi-select
		String selectedPreCondition = (String) requestScope("selectedPreCondition");
		logger.log(Level.INFO, "_criticalIllnessKey = " + selectedPreCondition);
		
		ArrayList<PreExistingCondition> _preExistingList = new ArrayList<PreExistingCondition>();
		
		if (selectedPreCondition != null)
		{
			CareService _cs = new CareService();
			StringTokenizer _st = new StringTokenizer(selectedPreCondition, ",");
			
			while (_st.hasMoreElements())
			{
				String _criticalIllnessKey = (String) _st.nextElement();
				
				CommonPreExistingDiseases _cpfd = _cs
				        .loadPreExistingDisease(new Long(_criticalIllnessKey));
				
				// Check for CaredPerson exist or not
				if (caredPersonKeyId != null)
				{
					// Check if the pre-existing disease has already been
					// attached
					// If yes omit that disease
					if (!_cs.isPreExistingDiseaseForCarePerson(new Long(
					        _criticalIllnessKey), new Long(caredPersonKeyId)))
					{
						PreExistingCondition _preExisitingCond = new PreExistingCondition();
						_preExisitingCond.getPreExisitingDiseases().setModel(
						        _cpfd);
						_preExistingList.add(_preExisitingCond);
					}
				} else
				{
					PreExistingCondition _preExisitingCond = new PreExistingCondition();
					_preExisitingCond.getPreExisitingDiseases().setModel(_cpfd);
					_preExistingList.add(_preExisitingCond);
				}
			}
		}
		
		_caredPerson.setPreExistingCondition(_preExistingList);
		
		return _caredPerson;
		
	}
	
}
