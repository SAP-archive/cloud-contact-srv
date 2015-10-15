package com.sap.hana.cloud.samples.contactsrv.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;
import com.sap.hana.cloud.samples.contactsrv.model.ValidationError;

/**
 * Deals with {@link ConstraintViolationException}s and maps the contained {@link ConstraintViolation}s to respective {@link ValidationError}s stored within 
 * a {@link StatusMessage}.
 * 
 * @see StatusMessage
 */
public class ConstraintViolationMapper 
{
	Logger log = LoggerFactory.getLogger(ConstraintViolationMapper.class);

	 // TODO I18N
    static StatusMessage DEFAULT_MSG = new StatusMessage(400, "api.data_validation.error", null, "Data validation failed!");

    /**
     * Returns (a clone) of the default {@link StatusMessage} used for reporting back validation errors.
     * 
     * @return (A clone) of the default {@link StatusMessage} used for reporting back validation errors.
     */
	public static StatusMessage getDefaultStatusMessage()
	{
		return DEFAULT_MSG.clone();
	}
	
	/**
	 * The name of the {@link ResourceBundle} that contains the I18N validation messages. 
	 */
	String resourceBundleName = null;

	/**
	 * Sets the name of the {@link ResourceBundle} that contains the I18N validation messages.
	 * 
	 * @param resourceBundleName The name of the {@link ResourceBundle} that contains the I18N validation messages
	 */
	public void setResourceBundleName(String resourceBundleName)
	{
		this.resourceBundleName = resourceBundleName;
	}
	
	/**
	 * Returns the name of the {@link ResourceBundle} that contains the I18N validation messages.
	 * 
	 * @return The name of the {@link ResourceBundle} that contains the I18N validation messages
	 */
	public String getResourceBundleName()
	{
		return this.resourceBundleName;
	}
	
	/**
	 * Extracts the names of all message placeholder (contained in curly braces) within
	 * the specified message template.
	 * 
	 * @param messageTemplate The message template
	 * @return {@link Set} of placeholder names
	 */
	public static Set<String> extractMessagePlaceHolders(final String messageTemplate)
	{
		final Pattern REGEX = Pattern.compile("(?<=\\{)([^}]*)(?=\\})"); // matches content within curly braces
		
		Set<String> retVal = new HashSet<String>();
		
		final Matcher regexMatcher = REGEX.matcher(messageTemplate);
		
		while (regexMatcher.find()) 
		{
			retVal.add(regexMatcher.group());
		} 
		
		return retVal;
	}
	
	/**
	 * Converts the {@link ConstraintViolation}s contained within the specified {@link ConstraintViolationException}
	 * to respective {@link ValidationError}s. 
	 * 
	 * @param constraint The {@link ConstraintViolationException} to convert
	 * @param locale The {@link Locale} used to get the message template/pattern
	 * @param resourceBundleName The name of the {@link ResourceBundle} containing the I18N validation messages 
	 * @return The respective {@link ValidationError}s 
	 */
	public static ValidationError[] convertConstraintViolationExceptionToValidationErrors(final ConstraintViolationException constraint, final Locale locale, final String resourceBundleName)
	{
		Set<ConstraintViolation<?>> constraints = constraint.getConstraintViolations();
		
		return convertConstraintViolationsToValidationErrors(constraints, locale, resourceBundleName);
	}
	
	/**
	 * Converts the {@link ConstraintViolation}s to respective {@link ValidationError}s. 
	 * 
	 * @param constraints The {@link ConstraintViolation}s to convert
	 * @param locale The {@link Locale} used to get the message template/pattern
	 * @param resourceBundleName The name of the {@link ResourceBundle} containing the I18N validation messages 
	 * @return The respective {@link ValidationError}s 
	 */
	public static ValidationError[] convertConstraintViolationsToValidationErrors(final Set<? extends ConstraintViolation<?>> constraints, final Locale locale, final String resourceBundleName)
	{
		// initialize validation error list
		List<ValidationError> validationErrors = new ArrayList<ValidationError>(constraints.size());
		
		// get resource bundle
		final ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName, locale);
		
		for (final ConstraintViolation<?> violation : constraints)
		{
			// get the message key
			final String messageKey = violation.getMessageTemplate().substring(1, (violation.getMessageTemplate().length() - 1));
			
			// get the *real* message template/pattern from the resource bundle
			final String messageTemplate = bundle.getString(messageKey);
			
			ValidationError error = new ValidationError(messageKey, 
														violation.getMessage(), 
														messageTemplate, 
														violation.getPropertyPath().toString(), 
														String.valueOf(violation.getInvalidValue()), 
														extractMessageParameter(violation, messageTemplate));
			validationErrors.add(error);
		}
		
		// transform to Array
		ValidationError[] errors = new ValidationError[validationErrors.size()];
		return validationErrors.toArray(errors);
	}
	
	/**
	 * Extracts all the message placeholder names and their values and returns them as a {@link Map}.
	 * 
	 * @param violation The {@link ConstraintViolation} from which to extract the attributes
	 * @param messageTemplate The message template/pattern used for the message
	 * @return {@link Map} containing the name and value of all message parameter
	 */
	public static Map<String, String> extractMessageParameter(final ConstraintViolation<?> violation, final String messageTemplate)
	{
		// sanity check #1
		if (violation == null || messageTemplate == null)
		{
			return Collections.emptyMap();
		}
		
		Set<String> paramKeys = extractMessagePlaceHolders(messageTemplate);
		
		// sanity check #2
		if (paramKeys == null || paramKeys.size() <= 0)
		{
			return Collections.emptyMap();
		}
		
		Map<String, String> retVal = new HashMap<String, String>(paramKeys.size());
		
		// get the attributes for the given constraint violation
		Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
		
		for (final String key : paramKeys)
		{
			String valueStr = null;
			
			Object value = attributes.get(key);

			if (value != null)
			{
				valueStr = value.toString();
			}
			
			retVal.put(key, valueStr);
		}
		
		return retVal;
	}


}
