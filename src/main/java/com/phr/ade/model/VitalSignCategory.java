package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;


@Model(schemaVersion = 2)
public class VitalSignCategory extends AbstractEntity implements Serializable
{
	/**
	 * 
	 */
    private static final long serialVersionUID = -8801807166296774770L;
    private String vitalSignCategoryName;
    private String klass;
    
    private ModelRef<VitalSignSubType> vitalSignSubType      = new ModelRef<VitalSignSubType>(
    		VitalSignSubType.class);

    
	public String getVitalSignCategoryName()
    {
	    return vitalSignCategoryName;
    }
	public void setVitalSignCategoryName(String vitalSignCategoryName)
    {
	    this.vitalSignCategoryName = vitalSignCategoryName;
    }
	
	
	public ModelRef<VitalSignSubType> getVitalSignSubType()
    {
	    return vitalSignSubType;
    }
	public String getKlass()
    {
	    return klass;
    }
	public void setKlass(String klass)
    {
	    this.klass = klass;
    }	
}
