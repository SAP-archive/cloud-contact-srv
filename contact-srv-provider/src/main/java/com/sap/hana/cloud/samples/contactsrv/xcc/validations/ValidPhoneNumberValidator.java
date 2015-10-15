package com.sap.hana.cloud.samples.contactsrv.xcc.validations;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.sap.hana.cloud.samples.contactsrv.model.PhoneNumber;


public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String>, MessageSourceAware
{
	MessageSource messageSource;
	
	/**
	 * The {@link PhoneNumberUtil} instance used to validate {@link PhoneNumber}s.
	 */
	private final static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	
	@Override
    public void initialize(ValidPhoneNumber constraintAnnotation)
    {   
    }

	@Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
		
		com.google.i18n.phonenumbers.Phonenumber.PhoneNumber no = null;
		
        try
        {
	        no = phoneUtil.parse(value, Locale.getDefault().getCountry());
        }
        catch (NumberParseException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		boolean valid = phoneUtil.isValidNumber(no);
		        
		
		return valid;
    }

	@Override
    public void setMessageSource(MessageSource messageSource)
    {
	    this.messageSource = messageSource;
    }
}
