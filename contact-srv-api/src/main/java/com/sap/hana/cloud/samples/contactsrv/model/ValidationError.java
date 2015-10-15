package com.sap.hana.cloud.samples.contactsrv.model;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object used to report information about validation errors.
 *
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationError implements Serializable
{
	/**
	 * The <code>serialVersionUID</code> of the class.
	 */
    private static final long serialVersionUID = 1L;

    String messageKey = null;
    
    String message = null;
    
    String messageTemplate = null;
    
    String path = null;
    
    String invalidValue = null;
    
    Map<String, String> messageParameter = null;
    
    public ValidationError() {}
    
    public ValidationError(String messageKey, String message, String messageTemplate, String path, String invalidValue, Map<String, String> parameter)
    {
    	this.setMessageKey(messageKey);
    	this.setMessage(message);
    	this.setMessageTemplate(messageTemplate);
    	this.setPath(path);
    	this.setInvalidValue(invalidValue);
    	this.setMessageParameter(parameter);
 
    }
    

	public Map<String, String> getMessageParameter()
	{
		return messageParameter;
	}

	public void setMessageParameter(Map<String, String> messageParameter)
	{
		this.messageParameter = messageParameter;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessageTemplate()
	{
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate)
	{
		this.messageTemplate = messageTemplate;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getInvalidValue()
	{
		return invalidValue;
	}

	public void setInvalidValue(String invalidValue)
	{
		this.invalidValue = invalidValue;
	}

	public String getMessageKey()
	{
		return messageKey;
	}

	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}
}