package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.slim3.util.DateUtil;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.Profile;
import com.phr.ade.persistence.CareDAO;
import com.phr.ade.persistence.ProfileDAO;
import com.phr.ade.values.ICareBridgeConstants;

public class ProfileService implements IProfileService, ICareBridgeConstants {

	private ProfileDAO getProfileDAO() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return new ProfileDAO();
	}

	@Override
	public Profile createProfile(Profile profile) throws Exception {
		// First look if the user name is taken
		if (searchProfileByEmail(profile) == null) {
			ProfileDAO _pdao = getProfileDAO();
			Key _key = _pdao.addModel(profile);
			profile.setKey(_key);

			// Depending upon the Purpose - add the user as
			// CareGiver, Cared or both

			enactOnPurpose(profile);

		} else {
			profile = null;
		}
		return profile;
	}

	/**
	 * Based on the purpose add record for CareGiver and Cared with minimum data
	 * 
	 * @param profile
	 * @throws Exception
	 */
	private void enactOnPurpose(Profile profile) throws Exception {

		Address _address = populateAddress(profile);
		ICareService _icareService = new CareService();

		if (profile.getPurpose().equals(SELF_CARE)) {
			Caregiver _cGiver = populateCaregiver(profile, _address);
			CaredPerson _cCaredPerson = populateCaredPerson(profile, _address);
			_icareService.createCaregiver(_cGiver);
			_icareService.createCaredPerson(_cCaredPerson);
		} else if (profile.getPurpose().equals(CARING_FOR_SOMEONE)) {
			Caregiver _cGiver = populateCaregiver(profile, _address);
			_icareService.createCaregiver(_cGiver);
		}

	}

	/**
	 * Build CareGiver from Profile
	 * 
	 * @return
	 */
	private Caregiver populateCaregiver(Profile profile, Address address) {
		Caregiver _careGiver = new Caregiver();

		_careGiver.setFirstName(profile.getFirstname());
		_careGiver.setLastName((profile.getLastname()));
		_careGiver.setGender(profile.getGender());
		_careGiver.setDob(profile.getDateofbirth());

		_careGiver.getAddress().setModel(address);
		_careGiver.getProfile().setModel(profile);
		return _careGiver;
	}

	/**
	 * Build CaredPerson from Profile
	 * 
	 * @param profile
	 * @return
	 */
	private CaredPerson populateCaredPerson(Profile profile, Address address) {
		CaredPerson _caredPerson = new CaredPerson();
		Address _address = new Address();
		_caredPerson.setFirstName(profile.getFirstname());
		_caredPerson.setLastName(profile.getLastname());
		_caredPerson.setGender(profile.getGender());
		_caredPerson.setDob(profile.getDateofbirth());
		_caredPerson.getProfile().setModel(profile);
		_caredPerson.getAddress().setModel(address);
		_caredPerson.setPreExistingCondition(profile.getPreExistingConditions());

		return _caredPerson;
	}

	/**
	 * Build address object
	 * 
	 * @param profile
	 * @return
	 */
	private Address populateAddress(Profile profile) {
		Address _address = new Address();
		_address.setCity(profile.getCity());
		_address.setCountry(profile.getCountry());
		_address.setEmail(profile.getEmail());

		return _address;

	}

	/**
	 * 
	 */
	@Override
	public Profile updateProfile(Profile profile) throws Exception {
		Profile _profile = searchProfileByEmail(profile);

		// Set new Password
		_profile.setPassword(profile.getPassword());

		// TODO Auto-generated method stub
		ProfileDAO _pdao = getProfileDAO();
		Key _key = _pdao.addModel(_profile);
		return _profile;

	}

	@Override
	public Profile searchProfileByEmail(Profile profile) throws Exception {
		ProfileDAO _pdao = getProfileDAO();

		return _pdao.searchProfileByEmail(profile);
	}

	@Override
	public Profile searchProfileByKey(Key key) throws Exception {

		return null;
	}

	@Override
	public List<CaredPerson> authenticateCaredPersonbyIMEI(Long imeiNumber) throws Exception {
		ProfileDAO _pdao = getProfileDAO();
		return _pdao.authenticateCaredPersonbyIMEI(imeiNumber);
	}

}
