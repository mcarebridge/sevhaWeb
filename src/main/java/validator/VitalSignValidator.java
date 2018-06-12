package validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

import com.phr.ade.model.Profile;
import com.phr.ade.service.ProfileService;

public class VitalSignValidator extends AbstractValidator
{
	
	public VitalSignValidator()
	{
	}
	
	public VitalSignValidator(String message)
	{
		super(message);
	}
	
	@Override
	public String validate(Map<String, Object> parameters, String name)
	{
		Object value = parameters.get(name);
		if (value == null || "".equals(value))
		{
			return null;
		}
		
		try
		{
			String _vital = (String) value;
			Integer _vitalInt = new Integer(_vital);
			
			if (name.equals("pulse"))
			{
				if (_vitalInt < 30 || _vitalInt > 200)
				{
					throw new Error();
				}
			} else if (name.equals("systolicPressure"))
			{
				if (_vitalInt < 30 || _vitalInt > 200)
				{
					throw new Error();
				}
			} else if (name.equals("diastolicPressure"))
			{
				if (_vitalInt < 30 || _vitalInt > 200)
				{
					throw new Error();
				}
			} else if (name.equals("bloodSugarNormal"))
			{
				if (_vitalInt < 50 || _vitalInt > 300)
				{
					throw new Error();
				}
			} else if (name.equals("bloodSugarFasting"))
			{
				if (_vitalInt < 30 || _vitalInt > 200)
				{
					throw new Error();
				}
			} else if (name.equals("sugarhb1ac"))
			{
				if (_vitalInt < 1 || _vitalInt > 12)
				{
					throw new Error();
				}
			} 
			else if (name.equals("height"))
			{
				if (_vitalInt < 24 || _vitalInt > 220)
				{
					throw new Error();
				}
			} else if (name.equals("height"))
			{
				if (_vitalInt < 3 || _vitalInt > 220)
				{
					throw new Error();
				}
			} else if (name.equals("temperature"))
			{
				if (_vitalInt < 96 || _vitalInt > 108)
				{
					throw new Error();
				}
			}
			
			return null;
		} catch (Throwable ignore)
		{
			if (message != null)
			{
				return message;
			}
			return ApplicationMessage.get(getMessageKey(),
			        resolveLabel(getLabel(name)));
		}
	}
	
	@Override
	protected String getMessageKey()
	{
		// TODO Auto-generated method stub
		return "validator.vital";
	}
	
	/**
	 * 
	 * @param label
	 * @return
	 */
	private String resolveLabel(String label)
	{
		String _resolvedLabel = "Vital Sign";
		
		if (label.equals("pulse"))
		{
			_resolvedLabel = "Pulse Rate";
		} else if (label.equals("systolicPressure"))
		{
			_resolvedLabel = "High Blood Pressure";
		} else if (label.equals("diastolicPressure"))
		{
			_resolvedLabel = "Low Blood Pressure";
		} else if (label.equals("bloodSugar"))
		{
			_resolvedLabel = "Normal Blood sugar";
		} else if (label.equals("bloodSugarFasting"))
		{
			_resolvedLabel = "Fasting Blood Sugar";
		}
		else if (label.equals("height"))
		{
			_resolvedLabel = "Height";
		}
		else if (label.equals("weight"))
		{
			_resolvedLabel = "Weight";
		}
		else if (label.equals("temperature"))
		{
			_resolvedLabel = "Temperature";
		}
		
		return _resolvedLabel;
	}
	
}
