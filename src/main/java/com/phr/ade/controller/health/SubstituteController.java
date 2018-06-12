/**
 * 
 */
package com.phr.ade.controller.health;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.phr.ade.model.Prescription;
import com.phr.ade.model.PrescriptionLines;
import com.phr.ade.model.Substitute;
import com.phr.ade.service.PrescriptionService;

/**
 * @author DS5002449
 * 
 */
public class SubstituteController extends BaseController
{

    private static Logger logger = Logger.getLogger(SubstituteController.class
	    .getName());

    private String fwdPage = "update/subsitute.jsp";

    @Override
    public Navigation executeLogic() throws Exception
    {

	String action = requestScope("actionParam");
	Long _profileId = (Long) sessionScope("profileId");
	String _caregiverId = (String) requestScope("caregiver");
	String _physicianId = (String) requestScope("physician");
//	Long _selectedCaredId = (Long) sessionScope("selectedCaredId");
	
	Long _selectedCaredId = extractSelectedCareId();
	
	String _key = (String) requestScope("key");
	pageTitle = "title.substitute";

	pageTitle = "title.alert";
	System.out.println("Page Title : " + pageTitle);

	if (action != null && action.equalsIgnoreCase("CRESUBS"))
	{

	    Validators v = new Validators(request);
	    boolean _isFormValid = validateCreateSubstitue(v);

	    if (!_isFormValid)
	    {
		showErrBox = true;
		fwdPage = "create/substitute.jsp";
	    } else
	    {

		PrescriptionService _ps = new PrescriptionService();
		Substitute _substitute = populateSubstitute(new Long(_key));
		PrescriptionLines _pl = _substitute.getPrescriptionLines()
			.getModel();
		Prescription _pres = _pl.getPrescription().getModel();

		_ps.createSubstitute(_substitute);

		List<PrescriptionLines> _psLinesList = _ps
			.loadPrescriptionLines(_pres.getKey().getId(), false);

		requestScope("prescription", _pres);
		requestScope("prescriptionLinesList", _psLinesList);

		fwdPage = "load/prescriptionLines.jsp";
	    }
	} else if (action != null && action.equalsIgnoreCase("LOADSUBS"))
	{
	    fwdPage = "update/substitute.jsp";
	} else if (action != null && action.equalsIgnoreCase("PRESLINES"))
	{
	    System.out.println("PRESLINES--->" + _key);
	    PrescriptionService _ps = new PrescriptionService();

	    PrescriptionLines _psLines = _ps
		    .loadPrescriptionLine(new Long(_key));
	    Prescription _prescription = _psLines.getPrescription().getModel();
	    List<PrescriptionLines> _pLinesList = _ps
		    .loadPrescriptionLines(_prescription.getKey().getId(), false);

	    requestScope("prescription", _prescription);
	    requestScope("prescriptionLinesList", _pLinesList);
	    fwdPage = "load/prescriptionLines.jsp";
	}

	return forward(fwdPage);
    }

    /**
     * 
     * @param v
     * @return
     */
    private boolean validateCreateSubstitue(final Validators v)
    {

	v.add("subsreason", v.required("subsreason"), v.byteType(),
		v.regexp("^[1-9]", "Select a Reason for Substitute"));
	v.add("suggestedBy", v.required("suggestedBy"), v.byteType(),
		v.regexp("^[1-9]", "Provide Suggested By"));

	v.add("substitutedrug", v.required("Substitute Drug"));
	v.add("strength", v.required("Strength"));

	boolean valid = v.validate();
	requestScope("errorSize", new Integer(v.getErrors().size()));
	return valid;
    }

    /**
     * 
     * @param _prescriptionLineId
     * @return
     * @throws Exception
     */
    private Substitute populateSubstitute(Long _prescriptionLineId)
	    throws Exception
    {
	PrescriptionService _ps = new PrescriptionService();
	Substitute _subs = new Substitute();
	PrescriptionLines _plines = _ps
		.loadPrescriptionLine(_prescriptionLineId);

	_subs.getPrescriptionLines().setModel(_plines);
	_subs.setSubstitueDrug((String) requestScope("substitutedrug"));
	_subs.setStrength((String) requestScope("strength"));
	_subs.setSubstitutionSuggestedBy((String) requestScope("suggestedBy"));
	_subs.setReasonForSubstitute((String) requestScope("subsreason"));
	return _subs;
    }

}
