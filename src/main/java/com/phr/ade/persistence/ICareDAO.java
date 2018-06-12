package com.phr.ade.persistence;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.values.ICareBridgeConstants;

public interface ICareDAO extends ICareBridgeConstants, IEntityDAO
{
	
	/*******************************************
	 * Physicians Methods
	 *******************************************/
	/**
	 * 
	 * @param caredKey
	 * @return
	 */
	Collection<Physician> loadAllPhysiciansGivenPrescriptionForCared(
	        Key caredKey);
	
	/**
	 * 
	 * @param profilekey
	 * @return
	 * @throws Exception
	 */
	List<Physician> loadPhysiciansForProfile(Key profilekey) throws Exception;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Physician loadPhysician(Key key) throws Exception;
	
	/*******************************************
	 * CaredPerson Methods
	 *******************************************/
	
	/**
	 * 
	 * @param key
	 */
	CaredPerson loadCaredPerson(Key key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<CaredPerson> loadCaredPersonsForCareGiver(Key key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<CaredPerson> loadCaredPersonsForProfile(Key profilekey);
	
	/*******************************************
	 * Caregiver Methods
	 *******************************************/
	
	/**
	 * 
	 * @param key
	 */
	Caregiver loadCaregiver(Key key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<Caregiver> loadCaregiversForProfile(Key profilekey);
	
	/*******************************************
	 * PreExistingDiseases Methods
	 *******************************************/
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	CommonPreExistingDiseases loadPreExistingDisease(Key key);
	
	/**
	 * 
	 * @return
	 */
	List<CommonPreExistingDiseases> loadAllPreExistingDiseases();
	
	/**
	 * 
	 * @param caredPersonKey
	 * @return
	 */
	List<PreExistingCondition> loadAllPreExistingConditonsForCaredPerson(
	        Key caredPersonKey);
	
	int getPreExistingConditionForCaredPerson(Key preExistingDisease,
	        Key caredPersonKey);
	
	/**
	 * This only return a dummy disease to link the default symptom
	 * 
	 * @return
	 */
	CommonPreExistingDiseases loadDefaultCommonPreExistingDisease();
	
	List<PreExistingDiseaseCommonSymptom> getCommonSymptoms();
	
	/**
	 * Get the Symptoms for a pre-existing disease. If none found return the
	 * default symptoms
	 * 
	 * @param preExistingDisease
	 * @return
	 */
	List<PreExistingDiseaseCommonSymptom> getCommonSymptomsForPreExistingDisease(
	        Key preExistingDiseaseKey, boolean returnDefault);
	
	/*******************************************
	 * EmergencyResponse Methods
	 *******************************************/
	
	EmergencyResponse loadEmergemcyResponseForCaredPerson(Key caredPersonKey);
	
	PreExistingDiseaseCommonSymptom loadSymptomByKey(Key symptomkey);
	
	/*******************************************
	 * Vital Parameters Methods
	 *******************************************/
	
	List<VitalParameter> loadLastSevenVitalParameterByCaredPerson(
	        Key caredPersonKey);
	
	VitalParameter loadLatestVitalParameterByCaredPerson(Key caredPersonKey);
	
}
