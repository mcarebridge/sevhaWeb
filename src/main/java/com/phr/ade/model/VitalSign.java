/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

/**
 * @author mcarebridge
 *
 */
@Model(schemaVersion = 1)
public class VitalSign extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
    private static final long serialVersionUID = -4594047254232134764L;
    private String vitalSignName;
    
    
	public String getVitalSignName()
    {
	    return vitalSignName;
    }
	public void setVitalSignName(String vitalSignName)
    {
	    this.vitalSignName = vitalSignName;
    }
}
