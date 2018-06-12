package com.phr.ade.controller.health;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Text;
import com.phr.ade.model.MasterData;
import com.phr.ade.model.PhysicianSpeciality;
import com.phr.ade.persistence.CareDAO;
import com.phr.ade.persistence.EntityUtilDAO;
import com.phr.ade.util.MasterDataUtil;

public class AdminDataController extends Controller {
	String actionParam = null;
	String pageTitle = "title.admin";
	String fwdPage = "admin/adminDataControl.jsp";

	@Override
	protected Navigation run() throws Exception {
		actionParam = (String) requestScope("actionParam");
		System.out.println("actionParam --------->" + actionParam);

		if (actionParam != null && actionParam.equalsIgnoreCase("SPECIALITY")) {
			fwdPage = "admin/addSpecialityData.jsp";
		} else if (actionParam != null && actionParam.equalsIgnoreCase("ADDSPECIALITY")) {
			fwdPage = addSpecialityData();
		}

		else if (actionParam != null && actionParam.equalsIgnoreCase("DATACONTROL")) {
			fwdPage = "admin/adminDataControl.jsp";
		} else if (actionParam != null && actionParam.equalsIgnoreCase("MASTER")) {
			fwdPage = "admin/masterDataControl.jsp";
		} else if (actionParam != null && actionParam.equalsIgnoreCase("ADDMASTER")) {

			String entityType = (String) requestScope("entityType");
			String masterdata = (String) requestScope("masterdata");

			MasterData _masterData = new MasterData();

			_masterData.setEntityType(entityType);
			_masterData.setData(new Text(masterdata));

			EntityUtilDAO _entityDAO = new EntityUtilDAO();
			_entityDAO.addMasterData(_masterData);

			fwdPage = "admin/masterDataControl.jsp";
		} else if (actionParam != null && actionParam.equalsIgnoreCase("LDMASTERDATA")) {
			MasterDataUtil _mdu = new MasterDataUtil();
			_mdu.addEntity();
		} 
		requestScope("pageTitle", pageTitle);
		// TODO Auto-generated method stub
		return forward(fwdPage);
	}

	/**
	 * 
	 */
	private String addSpecialityData() {

		PhysicianSpeciality _psModel = new PhysicianSpeciality();
		String _speciality = (String) requestScope("speciality");
		_psModel.setSpeciality(_speciality);

		CareDAO _careDAO = new CareDAO();
		_careDAO.addModel(_psModel);

		return "admin/addSpecialityData.jsp";
	}
}
