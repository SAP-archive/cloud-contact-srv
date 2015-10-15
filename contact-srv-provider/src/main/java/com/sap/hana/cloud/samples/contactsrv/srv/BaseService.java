package com.sap.hana.cloud.samples.contactsrv.srv;


/**
 * Abstract base class for all services.
 *
 */
public abstract class BaseService
{
	/**
	 * Creates an enclosing {@link ServiceException} for the specified {@link Exception} or
	 * propagates it directly if the specified {@link Exception} is an instance of 
	 * {@link ServiceException}. 
	 * 
	 * @param ex The {@link Exception} to handle
	 * @throws ServiceException The {@link ServiceException}
	 */
	protected void handleException(Exception ex) throws ServiceException
	{
		
		// final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		if (ex instanceof ServiceException)
		{
			throw (ServiceException) ex;
		}
		else
		{
			ServiceException up = new ServiceException(ex);
			throw up;
		}
	}
}
