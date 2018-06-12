package com.phr.ade.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelMeta;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.phr.ade.model.AbstractEntity;

public abstract class AbstractEntityDAO
{
	private static Logger      logger = Logger.getLogger(AbstractEntityDAO.class
	                                          .getName());
	private static Transaction gtx;
	public static double       readCounter;
	public static double       writeCounter;
	
	/**
	 * @return the gtx
	 */
	public static Transaction getGtx()
	{
		return gtx;
	}
	
	/**
	 * @param gtx
	 *            the gtx to set
	 */
	public static void setGtx(Transaction _gtx)
	{
		gtx = _gtx;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public Key addModel(AbstractEntity model)
	{
		return Datastore.put(model);
	}
	
	/**
	 * 
	 * @param models
	 * @return
	 */
	public List<Key> addModels(Iterable<AbstractEntity> models)
	{
		List<Key> _keys = null;
		_keys = Datastore.put(models);
		return _keys;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public void deleteModel(Key key)
	{
		Datastore.delete(key);
	}
	
	/**
	 * 
	 * @param models
	 * @return
	 */
	public void deleteModel(List<Key> keys)
	{
		Datastore.delete(keys);
	}
	
	/**
	 * 
	 * @param modelMeta
	 */
	public void deleteAll(ModelMeta modelMeta)
	{
		
		List<Entity> e = getAllModels(modelMeta);
		
		for (Iterator iterator = e.iterator(); iterator.hasNext();)
		{
			AbstractEntity abstractEntity = (AbstractEntity) iterator.next();
			deleteModel(abstractEntity.getKey());
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Entity getModel(Key key)
	{
		return Datastore.get(key);
	}
	
	/**
	 * 
	 * @param keys
	 * @return
	 */
	public List<Entity> getModels(List<Key> keys)
	{
		return Datastore.get(keys);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adviteya.persistence.BaseDAO#getModels(java.util.List)
	 */
	public static List<Entity> getAllModels(ModelMeta mmd)
	{
		List<Entity> _e = null;
		_e = Datastore.query(mmd).asList();
		
		int count = Datastore.query(mmd).count();
		return _e;
	}
	
	/**
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	public static Key createKey(Object obj, Long id)
	{
		
		return Datastore.createKey(obj.getClass(), id);
		
	}
	
}
