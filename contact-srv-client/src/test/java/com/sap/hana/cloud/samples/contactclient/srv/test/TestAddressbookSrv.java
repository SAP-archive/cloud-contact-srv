package com.sap.hana.cloud.samples.contactclient.srv.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.hana.cloud.samples.contactsrv.api.ContactFacade;
import com.sap.hana.cloud.samples.contactsrv.model.Address;
import com.sap.hana.cloud.samples.contactsrv.model.AddressType;
import com.sap.hana.cloud.samples.contactsrv.model.CommunicationType;
import com.sap.hana.cloud.samples.contactsrv.model.Contact;
import com.sap.hana.cloud.samples.contactsrv.model.EmailAddress;
import com.sap.hana.cloud.samples.contactsrv.model.PhoneNumber;
import com.sap.hana.cloud.samples.contactsrv.model.Salutation;
import com.sap.hana.cloud.samples.contactsrv.srv.DataValidationException;
import com.sap.hana.cloud.samples.contactsrv.util.ConstraintViolationMapper;

/**
 * Tests for the {@link ContactFacade} class.  
 */
public class TestAddressbookSrv
{
	static String endPoint = null;
	
	static ContactFacade addressbookSrv = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @BeforeClass
    public static void oneTimeSetUp() 
	{
		// one-time setup code  
		endPoint = "http://localhost:8080/api/v1";
		
		// set up providers
		List providers = new ArrayList();
		providers.add(new JacksonJaxbJsonProvider());
		providers.add(new ConstraintViolationMapper());
		
		// initialize proxy
		addressbookSrv  = JAXRSClientFactory.create(endPoint, ContactFacade.class, providers);
		
    }
	
	@AfterClass
    public static void oneTimeTearDown() 
	{
        // one-time cleanup code
    }
	
	@Before
	public void setUp() throws Exception 
	{
		// nothing needs to be done here
	}

	@After
	public void tearDown() throws Exception 
	{
		// nothing needs to be done here
	}
	
	/**
	 * Tests getting all contacts
	 */
	@Test
	public void testGetContacts() 
	{
		
		List<Contact> contactList = addressbookSrv.findAll();
		
		System.out.println(contactList);
		
	}
		
	/**
	 * Tests creating a contact.
	 */
	@Test
	public void testCRUD() 
	{	
		// create test data
		Contact contact = createTestData();
		
		// create contact
		contact = addressbookSrv.create(contact);
		assertNotNull("Contact should have been created!", contact);
		
		// read
		Contact anotherContactRef = addressbookSrv.findOne(contact.getId());
		assertNotNull("Contact should have been read!", anotherContactRef);
		
		// update
		anotherContactRef.setSalutation(Salutation.MRS);
		anotherContactRef.setFirstName("Daisy");
		
		Contact updatedContactRef = addressbookSrv.update(anotherContactRef.getId(), anotherContactRef);
		assertTrue("Contact name should be updated!", anotherContactRef.getFirstName().equals(updatedContactRef.getFirstName()));
		
		// delete
		addressbookSrv.delete(updatedContactRef.getId());
		
		try
		{
			updatedContactRef = addressbookSrv.findOne(updatedContactRef.getId()); // should be NULL
			fail("Contact should have been deleted!");
		}
		catch (Exception ex) {}
		
	}
	
	/**
	 * Tests that the data is validated an error messages are handled properly!
	 * 
	 * @see http://www.historyrundown.com/top-5-people-with-the-longest-names/
	 */
	@Test
	public void testDataValidation() 
	{
		
		// create test data
		Contact contact = createTestData();
		contact.setFirstName("Pablo Diego José Francisco de Paula Juan Nepomuceno María de los Remedios Cipriano de la Santísima Trinidad Ruiz y");
		contact.setLastName("Picasso");
		
		// create contact
		try
		{
			contact = addressbookSrv.create(contact);
			fail("Contact should have been refused!");
		}
		catch (DataValidationException ex)
		{
			System.out.println(ex);
		}
		catch (Exception ex) 
		{
			fail("Data validation does not work!");
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Creates a {@link Contact} with demo data.
	 * 
	 * @return {@link Contact} with demo data
	 */
	public static Contact createTestData()
	{
		Contact retVal = new Contact();
		
		// basic info
		retVal.setSalutation(Salutation.MR);
		retVal.setFirstName("Donald");
		retVal.setLastName("Duck");
		
		
		// address info
		Address address = new Address();
		
		address.setType(AddressType.WORK);
		
		address.setStreet("3111 World Dr");
		address.setStreet2("c/o Walt Disney World");
		address.setCity("Orlando");
		address.setZipCode("32830");
		address.setState("FL");
		address.setCountry("US");
		
		List<Address> addressList = new ArrayList<Address>(1);
		addressList.add(address);
		
		// phone numbers
		PhoneNumber phoneNumber = new PhoneNumber();
		
		phoneNumber.setType(CommunicationType.WORK);
		phoneNumber.setNumber("+1 407 824-4321");
		
		List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>(1);
		phoneNumberList.add(phoneNumber);
		
		// email
		EmailAddress emailAddress = new EmailAddress();
		
		emailAddress.setType(AddressType.WORK);
		emailAddress.setEmail("donald.duck@disney.com");
		
		List<EmailAddress> emailAddressList = new ArrayList<EmailAddress>(1);
		emailAddressList.add(emailAddress);
		
		// compose 
		retVal.setAddresses(addressList);
		retVal.setPhoneNumbers(phoneNumberList);
		retVal.setEmailAddresses(emailAddressList);
		
		return retVal;
	}
}
