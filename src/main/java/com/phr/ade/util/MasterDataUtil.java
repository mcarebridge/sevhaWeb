package com.phr.ade.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.phr.ade.meta.VitalSignCategoryMeta;
import com.phr.ade.meta.VitalSignCategoryValuesMeta;
import com.phr.ade.meta.VitalSignSubTypeMeta;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.PhysicianSpeciality;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.VitalSign;
import com.phr.ade.model.VitalSignCategory;
import com.phr.ade.model.VitalSignCategoryValues;
import com.phr.ade.model.VitalSignSubType;
import com.phr.ade.persistence.EntityUtilDAO;

public class MasterDataUtil implements UtilCommonConstants
{
	
	private String       entityTypes[] = new String[] {	                                   
	                                   PHYSICIAN_SPECIALITY,
	                                   COMMON_EXISTING_DISEASES,
	                                   PRE_EXISTING_DISEASES_SYMPTOM,
	                                   VITAL_SIGN,
	                                   VITAL_SIGN_SUB_TYPE,
	                                   VITAL_SIGN_CATEGORY,
	                                   VITAL_SIGN_CATEGORY_VALUES 
	                                   };
	
	private StringBuffer errReport     = new StringBuffer(
	                                           "Check file location for : <br> ");
	
	/**
	 * 
	 * @param entityType
	 */
	public void addEntity() throws Exception
	{
		// Add all
		// GlobalTransaction gtx = Datastore.beginGlobalTransaction();
		DataFeedReader dfr = new DataFeedReader();
		// AbstractOMCDAO.setGtx(gtx);
		
		// Start the seq from the lookup entity to transactional entities
		int i = entityTypes.length - 1;
		
		while (i >= 0)
		{
			String entityTypeName = entityTypes[i];
			System.out.println("Processing >>>> " + entityTypeName);
			
			// StringBuffer sb = new StringBuffer(
			// testDataFilepath.concat(entityType[i].concat(".csv")));
			
			// File f = new File(sb.toString());
			
			// if (!f.exists())
			// {
			// errReport.append(entityType[i].concat(".csv") + "<br>");
			// }
			
			List entityList = dfr.loadFileDataToEntity(entityTypes[i]);
			EntityUtilDAO _euDAO = new EntityUtilDAO();
			
			for (Iterator iterator = entityList.iterator(); iterator.hasNext();)
			{
				// gtx = Datastore.beginGlobalTransaction();
				// AbstractOMCDAO.setGtx(gtx);
				Transaction gtx = Datastore.beginTransaction();
				
				if (entityTypeName
				        .equalsIgnoreCase(COMMON_EXISTING_DISEASES))
				{
					CommonPreExistingDiseases obj = (CommonPreExistingDiseases) iterator
					        .next();
					_euDAO.addModel(obj);
				} else if (entityTypeName
				        .equalsIgnoreCase(PRE_EXISTING_DISEASES_SYMPTOM))
				{
					PreExistingDiseaseCommonSymptom obj = (PreExistingDiseaseCommonSymptom) iterator
					        .next();
					_euDAO.addModel(obj);
				} else if (entityTypeName
				        .equalsIgnoreCase(PHYSICIAN_SPECIALITY))
				{
					PhysicianSpeciality obj = (PhysicianSpeciality) iterator
					        .next();
					_euDAO.addModel(obj);
				} else if (entityTypeName.equalsIgnoreCase(VITAL_SIGN))
				{
					VitalSign obj = (VitalSign) iterator.next();
					_euDAO.addModel(obj);
				} else if (entityTypeName.equalsIgnoreCase(VITAL_SIGN_SUB_TYPE))
				{
					VitalSignSubType obj = (VitalSignSubType) iterator.next();
					// VitalSignSubTypeMeta _vitalSignSubTypeMeta = new
					// VitalSignSubTypeMeta();
					// _euDAO.deleteAll(_vitalSignSubTypeMeta);
					_euDAO.addModel(obj);
				} else if (entityTypeName.equalsIgnoreCase(VITAL_SIGN_CATEGORY))
				{
					VitalSignCategory obj = (VitalSignCategory) iterator.next();
					// VitalSignCategoryMeta _vitalSignCategoryMeta = new
					// VitalSignCategoryMeta();
					// _euDAO.deleteAll(_vitalSignCategoryMeta);
					_euDAO.addModel(obj);
				} else if (entityTypeName
				        .equalsIgnoreCase(VITAL_SIGN_CATEGORY_VALUES))
				{
					VitalSignCategoryValues obj = (VitalSignCategoryValues) iterator
					        .next();
					
					// as a temp solution - delete and and then add by recompile
//					 _euDAO.deleteAllVitalSignCategoryValues();
					_euDAO.addModel(obj);
				}
				gtx.commit();
			}
			
			i--;
		}
	}
}
