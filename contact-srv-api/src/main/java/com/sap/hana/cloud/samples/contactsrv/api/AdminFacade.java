package com.sap.hana.cloud.samples.contactsrv.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sap.hana.cloud.samples.contactsrv.model.admin.TenantInfo;

@Path("/admin")
public interface AdminFacade
{
	/**
	 * Returns the {@link TenantInfo}. 
	 * 
	 * @return {@link TenantInfo}
	 */
	@Produces("application/json")
	@GET
	public TenantInfo getTenantInfo();
	
}
