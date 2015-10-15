package com.sap.hana.cloud.samples.contactsrv.model.admin;

import java.io.Serializable;

public class TenantInfo implements Serializable
{
	/**
	 * The <code>serialVersionUID</code> of the class.
	 */
	private static final long serialVersionUID = 1L;
	
	String accountName = null;
	
	String tenantId = null;

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public String getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(String tenantId)
	{
		this.tenantId = tenantId;
	}
	
	
	
}
