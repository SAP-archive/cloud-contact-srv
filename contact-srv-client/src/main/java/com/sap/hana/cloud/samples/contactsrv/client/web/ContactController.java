package com.sap.hana.cloud.samples.contactsrv.client.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.sap.hana.cloud.samples.contactsrv.api.ContactFacade;
import com.sap.hana.cloud.samples.contactsrv.client.web.util.LocaleUtils;
import com.sap.hana.cloud.samples.contactsrv.model.Address;
import com.sap.hana.cloud.samples.contactsrv.model.Contact;
import com.sap.hana.cloud.samples.contactsrv.model.EmailAddress;
import com.sap.hana.cloud.samples.contactsrv.model.PhoneNumber;
import com.sap.hana.cloud.samples.contactsrv.model.StatusMessage;
import com.sap.hana.cloud.samples.contactsrv.model.ValidationError;
import com.sap.hana.cloud.samples.contactsrv.srv.ContactService;
import com.sap.hana.cloud.samples.contactsrv.srv.DataValidationException;
import com.sap.hana.cloud.samples.contactsrv.srv.ServiceException;


/**
 * Handles requests for the application home page.
 */
@Controller()
@Primary
public class ContactController
{
	private static final String STANDARD_VIEWNAME = "contacts";
	
	/**
	 * The {@link ContactService} to be used.
	 */
	@Autowired
	ContactFacade contactService;
	
	@ModelAttribute("contactList")
	public List<Contact> getContactList()
	{
		// get all contacts
		return contactService.findAll();
	}
	
	@ModelAttribute("countryList")
	public SortedMap<String, String> getCountryList(Locale locale)
	{
		return LocaleUtils.getCountryList(locale);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale)
	{
		return "redirect:/" + STANDARD_VIEWNAME + "/";
	}
	
	@RequestMapping(value = "/contacts/", method = RequestMethod.GET)
	public String contactPage(Locale locale, Model model)
	{
		model.addAttribute(getContactList());
		
		return STANDARD_VIEWNAME;
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about()
	{
		return "about";
	}
	
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("contact") @Valid Contact contact, BindingResult bindingResult, Model model)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		try
		{
			if (! bindingResult.hasErrors())
			{
				if (contact.getVersion() != null && contact.getVersion() < 1L)
				{
					contact = contactService.create(contact);
				}
				else
				{
					contact = contactService.update(contact.getId(), contact);
				}
				
				retVal.addObject(contact);
				retVal.addObject(getContactList());
				
				retVal = new ModelAndView("redirect:/contacts/{id}");
				retVal.addObject("id", contact.getId());
			}
		}
		catch (DataValidationException ex)
		{
			StatusMessage msg = ex.getStatusMessage();
			
			for (ValidationError valError : msg.getErrors())
			{
				/*
				 
				 objectName - the name of the affected object
				 field - the affected field of the object
				 rejectedValue - the rejected field value
				 bindingFailure - whether this error represents a binding failure (like a type mismatch); else, it is a validation failure
				 codes - the codes to be used to resolve this message
				 arguments - the array of arguments to be used to resolve this message
				 defaultMessage - the default message to be used to resolve this message
				  
				 */
				
				String[] codes = valError.getMessageParameter().keySet().toArray(new String[valError.getMessageParameter().keySet().size()]);
				Object[] arguments = valError.getMessageParameter().values().toArray(new Object[valError.getMessageParameter().values().size()]);
				
				FieldError error = new FieldError("contact", valError.getPath(), valError.getInvalidValue(), false, codes, arguments, valError.getMessage());
				
				bindingResult.addError(error);
			}
		
			retVal.addObject(bindingResult);
		}
		catch (ServiceException ex)
		{
			// TODO
		}
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("contact") @Valid Contact contact, BindingResult bindingResult, Model model) 
	{
		return this.save(contact, bindingResult, model);
	}
	
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="addAddress")
	public ModelAndView addAddress(@ModelAttribute("contact") Contact contact, BindingResult bindingResult, Model model)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		// TODO
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="addPhoneNumber")
	public ModelAndView addPhoneNumber(@ModelAttribute("contact") Contact contact, BindingResult bindingResult, Model model)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		if (contact.getPhoneNumbers() == null)
		{
			contact.setPhoneNumbers(new ArrayList<PhoneNumber>(1));
		}
		
		contact.getPhoneNumbers().add(new PhoneNumber());
		retVal.addObject(contact);
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="deletePhoneNumber")
	public ModelAndView deletePhoneNumber(@ModelAttribute("contact") Contact contact, BindingResult bindingResult, Model model, @RequestParam("deletePhoneNumber") Integer index)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		if (contact.getPhoneNumbers() != null)
		{
			contact.getPhoneNumbers().remove(index.intValue());
		}
		
		retVal.addObject(contact);
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="addEmail")
	public ModelAndView addEmail(@ModelAttribute("contact") Contact contact, BindingResult bindingResult, Model model)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		if (contact.getEmailAddresses() == null)
		{
			contact.setEmailAddresses(new ArrayList<EmailAddress>(1));
		}
		
		contact.getEmailAddresses().add(new EmailAddress());
		retVal.addObject(contact);
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="deleteEmail")
	public ModelAndView deleteEmail(@ModelAttribute("contact") Contact contact, BindingResult bindingResult, Model model, @RequestParam("deleteEmail") Integer index)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		if (contact.getEmailAddresses() != null)
		{
			contact.getEmailAddresses().remove(index.intValue());
		}
		
		retVal.addObject(contact);
		
		return retVal;
	}
	
	@RequestMapping(value="/contacts/*", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(@ModelAttribute("contact") @Valid Contact contact, BindingResult bindingResult, Model model, @RequestParam("delete") String value)
	{
		ModelAndView retVal = new ModelAndView("redirect:/" + STANDARD_VIEWNAME + "/");
		
		contactService.delete(contact.getId());
		
		// create an empty contact object
		contact = populateContact(new Contact());
		model.addAttribute(contact);
		
		retVal.addObject(getContactList());
		
		return retVal;
	}
            

	@RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
	// public ModelAndView getById(@RequestParam("id") String id, Model model)
	public ModelAndView getById(@PathVariable String id, Model model)
	{
		ModelAndView retVal = new ModelAndView(STANDARD_VIEWNAME);
		
		Contact contact = contactService.findOne(id);
		model.addAttribute(contact);
		
		return retVal;
	}

	/**
	 * Handles a {@link ServiceException} and returns some *meaningful* error message.
	 * 
	 * @param ex The {@link ServiceException} to handle
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public  @ResponseBody String handleServiceException(ServiceException ex) 
	{
		// do something really sophisticated here!
		return "We screwed up!";
	}
	
	/**
	 * Populates the specified {@link Contact} with initial (empty) address, email address and phone number
	 * information as required by the web view component. 
	 * 
	 * @param contact The {@link Contact} to populate
	 * @return The populated {@link Contact}
	 */
	@ModelAttribute("contact")
	public static Contact populateContact(Contact contact)
	{
		if (contact == null)
		{
			contact = new Contact();
		}
		
		if (contact.getAddresses() == null || contact.getAddresses().isEmpty())
		{
			Address address = new Address();
			List<Address> addressList = new ArrayList<Address>(1);
			addressList.add(address);
			contact.setAddresses(addressList);
		}
		
		if (contact.getPhoneNumbers() == null || contact.getPhoneNumbers().isEmpty())
		{
			PhoneNumber phone = new PhoneNumber();
			List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>(1);
			phoneNumberList.add(phone);
			contact.setPhoneNumbers(phoneNumberList);
		}
		
		if (contact.getEmailAddresses() == null || contact.getEmailAddresses().isEmpty())
		{
			EmailAddress email = new EmailAddress();
			List<EmailAddress> emailList = new ArrayList<EmailAddress>(1);
			emailList.add(email);
			contact.setEmailAddresses(emailList);
		}
		
		return contact;
	}
}
