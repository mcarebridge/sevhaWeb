/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.phr.ade.model.CaredPerson;
import com.phr.ade.model.VitalParameter;
import com.phr.ade.service.CareService;
import com.phr.ade.service.CaredPersonHealthHelper;
import com.phr.ade.service.VitalParameterService;

/**
 * @author deejay
 * 
 */
public class VitalParametersController extends BaseController
{
	private static Logger logger = Logger.getLogger(VitalParametersController.class
	                                     .getName());
	
	@Override
	public Navigation executeLogic() throws Exception
	{
		String action = requestScope("actionParam");
		String fwdPage = "load/vitalParameters.jsp";
		String _careNeedyKey = (String) sessionScope("selectedCaredId");
		
		boolean _isFormValid = false;
		
		Validators v = new Validators(request);
		_isFormValid = validate(v);
		
		if (action != null && action.equalsIgnoreCase("CREVITPARA"))
		{
			if (!_isFormValid)
			{
				showErrBox = true;
				fwdPage = "create/vitalParameters.jsp";
			} else
			{
				CareService _cs = new CareService();
				VitalParameter _vp = populateVitalParamter(Long
				        .parseLong(_careNeedyKey));
				_cs.addVitalParameters(_vp);
				
				List<VitalParameter> _vpList = _cs
				        .listLastSevenVitalParameter(Long
				                .parseLong(_careNeedyKey));
				requestScope("vitalParaList", _vpList);
			}
		} else if (action != null && action.equalsIgnoreCase("LISTVITALPARA"))
		{
			CareService _cs = new CareService();
			Long _selectedCaredId = extractSelectedCareId();
			
			List<VitalParameter> _vpList = _cs
			        .listLastSevenVitalParameter(_selectedCaredId);
			
			// Get the Last loaded Parameters
			
			VitalParameterService _vps = new VitalParameterService();
			VitalParameter _vitalPara = _vps.lastTakenVitalParameter(new Long(
			        careneedy));
			
			// Last Take Height and Weight
			// int _lastIndex = _vpList.size();
			// _lastIndex--;
			//
			// double _lastHeight = 0;
			// double _lastWeight = 0;
			// double _lastHeightInMts = 0;
			// double _bmi = 0;
			//
			// if (_vpList != null & _lastIndex > 0)
			// {
			// _lastHeight = _vpList.get(_lastIndex).getHeight();
			// _lastWeight = _vpList.get(_lastIndex).getWeight();
			// _lastHeightInMts = (_lastHeight / 100);
			// _bmi = CaredPersonHealthHelper.calculateBMI(_lastHeight,
			// _lastWeight);
			// }
			
			double _bmi = 0;
			
			if (_vitalPara != null)
			
			{
				_bmi = CaredPersonHealthHelper.calculateBMI(
				        _vitalPara.getHeight(), _vitalPara.getWeight());
				
				requestScope("lastTakenHeight", _vitalPara.getHeight());
				requestScope("lastTakenWeight", _vitalPara.getWeight());
				requestScope("bmi", _bmi);
				
				requestScope("vitalParaList", _vpList);
			}
		}
		
		return forward(fwdPage);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private boolean validate(final Validators v)
	{
		String _temp = request.getParameter("temperature");
		String _bloodSugar = request.getParameter("bloodSugar");
		String _systolicPressure = request.getParameter("systolicPressure");
		String _diastolicPressure = request.getParameter("systolicPressure");
		
		System.out.println("Temp---->" + _temp);
		
		v.add("height", v.required("Height"), v.maxlength(5),
		        v.doubleType("Height must be numeric"));
		
		v.add("weight", v.required("Weight"), v.maxlength(5),
		        v.doubleType("Weight must be numeric"),
		        v.doubleRange(1, 500, "Invalid Body Weight"));
		
		if (!(_temp.equalsIgnoreCase("")))
		{
			v.add("temperature", v.required("Temperature"),
			        v.doubleType("Temperature must be numeric"),
			        v.doubleRange(96, 107, "Invalid Body Temperature Range"));
		}
		
		v.add("pulse", v.required("Pulse"), v.maxlength(3),
		        v.doubleType("Pulse must be numeric"),
		        v.doubleRange(20, 200, "Invalid Pulse Range"));
		
		v.add("systolicPressure", v.required("BP (High)"), v.maxlength(5),
		        v.doubleType("BP (High) must be numeric"),
		        v.doubleRange(50, 250, "Invalid BP Range"));
		
		v.add("diastolicPressure", v.required("BP (Low)"), v.maxlength(5),
		        v.doubleType("BP (Low) must be numeric"),
		        v.doubleRange(50, 250, "Invalid BP Range"));
		
		if (!(_bloodSugar.equalsIgnoreCase("")))
		{
			v.add("bloodSugar", v.required("Blood Sugar"), v.maxlength(5),
			        v.doubleType("Blood Sugar must be numeric"),
			        v.doubleRange(50, 300, "Invalid Blood Sugar Range"));
		}
		
		v.add("bloodSugarFasting", v.required("Blood Sugar (Fasting)"),
		        v.maxlength(5),
		        v.doubleType("Blood Sugar(Fasting) must be numeric"),
		        v.doubleRange(50, 300, "Invalid Blood Sugar (Fasting) Range"));
		
		boolean valid = v.validate();
		requestScope("errorSize", new Integer(v.getErrors().size()));
		return valid;
	}
	
	private VitalParameter populateVitalParamter(Long _careNeedyKey)
	        throws Exception
	{
		VitalParameter _vp = new VitalParameter();
		
		String _temp = request.getParameter("temperature");
		String _bloodSugar = request.getParameter("bloodSugar");
		
		if (_temp.equalsIgnoreCase(""))
		{
			_temp = "0";
		}
		
		if (_bloodSugar.equalsIgnoreCase(""))
		{
			_bloodSugar = "0";
		}
		
		CareService _cs = new CareService();
		CaredPerson _cp = _cs.loadCaredPerson(_careNeedyKey);
		
		_vp.getCaredPerson().setModel(_cp);
		
		_vp.setHeight(new Double(request.getParameter("height")));
		_vp.setWeight(new Double(request.getParameter("weight")));
		_vp.setTemprature(new Double(_temp));
		_vp.setPulse(new Double(request.getParameter("pulse")));
		_vp.setSystolicPressure(new Double(request
		        .getParameter("systolicPressure")));
		_vp.setDiastolicPressure(new Double(request
		        .getParameter("diastolicPressure")));
		_vp.setBloodSugar(new Double(_bloodSugar));
		
		_vp.setBloodSugarFasting(new Double(request
		        .getParameter("bloodSugarFasting")));
		
		return _vp;
	}
	
}
