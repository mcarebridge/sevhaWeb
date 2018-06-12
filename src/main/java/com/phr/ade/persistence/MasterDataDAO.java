package com.phr.ade.persistence;

import java.util.Collection;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.meta.PhysicianMeta;
import com.phr.ade.meta.PhysicianSpecialityMeta;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PhysicianSpeciality;

public class MasterDataDAO extends AbstractEntityDAO
{
	/**
	 * Load all the Physician Specialities
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Collection<PhysicianSpeciality> loadPhysicianSpecialities()
	        throws Exception
	{
		PhysicianSpecialityMeta _splMeta = new PhysicianSpecialityMeta();
		List<PhysicianSpeciality> _phySpecialityList = Datastore
		        .query(_splMeta).asList();
		return _phySpecialityList;
	}
	
	/**
	 * Load a Speciality by Key
	 * 
	 * @param specialityKey
	 * @return
	 * @throws Exception
	 */
	public static PhysicianSpeciality loadPhysicianSpecialityByKey(
	        Long specialityId)
	{
		Key _specialityKey = Datastore.createKey(PhysicianSpeciality.class,
		        specialityId);
		
		PhysicianSpecialityMeta _phySplMeta = new PhysicianSpecialityMeta();
		PhysicianSpeciality _physicianSpeciality = Datastore.query(_phySplMeta)
		        .filter(_phySplMeta.key.equal(_specialityKey)).asSingle();
		return _physicianSpeciality;
	}
	
}
