package com.sap.hana.cloud.samples.contactsrv.api.impl;

import javax.ws.rs.core.Response;


/**
 * Abstract base class for all facades.
 *
 */
public abstract class BaseFacade
{
	/**
	 * TODO
	 * 
	 * @param ex The {@link Exception} to handle
	 * @return The corresponding {@link Response} for the caught {@link Exception}
	 * @deprecated In favor of {@link com.sap.hana.cloud.samples.contactsrv.web.util.ServiceExceptionMapper}
	 */
	@Deprecated 
	protected Response handleException(Exception ex) 
	{
		return Response.serverError().build();
		
	}
}
