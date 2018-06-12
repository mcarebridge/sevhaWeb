/**
 * 
 */
package com.phr.ade.service;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Profile;
import com.phr.ade.values.ICareBridgeConstants;

/**
 * @author DS5002449
 * 
 */
public interface ISecurityService extends ICareBridgeConstants
{
	/**
	 * Authenticate User / Password against the DB
	 * 
	 * @param profile
	 * @return
	 */
	Profile authUser(Profile profile) throws Exception;
	
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	Profile getProfileByUserId(Profile profile);
	
	
	
	/**
	 * 
	 * @param profileKey
	 * @return
	 */
	Key firstTimeUserUpdate(Long profileKey);
	
}
