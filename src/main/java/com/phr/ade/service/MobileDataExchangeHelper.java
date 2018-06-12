package com.phr.ade.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.Caregiver;
import com.phr.ade.model.CommonPreExistingDiseases;
import com.phr.ade.model.EmergencyResponse;
import com.phr.ade.model.Physician;
import com.phr.ade.model.PreExistingCondition;
import com.phr.ade.model.PreExistingDiseaseCommonSymptom;
import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.persistence.CareDAO;
import com.phr.ade.util.CareUtil;

public class MobileDataExchangeHelper
{
	
	public static StringBuffer populateCaredPersonRxData()
	        throws MalformedURLException, IOException
	{
		BufferedReader br = null;
		StringBuffer _xmlsb = new StringBuffer();
		
		URL oracle = new URL(
		        "http://caregiver.mcarebridge.com/health/template/caredperson.xml");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		        oracle.openStream()));
		
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			// System.out.println(inputLine);
			_xmlsb.append(inputLine);
		in.close();
		
		return null;
	}
	
	/**
	 * Fill caredperson data in the XML
	 * 
	 * @param xmlData
	 * @throws IOException
	 */
	public static StringBuffer fillCaredPersonData(CaredPerson caredPerson)
	        throws IOException
	{
		StringBuffer _physicianTemplate = getRawXMLString("caredperson");
		
		Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
		valuesMap.put("cpid", new StringBuffer(new Long(caredPerson.getKey()
		        .getId()).toString()));
		valuesMap.put("cpname", new StringBuffer(caredPerson.getFirstName()
		        .concat(" ").concat(caredPerson.getLastName())));
		valuesMap.put("rxprescribed",
		        fillRxPrescribed(caredPerson.getPrescription()));
		valuesMap.put("preexistingcondition",
		        fillPreExistingCondidionData(caredPerson
		                .getPreExistingCondition()));
		valuesMap.put("empergencyresponse",
		        fillEmergencyResponseData(caredPerson.getEmergencyResponse()));
		
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		sub.replaceIn(_physicianTemplate);
		
		return _physicianTemplate;
	}
	
	/**
	 * 
	 * @param prescription
	 * @throws IOException
	 */
	private static StringBuffer fillRxPrescribed(List<Prescription> prescription)
	        throws IOException
	{
		StringBuffer _rxPrescribed = new StringBuffer();
		
		for (Iterator<Prescription> iterator = prescription.iterator(); iterator
		        .hasNext();)
		{
			Prescription prescription2 = iterator.next();
			StringBuffer _rxPrescribedTemplate = getRawXMLString("rxprescribed");
			
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("rxid", new StringBuffer(new Long(prescription2
			        .getKey().getId()).toString()));
			valuesMap.put("rxtag",
			        new StringBuffer(prescription2.getPrescriptionTag()));
			valuesMap.put("physician", fillPhysician(prescription2
			        .getPhysician().getModel()));
			valuesMap.put("caregiver", fillCareGiver(prescription2
			        .getCareGiver().getModel()));
			valuesMap.put("rxlines",
			        fillRxLines(prescription2.getPrescriptionLines()));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_rxPrescribedTemplate);
			_rxPrescribed.append(_rxPrescribedTemplate);
		}
		
		return _rxPrescribed;
	}
	
	/**
	 * 
	 * @param physician
	 * @throws IOException
	 */
	private static StringBuffer fillPhysician(Physician physician)
	        throws IOException
	{
		
		StringBuffer _physicianTemplate = getRawXMLString("physician");
		
		Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
		valuesMap.put("phyid", new StringBuffer(new Long(physician.getKey()
		        .getId()).toString()));
		valuesMap.put("phyname", new StringBuffer(physician.getFirstname()
		        .concat(" ").concat(physician.getLastname())));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		sub.replaceIn(_physicianTemplate);
		
		return _physicianTemplate;
	}
	
	/**
	 * 
	 * @param caregiver
	 * @throws IOException
	 */
	private static StringBuffer fillCareGiver(Caregiver caregiver)
	        throws IOException
	{
		
		StringBuffer _careGiverTemplate = getRawXMLString("caregiver");
		
		Map valuesMap = new HashMap();
		valuesMap.put("cgid", caregiver.getKey().getId());
		valuesMap.put(
		        "cgname",
		        caregiver.getFirstName().concat(" ")
		                .concat(caregiver.getLastName()));
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		sub.replaceIn(_careGiverTemplate);
		
		return _careGiverTemplate;
	}
	
	/**
	 * 
	 * @param rxLines
	 * @throws IOException
	 */
	private static StringBuffer fillRxLines(List<PrescriptionLines> rxLines)
	        throws IOException
	{
		StringBuffer _rxLines = new StringBuffer();
		
		for (Iterator iterator = rxLines.iterator(); iterator.hasNext();)
		{
			PrescriptionLines rxLines2 = (PrescriptionLines) iterator.next();
			
			StringBuffer _rxLinesTemplate = getRawXMLString("rxlines");
			
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("rxlineid", new StringBuffer(new Long(rxLines2
			        .getKey().getId()).toString()));
			valuesMap.put("rx", new StringBuffer(rxLines2.getDrugName()));
			valuesMap.put("rxdosage", new StringBuffer(rxLines2.getStrength()));
			valuesMap.put("rxroute", new StringBuffer(rxLines2.getRoute()));
			String _freqInHrs = getFreqInHrs(rxLines2.getFrequency(),
			        rxLines2.getRxstartdate());
			valuesMap.put("rxschdl", new StringBuffer(_freqInHrs));
			
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_rxLinesTemplate);
			
			// For not Daily Rx - QWK, TIW, BIS
			//If it is not the right day for Rx it will return -1
			if (!_freqInHrs.equalsIgnoreCase("-1"))
			{
				_rxLines.append(_rxLinesTemplate);
			}
		}
		
		return _rxLines;
	}
	
	/**
	 * 
	 * @param frequency
	 * @return
	 */
	public static String getFreqInHrs(String frequency, Date rxLineStartDate)
	{
		String _freqInHrs = "";
		
		if (frequency.equalsIgnoreCase("MOD"))
		{
			_freqInHrs = "8";
		} else if (frequency.equalsIgnoreCase("AOD"))
		{
			_freqInHrs = "16";
		} else if (frequency.equalsIgnoreCase("EOD"))
		{
			_freqInHrs = "21";
		} else if (frequency.equalsIgnoreCase("MATD"))
		{
			_freqInHrs = "8-16";
		} else if (frequency.equalsIgnoreCase("AETD"))
		{
			_freqInHrs = "16-21";
		} else if (frequency.equalsIgnoreCase("METD"))
		{
			_freqInHrs = "8-21";
		} else if (frequency.equalsIgnoreCase("TID"))
		{
			_freqInHrs = "8-16-21";
		} else if (frequency.equalsIgnoreCase("QID"))
		{
			_freqInHrs = "8-12-18-21";
		}
		// Once a week
		else if (frequency.equalsIgnoreCase("QWK")
		        | frequency.equalsIgnoreCase("BIS")
		        | frequency.equalsIgnoreCase("TIW"))
		{
			// Check if this is the right day to rate Rx
			if (CareUtil.ifRxEligibleDay(frequency, rxLineStartDate))
			{
				_freqInHrs = "8";
			} else
			{
				_freqInHrs = "-1";
			}
		}
		// else if (frequency.equalsIgnoreCase("STAT"))
		// {
		// _freqInHrs = "8-16-21";
		// }
		else if (frequency.equalsIgnoreCase("OTH"))
		{
			_freqInHrs = "0";
		} else if (frequency.equalsIgnoreCase("SOS"))
		{
			_freqInHrs = "0";
		}
		
		return _freqInHrs;
		
	}
	
	/**
	 * 
	 * @param xmlData
	 * @throws IOException
	 */
	private static StringBuffer fillPreExistingCondidionData(
	        List<PreExistingCondition> preexisitingCond) throws IOException
	{
		StringBuffer _preExistingCond = new StringBuffer();
		
		for (Iterator iterator = preexisitingCond.iterator(); iterator
		        .hasNext();)
		{
			PreExistingCondition _preExistingCondition = (PreExistingCondition) iterator
			        .next();
			
			StringBuffer _preExistingXMLTemplate = getRawXMLString("preexistingcondition");
			
			Map valuesMap = new HashMap();
			valuesMap.put("pre_ext_condid", _preExistingCondition.getKey()
			        .getId());
			valuesMap.put("condition", fillConditionsData(_preExistingCondition
			        .getPreExisitingDiseases().getModel()));
			
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preExistingXMLTemplate);
			
			_preExistingCond.append(_preExistingXMLTemplate);
			
		}
		
		if (preexisitingCond.size() == 0)
		{
			StringBuffer _preExistingXMLTemplate = getRawXMLString("preexistingcondition");
			Map valuesMap = new HashMap();
			valuesMap.put("pre_ext_condid", "0");
			valuesMap.put("condition",
			        fillConditionsData(new CommonPreExistingDiseases()));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preExistingXMLTemplate);
			
			_preExistingCond.append(_preExistingXMLTemplate);
		}
		
		return _preExistingCond;
	}
	
	/**
	 * 
	 * @param preExistingDisease
	 * @return
	 * @throws IOException
	 */
	private static StringBuffer fillConditionsData(
	        CommonPreExistingDiseases preExistingDisease) throws IOException
	{
		
		StringBuffer _preConditionTemplate = getRawXMLString("condition");
		
		if (preExistingDisease.getDiseaseName() == null
		        | preExistingDisease.getDiseaseName() == "")
		{
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("condname", new StringBuffer("-"));
			/**
			 * @todo : Temp fix to show only default symptoms valuesMap
			 *       .put("symptom", fillSymptoms(new
			 *       ArrayList<PreExistingDiseaseCommonSymptom>()));
			 **/
			valuesMap.put("symptom", fillSymptoms(getDefaultSymptoms()));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preConditionTemplate);
			
		} else
		{
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("condname",
			        new StringBuffer(preExistingDisease.getDiseaseName()));
			/**
			 * @todo : Temp fix to show only default symptoms valuesMap
			 *       .put("symptom", fillSymptoms(new
			 *       ArrayList<PreExistingDiseaseCommonSymptom>()));
			 **/
			valuesMap.put("symptom", fillSymptoms(getDefaultSymptoms()));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preConditionTemplate);
		}
		
		return _preConditionTemplate;
	}
	
	/**
	 * 
	 * @param symptoms
	 * @throws IOException
	 */
	private static StringBuffer fillSymptoms(
	        List<PreExistingDiseaseCommonSymptom> symptoms) throws IOException
	{
		StringBuffer _symptoms = new StringBuffer();
		
		for (Iterator iterator = symptoms.iterator(); iterator.hasNext();)
		{
			PreExistingDiseaseCommonSymptom symptoms2 = (PreExistingDiseaseCommonSymptom) iterator
			        .next();
			StringBuffer _preConditionTemplate = getRawXMLString("symptom");
			Map valuesMap = new HashMap();
			valuesMap.put("symid", symptoms2.getKey().getId());
			valuesMap.put("symtag", symptoms2.getSymptom());
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preConditionTemplate);
			
			_symptoms.append(_preConditionTemplate);
		}
		
		if (symptoms == null | symptoms.size() == 0)
		{
			StringBuffer _preConditionTemplate = getRawXMLString("symptom");
			Map valuesMap = new HashMap();
			valuesMap.put("symid", "0");
			valuesMap.put("symtag", "-");
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_preConditionTemplate);
			
			_symptoms.append(_preConditionTemplate);
			
		}
		
		return _symptoms;
	}
	
	/**
	 * 
	 * @param xmlData
	 * @throws IOException
	 */
	private static StringBuffer fillEmergencyResponseData(
	        EmergencyResponse emergencyResponse) throws IOException
	{
		StringBuffer _emergencyResponseXMLTemplate = getRawXMLString("emergencyresponse");
		
		if (emergencyResponse != null)
		{
			
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("emgprovid", new StringBuffer(new Long(
			        emergencyResponse.getKey().getId()).toString()));
			valuesMap.put("emgprovname",
			        new StringBuffer(emergencyResponse.getProviderName()));
			valuesMap.put("emgcntnm",
			        new StringBuffer(emergencyResponse.getContactPerson()));
			valuesMap.put("emgcntcell", new StringBuffer(emergencyResponse
			        .getAddress().getModel().getCellphone()));
			// 09/06/2015 - Since we are using only one number for emergency
			// contact
			// We will not use fixedline
			// valuesMap.put("emgfixed", new StringBuffer(emergencyResponse
			// .getAddress().getModel().getFixedLine()));
			
			valuesMap.put("emgfixed", new StringBuffer("0"));
			
			// @todo : Extra field - to be taken off
			valuesMap.put("emgcntperson",
			        new StringBuffer(emergencyResponse.getContactPerson()));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_emergencyResponseXMLTemplate);
		} else
		{
			Map<String, StringBuffer> valuesMap = new HashMap<String, StringBuffer>();
			valuesMap.put("emgprovid", new StringBuffer("0"));
			valuesMap.put("emgprovname", new StringBuffer("-"));
			valuesMap.put("emgcntnm", new StringBuffer("-"));
			valuesMap.put("emgcntcell", new StringBuffer("-"));
			valuesMap.put("emgfixed", new StringBuffer("-"));
			// @todo : Extra field - to be taken off
			valuesMap.put("emgcntperson", new StringBuffer("-"));
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			sub.replaceIn(_emergencyResponseXMLTemplate);
		}
		
		return _emergencyResponseXMLTemplate;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private static StringBuffer getRawXMLString(String xmlTemplateSection)
	        throws IOException
	{
		BufferedReader br = null;
		StringBuffer _xmlsb = new StringBuffer();
		
		// URL oracle = new URL(
		// "http://caregiver.mcarebridge.com/health/template/"
		// + xmlTemplateSection + ".xml");
		//
		
		URL oracle = new URL("https://mcarebridge.appspot.com/health/template/"
		        + xmlTemplateSection + ".xml");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
		        oracle.openStream()));
		
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			// System.out.println(inputLine);
			_xmlsb.append(inputLine);
		in.close();
		
		return _xmlsb;
	}
	
	/**
	 * 
	 * @return
	 */
	private static List<PreExistingDiseaseCommonSymptom> getDefaultSymptoms()
	{
		CareDAO _careDAO = new CareDAO();
		return _careDAO.getCommonSymptoms();
	}
	
}
