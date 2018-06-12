/**
 * 
 */
package com.phr.ade.model;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

/**
 * @author DS5002449
 * 
 */
public class AbstractEntity
{

    @Attribute(primaryKey = true)
    private Key key;
    @Attribute(version = true)
    private Long version;
    @Attribute(listener = CreationDate.class)
    private Date createdDate;
    @Attribute(listener = ModificationDate.class)
    private Date updatedDate;
    // Use user Id
    private String createdBy;
    // Use user Id
    private String updatedBy;

    /**
     * @return the key
     */
    public Key getKey()
    {
	return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(Key key)
    {
	this.key = key;
    }

    /**
     * @return the version
     */
    public Long getVersion()
    {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(Long version)
    {
	this.version = version;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate()
    {
	return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate)
    {
	this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate()
    {
	return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate)
    {
	this.updatedDate = updatedDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy()
    {
	return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy)
    {
	this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy()
    {
	return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy)
    {
	this.updatedBy = updatedBy;
    }

}
