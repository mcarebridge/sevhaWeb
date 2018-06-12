package com.phr.ade.controller.health;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.NumberTypeValidator;
import org.slim3.controller.validator.Validators;
import org.slim3.util.DateUtil;

import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.persistence.VitalSignDAO;
import com.phr.ade.service.CareService;
import com.phr.ade.service.CaredPersonHealthHelper;
import com.phr.ade.service.VitalSignService;
import com.phr.ade.util.CareDateUtil;
import com.phr.ade.util.CareMailUtil;
import com.phr.ade.values.CareEmailObject;
import com.phr.ade.values.CareMailDTO;
import com.phr.ade.values.VitalSignDTO;
import com.phr.ade.values.VitalSignUserOutPutDTO;

import validator.RegisteredEmailVaidator;
import validator.VitalSignValidator;

public class CaredPortalController extends BaseController {

	private static Logger logger = Logger.getLogger(CaredPortalController.class.getName());

	private Double pulseDbl = new Double(0d);
	private Double temperatureDbl;
	private Double systolicPressureDbl;
	private Double diastolicPressureDbl;
	private Double bloodSugarNormalDbl;
	private Double bloodSugarFastingDbl;
	private Double sugarhb1acDbl;
	private Double respirationDbl;
	private Double heightDbl;
	private Double weightDbl;
	private String emailAddress;
	private String inputData;
	private Double existingIllnessDbl;
	private Boolean isAdvCalc = false;

	private String fwdPage = "load/caredPortalResultsLongFormat.jsp";

	@Override
	public Navigation executeLogic() throws Exception {
		String action = requestScope("actionParam");
		String eula = requestScope("EULA");
		String advmeterFlag = requestScope("advmeterFlag");
		System.out.println("EULA -----> " + eula);
		logger.info("advmeterFlag entry --- >" + advmeterFlag);

		pageTitle = "title.careportalresults";
		boolean _isFormValid = false;

		if (action != null && action.equalsIgnoreCase("CREPORTAL")) {

			Validators v = new Validators(request);
			_isFormValid = validateCaredPortal(v);
			if (!_isFormValid) {
				pageTitle = "title.careportal";
				fwdPage = "load/carePortal.jsp";

				CareService _cs = new CareService();
				List<CommonPreExistingDiseases> _cpfd = _cs.loadAllPreExistingDiseases();
				requestScope("allPreExistingDiseases", _cpfd);
				showErrBox = true;

			} else {

				String _gender = requestScope("gender");
				parseVitalValues();

				double _bmi = CaredPersonHealthHelper.calculateBMI(heightDbl, weightDbl);

				Date _dob = DateUtil.toDate((String) requestScope("dob"), "MM/dd/yyyy");
				int _age = CareDateUtil.calculateCurrentAge(_dob);

				VitalSignDTO _vitalSignDTO = new VitalSignDTO();
				_vitalSignDTO.setAge(_age);
				_vitalSignDTO.setInputData(inputData);

				_vitalSignDTO.setGender(Integer.parseInt(_gender));
//				_vitalSignDTO.setGender(-1);
				_vitalSignDTO.setEmailAddress(emailAddress);
				VitalSignDAO vitalSignDAO = new VitalSignDAO();
				vitalSignDAO.addModel(_vitalSignDTO);

				// @todo : overriding Gender to gender neutral. After storing the input data
				_vitalSignDTO.setGender(-1);
				Hashtable<String, Double> vitalSignCategoryPair = new Hashtable<String, Double>();

				vitalSignCategoryPair.put("BP_SYSTOLIC", systolicPressureDbl);
				vitalSignCategoryPair.put("BP_DIASTOLIC", diastolicPressureDbl);
				vitalSignCategoryPair.put("HEARTRATE_RESTING", pulseDbl);
				vitalSignCategoryPair.put("SUGAR_HB1AC", sugarhb1acDbl);
				vitalSignCategoryPair.put("SUGAR_RANDOM", bloodSugarNormalDbl);
				vitalSignCategoryPair.put("SUGAR_FASTING_PLASMA_GLUCOSE", bloodSugarFastingDbl);
				vitalSignCategoryPair.put("RESPIRATION_RATE", respirationDbl);
				vitalSignCategoryPair.put("BODY_TEMPERATURE", temperatureDbl);
				vitalSignCategoryPair.put("BMI", _bmi);

				_vitalSignDTO.setVitalSignCategoryPair(vitalSignCategoryPair);

				// Find all the VitalSign entered and build VitalSignDTO
				// Using Height Weight Calc BMI
				// Call for all the Vitalsigns for the age and gender
				// Match with the one entered and present the output dashboard.

				VitalSignService _vitalService = new VitalSignService();
				// List<VitalSignCategoryValues> _vitalSignCatValues =
				// _vitalService
				// .getVitalSignValuesForVitals(_vitalSignDTO);

				// List<VitalSignUserOutPutDTO> _vsSIgnUserOutputDTO =
				// _vitalService
				// .getVitalSignValuesForVitals(_vitalSignDTO);

				Map<String, VitalSignUserOutPutDTO> _vsSIgnUserOutputDTOMap = _vitalService
						.getVitalSignValuesForVitals(_vitalSignDTO);

				Map<String, VitalSignUserOutPutDTO> _vsSIgnUserOutputDTOMapSorted = sortMapByKey(
						_vsSIgnUserOutputDTOMap);

				CareEmailObject _careObj = new CareEmailObject();
				_careObj.setAge(_age);
				_careObj.setGender(Integer.parseInt(_gender));
				_careObj.setUserObject(_vsSIgnUserOutputDTOMapSorted);

				// send email

				logger.info("CarePortal : sending Email to " + emailAddress);

				if (emailAddress != "-") {
					String _body = CareMailUtil.loadVelocityTemplate("emailCarePortalLongForm.vm", _careObj);
					CareMailDTO _mailDTO = new CareMailDTO();
					_mailDTO.setMailBody(_body);
					_mailDTO.setMailsubject("sevha : Healthmeter snapshot");
					_mailDTO.setRecepiantName("Hello");
					_mailDTO.setToAddress(emailAddress);
					CareMailUtil.sendSimpleMail(_mailDTO);
				}

				logger.info("CarePortal : email completed ");

				Calendar _currCal = Calendar.getInstance();
				SimpleDateFormat _sdf = new SimpleDateFormat("YYYY-MMM-d");
				_sdf.setCalendar(_currCal);

				requestScope("age", _age);
				requestScope("gender", _gender);
				requestScope("currDate", _sdf.format(_currCal.getTime()));				

				// requestScope("vitalSignCatValues", _vitalSignCatValues);
				// requestScope("vitalSignOutPutList", _vsSIgnUserOutputDTO);

				requestScope("vitalSignOutPutMap", _vsSIgnUserOutputDTOMapSorted);

			}
		}
		
		if(isAdvCalc)
		{
			advmeterFlag = "block";
		}
		
		logger.info("advmeterFlag exit --- >" + advmeterFlag);
		
		requestScope("advmeterFlag", advmeterFlag);
		requestScope("showErrBox ", _isFormValid);
		return forward(fwdPage);
	}

	private static Map<String, VitalSignUserOutPutDTO> sortMapByKey(Map<String, VitalSignUserOutPutDTO> aItems) {
		TreeMap<String, VitalSignUserOutPutDTO> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		result.putAll(aItems);
		return result;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validateCaredPortal(final Validators v)

	{

		String pulse = requestScope("pulse");
		String temperature = requestScope("temperature");
		logger.info("Temperature >> " + temperature);
		String systolicPressure = requestScope("systolicPressure");
		String diastolicPressure = requestScope("diastolicPressure");
		String bloodSugarNormal = requestScope("bloodSugarNormal");
		String bloodSugarFasting = requestScope("bloodSugarFasting");
		String sugarhb1ac = requestScope("sugarhb1ac");
		String respiration = requestScope("respiration");
		String email = requestScope("email");
		String eula = requestScope("EULA");
		String existingIllness = requestScope("existingIllness");
		boolean _isAdvCalc = false;
		
		logger.info("existingIllness >> " + existingIllness);

		VitalSignValidator _vsValidator = new VitalSignValidator();
		RegisteredEmailVaidator _rev = new RegisteredEmailVaidator();

		v.add("gender", v.required("Gender"), v.byteType(), v.regexp("^[1-9]", "Select a Gender"));

		v.add("dob", v.required("Date of Birth"), v.dateType("MM/dd/yyyy", "Invalid Date of Birth"));

		v.add("purpose", v.required("Purpose"), v.byteType(), v.regexp("^[1-9]", "Select a purpose"));

		v.add("height", v.required("Height"), v.doubleType(), _vsValidator);
		v.add("weight", v.required("Weight"), _vsValidator);

		v.add("temperature", v.required("Temperature"), v.doubleType("Temperature must be numeric"),
				v.doubleRange(96, 107, "Invalid Body Temperature Range"));

		if (pulse != null && !pulse.equals("")) {
			v.add("pulse", _vsValidator);
		}

		if (systolicPressure != null && !systolicPressure.equals("")) {
			_isAdvCalc = true;
			v.add("systolicPressure", v.integerType("High Blood Pressure : No decimal allowed"), _vsValidator);
			// Must have diastolic pressure too
			v.add("diastolicPressure", v.required("Low Blood Pressure"));
		}

		if (diastolicPressure != null && !diastolicPressure.equals("")) {
			_isAdvCalc = true;
			v.add("diastolicPressure", v.integerType("Low Blood Pressure : No decimal allowed"), _vsValidator);
			// Must have systolic pressure too
			v.add("systolicPressure", v.required("High Blood Pressure"));

		}

		if (bloodSugarNormal != null && !bloodSugarNormal.equals("")) {
			_isAdvCalc = true;
			v.add("bloodSugarNormal", v.integerType("Normal Blood Sugar : No decimal allowed"), _vsValidator);
		}

		if (bloodSugarFasting != null && !bloodSugarFasting.equals("")) {
			_isAdvCalc = true;
			v.add("bloodSugarFasting", v.integerType("Fasting Blood Sugar : No decimal allowed"), _vsValidator);
		}

		if (sugarhb1ac != null && !sugarhb1ac.equals("")) {
			_isAdvCalc = true;
			v.add("sugarhb1ac", v.doubleRange(3.9D, 14D, "hb1ac : Invalid value Range"));
		}

		if (respiration != null && !respiration.equals("")) {
			_isAdvCalc = true;
			v.add("respiration", v.longRange(10, 40, "Respiration : Invalid value Range"));
		}

		if (email != null && !diastolicPressure.equals("")) {
			_isAdvCalc = true;
			v.add("email", v.maxlength(50), v.minlength(6, "Email : Address too short"),
					v.regexp(emailRegex, "Email : Invalid email address"));
		}

		if (eula == null) {
			v.add("EULA", v.required("Please read and accept Terms of Use"));
		} else {
			eula = "checked";
		}
		
		if(_isAdvCalc)
		{
			isAdvCalc = true;
		}

		boolean valid = v.validate();
		requestScope("eulaStatus", eula);
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}

	/**
	 * 
	 */
	private void parseVitalValues() {

		weightDbl = Double.parseDouble(((String) requestScope("weight") != "") ? (String) requestScope("weight") : "0");

		heightDbl = Double.parseDouble(((String) requestScope("height") != "") ? (String) requestScope("height") : "0");

		// pulseDbl = Double.parseDouble((((String) requestScope("pulse")).equals("") |
		// (String) requestScope("pulse") == null) ? (String) requestScope("pulse") :
		// "0");
		if (((String) requestScope("pulse")).equals("")) {
			pulseDbl = new Double(0);
		} else {
			pulseDbl = new Double((String) requestScope("pulse"));
		}

		temperatureDbl = Double
				.parseDouble(((String) requestScope("temperature") != "") ? (String) requestScope("temperature") : "0");

//		systolicPressureDbl = Double.parseDouble(
//				((String) requestScope("systolicPressure") != "") ? (String) requestScope("systolicPressure") : "0");

		if (((String) requestScope("systolicPressure")).equals("")) {
			systolicPressureDbl = new Double(0);
		} else {
			systolicPressureDbl = new Double((String) requestScope("systolicPressure"));
		}

//		diastolicPressureDbl = Double.parseDouble(
//				((String) requestScope("diastolicPressure") != "") ? (String) requestScope("diastolicPressure") : "0");

		if (((String) requestScope("diastolicPressure")).equals("")) {
			diastolicPressureDbl = new Double(0);
		} else {
			diastolicPressureDbl = new Double((String) requestScope("diastolicPressure"));
		}

//		bloodSugarNormalDbl = Double.parseDouble(
//				((String) requestScope("bloodSugarNormal") != "") ? (String) requestScope("bloodSugarNormal") : "0");

		if (((String) requestScope("bloodSugarNormal")).equals("")) {
			bloodSugarNormalDbl = new Double(0);
		} else {
			bloodSugarNormalDbl = new Double((String) requestScope("bloodSugarNormal"));
		}

//		sugarhb1acDbl = Double
//				.parseDouble(((String) requestScope("sugarhb1ac") != "") ? (String) requestScope("sugarhb1ac") : "0");

		if (((String) requestScope("sugarhb1ac")).equals("")) {
			sugarhb1acDbl = new Double(0);
		} else {
			sugarhb1acDbl = new Double((String) requestScope("sugarhb1ac"));
		}

//		bloodSugarFastingDbl = Double.parseDouble(
//				((String) requestScope("bloodSugarFasting") != "") ? (String) requestScope("bloodSugarFasting") : "0");
		
		if (((String) requestScope("bloodSugarFasting")).equals("")) {
			bloodSugarFastingDbl = new Double(0);
		} else {
			bloodSugarFastingDbl = new Double((String) requestScope("bloodSugarFasting"));
		}

//		respirationDbl = Double
//				.parseDouble(((String) requestScope("respiration") != "") ? (String) requestScope("respiration") : "0");
		
		if (((String) requestScope("respiration")).equals("")) {
			respirationDbl = new Double(0);
		} else {
			respirationDbl = new Double((String) requestScope("respiration"));
		}

		existingIllnessDbl = Double.parseDouble(
				((String) requestScope("existingIllness") != "") ? (String) requestScope("existingIllness") : "0");
		
		
		if (((String) requestScope("existingIllness")).equals("")) {
			existingIllnessDbl = new Double(0);
		} else {
			existingIllnessDbl = new Double((String) requestScope("existingIllness"));
		}
		
		emailAddress = (String) requestScope("email") != "" ? (String) requestScope("email") : "-";
		
		if (((String) requestScope("email")).equals("")) {
			emailAddress = "-";
		} else {
			emailAddress = (String) requestScope("email");
		}
		
		

		inputData = new String();
		inputData = "(";
		inputData += "(" + "weight" + "," + weightDbl + ")";
		inputData += "(" + "height" + "," + heightDbl + ")";
		inputData += "(" + "pulse" + "," + pulseDbl + ")";
		inputData += "(" + "temprerature" + "," + temperatureDbl + ")";
		inputData += "(" + "systolic" + "," + systolicPressureDbl + ")";
		inputData += "(" + "disstolic" + "," + diastolicPressureDbl + ")";
		inputData += "(" + "sugarhb1ac" + "," + sugarhb1acDbl + ")";
		inputData += "(" + "sugarhb1ac" + "," + sugarhb1acDbl + ")";
		inputData += "(" + "bloodSugarFasting" + "," + bloodSugarFastingDbl + ")";
		inputData += "(" + "respiration" + "," + respirationDbl + ")";
		inputData += "(" + "existingIllness" + "," + existingIllnessDbl + ")";
		inputData += ")";
	}
}
