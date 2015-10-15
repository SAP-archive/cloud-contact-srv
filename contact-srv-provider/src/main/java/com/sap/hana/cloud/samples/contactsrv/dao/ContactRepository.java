package com.sap.hana.cloud.samples.contactsrv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sap.hana.cloud.samples.contactsrv.model.Address;
import com.sap.hana.cloud.samples.contactsrv.model.Contact;

/**
 * Interface describing the life-cycle operations (e.g. CRUD operations) for {@link Contact} objects.
 */
public interface ContactRepository extends CrudRepository<Contact, String> 
{
	/**
	 * Returns a {@link List} of all {@link Contact} objects.
	 * 
	 * @return A {@link List} of all {@link Contact} objects
	 */
	@Query("SELECT DISTINCT c from Contact c JOIN FETCH c.addresses JOIN FETCH c.phoneNumbers JOIN FETCH c.emailAddresses ORDER BY  c.lastName, c.firstName ASC")
	public List<Contact> queryAll();

	/**
	 * Returns a {@link List} of all {@link Contact} objects with an {@link Address}
	 * matching the specified 2-letter country code.
	 * 
	 * @param country The country to search for
	 * @return {@link List} of all {@link Contact} objects with an {@link Address} matching the specified 2-letter country code
	 * 
	 * @see http://www.davros.org/misc/iso3166.txt
	 */
	public List<Contact> findByAddressesCountry(String country);
	
}
