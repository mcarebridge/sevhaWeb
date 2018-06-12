package test.phr;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.phr.ade.model.VitalSign;
import com.phr.ade.model.VitalSignSubType;
import com.phr.ade.persistence.VitalSignDAO;
import com.phr.ade.values.VitalSignDTO;

public class VitalSignsTest
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
	public void testVitalSignSubTypes()
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		// TODO Auto-generated constructor stub
		try
		{
			VitalSignDAO vsDAO = new VitalSignDAO();
			VitalSignDTO vsignDTO = new VitalSignDTO();
			
			VitalSign _vSign = new VitalSign();
			
			List<VitalSignSubType> _vsSubType = vsDAO
			        .findVitalSignSubTypeForVitalSign(_vSign);
			
			System.out.println(_vsSubType.size());
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
