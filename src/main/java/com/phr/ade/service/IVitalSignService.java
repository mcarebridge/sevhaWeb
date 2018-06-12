package com.phr.ade.service;

import java.util.List;
import java.util.Map;

import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.values.ICareBridgeConstants;
import com.phr.ade.values.VitalSignDTO;
import com.phr.ade.values.VitalSignUserOutPutDTO;

public interface IVitalSignService extends ICareBridgeConstants
{
	
	/**
	 * 
	 * @param vitalSignsList
	 * @return
	 */
	Map<String,VitalSignUserOutPutDTO> getVitalSignValuesForVitals(
	        VitalSignDTO vitalSignDTO) throws Exception;
	
}
