package com.sap.hana.cloud.samples.contactsrv.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hana.cloud.samples.contactsrv.model.Contact;

/**
 * Default implementation of the {@link ContactDAO} interface based on Spring Data JPA.
 * 
 * @see http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/
 */
@Service
public class ContactDAOImpl implements ContactDAO
{

	/**
	 * The spring-data-jpa repository to be used for persistence operations.
	 */
	@Autowired 
	ContactRepository repository = null;
	
	@Override
/* 
	public List<Contact> findAll()
    {
	    return repository.queryAll();
    }
*/
	
	public Iterable<Contact> findAll()
    {
	    return repository.findAll();
    }
	
	@Override
    public Contact save(@Valid Contact entity)
    {
	    return repository.save(entity);
    }

	@SuppressWarnings("unchecked")
    @Override
    public Iterable<Contact> save(@Valid Iterable<? extends Contact> entities)
    {
	    return (Iterable<Contact>) repository.save(entities);
    }

	@Override
    public Contact findOne(String id)
    {
	    return repository.findOne(id);
    }

	@Override
    public boolean exists(String id)
    {
	    return repository.exists(id);
    }

	@Override
    public long count()
    {
	    return repository.count();
    }

	@Override
    public void delete(String id)
    {
	    repository.delete(id);
    }

	@Override
    public void delete(Contact entity)
    {
	    repository.delete(entity);
    }

	@Override
    public void delete(Iterable<? extends Contact> entities)
    {
	   repository.delete(entities);
    }

	@Override
    public void deleteAll()
    {
	    repository.deleteAll();
    }
	
	@Override
	public List<Contact> findByAddressesCountry(String country)
	{
		return repository.findByAddressesCountry(country);
	}

}
