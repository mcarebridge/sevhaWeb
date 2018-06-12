/**
 * 
 */
package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * @author DS5002449
 * 
 */
@Model(schemaVersion = 1)
public class CareAlert extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1897603239053648308L;

    private ModelRef<Caregiver> caregiver = new ModelRef<Caregiver>(
	    Caregiver.class);

    private ModelRef<Prescription> prescription = new ModelRef<Prescription>(
	    Prescription.class);

    /**
     * @return the caregiver
     */
    public ModelRef<Caregiver> getCaregiver()
    {
	return caregiver;
    }

    /**
     * @return the prescription
     */
    public ModelRef<Prescription> getPrescription()
    {
	return prescription;
    }

}
