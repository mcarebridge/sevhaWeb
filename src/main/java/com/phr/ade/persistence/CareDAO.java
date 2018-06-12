package com.phr.ade.persistence;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.phr.ade.meta.CaredPersonMeta;
import com.phr.ade.meta.CaregiverMeta;
import com.phr.ade.meta.CommonPreExistingDiseasesMeta;
import com.phr.ade.meta.EmergencyResponseMeta;
import com.phr.ade.meta.PhysicianMeta;
import com.phr.ade.meta.PreExistingConditionMeta;
import com.phr.ade.meta.PreExistingDiseaseCommonSymptomMeta;
import com.phr.ade.meta.PrescriptionMeta;
import com.phr.ade.meta.VitalParameterMeta;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.VitalParameter;

public class CareDAO extends AbstractEntityDAO implements ICareDAO
{
	
	private static Logger logger = Logger.getLogger(CareDAO.class.getName());
	
	/*******************************************
	 * Physicians Methods
	 *******************************************/
	
	/**
	 * Find the Physician attached to a CaredPerson
	 */
	public Collection<Physician> loadAllPhysiciansGivenPrescriptionForCared(
	        Key caredKey)
	{
		PrescriptionMeta _presMeta = new PrescriptionMeta();
		List<Prescription> _prescriptions = Datastore.query(_presMeta)
		        .filter(_presMeta.caredPerson.equal(caredKey)).asList();
		
		Hashtable<Long, Physician> _phyHashtable = new Hashtable<Long, Physician>();
		
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
		PhysicianMeta _phyMeta = new PhysicianMeta();
		List<Physician> _physicianList = Datastore.query(_phyMeta)
		        .filter(_phyMeta.profile.equal(profilekey)).asList();
		return _physicianList;
	}
	
	/**
     * 
     */
	public Physician loadPhysician(Key key) throws Exception
	
	{
		PhysicianMeta _phyMeta = new PhysicianMeta();
		Physician _physician = Datastore.query(_phyMeta)
		        .filter(_phyMeta.key.equal(key)).asSingle();
		return _physician;
	}
	
	/*******************************************
	 * CaredPerson Methods
	 *******************************************/
	
	@Override
	public CaredPerson loadCaredPerson(Key key)
	{
		CaredPersonMeta _cpMeta = new CaredPersonMeta();
		CaredPerson _caredPerson = Datastore.query(_cpMeta)
		        .filter(_cpMeta.key.equal(key)).asSingle();
		return _caredPerson;
	}
	
	@Override
	public List<CaredPerson> loadCaredPersonsForCareGiver(Key caregiverkey)
	{
		CaredPersonMeta _cpMeta = new CaredPersonMeta();
		List<CaredPerson> _caredPersonList = Datastore.query(_cpMeta)
		        .filter(_cpMeta.caregiver.equal(caregiverkey)).asList();
		return _caredPersonList;
	}
	
	@Override
	public List<CaredPerson> loadCaredPersonsForProfile(Key profilekey)
	{
		CaredPersonMeta _cpMeta = new CaredPersonMeta();
		List<CaredPerson> _caredPersonList = Datastore.query(_cpMeta)
		        .filter(_cpMeta.profile.equal(profilekey)).asList();
		return _caredPersonList;
	}
	
	/*******************************************
	 * Caregiver Methods
	 *******************************************/
	
	@Override
	public Caregiver loadCaregiver(Key key)
	{
		CaregiverMeta _cgMeta = new CaregiverMeta();
		Caregiver _careGiver = Datastore.query(_cgMeta)
		        .filter(_cgMeta.key.equal(key)).asSingle();
		return _careGiver;
	}
	
	/**
     * 
     */
	@Override
	public List<Caregiver> loadCaregiversForProfile(Key profilekey)
	{
		CaregiverMeta _cgMeta = new CaregiverMeta();
		List<Caregiver> _careGiverList = Datastore.query(_cgMeta)
		        .filter(_cgMeta.profile.equal(profilekey)).asList();
		return _careGiverList;
	}
	
	/*******************************************
	 * PreExistingDiseases Methods
	 *******************************************/
	
	@Override
	public CommonPreExistingDiseases loadPreExistingDisease(Key key)
	{
		CommonPreExistingDiseasesMeta _preExistingMeta = new CommonPreExistingDiseasesMeta();
		CommonPreExistingDiseases _preExisting = Datastore
		        .query(_preExistingMeta)
		        .filter(_preExistingMeta.key.equal(key)).asSingle();
		return _preExisting;
	}
	
	@Override
	public List<CommonPreExistingDiseases> loadAllPreExistingDiseases()
	{
		CommonPreExistingDiseasesMeta _preExistingMeta = new CommonPreExistingDiseasesMeta();
		return Datastore.query(_preExistingMeta).asList();
	}
	
	@Override
	public List<PreExistingCondition> loadAllPreExistingConditonsForCaredPerson(
	        Key caredPersonKey)
	{
		PreExistingConditionMeta _preExistingConditionsMeta = new PreExistingConditionMeta();
		List<PreExistingCondition> _preExistingCondList = Datastore
		        .query(_preExistingConditionsMeta)
		        .filter(_preExistingConditionsMeta.caredPerson
		                .equal(caredPersonKey)).asList();
		
		return _preExistingCondList;
	}
	
	@Override
	public CommonPreExistingDiseases loadDefaultCommonPreExistingDisease()
	{
		CommonPreExistingDiseasesMeta _commonPreExistingDiseaseMeta = new CommonPreExistingDiseasesMeta();
		
		CommonPreExistingDiseases _commonPreExistingDisease = Datastore
		        .query(_commonPreExistingDiseaseMeta)
		        .filter(_commonPreExistingDiseaseMeta.diseaseCode.equal("DFLT"))
		        .asSingle();
		
		return _commonPreExistingDisease;
	}
	
	/**
     * 
     */
	public List<PreExistingDiseaseCommonSymptom> getCommonSymptoms()
	{
		CommonPreExistingDiseases _commonPreExistingDiseases = loadDefaultCommonPreExistingDisease();
		
		PreExistingDiseaseCommonSymptomMeta _commonSymptomMeta = new PreExistingDiseaseCommonSymptomMeta();
		
		List<PreExistingDiseaseCommonSymptom> _commonSymptomList = Datastore
		        .query(_commonSymptomMeta)
		        .filter(_commonSymptomMeta.preExistingDisease
		                .equal(_commonPreExistingDiseases.getKey())).asList();
		
		return _commonSymptomList;
		
	}
	
	/**
	 * Get the Symptoms for a pre-existing disease. If none found return the
	 * default symptoms
	 * 
	 * @param preExistingDisease
	 * @return
	 */
	public List<PreExistingDiseaseCommonSymptom> getCommonSymptomsForPreExistingDisease(
	        Key preExistingDiseaseKey, boolean returnDefault)
	{
		PreExistingDiseaseCommonSymptomMeta _commonSymptomMeta = new PreExistingDiseaseCommonSymptomMeta();
		
		List<PreExistingDiseaseCommonSymptom> _commonSymptomList = Datastore
		        .query(_commonSymptomMeta)
		        .filter(_commonSymptomMeta.preExistingDisease
		                .equal(preExistingDiseaseKey)).asList();
		
		if (_commonSymptomList == null & returnDefault == true)
		{
			_commonSymptomList = getCommonSymptoms();
			
		}
		
		return _commonSymptomList;
	}
	
	/**
	 * Load PreExistingCondition for a CaredPerson by PreExistingDisease
	 */
	public int getPreExistingConditionForCaredPerson(Key preExistingDisease,
	        Key caredPersonKey)
	{
		PreExistingConditionMeta _preExistingConditionsMeta = new PreExistingConditionMeta();
		int _preExistinDisCount = Datastore
		        .query(_preExistingConditionsMeta)
		        .filter(_preExistingConditionsMeta.preExisitingDiseases
		                .equal(preExistingDisease),
		                _preExistingConditionsMeta.caredPerson
		                        .equal(caredPersonKey)).count();
		
		logger.log(Level.INFO, "PreExistingDisease found for  = "
		        + preExistingDisease.getId() + " = " + _preExistinDisCount);
		
		return _preExistinDisCount;
	}
	
	/*******************************************
	 * EmergencyResponse Methods
	 *******************************************/
	
	@Override
	public EmergencyResponse loadEmergemcyResponseForCaredPerson(
	        Key caredPersonKey)
	{
		EmergencyResponseMeta _emResponseMeta = new EmergencyResponseMeta();
		EmergencyResponse _emResponse = Datastore.query(_emResponseMeta)
		        .filter(_emResponseMeta.caredPerson.equal(caredPersonKey))
		        .asSingle();
		return _emResponse;
	}
	
	/**
	 * 
	 */
	public PreExistingDiseaseCommonSymptom loadSymptomByKey(Key symptomkey)
	{
		PreExistingDiseaseCommonSymptomMeta _symptomMeta = new PreExistingDiseaseCommonSymptomMeta();
		PreExistingDiseaseCommonSymptom _symptom = Datastore
		        .query(_symptomMeta).filter(_symptomMeta.key.equal(symptomkey))
		        .asSingle();
		return _symptom;
	}
	
	/**
	 * Vital Parameters Method
	 */
	
	@Override
	public List<VitalParameter> loadLastSevenVitalParameterByCaredPerson(
	        Key caredPersonKey)
	{
		VitalParameterMeta _vpm = new VitalParameterMeta();
		
		List<VitalParameter> _vitalParameterList = Datastore.query(_vpm)
		        .filter(_vpm.caredPerson.equal(caredPersonKey)).limit(7)
		        .sort("createdDate", SortDirection.DESCENDING).asList();
		
		return _vitalParameterList;
	}
	
	/**
	 * 
	 */
	@Override
	public VitalParameter loadLatestVitalParameterByCaredPerson(
	        Key caredPersonKey)
	{
		VitalParameter _vPara = null;
		
		VitalParameterMeta _vpm = new VitalParameterMeta();
		
		List<VitalParameter> _vitalParameterList = Datastore.query(_vpm)
		        .filter(_vpm.caredPerson.equal(caredPersonKey)).limit(1)
		        .sort("createdDate", SortDirection.DESCENDING).asList();
		
		if (_vitalParameterList != null & _vitalParameterList.size() != 0)
		{
			_vPara = _vitalParameterList.get(0);
		}
		return _vPara;
	}
}
