package com.phr.ade.persistence;

import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.phr.ade.meta.MasterDataMeta;
import com.phr.ade.meta.VitalSignCategoryValuesMeta;
import com.phr.ade.model.MasterData;
import com.phr.ade.model.Prescription;

public class EntityUtilDAO extends AbstractEntityDAO
{
	
	public Key addMasterData(MasterData masterData)
	{
		Key _key = addModel(masterData);
		
		return _key;
	}
	
	/**
	 * 
	 * @param entityType
	 * @return
	 */
	public MasterData readMasterData(String entityType)
	{
		MasterDataMeta _masterDataMeta = new MasterDataMeta();
		MasterData masterData = Datastore.query(_masterDataMeta)
		        .filter(_masterDataMeta.entityType.equal(entityType))
		        .asSingle();
		
		return masterData;
	}
	
	/**
	 * Clean up method
	 */
	public void deleteAllVitalSignCategoryValues()
	{
		
		DatastoreService datastore = DatastoreServiceFactory
		        .getDatastoreService();
		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		
		VitalSignCategoryValuesMeta _catValuesMeta = new VitalSignCategoryValuesMeta();
		List<Key> _vsCatValueKeyList = Datastore.query(_catValuesMeta)
		        .asKeyList();
		
		for (Iterator iterator = _vsCatValueKeyList.iterator(); iterator
		        .hasNext();)
		{
			Transaction txn = datastore.beginTransaction(options);
			Key key = (Key) iterator.next();
			datastore.delete(txn, key);
			txn.commit();
		}
		
	}
	
}
