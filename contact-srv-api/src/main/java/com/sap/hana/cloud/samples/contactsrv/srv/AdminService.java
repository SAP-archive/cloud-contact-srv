package com.sap.hana.cloud.samples.contactsrv.srv;

import com.sap.hana.cloud.samples.contactsrv.model.admin.TenantInfo;

public interface AdminService
{
	/**
	 * Returns the {@link TenantInfo}. 
	 * 
	 * @return {@link TenantInfo}
	 */
	public TenantInfo getTenantInfo();
	
}
