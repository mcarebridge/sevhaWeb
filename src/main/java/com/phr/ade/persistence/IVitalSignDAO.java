package com.phr.ade.persistence;

import java.util.ArrayList;
import java.util.List;

import com.phr.ade.model.VitalSign;
import com.phr.ade.model.VitalSignCategory;
import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.model.VitalSignSubType;
import com.phr.ade.values.ICareBridgeConstants;
import com.phr.ade.values.VitalSignDTO;

public interface IVitalSignDAO extends ICareBridgeConstants, IEntityDAO
{
	
	List<VitalSignCategoryValues> matchVitalSigns(Integer gender, Integer age,
	        String vitalSignCategory, Double vitalSignCategoryValueProvided)
	        throws Exception;
	
	/**
	 * 
	 * @param vitalSign
	 * @return
	 * @throws Exception
	 */
	List<VitalSignSubType> findVitalSignSubTypeForVitalSign(VitalSign vitalSign)
	        throws Exception;
	
	
	
	VitalSignSubType findVitalSignSubTypeForVitalSignName(
	        String vitalSignSubTypeName) throws Exception;
	
	
	List<VitalSignCategory> findVitalSignCategoriesForVitalSignSubType(
	        VitalSignSubType vitalSignSubType) throws Exception;
	
	/**
	 * 
	 * @param vitalSignSubType
	 * @return
	 * @throws Exception
	 */
	List<VitalSignCategoryValues> findVitalSignCategoryValueForVitalSignCategory(Integer gender, Integer age,
	        List<VitalSignCategory> vitalSignCategories) throws Exception;
	
}
