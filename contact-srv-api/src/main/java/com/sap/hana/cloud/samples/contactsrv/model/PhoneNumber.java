package com.sap.hana.cloud.samples.contactsrv.model;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The phone number of a {@link Contact}.
 */
public class PhoneNumber extends BaseObject implements Serializable
{
	/**
	 * The <code>serialVersionUID</code> of the class.
	 */
	private static final long serialVersionUID = 1L;

	protected CommunicationType type = null;
	
	@Size(max = 30, message = "{api.data_validation.max_length.error}")
	//@ValidPhoneNumber(message = "{model.phone_numer.number.validity.error}")
	protected String number = null;

	public CommunicationType getType()
	{
		return type;
	}

	public void setType(CommunicationType type)
	{
		this.type = type;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    return new ToStringBuilder(this).appendSuper(super.toString()).append("type", this.type).append("number", this.number).toString();
	}
}
