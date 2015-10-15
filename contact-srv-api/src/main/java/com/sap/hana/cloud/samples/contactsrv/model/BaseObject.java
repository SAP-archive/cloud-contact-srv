package com.sap.hana.cloud.samples.contactsrv.model;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Base class for all domain model objects.
 */
public abstract class BaseObject 
{

	/**
	 * The (globally unique) ID of the object.
	 */
	@NotNull(message = "{model.object.id.not_null.error}")
	@Size(max = 36, message = "{api.data_validation.max_length.error}")
    private String id = UUID.randomUUID().toString();

	/**
	 * The {@link Date} the object was created at.
	 */
    private Date createdAt = null;
 
    /**
	 * The {@link Date} the object was last modified at.
	 */
    private Date lastModifiedAt = null;

    /**
     * ID of the user who created the object.
     */
    @Size(max = 20, message = "{api.data_validation.max_length.error}")
    private String createdBy = null;
    
    /**
     * ID of the user who was the last to modify the object.
     */
    @Size(max = 20, message = "{api.data_validation.max_length.error}")
    private String lastModifiedBy = null;
    
    /**
     * The version number used for optimistic locking.
     * 
     * @see http://en.wikibooks.org/wiki/Java_Persistence/Locking
     * @see http://eclipse.org/eclipselink/documentation/2.4/jpa/extensions/a_optimisticlocking.htm
     */
    private Long version = 0L;
    
    /**
     * Life-cycle event callback, which automatically sets the last modification date.  
     */
    protected void updateAuditInformation() 
    {
        lastModifiedAt = new Date();
        
        // TODO - obtain currently logged-on user
    }

    /**
     * Life-cycle event callback, which automatically creates a unique ID for the object
     * and populates its audit information.  
     */
    protected void generateAuditInformation() 
    {   
        final Date now = new Date();
        
        createdAt = now;
        lastModifiedAt = now;
        
        // TODO - obtain currently logged-on user
    }

    public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
    
	public Date getCreatedAt()
	{
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt()
	{
		return this.lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt)
	{
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getCreatedBy()
	{
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy()
	{
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    return new ToStringBuilder(this).append("id", this.id).append("createdAt", this.createdAt).append("createdBy", this.createdBy)
	           .append("lastModifiedAt", this.lastModifiedAt).append("lastModifiedBy", this.lastModifiedBy).append("version", this.version).toString();
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public final boolean equals(Object obj) 
	{
        if (this == obj) 
        {
        	return true;
        }
        
        if (obj == null || !(obj instanceof BaseObject)) 
        {
            return false;
        }

        BaseObject other = (BaseObject) obj;

        if (id == null) 
        {
        	return false;
        }

        return id.equals(other.getId());
    }

	/**
	 * @see java.lang.Object#hashCode()
	 */
    public final int hashCode() 
    {
        if (id != null) 
        {
            return id.hashCode();
        } 
        else 
        {
            return super.hashCode();
        }
    }
}
