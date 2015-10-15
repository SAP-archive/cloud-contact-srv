package com.sap.hana.cloud.samples.contactsrv.srv;

import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;

/**
 * {@link RuntimeException} used by the service layer.
 */
public class ServiceException extends RuntimeException
{

	/**
	 * The <code>serialVersionUID</code> of this class.
	 */
    private static final long serialVersionUID = 1L;

    StatusMessage msg = null;
 
	public ServiceException()
	{	
		super();
		msg = new StatusMessage();
	}

	public ServiceException(String arg0)
	{
		super(arg0);
		msg = new StatusMessage();
		msg.setMessage(arg0);
	}

	public ServiceException(Throwable arg0)
	{
		super(arg0);
		msg = new StatusMessage();
		msg.setDescription(arg0.getMessage());
	}

	public ServiceException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		msg = new StatusMessage();
		msg.setMessage(arg0);
	}
	
	public ServiceException(StatusMessage msg)
	{
		super();
		this.msg = msg;
	}
	
	public StatusMessage getStatusMessage()
	{
		return this.msg;
	}

}
