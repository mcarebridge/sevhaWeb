package com.phr.ade.persistence;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Profile;
import com.phr.ade.values.ICareBridgeConstants;

public interface IProfileDAO extends IEntityDAO, ICareBridgeConstants
{
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	Profile searchProfileByEmail(Profile profile);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	Profile searchProfileByKey(Key key);
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	
	Profile authenticateUser(Profile profile) throws Exception;
	
	/**
	 * Get Profile only by UserId
	 * 
	 * @param profile
	 * @return
	 */
	Profile getProfileByUserId(Profile profile);
	
	/**
	 * 
	 * @param imeiNumber
	 * @return
	 */
	List<CaredPerson> authenticateCaredPersonbyIMEI(Long imeiNumber);
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	Key updateProfile(Profile profile);
	
}
