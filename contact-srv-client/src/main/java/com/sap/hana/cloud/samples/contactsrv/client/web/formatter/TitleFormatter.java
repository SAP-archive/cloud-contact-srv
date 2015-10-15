package com.sap.hana.cloud.samples.contactsrv.client.web.formatter;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import com.sap.hana.cloud.samples.contactsrv.model.Title;


public class TitleFormatter implements Formatter<Title>
{
	 
	  @Resource
	  private MessageSource messageSource;
	     
	  @Override
	  public String print(Title title, Locale locale) 
	  {
	    return messageSource.getMessage(title.getKey(), null, title.name(), locale);
	  }
	 
	  @Override
	  public Title parse(String text, Locale locale) throws ParseException 
	  {
	    return Title.valueOf(text.toUpperCase());
	  }
}
