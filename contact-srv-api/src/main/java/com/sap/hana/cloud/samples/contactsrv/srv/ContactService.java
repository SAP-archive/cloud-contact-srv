package com.sap.hana.cloud.samples.contactsrv.srv;

import java.util.List;

import com.sap.hana.cloud.samples.contactsrv.model.Contact;

/**
 * Provides CRUD (Create, Read, Update, Delete) operations as well as other
 * life-cycle related functions for {@link Contact} objects.
 */
public interface ContactService
{

	/**
	 * Creates the specified {@link Contact}.
	 * 
	 * @param contact The {@link Contact} to create
	 * @return The {@link Contact} 
	 * @throws ServiceException In case of an error
	 */
	Contact createContact(Contact contact) throws ServiceException;

	/**
	 * Saves the specified {@link Contact}.
	 * 
	 * @param contact The {@link Contact} to save
	 * @return The {@link Contact} 
	 * @throws ServiceException In case of an error
	 */
	Contact saveContact(Contact contact) throws ServiceException;
	
	/**
	 * Deletes the specified {@link Contact}.
	 * 
	 * @param contact The {@link Contact} to delete
	 * @throws ServiceException In case of an error
	 */
	void deleteContact(Contact contact) throws ServiceException;

	/**
	 * Returns a {@link List} of all {@link Contact} objects.
	 * 
	 * @return {@link List} of all {@link Contact} objects
	 * @throws ServiceException In case of an error
	 */
	List<Contact> getAllContactes() throws ServiceException;

	/**
	 * Returns the {@link Contact} with the specified ID or <code>NULL</code> if no {@link Contact} object with the specified ID exists.
	 * 
	 * @param id The id of the  {@link Contact} to retrieve
	 * @return The {@link Contact} with the specified ID or <code>NULL</code> if no {@link Contact} object with the specified ID exists
	 * @throws ServiceException In case of an error
	 */
	Contact getContactById(String id) throws ServiceException;

	/**
	 * Updates the specified {@link Contact}.
	 * 
	 * @param contact The {@link Contact} to update
	 * @return The {@link Contact} 
	 * @throws ServiceException In case of an error
	 */
	Contact updateContact(Contact contact) throws ServiceException;

}
