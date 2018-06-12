package test.phr.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.util.GoogleTextConverter;

public class DataFeedReader
{
	
	private ArrayList<String>  headerList = new ArrayList<String>();
	private ArrayList<HashMap> beanMap    = new ArrayList<HashMap>();
	
	public ArrayList getBeanMap()
	{
		return beanMap;
	}
	
	private StringBuffer errData = new StringBuffer();
	private int          records = 1;
	
	/**
	 * @param args
	 *            the command line arguments
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public List loadFileDataToEntity(String testDataFilePath, String modelName)
	        throws IOException, InstantiationException, IllegalAccessException,
	        ClassNotFoundException, InvocationTargetException,
	        SecurityException, NoSuchMethodException
	{
		
		ArrayList entityList = new ArrayList();
		headerList = new ArrayList<String>();
		beanMap = new ArrayList<HashMap>();
		records = 1;
		
		// try {
		readFeedFile(testDataFilePath);
		
		ArrayList beanList = getBeanMap();
		
		for (int i = 0; i < beanList.size(); i++)
		{
			
			HashMap map = (HashMap) beanList.get(i);
			
			Object o = Class.forName("com.phr.ade.model." + modelName)
			        .newInstance();
			
			DateLocaleConverter dateLocaleConverter = new DateLocaleConverter(
			        Locale.US, "MM/dd/yyyy kk:mm");
			
			GoogleTextConverter _gtc = new GoogleTextConverter();
			ConvertUtils.register(_gtc,
			        com.google.appengine.api.datastore.Text.class);
			ConvertUtils.register(dateLocaleConverter, java.util.Date.class);
			BeanUtils.populate(o, map);
			// Fill Key
			
			String keyId = (String) map.get("Id");
			
			// No Id is being passed for Timesheets. It should be generated at
			// run time
			if (keyId != null)
			{
				Key k = Datastore
				        .createKey(o.getClass(), Long.parseLong(keyId));
				Method m = o.getClass().getMethod("setKey", Key.class);
				Object _parameters[] = new Object[] { k };
				m.invoke(o, _parameters);
			}
			
			// Fill Ref Key
			
			o = fillRefKeys(o, map);
			
			// Special case for filling the calculated fields
			// 1. Fill Timesheet : dailyEffort
			/**
			if (modelName.equals("MA_Timesheet"))
			{
				
				Calendar c = Calendar.getInstance();
				
				MA_Timesheet tSheet = (MA_Timesheet) o;
				Date in = tSheet.getInDateTime();
				Date out = tSheet.getOutDateTime();
				long diff = out.getTime() - in.getTime();
				
				// Store the time in secs
				
				BigDecimal secs = new BigDecimal(diff);
				BigDecimal base = new BigDecimal(1000);
				BigDecimal _timediff = secs.divide(base, 2,
				        BigDecimal.ROUND_FLOOR);
				
				Double dailyEffort = new Double(_timediff.doubleValue());
				tSheet.setDailyEffort(dailyEffort);
				
			}
			**/
			
			entityList.add(o);
			
		}// END - FOR
		return entityList;
		
	}
	
	private Object fillRefKeys(Object o, HashMap map) throws SecurityException,
	        NoSuchMethodException, IllegalArgumentException,
	        IllegalAccessException, InvocationTargetException,
	        InstantiationException, IOException
	{
		
		Method m1 = null;
		Object _parameters[] = null;
		// o is object instance of the Entity
		
		/**
		if (map.containsKey("templateRefId"))
		{
			
			// Set Key in the Country
			String keyId = (String) map.get("templateRefId");
			MA_Template template = new MA_Template();
			Key k = Datastore.createKey(MA_Template.class,
			        Long.parseLong(keyId.trim()));
			template.setKey(k);
			m1 = o.getClass().getMethod("getTemplateRef");
			_parameters = new Object[] { template };
			
			Object _o1 = m1.invoke(o, null);
			// This is equivalent to entity.setModelRef(foreigneEntity);
			Method m2 = _o1.getClass().getMethod("setModel", Object.class);
			m2.invoke(_o1, _parameters);
			
		}
		
		if (map.containsKey("countryRefId"))
		{
			
			// Set Key in the Country
			String keyId = (String) map.get("countryRefId");
			MA_Country country = new MA_Country();
			Key k = Datastore
			        .createKey(MA_Country.class, Long.parseLong(keyId));
			country.setKey(k);
			m1 = o.getClass().getMethod("getCountryRef");
			_parameters = new Object[] { country };
			
			Object _o1 = m1.invoke(o, null);
			// This is equivalent to entity.setModelRef(foreigneEntity);
			Method m2 = _o1.getClass().getMethod("setModel", Object.class);
			m2.invoke(_o1, _parameters);
			
		}
		
		if (map.containsKey("stateRefId"))
		{
			
			// Set Key in the State
			String keyId = (String) map.get("stateRefId");
			MA_State state = new MA_State();
			Key k = Datastore.createKey(MA_State.class, Long.parseLong(keyId));
			state.setKey(k);
			m1 = o.getClass().getMethod("getStateRef");
			_parameters = new Object[] { state };
			
			Object _o1 = m1.invoke(o, null);
			// This is equivalent to entity.setModelRef(foreigneEntity);
			Method m2 = _o1.getClass().getMethod("setModel", Object.class);
			m2.invoke(_o1, _parameters);
			
		}
		**/
		
		if (map.containsKey("preExistingDiseaseId"))
		{
			
			// Set Key in the State
			String keyId = (String) map.get("preExistingDiseaseId");
			CommonPreExistingDiseases _cped = new CommonPreExistingDiseases();
			Key k = Datastore.createKey(CommonPreExistingDiseases.class, Long.parseLong(keyId));
			_cped.setKey(k);
			m1 = o.getClass().getMethod("getPreExistingDisease");
			_parameters = new Object[] { _cped };
			
			Object _o1 = m1.invoke(o, null);
			// This is equivalent to entity.setModelRef(foreigneEntity);
			Method m2 = _o1.getClass().getMethod("setModel", Object.class);
			m2.invoke(_o1, _parameters);
			
		}
		
		return o;
	}
	
	/**
	 * FileName should be same as BeanName
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void readFeedFile(String fileName) throws IOException
	{
		
		FileReader fr = null;
		File f = new File(fileName);
		if (f.exists())
		{
			try
			{
				fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				StringBuffer sb = new StringBuffer();
				String eachLine = "";
				while (eachLine != null)
				{
					eachLine = br.readLine();
					
					if (eachLine != null)
					{
						readDelimitedString(eachLine);
					}
				}
			} finally
			{
				fr.close();
			}
		}
	}
	
	/**
	 * 
	 * @param data
	 */
	private void readDelimitedString(String data)
	{
		String element = "";
		int i = 0;
		HashMap<String, String> bodyMap = new HashMap<String, String>();
		
		try
		{
			StringTokenizer st = new StringTokenizer(data, ",");
			
			// Fill Header
			if (records == 1)
			{
				while (st.hasMoreTokens())
				{
					element = (String) st.nextElement();
					// logger.log(Level.INFO,"### Header" + element);
					headerList.add(element);
					i++;
				}
			}
			
			// fill body
			if (records > 1)
			{
				while (st.hasMoreTokens())
				{
					element = (String) st.nextElement();
					// logger.log(Level.INFO,"### Body" + element);
					bodyMap.put(headerList.get(i), element);
					i++;
				}
				beanMap.add(bodyMap);
			}
		} catch (StringIndexOutOfBoundsException e)
		{
			errData.append(data);
			errData.append("\n");
			e.printStackTrace();
		}
		records++;
	}
}
