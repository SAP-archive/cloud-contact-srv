package com.sap.hana.cloud.samples.contactsrv.client;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sap.core.connectivity.api.http.HttpDestination;
import com.sap.hana.cloud.samples.contactsrv.api.ContactFacade;

public class AddressbookServiceFactory
{
	/**
	 * Name of the logical destination of the Addressbook service to be used.
	 * Should match the resource name specified in the <code>web.xml</code>.
	 */
	final static String DESTINATION_NAME = "Addressbook-Service";

	/**
	 * The HTTP URL of the Addressbook service to be used.
	 */
	final static String endPoint = getEndPoint();

	/**
	 * Returns the URI of the Addressbook service to be used.
	 * 
	 * @return URI of the Addressbook service to be used
	 */
	static String getEndPoint()
	{
		String retVal = getAddressbookServiceDestination();
		
		if (retVal == null)
		{
			/*
			 *  apply defensive programming and try to make an educated guess
			 */
			
			// check if we are running locally
			final String landscape = System.getenv("HC_APPLICATION");
			
			if (landscape == null)
			{
				// no landscape maintained, let's try with defaults
				retVal = "http://localhost:8080";
			}
			else 
			{
				// URL pattern
				final String urlPattern = "{0}://{1}-{2}.{3}";
				
				// the host landscape
				final String host = System.getenv("HC_HOST");
				
				// tenant/account name
				final String account = System.getenv("HC_ACCOUNT");			
				
				retVal = new MessageFormat(urlPattern).format(new Object[]{ "https", "srvdemo", account, host });
			}
		}

		return retVal;
	}

	/**
	 * Returns the URI of the Addressbook service to be used via the corresponding 
	 * destination of the Connectivity service or <code>NULL</code> if no valid
	 * destination was found. 
	 * 
	 * @return URI of the Addressbook service to be used via the corresponding 
	 * destination of the Connectivity service or <code>NULL</code> if no valid
	 * destination was found
	 * 
	 * @see https://help.hana.ondemand.com/help/frameset.htm?e5c9867dbb571014957ef9d7a8846b1c.html
	 */
	static String getAddressbookServiceDestination()
	{
		String retVal = null;
		
		try
		{
			Context ctx = new InitialContext();

			HttpDestination destination = null;

			destination = (HttpDestination) ctx.lookup("java:comp/env/" + DESTINATION_NAME);
			
			if (destination != null && destination.getURI() != null)
			{
				retVal = destination.getURI().toASCIIString();
			}
		}
		catch (NamingException ex)
		{
			// no destination maintained, we'll continue by trying to make an educated guess 
		}
        catch (URISyntaxException ex)
        {
	        ex.printStackTrace();
        }
		
		return retVal;

	}

	/**
	 * Returns a reference to the Addressbook service (proxy) to be used. 
	 * 
	 * @return A reference to the Addressbook service (proxy) to be used
	 */
	public ContactFacade getService()
	{
		ContactFacade retVal = null;

		// set up providers
		List<JacksonJsonProvider> providers = new ArrayList<JacksonJsonProvider>();
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		providers.add(jsonProvider);
		
		// initialize proxy
		retVal  = JAXRSClientFactory.create(endPoint, ContactFacade.class, providers);

		return retVal;
	}
}
