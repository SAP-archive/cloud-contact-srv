package com.sap.hana.cloud.samples.contactsrv.client.web.formatter;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import com.sap.hana.cloud.samples.contactsrv.model.CommunicationType;

public class CommunicationTypeFormatter implements Formatter<CommunicationType>
{
	 
	  @Resource
	  private MessageSource messageSource;
	     
	  @Override
	  public String print(CommunicationType commType, Locale locale) 
	  {
	    return messageSource.getMessage(commType.getKey(), null, commType.name(), locale);
	  }
	 
	  @Override
	  public CommunicationType parse(String text, Locale locale) throws ParseException 
	  {
	    return CommunicationType.valueOf(text.toUpperCase());
	  }
}
