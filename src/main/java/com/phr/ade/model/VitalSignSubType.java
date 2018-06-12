package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 2)
public class VitalSignSubType extends AbstractEntity implements Serializable
{
	
    /**
	 * 
	 */
    private static final long serialVersionUID = 2520656776480682129L;
    private ModelRef<VitalSign> vitalSign      = new ModelRef<VitalSign>(
    		VitalSign.class);

    private String vitalSignSubTypeName;
    private String vitalSignSubTypeUnit;

	public String getVitalSignSubTypeName()
    {
	    return vitalSignSubTypeName;
    }

	public void setVitalSignSubTypeName(String vitalSignSubTypeName)
    {
	    this.vitalSignSubTypeName = vitalSignSubTypeName;
    }
	
	public ModelRef<VitalSign> getVitalSign()
    {
	    return vitalSign;
    }

	public String getVitalSignSubTypeUnit()
    {
	    return vitalSignSubTypeUnit;
    }

	public void setVitalSignSubTypeUnit(String vitalSignSubTypeUnit)
    {
	    this.vitalSignSubTypeUnit = vitalSignSubTypeUnit;
    }
}
