package com.phr.ade.service;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.values.ICareBridgeConstants;

public interface ICareService extends ICareBridgeConstants
{
	
	/**********************************************
	 * Physician Methods
	 **********************************************/
	Collection<Physician> loadAllPhysiciansGivenPrescriptionForCared(
	        Key caredKey) throws Exception;;
	
	Key createPhysician(Physician physician) throws Exception;
	
	Key updatePhysician(Physician physician) throws Exception;
	
	List<Physician> loadPhysiciansForProfile(Key profilekey) throws Exception;
	
	Physician loadPhysician(Long key) throws Exception;
	
	/**********************************************
	 * Cared Person Methods
	 * 
	 **********************************************/
	
	Key createCaredPerson(CaredPerson model) throws Exception;
	
	Key updateCaredPerson(CaredPerson model) throws Exception;
	
	CaredPerson loadCaredPerson(Long key) throws Exception;
	
	List<CaredPerson> loadCaredPersonsForCareGiver(Long caregiverKey)
	        throws Exception;
	
	List<CaredPerson> loadCaredPersonsForProfile(Long profilekey)
	        throws Exception;
	
	/**********************************************
	 * Caregiver Methods
	 **********************************************/
	
	Key createCaregiver(Caregiver model) throws Exception;
	
	Key updateCaregiver(Caregiver model) throws Exception;
	
	Caregiver loadCaregiver(Long key) throws Exception;
	
	List<Caregiver> loadCaregiversForProfile(Long profilekey) throws Exception;
	
	/**********************************************
	 * Critical Illness Methods
	 **********************************************/
	CommonPreExistingDiseases loadPreExistingDisease(Long key) throws Exception;
	
	List<CommonPreExistingDiseases> loadAllPreExistingDiseases()
	        throws Exception;
	
	boolean isPreExistingDiseaseForCarePerson(Long preExistingDisease,
	        Long caredPersonKey) throws Exception;
	
	/**********************************************
	 * EmergencyResponse Methods
	 **********************************************/
	
	EmergencyResponse loadEmergemcyResponseForCaredPerson(Long key)
	        throws Exception;
	
	Collection<EmergencyResponse> loadEmergemcyResponseForCaredPerson(List keys)
	        throws Exception;
	
	Hashtable<CaredPerson, String> loadCaredPersonListByEmergencyContact(
	        Long profilekey) throws Exception;
	
	Key createEmergencyResponse(EmergencyResponse emergencyResponse)
	        throws Exception;
	
	PreExistingDiseaseCommonSymptom loadSymptomByKey(Long key) throws Exception;
	
	/*********************************************
	 * Vital Parameter Methods
	 *********************************************/
	
	Key addVitalParameters(VitalParameter vitalParameter) throws Exception;
	
	List<VitalParameter> listLastSevenVitalParameter(Long caredPersonkey)
	        throws Exception;
	
	VitalParameter listLatestVitalParameter(Long caredPersonkey)
	        throws Exception;
	
}
