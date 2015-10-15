package com.sap.hana.cloud.samples.contactsrv.xcc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sap.hana.cloud.samples.contactsrv.model.ValidationError;
import com.sap.hana.cloud.samples.contactsrv.srv.DataValidationException;
import com.sap.hana.cloud.samples.contactsrv.util.ConstraintViolationMapper;

/**
 * Ensures data validation based on JSR-303.
 * 
 * @see http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/aop.html
 */
@Aspect
@Component
public class DataValidationAspect
{

	@Inject
	LocalValidatorFactoryBean validator = null;
	
	@Value("${messageResourceBundleName}")
	String messageResourceBundleName = null; 

	/**
	 * Validates the data of any method parameter annotated with {@link Valid}.
	 * 
	 * @param joinPoint The intercepted {@link JoinPoint}
	 * @throws DataValidationException In case of an validation error
	 */
	@Before("execution(* com.sap.hana.cloud.samples.contactsrv.srv.*.*(@javax.validation.Valid (*)))")
	public void validateIncomingData(JoinPoint joinPoint) throws DataValidationException
	{
		// get information about the intercepted method
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		final Method method = methodSignature.getMethod();

		// get the annotations and names of the method parameters
		final Annotation[][] paramAnnotations = method.getParameterAnnotations();
		final String[] paramNames = methodSignature.getParameterNames();

		// get the message parameter themselves
		final Object[] param = joinPoint.getArgs();

		// check whether or not they have been annotated with @Valid
		for (int i = 0; i < param.length; i++)
		{
			validateParam(paramAnnotations[i], param[i], paramNames[i]);
		}
	}

	/**
	 * 
	 * @param annotations
	 * @param param
	 * @param paramName
	 * @throws DataValidationException
	 */
	void validateParam(Annotation[] annotations, Object param, String paramName) throws DataValidationException
	{
		// check if parameter needs to be validated
		if (! needsValidation(annotations))
		{
			return;
		}
		
		try // to validate the parameter
		{
			
			Set<ConstraintViolation<Object>> constraints = this.validator.getValidator().validate(param);
			
			// get locale
			final Locale locale = LocaleContextHolder.getLocale();
			
			ValidationError[] errors = ConstraintViolationMapper.convertConstraintViolationsToValidationErrors(constraints, locale, this.messageResourceBundleName);
			
			// only throw data validation exception in case we found errors ;)
			if (errors != null && errors.length > 0)
			{
				throw new DataValidationException(errors);
			}
		}
		catch (ConstraintViolationException ex)
		{
			throw new DataValidationException(ex);
		}
	}

	/**
	 * 
	 * @param annotations
	 * @return
	 */
	private boolean needsValidation(Annotation[] annotations)
	{
		for (Annotation annotation : annotations)
		{
			if (Valid.class.isInstance(annotation))
			{
				return true;
			}
		}
		
		return false;
	}

	public String getMessageResourceBundleName()
	{
		return messageResourceBundleName;
	}

	public void setMessageResourceBundleName(String messageResourceBundleName)
	{
		this.messageResourceBundleName = messageResourceBundleName;
	}

}
