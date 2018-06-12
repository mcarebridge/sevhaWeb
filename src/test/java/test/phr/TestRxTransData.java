package test.phr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.phr.ade.service.CareTransactionService;

public class TestRxTransData {
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
		    new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testCareDAO() {
		
		CareTransactionService _ct = new CareTransactionService();
		
		String _rxData = "(ACTION=TAKEN)(RXTAKEN=3060001,1111,222222)(SYMPTOMS=555,666,)";
		//_ct.extractRxAndSymptoms(_rxData);
		
	}

}
