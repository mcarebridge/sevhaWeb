/**
 * 
 */
package com.phr.ade.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.persistence.CareDAO;

/**
 * @author dheerajs
 *
 */
public class VitalParameterService implements IVitalParameterService
{
	
	public VitalParameter lastTakenVitalParameter(Long caredPersonKey)
	        throws Exception
	{
		CareDAO _careDAO = new CareDAO();
		Key _key = Datastore.createKey(CaredPerson.class,
		        caredPersonKey.longValue());
		
		VitalParameter _vitalParameter = _careDAO
		        .loadLatestVitalParameterByCaredPerson(_key);
		
		// CareService _cs = new CareService();
		// VitalParameter _vitalParameter = null;
		//
		// List<VitalParameter> _vpList = _cs
		// .listLastSevenVitalParameter(caredPersonKey);
		//
		// // Last Take Height and Weight
		// int _lastIndex = _vpList.size();
		// _lastIndex--;
		//
		// if (_vpList != null & _lastIndex > 0)
		// {
		// _vitalParameter = _vpList.get(_lastIndex);
		// }
		
		return _vitalParameter;
	}
	
}
