package com.sap.hana.cloud.samples.contactsrv.web.util;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.validation.BeanValidationProvider;

/**
 * Custom implementation of {@link JAXRSBeanValidationInInterceptor} to overwrite the standard behavior of
 * calling {@link BeanValidationProvider#validateParameters(Object, Method, Object[])} in favor of calling
 * {@link BeanValidationProvider#validateBean(Object)}. 
 * 
 * The issue with the standard implementation is that it includes the method name as part of the 
 * {@link javax.validation.ConstraintViolation#getPropertyPath()}.
 * 
 * @see BeanValidationInInterceptor#handleValidation(Message, Object, Method, List<Object>)
 *
 */
public class CustomJAXRSBeanValidationInInterceptor extends JAXRSBeanValidationInInterceptor
{
	/*
	 * (non-Javadoc)
	 * @see org.apache.cxf.validation.BeanValidationInInterceptor#handleValidation(org.apache.cxf.message.Message, java.lang.Object, java.lang.reflect.Method, java.util.List)
	 */
    @Override
    protected void handleValidation(final Message message, final Object resourceInstance,
                                    final Method method, final List<Object> arguments) 
    {
        if (arguments.size() > 0) 
        {
            BeanValidationProvider provider = getProvider(message);
            
            for (Object obj : arguments)
            {
            	provider.validateBean(obj);
            }
            
            message.getExchange().put(BeanValidationProvider.class, provider);
        }
    }
}
