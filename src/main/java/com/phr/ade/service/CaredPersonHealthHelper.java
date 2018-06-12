package com.phr.ade.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.util.ComplianceDTO;

public class CaredPersonHealthHelper
{
	private final static String GREEN  = "GREEN";
	private final static String ORANGE = "ORANGE";
	private final static String RED    = "RED";
	private final static String GRAY   = "GRAY";
	private final static String PURPLE = "PURPLE";
	
	private static Logger       logger = Logger.getLogger(CaredPersonHealthHelper.class
	                                           .getName());
	
	public CaredPersonHealthHelper()
	{
	}
	
	public static double calculateBMI(double height, double weight)
	{
		double _bmi = 0;
		double heightInMts = height / 100;
		
		_bmi = (weight / (heightInMts * heightInMts));
		
		DecimalFormat df2 = new DecimalFormat("###.#");
		_bmi = Double.valueOf(df2.format(_bmi));
		
		return _bmi;
	}
	
	/**
	 * 
	 * @param rxName
	 * @param tobeConsumed
	 * @param actualConsumed
	 * @return
	 */
	public static ComplianceDTO rxComplianceAlert(String rxName,
	        Long tobeConsumed, Long actualConsumed)
	{
		logger.log(Level.INFO, " rx " + rxName + " tobe " + tobeConsumed
		        + " actual " + actualConsumed);
		
		double _compliance = (actualConsumed.longValue() * 100)
		        / tobeConsumed.longValue();
		
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem(rxName);
		_cDTO.setComplianceValue(new Double(_compliance).toString());
		_cDTO.setUnit("%");
		
		// Temp Code
		if (_compliance > 95)
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (_compliance > 90 & _compliance < 95)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (_compliance < 90)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param bmi
	 * @return
	 */
	public static ComplianceDTO bmiAlert(double bmi)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("BMI");
		_cDTO.setComplianceValue(new Long(Math.round(bmi)).toString());
		_cDTO.setUnit("");
		
		// Temp Code
		if ((bmi >= 26 && bmi <= 29))
		{
			_cDTO.setAlertIndicator(ORANGE);
		} 
		else if (bmi < 20)
		{
			_cDTO.setAlertIndicator(ORANGE);
		}
		else if (bmi >= 20 && bmi < 26)
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (bmi > 29)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param vitalParameter
	 * @return
	 */
	public static ArrayList<ComplianceDTO> vitalParameterAlert(
	        VitalParameter vitalParameter, CaredPerson caredPerson)
	{
		ArrayList<ComplianceDTO> _cDTOList = new ArrayList<ComplianceDTO>();
		
		double _bodyTemperature = vitalParameter.getTemprature();
		_cDTOList.add(tempratureAlert(_bodyTemperature));
		
		double _pulse = vitalParameter.getPulse();
		_cDTOList.add(pulseAlert(_pulse, caredPerson));
		
		double _systolicPressure = vitalParameter.getSystolicPressure();
		_cDTOList.add(systolicPressureAlert(_systolicPressure, caredPerson));
		
		double _diastolicPressure = vitalParameter.getDiastolicPressure();
		_cDTOList.add(diastolicPressureAlert(_diastolicPressure, caredPerson));
		
		double _bloodSugar = vitalParameter.getBloodSugar();
		_cDTOList.add(bloodSugarAlert(_bloodSugar, caredPerson));
		
		double _bloodSugarFasting = vitalParameter.getBloodSugarFasting();
		_cDTOList.add(bloodSugarAlertFasting(_bloodSugarFasting, caredPerson));
		
		return _cDTOList;
	}
	
	/**
	 * 
	 * @param temprature
	 * @return
	 */
	public static ComplianceDTO tempratureAlert(double temprature)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Temperature");
		_cDTO.setComplianceValue(new Double(temprature).toString());
		_cDTO.setUnit("F");
		
		// Temp Code
		
		if (temprature == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (temprature > 97 && temprature <= 99)
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (temprature > 99 && temprature <= 102)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (temprature > 103)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param pulse
	 * @return
	 */
	public static ComplianceDTO pulseAlert(double pulse, CaredPerson caredPerson)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Pulse");
		_cDTO.setComplianceValue(new Double(pulse).toString());
		_cDTO.setUnit("bpm");
		
		// Temp Code
		// @TODO need to add Age Factor
		if (pulse == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (pulse >= 60 & pulse <= 80)
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (pulse > 80)
		{
			_cDTO.setAlertIndicator(RED);
		} else if (pulse < 60)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param bloodPressure
	 * @return
	 */
	public static ComplianceDTO systolicPressureAlert(double bloodPressure, CaredPerson caredPerson)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Systolic(High) BP");
		_cDTO.setComplianceValue(new Double(bloodPressure).toString());
		_cDTO.setUnit("mm Hg");
		
		// Temp Code
		// @TODO need to add Age Factor
		if (bloodPressure == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (bloodPressure <= 120 && bloodPressure >= 90 )
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (bloodPressure > 120 && bloodPressure <= 139)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (bloodPressure >= 140 | bloodPressure <= 90)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param bloodPressure
	 * @return
	 */
	public static ComplianceDTO diastolicPressureAlert(double bloodPressure, CaredPerson caredPerson)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Diastolic(Low) BP");
		_cDTO.setComplianceValue(new Double(bloodPressure).toString());
		_cDTO.setUnit("mm Hg");
		
		// Temp Code
		// @TODO need to add Age Factor
		if (bloodPressure == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (bloodPressure <= 80 && bloodPressure >= 60 )
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (bloodPressure > 80 && bloodPressure < 89)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (bloodPressure >= 90)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * Validate Normal Blood Sugar
	 * 
	 * @param bloodSugar
	 * @return
	 */
	public static ComplianceDTO bloodSugarAlert(double bloodSugar, CaredPerson caredPerson)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Blood Sugar (Normal)");
		_cDTO.setComplianceValue(new Double(bloodSugar).toString());
		_cDTO.setUnit("mg/dl");
		
		// Temp Code
		// @TODO need to add Age Factor
		if (bloodSugar == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (bloodSugar <= 140 | bloodSugar >= 70)
		{
			_cDTO.setAlertIndicator(GREEN);
			if (bloodSugar > 40 | bloodSugar < 70)
			{
				_cDTO.setAlertIndicator(ORANGE);
			}
			
		} else if (bloodSugar > 140 | bloodSugar <= 180)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (bloodSugar > 180 | bloodSugar <= 40)
		{
			_cDTO.setAlertIndicator(RED);
		}
		
		return _cDTO;
	}
	
	/**
	 * 
	 * @param bloodSugar
	 * @return
	 */
	public static ComplianceDTO bloodSugarAlertFasting(double bloodSugar, CaredPerson caredPerson)
	{
		ComplianceDTO _cDTO = new ComplianceDTO();
		_cDTO.setComplianceItem("Blood Sugar (Fasting)");
		_cDTO.setComplianceValue(new Double(bloodSugar).toString());
		_cDTO.setUnit("mg/dl");
		
		// Temp Code
		// @TODO need to add Age Factor
		if (bloodSugar == 0)
		{
			_cDTO.setAlertIndicator(GRAY);
		} else if (bloodSugar <= 100 | bloodSugar >= 70)
		{
			_cDTO.setAlertIndicator(GREEN);
		} else if (bloodSugar > 100 | bloodSugar <= 120)
		{
			_cDTO.setAlertIndicator(ORANGE);
		} else if (bloodSugar > 120)
		{
			_cDTO.setAlertIndicator(RED);
		} else if (bloodSugar < 40)
		{
			_cDTO.setAlertIndicator(PURPLE);
		}
		
		return _cDTO;
	}
	
}
