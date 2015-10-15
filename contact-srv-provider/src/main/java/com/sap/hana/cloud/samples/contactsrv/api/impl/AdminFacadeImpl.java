package com.sap.hana.cloud.samples.contactsrv.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;

import com.sap.cloud.account.TenantContext;
import com.sap.hana.cloud.samples.contactsrv.model.admin.TenantInfo;

/**
 *  Provides the public API for administration purposes.
 */
@Service("adminFacade")
@Path("/admin")
@org.codehaus.enunciate.Facet(name = "grouping", value= "Administration API", 
documentation = "Provides REST endpoints for administration purposes")
public class AdminFacadeImpl extends BaseFacade 
{
	/**
	 * Returns the tenant info. 
	 * 
	 * @return {@link Response} representation of the tenant info
	 * 
	 * @name Get tenant information
	 */
	@Produces("application/json")
	@GET
	@org.codehaus.enunciate.jaxrs.TypeHint(TenantInfo.class)
	public Response getTenantInfo() 
	{
		TenantInfo retVal = new TenantInfo();
		
		try 
		{
			InitialContext ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:comp/env");
			TenantContext tenantContext = (TenantContext) envCtx.lookup("TenantContext");
			
			retVal.setAccountName(tenantContext.getAccountName());
			retVal.setTenantId(tenantContext.getTenantId());
			
		} 
		catch (Exception ex) 
		{
			String msg = ex.getLocalizedMessage();
			return Response.ok(msg).build();
		}
		
		return Response.ok(retVal).status(Status.CREATED).build();
	}
}
