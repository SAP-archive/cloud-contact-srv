package com.sap.hana.cloud.samples.contactsrv.web.util;

import java.util.Arrays;
import java.util.Locale;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.validation.ResponseConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;
import com.sap.hana.cloud.samples.contactsrv.model.ValidationError;
import com.sap.hana.cloud.samples.contactsrv.util.ConstraintViolationMapper;

/**
 * Deals with {@link ConstraintViolationException}s intercepted in the inbound RESTful service communication
 * and maps the contained {@link ConstraintViolation} to respective {@link ValidationError}s stored within 
 * a {@link StatusMessage}.
 * 
 * @see org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor
 * @see StatusMessage
 */
@Provider
public class ValidationExceptionMapper extends ConstraintViolationMapper implements ExceptionMapper<ValidationException>
{
	Logger log = LoggerFactory.getLogger(ValidationExceptionMapper.class);


	/**
	 * Catches a {@link ValidationException} and in case its an instance of 
	 * {@link ConstraintViolationException} it maps the contained {@link ConstraintViolation}
	 * to respective {@link ValidationError}s stored within a {@link StatusMessage}.
	 * 
	 * @param exception The caught {@link ValidationException}
	 * @return The {@link Response} containing a respective {@link StatusMessage} 
	 * @see StatusMessage
	 */
	@Override
	public Response toResponse(ValidationException exception)
	{
		if (exception instanceof ConstraintViolationException)
		{
			
			final ConstraintViolationException constraint = (ConstraintViolationException) exception;
			final boolean isResponseException = constraint instanceof ResponseConstraintViolationException;

			// get locale
			final Locale locale = LocaleContextHolder.getLocale();
			
			// convert constraint violation
			ValidationError[] errors = convertConstraintViolationExceptionToValidationErrors(constraint, locale, getResourceBundleName());

			if (isResponseException)
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			else
			{
				StatusMessage msg = getDefaultStatusMessage();
				msg.setErrors(Arrays.asList(errors));
				
				return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
			}
		}
		else
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}
}
