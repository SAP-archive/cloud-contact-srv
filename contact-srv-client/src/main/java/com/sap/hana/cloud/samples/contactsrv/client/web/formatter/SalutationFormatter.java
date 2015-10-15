package com.sap.hana.cloud.samples.contactsrv.client.web.formatter;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import com.sap.hana.cloud.samples.contactsrv.model.Salutation;


public class SalutationFormatter implements Formatter<Salutation>
{
	 
	  @Resource
	  private MessageSource messageSource;
	     
	  @Override
	  public String print(Salutation salutation, Locale locale) 
	  {
	    return messageSource.getMessage(salutation.getKey(), null, salutation.name(), locale);
	  }
	 
	  @Override
	  public Salutation parse(String text, Locale locale) throws ParseException 
	  {
	    return Salutation.valueOf(text.toUpperCase());
	  }
}
