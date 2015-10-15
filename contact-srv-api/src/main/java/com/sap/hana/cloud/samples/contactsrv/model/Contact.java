package com.sap.hana.cloud.samples.contactsrv.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A contact person.
 */
@XmlRootElement
public class Contact extends BaseObject implements Serializable
{
	/**
	 * The <code>serialVersionUID</code> of the class.
	 */
	private static final long serialVersionUID = 1L;

	protected Salutation salutation = null;
	
	protected Title title = null;
	
	@Size(max = 30, message = "{api.data_validation.max_length.error}")
	protected String firstName = null;
	
	@Size(max = 30, message = "{api.data_validation.max_length.error}")
	protected String lastName = null;
	
	@Valid
	protected List<Address> addresses = null;
	
	@Valid
	protected List<PhoneNumber> phoneNumbers = null;
	
	@Valid
	protected List<EmailAddress> emailAddresses = null;

	/**
	 * The salutation
	 */
	public Salutation getSalutation()
	{
		return salutation;
	}

	public void setSalutation(Salutation salutation)
	{
		this.salutation = salutation;
	}

	/**
	 * The title
	 */
	public Title getTitle()
	{
		return title;
	}

	public void setTitle(Title title)
	{
		this.title = title;
	}

	/**
	 * The first name 
	 */
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/*
	 * The last name
	 */
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public List<Address> getAddresses()
	{
		return addresses;
	}

	public void setAddresses(List<Address> addresses)
	{
		this.addresses = addresses;
	}

	public List<PhoneNumber> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}

	public List<EmailAddress> getEmailAddresses()
	{
		return emailAddresses;
	}

	public void setEmailAddresses(List<EmailAddress> emailAdresses)
	{
		this.emailAddresses = emailAdresses;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    return new ToStringBuilder(this).appendSuper(super.toString()).append("salutation", this.salutation).append("title", this.title)
	            .append("firstName", this.firstName).append("lastName", this.lastName).append("addresses", this.addresses)
	            .append("phoneNumbers", this.phoneNumbers).append("emailAddresses", this.emailAddresses).toString();
	}
	
}
