package com.phr.ade.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.controller.health.CaredPersonMobileSecurityController;
import com.phr.ade.meta.AddressMeta;
import com.phr.ade.meta.CaredPersonMeta;
import com.phr.ade.meta.ProfileMeta;
import com.phr.ade.model.Address;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Profile;
import com.phr.ade.security.Encrypt;

public class ProfileDAO extends AbstractEntityDAO implements IProfileDAO
{
	
	private static Logger logger = Logger.getLogger(ProfileDAO.class.getName());
	
	@Override
	public Profile searchProfileByEmail(Profile profile)
	{
		ProfileMeta profileMeta = new ProfileMeta();
		Profile _profile = Datastore.query(profileMeta)
		        .filter(profileMeta.email.equal(profile.getEmail())).asSingle();
		
		return _profile;
	}
	
	@Override
	public Profile searchProfileByKey(Key key)
	{
		ProfileMeta profileMeta = new ProfileMeta();
		Profile _profile = Datastore.query(profileMeta)
		        .filter(profileMeta.key.equal(key)).asSingle();
		
		return _profile;
	}
	
	/**
	 * Authenticate User
	 * @throws Exception 
	 */
	@Override
	public Profile authenticateUser(Profile profile) throws Exception
	{
		ProfileMeta profileMeta = new ProfileMeta();
		String _passwordEnc = Encrypt.encodeString(profile.getPassword());
		
		Profile _profile = Datastore
		        .query(profileMeta)
		        .filter(profileMeta.email.equal(profile.getEmail()),
		                profileMeta.password.equal(_passwordEnc)).asSingle();
		
		return _profile;
	}
	
	/**
	 * load profile only by UserId. This is used for retrieving password string.
	 */
	public Profile getProfileByUserId(Profile profile)
	{
		ProfileMeta profileMeta = new ProfileMeta();
		Profile _profile = Datastore.query(profileMeta)
		        .filter(profileMeta.email.equal(profile.getEmail())).asSingle();
		
		return _profile;
	}
	
	/**
	 * Locate the CaredPerson by IMEI number. Since we will allow up to two
	 * person for same IMEI, we are returning a list
	 */
	@Override
	public List<CaredPerson> authenticateCaredPersonbyIMEI(Long imeiNumber)
	{
		
		ArrayList<CaredPerson> caredPersonList = new ArrayList<CaredPerson>();
		
		AddressMeta addressMeta = new AddressMeta();
		List<Address> _address = Datastore.query(addressMeta)
		        .filter(addressMeta.imei.equal(imeiNumber)).asList();
		
		for (Iterator iterator = _address.iterator(); iterator.hasNext();)
		{
			Address address1 = (Address) iterator.next();
			
			logger.log(Level.INFO, "--Address Key with IMEI --"
			        + address1.getKey().getId());
			
			CaredPersonMeta caredPersonMeta = new CaredPersonMeta();
			List<CaredPerson> _caredPersonList = Datastore
			        .query(caredPersonMeta)
			        .filter(caredPersonMeta.address.equal(address1.getKey()))
			        .asList();
			
			logger.log(Level.INFO, "--CaredPerson List Size --"
			        + _caredPersonList.size());
			
			caredPersonList.addAll(_caredPersonList);
		}
		
		logger.log(Level.INFO, "--CaredPerson List Final Size --"
		        + caredPersonList.size());
		
		return caredPersonList;
	}
	
	@Override
	public Key updateProfile(Profile profile)
	{
		Key _profileKey = addModel(profile);
		return _profileKey;
	}
}
