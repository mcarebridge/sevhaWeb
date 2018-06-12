package com.phr.ade.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Profile;

public interface IProfileService
{

    /**
     * 
     * @param profile
     * @return
     */
    Profile createProfile(Profile profile) throws Exception;
    
    
    /**
     * This method also takescare of Active and inactive Users
     * @param profile
     * @return
     */
    Profile updateProfile(Profile profile) throws Exception;

    /**
     * 
     * @param profile
     * @return
     */
    Profile searchProfileByEmail(Profile profile) throws Exception;

    
    /**
     * 
     * @param key
     * @return
     */
    Profile searchProfileByKey(Key key) throws Exception;
    
    
    /**
     * 
     * @param imeiNumber
     * @return
     */
    List<CaredPerson> authenticateCaredPersonbyIMEI(Long imeiNumber) throws Exception;

}
