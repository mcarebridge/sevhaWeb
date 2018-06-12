package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.Profile;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.persistence.CareDAO;
import com.phr.ade.persistence.ICareDAO;
import com.phr.ade.persistence.IPrescriptionDAO;
import com.phr.ade.util.ComponentFactory;

public class CareService implements ICareService
{
	
	/**
	 * Component Loader
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private CareDAO getCareDAO() throws ClassNotFoundException,
	        InstantiationException, IllegalAccessException
	{
		// ComponentFactory _factory = ComponentFactory.getInstance();
		// Object _obj =
		// _factory.getComponent("com.phr.ade.persistence.CareDAO");
		// System.out.println("------->" + _obj.getClass().getName());
		// CareDAO _careDAO = (CareDAO) _obj;
		// return _careDAO;
		return new CareDAO();
	}
	
	@Override
	public Collection<Physician> loadAllPhysiciansGivenPrescriptionForCared(
	        Key caredKey) throws Exception
	{
		IPrescriptionDAO _ipDAO = (IPrescriptionDAO) ComponentFactory
		        .getInstance().getComponent(
		                "com.phr.ade.persistence.PrescriptionDAO");
		
		Hashtable<Long, Physician> _phyHashtable = new Hashtable<Long, Physician>();
		
		List<Prescription> _prescriptions = _ipDAO
		        .loadPrescriptionsByCaredPerson(caredKey);
		
		for (Iterator iterator = _prescriptions.iterator(); iterator.hasNext();)
		{
			Prescription _prescription = (Prescription) iterator.next();
			Long _phyKey = new Long(_prescription.getPhysician().getKey()
			        .getId());
			_phyHashtable.put(_phyKey, _prescription.getPhysician().getModel());
		}
		
		return _phyHashtable.values();
	}
	
	@Override
	public List<Physician> loadPhysiciansForProfile(Key profilekey)
	        throws Exception
	{
		
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadPhysiciansForProfile(profilekey);
	}
	
	@Override
	public Key createPhysician(Physician physician) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Key _key = _careDAO.addModel(physician);
		Address _address = physician.getAddress().getModel();
		_careDAO.addModel(_address);
		return _key;
	}
	
	@Override
	public Key updatePhysician(Physician physician) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Key _key = _careDAO.addModel(physician);
		Address _address = physician.getAddress().getModel();
		_careDAO.addModel(_address);
		return _key;
	}
	
	@Override
	public Physician loadPhysician(Long key) throws Exception
	{
		Key _key = Datastore.createKey(Physician.class, key);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadPhysician(_key);
	}
	
	/*****************************************************
	 * Cared Person methods
	 ****************************************************/
	
	@Override
	public Key createCaredPerson(CaredPerson model) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Address _address = model.getAddress().getModel();
		List<PreExistingCondition> _preExistingCond = model
		        .getPreExistingCondition();
		Key _key = _careDAO.addModel(model);
		_careDAO.addModel(_address);
		
		for (Iterator iterator = _preExistingCond.iterator(); iterator
		        .hasNext();)
		{
			PreExistingCondition preExistingCondition = (PreExistingCondition) iterator
			        .next();
			preExistingCondition.getCaredPerson().setKey(_key);
			_careDAO.addModel(preExistingCondition);
		}
		
		return _key;
	}
	
	@Override
	public Key updateCaredPerson(CaredPerson model) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Address _address = model.getAddress().getModel();
		List<PreExistingCondition> _preExistingCond = model
		        .getPreExistingCondition();
		
		Key _key = _careDAO.addModel(model);
		_careDAO.addModel(_address);
		
		for (Iterator iterator = _preExistingCond.iterator(); iterator
		        .hasNext();)
		{
			PreExistingCondition preExistingCondition = (PreExistingCondition) iterator
			        .next();
			preExistingCondition.getCaredPerson().setKey(_key);
			_careDAO.addModel(preExistingCondition);
		}
		return _key;
	}
	
	@Override
	public CaredPerson loadCaredPerson(Long key) throws Exception
	{
		Key _key = Datastore.createKey(CaredPerson.class, key);
		ICareDAO _careDAO = getCareDAO();
		
		CaredPerson _caredPerson = _careDAO.loadCaredPerson(_key);
		List<PreExistingCondition> _preExistingCondList = _careDAO
		        .loadAllPreExistingConditonsForCaredPerson(_caredPerson
		                .getKey());
		_caredPerson.setPreExistingCondition(_preExistingCondList);
		
		return _caredPerson;
	}
	
	@Override
	public List<CaredPerson> loadCaredPersonsForCareGiver(Long caregiverKey)
	        throws Exception
	{
		Key _key = Datastore.createKey(Caregiver.class, caregiverKey);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadCaredPersonsForCareGiver(_key);
	}
	
	@Override
	public List<CaredPerson> loadCaredPersonsForProfile(Long profilekey)
	        throws Exception
	{
		Key _key = Datastore.createKey(Profile.class, profilekey);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadCaredPersonsForProfile(_key);
	}
	
	/*****************************************************
	 * Caregiver methods
	 ****************************************************/
	
	@Override
	public Key createCaregiver(Caregiver model) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Key _key = _careDAO.addModel(model);
		Address _address = model.getAddress().getModel();
		_careDAO.addModel(_address);
		return _key;
	}
	
	@Override
	public Key updateCaregiver(Caregiver model) throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Key _key = _careDAO.addModel(model);
		Address _address = model.getAddress().getModel();
		_careDAO.addModel(_address);
		return _key;
	}
	
	@Override
	public Caregiver loadCaregiver(Long key) throws Exception
	{
		Key _key = Datastore.createKey(Caregiver.class, key);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadCaregiver(_key);
	}
	
	@Override
	public List<Caregiver> loadCaregiversForProfile(Long profilekey)
	        throws Exception
	{
		Key _key = Datastore.createKey(Profile.class, profilekey);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadCaregiversForProfile(_key);
	}
	
	@Override
	public CommonPreExistingDiseases loadPreExistingDisease(Long key)
	        throws Exception
	{
		Key _key = Datastore.createKey(CommonPreExistingDiseases.class, key);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadPreExistingDisease(_key);
	}
	
	@Override
	public List<CommonPreExistingDiseases> loadAllPreExistingDiseases()
	        throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadAllPreExistingDiseases();
	}
	
	public boolean isPreExistingDiseaseForCarePerson(Long preExistingDisease,
	        Long caredPersonKey) throws Exception
	{
		boolean _matchFound = false;
		ICareDAO _careDAO = getCareDAO();
		Key _preExDiseasekey = Datastore.createKey(
		        CommonPreExistingDiseases.class, preExistingDisease);
		Key _caredPersonkey = Datastore.createKey(CaredPerson.class,
		        caredPersonKey);
		if (_careDAO.getPreExistingConditionForCaredPerson(_preExDiseasekey,
		        _caredPersonkey) > 0)
		{
			_matchFound = true;
		}
		
		return _matchFound;
	}
	
	@Override
	public EmergencyResponse loadEmergemcyResponseForCaredPerson(Long key)
	        throws Exception
	{
		Key _key = Datastore.createKey(CaredPerson.class, key);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadEmergemcyResponseForCaredPerson(_key);
	}
	
	@Override
	public PreExistingDiseaseCommonSymptom loadSymptomByKey(Long key)
	        throws Exception
	{
		Key _key = Datastore.createKey(PreExistingDiseaseCommonSymptom.class,
		        key);
		ICareDAO _careDAO = getCareDAO();
		return _careDAO.loadSymptomByKey(_key);
	}
	
	/**
	 * Add Vital Parameters
	 */
	@Override
	public Key addVitalParameters(VitalParameter vitalParameter)
	        throws Exception
	{
		ICareDAO iCareDAO = getCareDAO();
		return iCareDAO.addModel(vitalParameter);
	}
	
	/**
	 * List last ten Vital Parameters
	 */
	@Override
	public List<VitalParameter> listLastSevenVitalParameter(Long caredPersonkey)
	        throws Exception
	{
		ICareDAO iCareDAO = getCareDAO();
		Key _key = Datastore.createKey(CaredPerson.class, caredPersonkey);
		return iCareDAO.loadLastSevenVitalParameterByCaredPerson(_key);
	}
	
	/**
	 * Load Latest Vital Parameters
	 */
	@Override
	public VitalParameter listLatestVitalParameter(Long caredPersonkey)
	        throws Exception
	{
		ICareDAO iCareDAO = getCareDAO();
		Key _key = Datastore.createKey(CaredPerson.class, caredPersonkey);
		return iCareDAO.loadLatestVitalParameterByCaredPerson(_key);
	}
	
	public Key createEmergencyResponse(EmergencyResponse emergencyResponse)
	        throws Exception
	{
		ICareDAO _careDAO = getCareDAO();
		Key _key = _careDAO.addModel(emergencyResponse);
		_careDAO.addModel(emergencyResponse.getAddress().getModel());
		return _key;
	}
	
	@Override
	public Collection<EmergencyResponse> loadEmergemcyResponseForCaredPerson(
	        List keys) throws Exception
	{
		ArrayList<EmergencyResponse> _empergencyResponseList = new ArrayList<EmergencyResponse>();
		
		for (Iterator iterator = keys.iterator(); iterator.hasNext();)
		{
			Long _cpKey = (Long) iterator.next();
			EmergencyResponse _er = loadEmergemcyResponseForCaredPerson(_cpKey);
			_empergencyResponseList.add(_er);
		}
		
		return _empergencyResponseList;
	}
	
	/**
	 * Build a Hashtable if the CaredPerson has a emergenc Contact or Not
	 */
	@Override
	public Hashtable<CaredPerson, String> loadCaredPersonListByEmergencyContact(
	        Long profilekey) throws Exception
	{
		Hashtable<CaredPerson, String> _caredPersonStatusTable = new Hashtable<CaredPerson, String>();
		
		List<CaredPerson> _caredPersonList = loadCaredPersonsForProfile(profilekey);
		
		for (CaredPerson _caredPerson : _caredPersonList)
		{
			EmergencyResponse _er = loadEmergemcyResponseForCaredPerson(new Long(
			        _caredPerson.getKey().getId()));
			if (_er != null)
			{
				_caredPersonStatusTable.put(_caredPerson, "Added");
			} else
			{
				_caredPersonStatusTable.put(_caredPerson, "None");
			}
		}
		
		return _caredPersonStatusTable;
	}
}
