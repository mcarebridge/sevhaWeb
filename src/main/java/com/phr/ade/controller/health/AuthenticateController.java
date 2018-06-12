package com.phr.ade.controller.health;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.phr.ade.model.Profile;
import com.phr.ade.security.Encrypt;
import com.phr.ade.service.ProfileService;
import com.phr.ade.service.SecurityService;
import com.phr.ade.util.CareMailUtil;
import com.phr.ade.util.CareUtil;
import com.phr.ade.values.CareMailDTO;

public class AuthenticateController extends BaseController
{
	
	private static Logger logger = Logger.getLogger(AuthenticateController.class
	                                     .getName());
	private String        action;
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		
		String fwdPage = "care.jsp";
		action = requestScope("actionParam");
		logger.log(Level.INFO, "ACTION = " + action);
		String _timezoneoffset = requestScope("timezoneoffset");
		sessionScope("timezoneoffset", _timezoneoffset);
		pageTitle = "title.dashboard";
		
		Validators v = new Validators(request);
		boolean _isFormValid = validate(v);
		
		if (action != null && action.equalsIgnoreCase("AUTH"))
		{
			if (!_isFormValid)
			{
				showErrBox = true;
			}
			
			if (_isFormValid)
			{
				
				Profile _profile = new Profile();
				_profile.setEmail(((String) requestScope("username"))
				        .toLowerCase());
				_profile.setPassword((String) requestScope("password"));
				
				SecurityService _service = new SecurityService();
				_profile = _service.authUser(_profile);
				
				if (_profile == null)
				{
					fwdPage = "care.jsp";
					v.getErrors().put("AUTHFAILED", "LoginId or Password not matched");
					System.out.println("--- Unable to Authenticate ---");
					showErrBox = true;
				} else
				{
					sessionScope("profile", _profile);
					sessionScope("profileId", new Long(_profile.getKey()
					        .getId()));
					
					if (_profile.getFirstTimeUser())
					{
						fwdPage = "load/firstTimeUser.jsp";
					} else
					{
						paintDashboard();
						fwdPage = "load/careDashboard.jsp";
					}
				}
				
			}
		} else if (action != null && action.equalsIgnoreCase("1STTIMECHK"))
		{
			SecurityService _service = new SecurityService();
			_profileId = (Long) sessionScope("profileId");
			System.out.println("---Profile Id ----" + _profileId);
			_service.firstTimeUserUpdate(_profileId);
			fwdPage = "load/careDashboard.jsp";
		} else if (action != null && action.equalsIgnoreCase("CHGPASSWORD"))
		{
			logger.log(Level.INFO, "ACTION = " + action);
			
			if (!_isFormValid)
			{
				showErrBox = true;
			} else
			{
				
				Profile _profile = new Profile();
				_profile.setEmail(((String) requestScope("username"))
				        .toLowerCase());
				SecurityService _service = new SecurityService();
				_profile = _service.getProfileByUserId(_profile);
				boolean _validUserId = false;
				String _fullpath = "";
				
				if (_profile != null)
				
				{
					_validUserId = true;
					String _userIdEnc = Encrypt.encodeString(_profile
					        .getEmail());
					String _passwordEnc = Encrypt.encodeString(_profile
					        .getPassword());
					
					logger.log(Level.INFO, "Enc string = " + _userIdEnc + "&"
					        + _passwordEnc);
					
					HttpServletRequest _request = (HttpServletRequest) this.request;
					String _serverName = _request.getServerName();
					
					String _path = _request.getRequestURI().substring(
					        request.getContextPath().length());
					
					_fullpath = "https://" + _serverName + _path + "?u="
					        + _userIdEnc + "&" + "p=" + _passwordEnc
					        + "&actionParam=REGENPASS";
					
					logger.log(Level.INFO, "path to click = " + _fullpath);
					
					requestScope("validUserId", _validUserId);
					
					// send partially hidded email to user
					
					requestScope("maskedEmail",
					        CareUtil.partialMaskEmail(_profile.getEmail()));
					
					// send Mail.
					CareMailUtil _cmail = CareMailUtil.getInstance();
					CareMailDTO _cMailDTO = new CareMailDTO();
					_cMailDTO.setToAddress(_profile.getEmail());
					_cMailDTO
					        .setMailsubject("sevha : password reset notification");
					_cMailDTO.setRecepiantName(_profile.getFirstname());
					
					StringBuffer _sb = new StringBuffer();
					_sb.append("Dear " + _profile.getFirstname() + ",");
					_sb.append("\n");
					_sb.append("Please use the following url to reset your password. ");
					_sb.append("\n");
					_sb.append(_fullpath);
					_sb.append("\n");
					_sb.append("Sincerly,");
					_sb.append("\n");
					_sb.append("Customer Support");
					_cMailDTO.setMailBody(_sb.toString());
					_cmail.sendSimpleMail(_cMailDTO);
					
					fwdPage = "load/loadpasswordreset.jsp";
				} else
				{
					showErrBox = true;
					v = new Validators(request);
					v.getErrors().put("AUTHFAILED", "User name not found");
					fwdPage = "care.jsp";
				}
				
			}
			
			// fwdPage = "update/updatepassword.jsp";
		}
		/**
		 * This logic is added in BaseController if session is null
		 */
		
		else if (action != null && action.equalsIgnoreCase("REGENPASS"))
		{
			String _userNameEnc = (String) requestScope("u");
			String _userName = Encrypt.decodeString(_userNameEnc);
			request.setAttribute("username", _userName);
			
			fwdPage = "update/updatepassword.jsp";
		} else if (action != null && action.equalsIgnoreCase("UPDATEPASSWORD"))
		{
			logger.log(Level.INFO, "ACTION = " + action);
			
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "update/updatepassword.jsp";
			} else
			{
				String _username = (String) requestScope("username");
				String _password = (String) requestScope("password");
				String _passwordEnc = Encrypt.encodeString(_password);
				
				Profile _profile = new Profile();
				_profile.setEmail(_username.toLowerCase());
				_profile.setPassword(_passwordEnc);
				
				ProfileService _ps = new ProfileService();
				_ps.updateProfile(_profile);
				
				fwdPage = "/health";
			}
		} 
//			else if (action != null && action.equalsIgnoreCase("APPLN"))
//		{
//			fwdPage = "care.jsp";
//			pageTitle = "title.welcome";
//		}
		
		return forward(fwdPage);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v)
	{
		// v.add("location", v.required());
		if (action != null && action.equalsIgnoreCase("AUTH"))
		{
			v.add("username", v.required("User Name"));
			v.add("password", v.required("Password"));
		} else if (action != null && action.equalsIgnoreCase("UPDATEPASSWORD"))
		{
			v.add("password", v.required("Password"), v.minlength(8),
			        v.maxlength(12), v.regexp(passwordRegex, passwordRegexMsg));
			
		} else if (action != null && action.equalsIgnoreCase("CHGPASSWORD"))
		{
			v.add("username", v.required("User Name"));
		}
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
}
