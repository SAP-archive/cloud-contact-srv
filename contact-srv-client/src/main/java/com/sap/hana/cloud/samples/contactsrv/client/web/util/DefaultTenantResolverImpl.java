package com.sap.hana.cloud.samples.contactsrv.client.web.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sap.cloud.account.TenantContext;

/**
 * Default implementation that retrieves the current tenant using the 
 * {@link TenantContext} interface provided by SAP HANA Cloud Platform.
 * 
 * @see TenantContext
 * 
 * @see https://help.hana.ondemand.com/help/frameset.htm?255422aab2c4459e837d8c22d11b4a54.html
 * @see https://help.hana.ondemand.com/help/frameset.htm?54a76156cd114e5d928642b8dde47b91.html
 */
public class DefaultTenantResolverImpl implements CurrentTenantResolver
{
	/**
	 * Returns the ID of the current tenant using the standard mechanism provided by the SAP HANA Cloud Platform.
	 * 
	 * @return The id of the current tenant
	 * 
	 * @see https://help.hana.ondemand.com/help/frameset.htm?255422aab2c4459e837d8c22d11b4a54.html
	 * @see https://help.hana.ondemand.com/help/frameset.htm?54a76156cd114e5d928642b8dde47b91.html
	 * 
	 */
	@Override
	public String getCurrentTenantId()
	{
		String retVal = null;
		
		try
		{
			retVal = getTenantId();
		}
		catch (NamingException ex)
		{
			retVal = "dev_defaultId";
		}
		
		return retVal;
	}
	
	/**
	 * Returns the ID of the tenant using the standard mechanism provided by the  SAP HANA Cloud Platform.
	 *  
	 * @return The id of the current tenant
	 * @throws NamingException In case of an error while retrieving the {@link TenantContext} via JNDI
	 * 
	 * @see https://help.hana.ondemand.com/help/frameset.htm?255422aab2c4459e837d8c22d11b4a54.html
	 * @see https://help.hana.ondemand.com/help/frameset.htm?54a76156cd114e5d928642b8dde47b91.html
	 * 
	 */
	static String getTenantId() throws NamingException
	{
		String retVal = null;
		
		InitialContext ctx = new InitialContext();
		Context envCtx = (Context) ctx.lookup("java:comp/env");
		TenantContext tenantContext = (TenantContext) envCtx.lookup("TenantContext");
		retVal = tenantContext.getTenantId();
		
		return retVal;
	}

}
