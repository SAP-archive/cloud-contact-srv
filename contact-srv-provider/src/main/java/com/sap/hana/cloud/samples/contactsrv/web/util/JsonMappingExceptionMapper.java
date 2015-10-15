package com.sap.hana.cloud.samples.contactsrv.web.util;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;
import com.sap.hana.cloud.samples.contactsrv.util.CustomObjectMapper;

/**
 * 
 * @see http://cxf.apache.org/docs/jax-rs-basics.html#JAX-RSBasics-Exceptionhandling
 * @see http://fusesource.com/docs/esb/4.2/rest/RESTExceptionMapper.html
 * 
 */
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException>
{

	/**
	 * The {@link ObjectMapper} to be used.
	 * 
	 * @see CustomObjectMapper
	 */
	@Inject
	ObjectMapper objectMapper = null;


	/**
	 * TODO
	 * 
	 * @param exception The caught {@link JsonMappingException}
	 * @return The corresponding {@link Response} containing a {@link StatusMessage}
	 */
	@Override
	public Response toResponse(JsonMappingException exception)
	{	
		final String PACKAGE_NAME = "com.sap.hana.cloud.samples.contactsrv.model.";
		
		final String PATTERN = "\\n at \\[Source: org.apache.cxf.transport.http.AbstractHTTPDestination\\$.{1,12};\\s";
		final String REPLACE = " at location: [";
		
		String msgStr = exception.getMessage().replaceAll(PACKAGE_NAME, ""); // remove full-qualified package names
		msgStr = msgStr.replaceAll(PATTERN,REPLACE);
	
		StatusMessage message = new StatusMessage();
		message.setCode(400);
		message.setDescription(msgStr);
		message.setError("Mapping error");

		return Response.status(message.getCode()).entity(message).type(MediaType.APPLICATION_JSON).build();
	}
}
