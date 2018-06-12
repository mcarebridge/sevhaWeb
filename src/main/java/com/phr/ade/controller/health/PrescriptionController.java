/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.DateUtil;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.Physician;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Profile;
import com.phr.ade.model.Substitute;
import com.phr.ade.service.CareService;
import com.phr.ade.service.PrescriptionService;

/**
 * @author DS5002449
 * 
 */
public class PrescriptionController extends BaseController
{
	
	private static Logger logger  = Logger.getLogger(PrescriptionController.class
	                                      .getName());
	private String        fwdPage = "load/prescription.jsp";
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String action = requestScope("actionParam");
		logger.log(Level.INFO, " Rx action : ", action);
		
		String _rxLineDisabled = requestScope("rxLineDisabled");
		Long _profileId = (Long) sessionScope("profileId");
		String _caregiverId = (String) requestScope("caregiver");
		String _physicianId = (String) requestScope("physician");
		
		// Long _selectedCaredId = (Long) sessionScope("selectedCaredId");
		
		Long _selectedCaredId = extractSelectedCareId();
		
		Date _dateofvisit = DateUtil.toDate(
		        (String) requestScope("dateofvisit"), "MM/dd/yyyy");
		
		String _key = requestScope("key");
		logger.log(Level.INFO, " Rx Key is : ", _key);
		
		pageTitle = "title.prescription";
		System.out.println("Page Title : " + pageTitle);
		
		if (action != null && action.equalsIgnoreCase("LOADPRES"))
		{
			PrescriptionService _psService = new PrescriptionService();
			List<Prescription> _prescriptions = _psService
			        .loadPrescriptionsByCaredPerson(new Long(_selectedCaredId));
			requestScope("prescriptionList", _prescriptions);
			fwdPage = "load/prescription.jsp";
		}
		if (action != null && action.equalsIgnoreCase("PRESDTLS"))
		{
			fwdPage = "load/prescriptionLines.jsp";
		} else if (action != null && action.equalsIgnoreCase("NEWPRES"))
		{
			paintPrescriptionPage(_profileId, _selectedCaredId);
			fwdPage = "create/prescription.jsp";
		} else if (action != null && action.equalsIgnoreCase("CREPRES"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validate(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "create/prescription.jsp";
				paintPrescriptionPage(_profileId, _selectedCaredId);
			} else
			{
				PrescriptionService _ps = new PrescriptionService();
				
				Prescription _prescription = populatePrescription(new Long(
				        _caregiverId), _selectedCaredId,
				        new Long(_physicianId), _dateofvisit);
				_ps.createPrescription(_prescription);
				paintPrescriptionPage(_profileId, _selectedCaredId);
				
				fwdPage = "load/prescription.jsp";
			}
			
		} else if (action != null && action.equalsIgnoreCase("LDUPDPRES"))
		{
			PrescriptionService _ps = new PrescriptionService();
			Prescription _p = _ps.loadPrescriptionById(new Long(_key));
			requestScope("physician", _p.getPhysician().getKey().getId());
			requestScope("caregiver", _p.getCareGiver().getKey().getId());
			requestScope("prescriptionName", _p.getPrescriptionTag());
			requestScope("medicalCondition", _p.getMedicalCondition());
			requestScope("key", _p.getKey().getId());
			paintPrescriptionPage(_profileId, _selectedCaredId);
			
			fwdPage = "update/prescription.jsp";
		} else if (action != null && action.equalsIgnoreCase("UPDPRES"))
		{
			Prescription _p = populatePrescription(new Long(_caregiverId),
			        _selectedCaredId, new Long(_physicianId), _dateofvisit);
			Key _prescriptionKey = Datastore.createKey(Prescription.class,
			        new Long(_key));
			_p.setKey(_prescriptionKey);
			PrescriptionService _ps = new PrescriptionService();
			_ps.updatePrescription(_p);
			
			List<Prescription> _prescriptions = _ps
			        .loadPrescriptionsByCaredPerson(_selectedCaredId);
			requestScope("prescriptionList", _prescriptions);
			fwdPage = "load/prescription.jsp";
		}
		
		else if (action != null && action.equalsIgnoreCase("PRESLINES"))
		{
			PrescriptionService _ps = new PrescriptionService();
			Prescription _prescription = _ps
			        .loadPrescriptionById(new Long(_key));
			
			List<PrescriptionLines> _psLinesUpd = buidlRxLinesView(_key);
			
			// List<PrescriptionLines> _psLines = _ps.loadPrescriptionLines(
			// new Long(_key), false);
			//
			// List<PrescriptionLines> _psLinesUpd = new
			// ArrayList<PrescriptionLines>();
			//
			// for (Iterator iterator = _psLines.iterator();
			// iterator.hasNext();)
			// {
			// PrescriptionLines prescriptionLines = (PrescriptionLines)
			// iterator
			// .next();
			//
			// Date _rxStartDate = prescriptionLines.getRxstartdate();
			//
			// Calendar _c = Calendar.getInstance();
			// _c.setTime(_rxStartDate);
			// _c.set(Calendar.HOUR, 0);
			// _c.set(Calendar.MINUTE, 0);
			// _c.set(Calendar.SECOND, 0);
			//
			// _c.add(Calendar.DATE, prescriptionLines.getDuration());
			// Date _expDate = _c.getTime();
			//
			// prescriptionLines.setRxEnddate(_expDate);
			//
			// Calendar _currDate = Calendar.getInstance();
			// _currDate.set(Calendar.HOUR, 0);
			// _currDate.set(Calendar.MINUTE, 0);
			// _currDate.set(Calendar.SECOND, 0);
			//
			// if (_expDate.before(_currDate.getTime()))
			// {
			// prescriptionLines.setRxExpired(true);
			// }
			//
			// _psLinesUpd.add(prescriptionLines);
			//
			// }
			
			requestScope("prescription", _prescription);
			requestScope("prescriptionLinesList", _psLinesUpd);
			
			fwdPage = "load/prescriptionLines.jsp";
		} else if (action != null && action.equalsIgnoreCase("CRELINES"))
		{
			Validators v = new Validators(request);
			boolean _isFormValid = validatePrescriptionLines(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "create/prescriptionLines.jsp";
			} else
			{
				PrescriptionLines _psLines = populatePrescriptionLines(new Long(
				        _key));
				PrescriptionService _ps = new PrescriptionService();
				_ps.createPrescriptionLines(_psLines);
				
				String _rxcreatetype = requestScope("rxcreatetype");
				
				if(_rxcreatetype.equalsIgnoreCase("REORDER"))
				{
					logger.log(Level.INFO,"<-- Invoke Reorder Block -->");
					String _rxLineKey = requestScope("rxlinekey"); 
					_ps.markPrescriptionLineReordered(new Long(_rxLineKey));
				}
				
				
				Prescription _prescription = _ps.loadPrescriptionById(new Long(
				        _key));
				
				// List<PrescriptionLines> _psLinesList = _ps
				// .loadPrescriptionLines(new Long(_key), false);
				
				List<PrescriptionLines> _psLinesUpd = buidlRxLinesView(_key);
				
				requestScope("prescription", _prescription);
				requestScope("prescriptionLinesList", _psLinesUpd);
				
				fwdPage = "load/prescriptionLines.jsp";
			}
			
		} else if (action != null && action.equalsIgnoreCase("LDCRELINES"))
		{
			PrescriptionService _ps = new PrescriptionService();
			Prescription _prescription = _ps
			        .loadPrescriptionById(new Long(_key));
			requestScope("prescription", _prescription);
			requestScope("rxcreatetype", "NEWORDER");
			fwdPage = "create/prescriptionLines.jsp";
		} else if (action != null && action.equalsIgnoreCase("REORDLINES"))
		{
			// PrescriptionService _ps = new PrescriptionService();
			// Prescription _prescription = _ps
			// .loadPrescriptionById(new Long(_key));
			// requestScope("prescription", _prescription);
			reorderRxLines(new Long(_key));
			fwdPage = "create/prescriptionLines.jsp";
		} else if (action != null && action.equalsIgnoreCase("EDTLINES"))
		{
			// PrescriptionService _ps = new PrescriptionService();
			// PrescriptionLines _pl = _ps.loadPrescriptionLine(new Long(_key));
			// requestScope("rxstartdate",
			// DateUtil.toString(_pl.getRxstartdate(), "MMM/dd/yyyy"));
			// requestScope("drugName", _pl.getDrugName());
			// requestScope("strength", _pl.getStrength());
			// requestScope("unit", _pl.getUnit());
			// requestScope("route", _pl.getRoute());
			// requestScope("frequency", _pl.getFrequency());
			// // requestScope("howmuch", _pl.getHowMuch());
			// requestScope("duration", _pl.getDuration());
			// requestScope("prescriptionKey", _pl.getPrescription().getKey()
			// .getId());
			// requestScope("prescriptionTag", _pl.getPrescription().getModel()
			// .getPrescriptionTag());
			editRxLines(new Long(_key));
			fwdPage = "update/prescriptionLines.jsp";
		} else if (action != null && action.equalsIgnoreCase("UPDLINES"))
		{
			
			Validators v = new Validators(request);
			boolean _isFormValid = validatePrescriptionLines(v);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "update/prescriptionLines.jsp";
			} else
			{
				PrescriptionLines _plines = populatePrescriptionLinesByLineId(new Long(
				        _key));
				PrescriptionService _ps = new PrescriptionService();
				_ps.updatePrescriptionLines(_plines);
				
				// List<PrescriptionLines> _psLinesList = _ps
				// .loadPrescriptionLines(new Long(_plines
				// .getPrescription().getKey().getId()), false);
				//
				// List<PrescriptionLines> _psLinesUpd = new
				// ArrayList<PrescriptionLines>();
				//
				// for (Iterator iterator = _psLinesList.iterator(); iterator
				// .hasNext();)
				// {
				// PrescriptionLines prescriptionLines = (PrescriptionLines)
				// iterator
				// .next();
				//
				// Date _rxStartDate = prescriptionLines.getRxstartdate();
				//
				// Calendar _c = Calendar.getInstance();
				// _c.setTime(_rxStartDate);
				// _c.set(Calendar.HOUR, 0);
				// _c.set(Calendar.MINUTE, 0);
				// _c.set(Calendar.SECOND, 0);
				//
				// _c.add(Calendar.DATE, prescriptionLines.getDuration());
				// Date _expDate = _c.getTime();
				//
				// prescriptionLines.setRxEnddate(_expDate);
				//
				// Calendar _currDate = Calendar.getInstance();
				// _currDate.set(Calendar.HOUR, 0);
				// _currDate.set(Calendar.MINUTE, 0);
				// _currDate.set(Calendar.SECOND, 0);
				//
				// if (_expDate.before(_currDate.getTime()))
				// {
				// prescriptionLines.setRxExpired(true);
				// }
				//
				// _psLinesUpd.add(prescriptionLines);
				//
				// }
				
				String _prescriptionKey = new Long(_plines.getPrescription().getKey().getId()).toString();
				
				List<PrescriptionLines> _psLinesUpd = buidlRxLinesView(_prescriptionKey);
				
				requestScope("prescription", _plines.getPrescription()
				        .getModel());
				requestScope("prescriptionLinesList", _psLinesUpd);
				
				fwdPage = "load/prescriptionLines.jsp";
			}
			
		} else if (action != null && action.equalsIgnoreCase("LOADSUBS"))
		{
			
			PrescriptionService _ps = new PrescriptionService();
			PrescriptionLines _plines = _ps
			        .loadPrescriptionLine(new Long(_key));
			Substitute _subs = _ps
			        .loadCurrentSubstituteForPrescriptionLine(new Long(_key));
			
			List<Substitute> _subList = _ps
			        .listSubstitutesForPrescriptionLine(new Long(_key));
			
			if (_subs != null)
			{
				requestScope("substitutedrug", _subs.getSubstitueDrug());
				requestScope("strength", _subs.getStrength());
				requestScope("suggestedBy", _subs.getSubstitutionSuggestedBy());
				requestScope("subsreason", _subs.getReasonForSubstitute());
				requestScope("key", _subs.getPrescriptionLines().getKey()
				        .getId());
			}
			requestScope("prescriptionLines", _plines);
			fwdPage = "create/substitute.jsp";
		} else if (action != null && action.equalsIgnoreCase("DASHBOARD"))
		{
			paintDashboard();
			fwdPage = "load/careDashboard.jsp";
		}
		
		return forward(fwdPage);
	}
	
	/**
	 * 
	 */
	private void editRxLines(Long key) throws Exception
	{
		PrescriptionService _ps = new PrescriptionService();
		PrescriptionLines _pl = _ps.loadPrescriptionLine(key);
		
		Calendar _c = Calendar.getInstance();
		
		requestScope("rxstartdate",
		        DateUtil.toString(_c.getTime(), "MM/dd/yyyy"));
		requestScope("drugName", _pl.getDrugName());
		requestScope("strength", _pl.getStrength());
		requestScope("unit", _pl.getUnit());
		requestScope("route", _pl.getRoute());
		requestScope("frequency", _pl.getFrequency());
		requestScope("mealoption", _pl.getMealOption());
		// requestScope("howmuch", _pl.getHowMuch());
		requestScope("duration", _pl.getDuration());
		requestScope("prescriptionKey", _pl.getPrescription().getKey().getId());
		requestScope("prescriptionTag", _pl.getPrescription().getModel()
		        .getPrescriptionTag());
	}
	
	private void reorderRxLines(Long key) throws Exception
	{
		PrescriptionService _ps = new PrescriptionService();
		PrescriptionLines _pl = _ps.loadPrescriptionLine(key);
		
		Calendar _c = Calendar.getInstance();
		
		requestScope("rxstartdate",
		        DateUtil.toString(_c.getTime(), "MM/dd/yyyy"));
		requestScope("drugName", _pl.getDrugName());
		requestScope("strength", _pl.getStrength());
		requestScope("unit", _pl.getUnit());
		requestScope("route", _pl.getRoute());
		requestScope("frequency", _pl.getFrequency());
		// requestScope("howmuch", _pl.getHowMuch());
		requestScope("duration", _pl.getDuration());
		requestScope("key", _pl.getPrescription().getKey().getId());
		requestScope("rxlinekey", _pl.getKey().getId());
		requestScope("prescriptionTag", _pl.getPrescription().getModel()
		        .getPrescriptionTag());
		requestScope("rxcreatetype", "REORDER");
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v)
	{
		
		v.add("physician", v.required("Physician"),
		        v.longType("Select a Physician"));
		v.add("caregiver", v.required("Caregiver"),
		        v.longType("Select a Care Giver"));
		
		v.add("prescriptionName", v.required("Prescription Tag"),v.maxlength(50), v.minlength(5));
		v.add("medicalCondition", v.required("Medical Condition"),v.maxlength(100), v.minlength(5));
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validatePrescriptionLines(final Validators v)
	{
		
		v.add("rxstartdate", v.required("Rx Start Date"));
		v.add("drugName", v.required("Drug Name"), v.maxlength(30));
		v.add("strength", v.required("Strength"), v.maxlength(3),
		        v.integerType("Strength:Please enter a number"));
		v.add("unit", v.regexp("^((?!-1).)*$", "Select a Unit"));
		v.add("route", v.regexp("^((?!-1).)*$", "Select a Route"));
		v.add("frequency", v.regexp("^((?!-1).)*$", "Select a Frequency of Dosage"));
		v.add("mealoption", v.regexp("^((?!-1).)*$", "Select Meal Option"));
		// v.add("howmuch", v.required("How Much"));
		v.add("duration", v.required("Duration"), v.maxlength(3),
		        v.integerType("Duration is a number"));
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validateLoadPrescriptionLines(final Validators v)
	{
		
		v.add("prescription", v.required("Prescription"), v.byteType(),
		        v.regexp("^[1-9]", "Select a Prescription"));
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	/**
     * 
     */
	private void paintPrescriptionPage(Long profileId, Long _selectedCaredId)
	        throws Exception
	{
		CareService _cs = new CareService();
		PrescriptionService _psService = new PrescriptionService();
		Key _profilekey = Datastore.createKey(Profile.class, profileId);
		List<Physician> _physicians = _cs.loadPhysiciansForProfile(_profilekey);
		
		List<Caregiver> _careGivers = _cs.loadCaregiversForProfile(profileId);
		List<Prescription> _prescriptions = _psService
		        .loadPrescriptionsByCaredPerson(_selectedCaredId);
		requestScope("prescriptionList", _prescriptions);
		
		requestScope("physicians", _physicians);
		requestScope("careGivers", _careGivers);
	}
	
	/**
	 * 
	 * @param _careGiverId
	 * @param _caredPersonId
	 * @param _physicianId
	 * @return
	 * @throws Exception
	 */
	private Prescription populatePrescription(Long _careGiverId,
	        Long _caredPersonId, Long _physicianId, Date rxStartDate)
	        throws Exception
	{
		Prescription _p = new Prescription();
		CareService _cs = new CareService();
		
		CaredPerson _cp = _cs.loadCaredPerson(_caredPersonId);
		Caregiver _cg = _cs.loadCaregiver(_careGiverId);
		Physician _phy = _cs.loadPhysician(_physicianId);
		
		_p.getCaredPerson().setModel(_cp);
		_p.getCareGiver().setModel(_cg);
		_p.getPhysician().setModel(_phy);
		_p.setPrescriptionTag((String) requestScope("prescriptionName"));
		_p.setMedicalCondition((String) requestScope("medicalCondition"));
		_p.setRxStartDate(rxStartDate);
		return _p;
	}
	
	/**
	 * 
	 * @param _prescriptionKey
	 * @return
	 * @throws Exception
	 */
	private PrescriptionLines populatePrescriptionLines(Long _prescriptionKey)
	        throws Exception
	{
		PrescriptionLines _pl = new PrescriptionLines();
		
		PrescriptionService _ps = new PrescriptionService();
		Prescription _prescription = _ps.loadPrescriptionById(_prescriptionKey);
		
		System.out.println("_prescription Obj ------->"
		        + _prescription.getKey().getId());
		
		// Key _rxKey = Datastore.createKey(Prescription.class,
		// _prescriptionKey);
		_pl.getPrescription().setModel(_prescription);
		
		_pl.setDrugName((String) requestScope("drugName"));
		Date _rxstartdate = DateUtil.toDate(
		        (String) requestScope("rxstartdate"), "MM/dd/yyyy");
		_pl.setRxstartdate(_rxstartdate);
		_pl.setStrength((String) requestScope("strength"));
		_pl.setUnit((String) requestScope("unit"));
		_pl.setRoute((String) requestScope("route"));
		_pl.setFrequency((String) requestScope("frequency"));
		_pl.setMealOption((String) requestScope("mealoption"));
		_pl.setReordered(false);
		// _pl.setHowMuch((String) requestScope("howmuch"));
		_pl.setDuration(new Integer((String) requestScope("duration")));
		return _pl;
		
	}
	
	/**
	 * 
	 * @param _prescriptionKey
	 * @return
	 * @throws Exception
	 */
	private PrescriptionLines populatePrescriptionLinesByLineId(
	        Long _prescriptionLineId) throws Exception
	{
		System.out.println("-----> Strength"
		        + (String) requestScope("strength"));
		System.out.println("-----> unit " + (String) requestScope("unit"));
		
		String _rxLineDisabled = requestScope("rxLineDisabled");
		PrescriptionService _ps = new PrescriptionService();
		PrescriptionLines _pl = _ps.loadPrescriptionLine(_prescriptionLineId);
		
		_pl.setDrugName((String) requestScope("drugName"));
		_pl.setStrength((String) requestScope("strength"));
		_pl.setUnit((String) requestScope("unit"));
		_pl.setRoute((String) requestScope("route"));
		_pl.setFrequency((String) requestScope("frequency"));
		_pl.setMealOption((String) requestScope("mealoption"));
		// _pl.setHowMuch((String) requestScope("howmuch"));
		_pl.setDuration(new Integer((String) requestScope("duration")));
		if (_rxLineDisabled != null)
		{
			if (_rxLineDisabled.equalsIgnoreCase("true"))
			{
				_pl.setStopRxLine(true);
			}
		}
		
		return _pl;
		
	}
	
	/**
	 * 
	 * @param _prescriptionLineId
	 * @return
	 * @throws Exception
	 */
	private Substitute populateSubstitute(Long _prescriptionLineId)
	        throws Exception
	{
		PrescriptionService _ps = new PrescriptionService();
		Substitute _subs = new Substitute();
		PrescriptionLines _plines = _ps
		        .loadPrescriptionLine(_prescriptionLineId);
		
		_subs.getPrescriptionLines().setModel(_plines);
		_subs.setSubstitueDrug((String) requestScope("substitutedrug"));
		_subs.setStrength((String) requestScope("strength"));
		_subs.setSubstitutionSuggestedBy((String) requestScope("suggestedBy"));
		_subs.setReasonForSubstitute((String) requestScope("subsreason"));
		return _subs;
	}
	
	/**
	 * 
	 * @param prescriptionKey
	 * @return
	 */
	private List<PrescriptionLines> buidlRxLinesView(String prescriptionKey)
	        throws Exception
	{
		PrescriptionService _ps = new PrescriptionService();
		Prescription _prescription = _ps.loadPrescriptionById(new Long(
		        prescriptionKey));
		
		List<PrescriptionLines> _psLines = _ps.loadPrescriptionLines(new Long(
		        prescriptionKey), false);
		
		List<PrescriptionLines> _psLinesUpd = new ArrayList<PrescriptionLines>();
		
		for (Iterator iterator = _psLines.iterator(); iterator.hasNext();)
		{
			PrescriptionLines prescriptionLines = (PrescriptionLines) iterator
			        .next();
			
			Date _rxStartDate = prescriptionLines.getRxstartdate();
			
			Calendar _c = Calendar.getInstance();
			_c.setTime(_rxStartDate);
			_c.set(Calendar.HOUR, 0);
			_c.set(Calendar.MINUTE, 0);
			_c.set(Calendar.SECOND, 0);
			
			_c.add(Calendar.DATE, prescriptionLines.getDuration());
			Date _expDate = _c.getTime();
			
			prescriptionLines.setRxEnddate(_expDate);
			
			Calendar _currDate = Calendar.getInstance();
			_currDate.set(Calendar.HOUR, 0);
			_currDate.set(Calendar.MINUTE, 0);
			_currDate.set(Calendar.SECOND, 0);
			
			if (_expDate.before(_currDate.getTime()))
			{
				prescriptionLines.setRxExpired(true);
			}
			
			_psLinesUpd.add(prescriptionLines);
			
		}
		
		return _psLinesUpd;
	}
	
}
