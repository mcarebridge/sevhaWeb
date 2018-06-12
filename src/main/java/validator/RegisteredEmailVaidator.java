package validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

import com.phr.ade.model.Profile;
import com.phr.ade.service.ProfileService;

public class RegisteredEmailVaidator extends AbstractValidator
{
	
	public RegisteredEmailVaidator()
	{
		
	}
	
	public RegisteredEmailVaidator(String message)
	{
		
		super(message);
		System.out.println("------>" + "RegisteredEmailVaidator.constructor  "
		        + message);
	}
	
	@Override
	protected String getMessageKey()
	{
		// TODO Auto-generated method stub
		return "validator.registeredEmail";
	}
	
	@Override
	public String validate(Map<String, Object> parameters, String name)
	{
		System.out.println("------>"
		        + "RegisteredEmailVaidator.validate name --- " + name);
		// TODO Auto-generated method stub
		Object value = parameters.get(name);
		System.out.println("------>"
		        + "RegisteredEmailVaidator.validate value --- " + value);
		if (value == null || "".equals(value))
		{
			return null;
		}
		
		try
		{
			String _emailAddress = (String) value;
			ProfileService _ps = new ProfileService();
			Profile _profile = new Profile();
			_profile.setEmail(_emailAddress);
			_profile = _ps.searchProfileByEmail(_profile);
			
			
			if (_profile != null && _profile.getKey() != null)
			{
				throw new Error();
			}
			
			return null;
		} catch (Throwable ignore)
		{
			if (message != null)
			{
				return message;
			}
			return ApplicationMessage.get(getMessageKey(), getLabel(name));
		}
	}
	
}
