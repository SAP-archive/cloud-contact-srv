package com.sap.hana.cloud.samples.contactsrv.api;


import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.hana.cloud.samples.contactsrv.model.Contact;
import com.sap.hana.cloud.samples.contactsrv.srv.ServiceException;

@Path("/contacts")
public interface ContactFacade 
{
	@Produces("application/json")
	@GET
	public List<Contact> findAll() throws ServiceException;
		
	@Path("/{id}")
	@Produces("application/json")
	@GET
	public Contact findOne(@PathParam("id") String id) throws ServiceException;

	@Consumes("application/json")
	@Produces("application/json")
	@POST
	public Contact create(@Valid Contact contact) throws ServiceException;
	
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	@PUT
	public Contact update(@PathParam("id") String id, @Valid Contact contact) throws ServiceException;
	
	@Path("/{id}")
	@Consumes({MediaType.WILDCARD})
	@Produces({MediaType.WILDCARD})
	@DELETE
	public void delete(@PathParam("id") String id) throws ServiceException;

}
