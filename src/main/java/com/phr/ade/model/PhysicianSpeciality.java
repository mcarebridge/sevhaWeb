package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

@Model(schemaVersion = 1)
public class PhysicianSpeciality extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8581954906651697353L;
    private String speciality;

    public String getSpeciality()
    {
	return speciality;
    }

    public void setSpeciality(String speciality)
    {
	this.speciality = speciality;
    }
}
