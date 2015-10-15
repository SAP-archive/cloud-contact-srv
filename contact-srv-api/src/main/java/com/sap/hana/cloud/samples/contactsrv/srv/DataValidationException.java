package com.sap.hana.cloud.samples.contactsrv.srv;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;
import com.sap.hana.cloud.samples.contactsrv.model.ValidationError;
import com.sap.hana.cloud.samples.contactsrv.util.ConstraintViolationMapper;

/**
 * {@link RuntimeException} used by the service layer.
 */
public class DataValidationException extends ServiceException
{

	/**
	 * The <code>serialVersionUID</code> of this class.
	 */
    private static final long serialVersionUID = 1L;

	public DataValidationException(ConstraintViolationException arg0)
	{
		super(arg0);
		this.msg = ConstraintViolationMapper.getDefaultStatusMessage();
	}
	
	public DataValidationException(ValidationError... errors)
	{
		super();
		this.msg = ConstraintViolationMapper.getDefaultStatusMessage();
		this.msg.setErrors(Arrays.asList(errors));
	}
	
	
	public StatusMessage getStatusMessage()
	{
		return this.msg;
	}

}
