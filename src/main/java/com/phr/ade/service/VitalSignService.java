package com.phr.ade.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.model.VitalSignSubType;
import com.phr.ade.persistence.VitalSignDAO;
import com.phr.ade.values.VitalSignDTO;
import com.phr.ade.values.VitalSignUserOutPutDTO;

public class VitalSignService implements IVitalSignService
{
	
	private static Logger logger = Logger.getLogger(VitalSignService.class
	                                     .getName());
	
	@Override
	public Map<String, VitalSignUserOutPutDTO> getVitalSignValuesForVitals(
	        VitalSignDTO vitalSignDTO) throws Exception
	{
		VitalSignDAO vitalSignDAO = new VitalSignDAO();
		ArrayList<VitalSignCategoryValues> _vitalSignListAll = new ArrayList<VitalSignCategoryValues>();
		ArrayList<VitalSignUserOutPutDTO> _vsUserOutputDTOList = new ArrayList<VitalSignUserOutPutDTO>();
		Map<String, VitalSignUserOutPutDTO> _vsUserOutputDTOMap = new LinkedHashMap<>();
		
		Hashtable vitalSignKeyValue = vitalSignDTO.getVitalSignCategoryPair();
		
		Enumeration enu = vitalSignKeyValue.keys();
		
		while (enu.hasMoreElements())
		{
			String _vitalSignSubType = (String) enu.nextElement();
			Double _vitalSignSubTypeValue = (Double) vitalSignKeyValue
			        .get(_vitalSignSubType);
			
			// note if no value is find the VitalSignCategory is always =
			// "IDEAL"
			
			List<VitalSignCategoryValues> _vitalSignList = vitalSignDAO
			        .matchVitalSigns(vitalSignDTO.getGender(),
			                vitalSignDTO.getAge(), _vitalSignSubType,
			                _vitalSignSubTypeValue);
			
			VitalSignUserOutPutDTO _vsOutputDTO = transformToUserOutPut(
			        _vitalSignList, _vitalSignSubType, _vitalSignSubTypeValue);
			
			// _vsUserOutputDTOList.add(_vsOutputDTO);
			_vsUserOutputDTOMap.put(_vsOutputDTO.getVitalSignSubType(),
			        _vsOutputDTO);
			
			// _vitalSignListAll.addAll(_vitalSignList);
		}
		
		// Debug start
		// for (Iterator iterator = _vitalSignListAll.iterator(); iterator
		// .hasNext();)
		// {
		// VitalSignCategoryValues vitalSignCategoryValues =
		// (VitalSignCategoryValues) iterator
		// .next();
		// logger.log(Level.INFO, "Key "
		// + vitalSignCategoryValues.getKey().getId()
		// + "\t"
		// + vitalSignCategoryValues.getVitalSignCategory().getModel()
		// .getVitalSignSubType().getModel()
		// .getVitalSignSubTypeName()
		// + "\t"
		// + vitalSignCategoryValues.getVitalSignCategory().getModel()
		// .getVitalSignCategoryName() + "\t"
		// + vitalSignCategoryValues.getGender() + "\t"
		// + vitalSignCategoryValues.getMaxAge() + "\t"
		// + vitalSignCategoryValues.getMinAge() + "\t"
		// + vitalSignCategoryValues.getMinTargetValue() + "\t"
		// + vitalSignCategoryValues.getMaxTargetValue() + "\t");
		// }
		// debug end
		
		return _vsUserOutputDTOMap;
	}
	
	/**
	 * 
	 * @param vitalSignList
	 * @param vitalSignSubType
	 * @param vitalSignSubTypeValue
	 * @return
	 */
	private VitalSignUserOutPutDTO transformToUserOutPut(
	        List<VitalSignCategoryValues> vitalSignList,
	        String vitalSignSubType, Double vitalSignSubTypeValue)
	{
		
		VitalSignUserOutPutDTO _vitalSignUserOutPutDTO = new VitalSignUserOutPutDTO();
		
		_vitalSignUserOutPutDTO
		        .setVitalSignValueProvided(vitalSignSubTypeValue);
		_vitalSignUserOutPutDTO.setVitalSignSubType(vitalSignSubType);
		
		for (Iterator iterator = vitalSignList.iterator(); iterator.hasNext();)
		{
			VitalSignCategoryValues _vitalSignCategoryValues = (VitalSignCategoryValues) iterator
			        .next();
			
			// Do not add if the passed on Value is zero.
			// This happens of the user doesn't enter value and default internal
			// min value is zero
			// Applicable for SUGAR test
			
			if (vitalSignSubTypeValue > 0)
			{
				if (vitalSignSubTypeValue <= _vitalSignCategoryValues
				        .getMaxTargetValue()
				        && vitalSignSubTypeValue >= _vitalSignCategoryValues
				                .getMinTargetValue())
				{
					_vitalSignUserOutPutDTO
					        .setVitalSignCatValReported(_vitalSignCategoryValues);
				}
			}
			
			if (_vitalSignCategoryValues.getVitalSignCategory().getModel()
			        .getVitalSignCategoryName().equalsIgnoreCase("IDEAL"))
			{
				_vitalSignUserOutPutDTO
				        .setVitalSignCatValIDEAL(_vitalSignCategoryValues);
			}
			
		}
		
		return _vitalSignUserOutPutDTO;
	}
	
}
