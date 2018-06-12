/**
 * 
 */
package com.phr.ade.service;

import com.phr.ade.model.VitalParameter;
import com.phr.ade.values.ICareBridgeConstants;

/**
 * @author dheerajs
 *
 */
public interface IVitalParameterService extends ICareBridgeConstants
{
	
	VitalParameter lastTakenVitalParameter(Long caredPersonKey)
	        throws Exception;
	
}
