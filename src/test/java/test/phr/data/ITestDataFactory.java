package test.phr.data;

import java.util.List;

import com.google.appengine.api.datastore.Key;

public interface ITestDataFactory
{
	
	List<Key> setCountries();
	
	List<Key> setStates();
	
	List<Key> setUserProfiles();
	
	List<Key> setNatureOfCompanies();
	
}
