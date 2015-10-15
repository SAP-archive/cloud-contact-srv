package com.sap.hana.cloud.samples.contactsrv.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object used to report (error) messages.
 *
 */
@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusMessage implements Serializable, Cloneable
{
	/**
	 * The <code>serialVersionUID</code> of the class.
	 */
    private static final long serialVersionUID = 1L;

	int code = 500;
	
	String error = null;
	
	@XmlElement(name="error_description")
	String description = null;
	
	String message = null;
	
	List<ValidationError> errors = null;

	public StatusMessage(){}
	
	public StatusMessage(int code, String error, String description, String message, ValidationError...errors)
	{
		this.setCode(code);
		this.setError(error);
		this.setDescription(description);
		this.setMessage(message);
		
		if (errors != null)
		{
			this.setErrors(Arrays.asList(errors));
		}
	}
	
	public StatusMessage(String error, String description, String message, ValidationError...errors)
	{
		this.setError(error);
		this.setDescription(description);
		this.setMessage(message);
		
		if (errors != null)
		{
			this.setErrors(Arrays.asList(errors));
		}
	}
	
	
	public List<ValidationError> getErrors()
	{
		return errors;
	}

	public void setErrors(List<ValidationError> errors)
	{
		this.errors = errors;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).appendSuper(super.toString())
	            .append("error", this.error).append("message", this.message).append("description", this.description)
	            .append("code", this.code).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
	    return new HashCodeBuilder(-950597061, 209594933).appendSuper(super.hashCode()).append(this.message)
	            .append(this.error).append(this.description).append(this.code).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object)
	{
	    if (object == this)
	    {
		    return true;
	    }
	    
	    if (!(object instanceof StatusMessage))
	    {
		    return false;
	    }
	    
	    StatusMessage rhs = (StatusMessage) object;
	    
	    return new EqualsBuilder().appendSuper(super.equals(object)).append(this.message, rhs.message)
	            .append(this.error, rhs.error).append(this.description, rhs.description).append(this.code, rhs.code)
	            .isEquals();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public StatusMessage clone()
	{
		StatusMessage retVal = new StatusMessage();
		
		retVal.setCode(this.getCode());
		
		retVal.description = (this.getDescription() == null) ? null : new String(this.getDescription());
		retVal.error = (this.getError() == null) ? null : new String(this.getError());
		retVal.message = (this.getMessage() == null) ? null : new String(this.getMessage());
		
		return retVal;
	}
	
}
	