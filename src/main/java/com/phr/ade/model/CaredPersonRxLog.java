package com.phr.ade.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 2)
public class CaredPersonRxLog extends AbstractEntity implements Serializable
{
	private ModelRef<CaredPersonRxResponse> caredPersonResponse = new ModelRef<CaredPersonRxResponse>(
	                                                                    CaredPersonRxResponse.class);
	
	private ModelRef<PrescriptionLines>     prescriptionLines   = new ModelRef<PrescriptionLines>(
	                                                                    PrescriptionLines.class);
	
	private Date                            responseDateTime;
	
	public ModelRef<CaredPersonRxResponse> getCaredPersonResponse()
	{
		return caredPersonResponse;
	}
	
	public ModelRef<PrescriptionLines> getPrescriptionLines()
	{
		return prescriptionLines;
	}
	
	public Date getResponseDateTime()
	{
		return responseDateTime;
	}
	
	public void setResponseDateTime(Date responseDateTime)
	{
		this.responseDateTime = responseDateTime;
	}
	
}
