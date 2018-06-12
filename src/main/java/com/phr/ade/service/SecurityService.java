package com.phr.ade.service;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.Profile;
import com.phr.ade.persistence.IProfileDAO;
import com.phr.ade.persistence.ProfileDAO;

public class SecurityService implements ISecurityService
{
	
	/**
	 * Authenticate UserId
	 * @throws Exception 
	 */
	@Override
	public Profile authUser(Profile profile) throws Exception
	{
		ProfileDAO _pdao = new ProfileDAO();
		
		return _pdao.authenticateUser(profile);
	}
	
	@Override
	public Key firstTimeUserUpdate(Long profileKey)
	{
		IProfileDAO _pDAO = new ProfileDAO();
		Key _profilekey = Datastore.createKey(Profile.class, profileKey);
		Profile _profile = _pDAO.searchProfileByKey(_profilekey);
		_profile.setFirstTimeUser(false);
		
		return _pDAO.addModel(_profile);
	}
	
	@Override
	public Profile getProfileByUserId(Profile profile)
	{
		ProfileDAO _pdao = new ProfileDAO();
		
		return _pdao.getProfileByUserId(profile);
	}
	
}
