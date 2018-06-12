package com.phr.ade.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Text;

@Model(schemaVersion = 2)
public class MasterData extends AbstractEntity implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 248835146493323996L;
    private String entityType;
    private Text data;

    public String getEntityType()
    {
	return entityType;
    }

    public void setEntityType(String entityType)
    {
	this.entityType = entityType;
    }

    public Text getData()
    {
	return data;
    }

    public void setData(Text data)
    {
	this.data = data;
    }

}
