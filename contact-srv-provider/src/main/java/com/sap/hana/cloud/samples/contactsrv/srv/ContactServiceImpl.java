package com.sap.hana.cloud.samples.contactsrv.srv;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sap.hana.cloud.samples.contactsrv.dao.ContactDAO;
import com.sap.hana.cloud.samples.contactsrv.model.Contact;

/**
 * Default implementation of the {@link ContactService} interface.
 */
@Service
@Primary
public class ContactServiceImpl extends BaseService implements ContactService
{
	/**
	 * The {@link Logger} to be used. Declared here so that it shows up in logging console early on.
	 */
	@SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
	
	/** 
	 * The {@link ContactDAO} used for persistence operations. 
	 */
	@Autowired
	ContactDAO contactDAO;

	/**
	 * {@inheritDoc}
	 */
	public Contact createContact(@Valid Contact contact) throws ServiceException
	{
		return this.saveContact(contact);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Contact saveContact(@Valid Contact contact) throws ServiceException
	{
		Contact retVal = null;
		
		try
		{
			retVal = contactDAO.save(contact);
		}
		catch (Exception ex)
		{
			this.handleException(ex);
		}
		
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Contact> getAllContactes() throws ServiceException
	{
		List<Contact> retVal = null;
		
		try
		{
			Iterable<Contact> result = contactDAO.findAll();
			
			if (result instanceof List)
				retVal = (List) result;
		}
		catch (Exception ex)
		{
			this.handleException(ex);
		}
		
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteContact(Contact contact) throws ServiceException
	{
		try
		{	
			contactDAO.delete(contact);
		}
		catch (Exception ex)
		{
			this.handleException(ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Contact getContactById(String id) throws ServiceException
	{
		Contact retVal = new Contact();
		
		try
		{
			retVal = contactDAO.findOne(id);
		}
		catch (Exception ex)
		{
			this.handleException(ex);
		}
		
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	public Contact updateContact(@Valid Contact contact) throws ServiceException
	{
		return this.saveContact(contact);
	}
}