package com.phr.ade.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Filter;
import org.slim3.datastore.FilterCriterion;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.phr.ade.meta.VitalSignCategoryMeta;
import com.phr.ade.meta.VitalSignCategoryValuesMeta;
import com.phr.ade.meta.VitalSignSubTypeMeta;
import com.phr.ade.model.VitalSign;
import com.phr.ade.model.VitalSignCategory;
import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.model.VitalSignSubType;
import com.phr.ade.values.VitalSignDTO;

public class VitalSignDAO extends AbstractEntityDAO implements IVitalSignDAO
{
	private static Logger logger = Logger.getLogger(VitalSignDAO.class
	                                     .getName());
	
	@Override
	public List<VitalSignCategoryValues> matchVitalSigns(Integer gender,
	        Integer age, String vitalSignSubType, Double vitalSignSubTypeValue)
	        throws Exception
	{
		
		/**
		 * Logic :
		 * 
		 * Step 1: find all the VitalSignCategory for the VitalSignSubType >
		 * Returns List<VitalSignSubType>
		 * 
		 * Step 2: find all the VitalSignCategoryValues for {gender, age, in
		 * List{VitalSignSubType}}
		 * 
		 * Step 2 : return this as {VitalSignSubType,
		 * List{VitalSignCategoryValue}}
		 * 
		 */
		
		VitalSignSubType _vitalSignSubType = findVitalSignSubTypeForVitalSignName(vitalSignSubType);
		List<VitalSignCategory> _vitalSignCategoryList = findVitalSignCategoriesForVitalSignSubType(_vitalSignSubType);
		List<VitalSignCategoryValues> _vitalSignCategoryValuesList = findVitalSignCategoryValueForVitalSignCategory(
		        gender, age, _vitalSignCategoryList);
		
		return _vitalSignCategoryValuesList;
		
	}
	
	@Override
	public List<VitalSignSubType> findVitalSignSubTypeForVitalSign(
	        VitalSign vitalSign) throws Exception
	{
		VitalSignSubTypeMeta _vitalSignSubType = new VitalSignSubTypeMeta();
		
		List<VitalSignSubType> _listSubType = Datastore
		        .query(_vitalSignSubType)
		        .filter(_vitalSignSubType.vitalSign.equal(vitalSign.getKey()))
		        .asList();
		
		return _listSubType;
	}
	
	/**
	 * Find VitalSignSubType by subTypeName
	 * 
	 * @param vitalSignSubTypeName
	 * @return
	 * @throws Exception
	 */
	public VitalSignSubType findVitalSignSubTypeForVitalSignName(
	        String vitalSignSubTypeName) throws Exception
	{
		VitalSignSubTypeMeta _vitalSignSubType = new VitalSignSubTypeMeta();
		
		VitalSignSubType _listSubType = Datastore
		        .query(_vitalSignSubType)
		        .filter(_vitalSignSubType.vitalSignSubTypeName
		                .equal(vitalSignSubTypeName)).asSingle();
		
		return _listSubType;
	}
	
	@Override
	public List<VitalSignCategory> findVitalSignCategoriesForVitalSignSubType(
	        VitalSignSubType vitalSignSubType) throws Exception
	{
		VitalSignCategoryMeta _vSignCatMeta = new VitalSignCategoryMeta();
		List<VitalSignCategory> _listSignCat = Datastore
		        .query(_vSignCatMeta)
		        .filter(_vSignCatMeta.vitalSignSubType.equal(vitalSignSubType
		                .getKey())).asList();
		
		return _listSignCat;
	}
	
	@Override
	public List<VitalSignCategoryValues> findVitalSignCategoryValueForVitalSignCategory(
	        Integer gender, Integer age,
	        List<VitalSignCategory> vitalSignCategories) throws Exception
	{
		VitalSignCategoryValuesMeta _vSignCatValuesMeta = new VitalSignCategoryValuesMeta();
		ArrayList<VitalSignCategoryValues> _vitalSignCategoryValuesList = new ArrayList<VitalSignCategoryValues>();
		
		for (Iterator iterator = vitalSignCategories.iterator(); iterator
		        .hasNext();)
		{
			VitalSignCategory _vitalSignCategory = (VitalSignCategory) iterator
			        .next();
			
			List<VitalSignCategoryValues> _vitalSignCategoryValues = Datastore
			        .query(_vSignCatValuesMeta)
			        .filter(_vSignCatValuesMeta.maxAge.greaterThanOrEqual(age),
			                // _vSignCatValuesMeta.minAge.greaterThanOrEqual(age),
			                _vSignCatValuesMeta.gender.equal(gender),
			                _vSignCatValuesMeta.vitalSignCategory
			                        .equal(_vitalSignCategory.getKey()))
			        .sort("maxAge", SortDirection.ASCENDING).asList();
			
			logger.log(
			        Level.INFO,
			        "vitalSignCategoryKey = "
			                + _vitalSignCategory.getKey().getId()
			                + " -- "
			                + _vitalSignCategory.getVitalSignSubType()
			                        .getModel().getVitalSignSubTypeName()
			                + " -- "
			                + _vitalSignCategory.getVitalSignCategoryName()
			                + " --- Values found : -- "
			                + _vitalSignCategoryValues.size());
			
			// Since this sorted just pick the first Record
			if (_vitalSignCategoryValues.size() > 0)
			{
				_vitalSignCategoryValuesList.add(_vitalSignCategoryValues
				        .get(0));
			}
			
			// for (Iterator iterator2 = _vitalSignCategoryValues.iterator();
			// iterator2
			// .hasNext();)
			// {
			// VitalSignCategoryValues _vitalSignCategoryValue1 =
			// (VitalSignCategoryValues) iterator2
			// .next();
			// if(_vitalSignCategoryValue1.getMinAge() < age)
			// {
			// _vitalSignCategoryValuesList.add(_vitalSignCategoryValue1);
			// }
			//
			// }
			
		}
		
		return _vitalSignCategoryValuesList;
	}
}
