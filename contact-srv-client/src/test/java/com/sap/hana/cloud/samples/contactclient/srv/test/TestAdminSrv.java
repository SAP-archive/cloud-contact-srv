package com.sap.hana.cloud.samples.contactclient.srv.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.hana.cloud.samples.contactsrv.api.AdminFacade;
import com.sap.hana.cloud.samples.contactsrv.model.admin.TenantInfo;

/**
 * Tests for the {@link AdminFacade} class.  
 */
public class TestAdminSrv
{
	static String endPoint = null;
	
	static AdminFacade adminSrv = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeClass
    public static void oneTimeSetUp() 
	{
		// one-time setup code  
		endPoint = "http://localhost:8080/api/v1";
		
		// set up providers
		List<JacksonJsonProvider> providers = new ArrayList<JacksonJsonProvider>();
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		providers.add(jsonProvider);
		
		// initialize proxy
		adminSrv = JAXRSClientFactory.create(endPoint, AdminFacade.class, providers);

    }
	
	@AfterClass
    public static void oneTimeTearDown() 
	{
        // one-time cleanup code
    }
	
	@Before
	public void setUp() throws Exception 
	{
		// nothing needs to be done here
	}

	@After
	public void tearDown() throws Exception 
	{
		// nothing needs to be done here
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testTenantId() 
	{		
		TenantInfo info = adminSrv.getTenantInfo();
		
		System.out.println(info.getAccountName());
		
	}
	
}
