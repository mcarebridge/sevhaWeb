package test.phr;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.phr.ade.meta.CaredPersonMeta;
import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Profile;

public class TestCareDAO
{
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	                                                    new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setUp()
	{
		helper.setUp();
	}
	
	@After
	public void tearDown()
	{
		helper.tearDown();
	}
	
	@Test
	public void testCareDAO()
	{
		
		Long _profileId = new Long(106);
		Key _key = Datastore.createKey(Profile.class, _profileId);
		CaredPersonMeta _cpMeta = new CaredPersonMeta();
		List<CaredPerson> _caredPersonList = Datastore.query(_cpMeta)
		        .filter(_cpMeta.profile.equal(_key)).asList();
		assertEquals(1, _caredPersonList.size());
		
	}
}
