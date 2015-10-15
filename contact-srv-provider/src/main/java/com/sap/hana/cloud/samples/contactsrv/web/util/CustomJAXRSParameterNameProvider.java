package com.sap.hana.cloud.samples.contactsrv.web.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ParameterNameProvider;

import org.apache.cxf.jaxrs.model.Parameter;
import org.apache.cxf.jaxrs.utils.ResourceUtils;
import org.apache.cxf.jaxrs.validation.JAXRSParameterNameProvider;

/**
 * Custom implementation of the {@link ParameterNameProvider} to provide more control about the parameter names determined during the validation of data.
 */
public class CustomJAXRSParameterNameProvider extends JAXRSParameterNameProvider implements ParameterNameProvider
{
	@Override
    public List<String> getParameterNames(final Constructor< ? > constructor) 
    {
        final List< String > parameterNames = new ArrayList< String >();
        
        for (int i = 0; i < constructor.getParameterTypes().length; ++i) 
        {
            parameterNames.add("");
        }
        
        return parameterNames;
    }	
	
	/**
	 * 
	 * @see org.apache.cxf.jaxrs.validation.JAXRSParameterNameProvider#getParameterNames(java.lang.reflect.Method)
	 */
	@Override
	public List<String> getParameterNames(final Method method)
	{
		final List<Parameter> parameters = ResourceUtils.getParameters(method);
		final List<String> parameterNames = new ArrayList<String>();

		for (int i = 0; i < parameters.size(); ++i)
		{
			final StringBuilder sb = new StringBuilder();

			Parameter parameter = parameters.get(i);
			
			if (parameter.getName() != null)
			{
				sb.append(parameter.getType().toString());
				sb.append("(\"" + parameter.getName() + "\")");
				sb.append(" ");
			}
			sb.append(method.getParameterTypes()[i].getSimpleName().toLowerCase());

			parameterNames.add("");
		}

		return parameterNames;
	}
}
