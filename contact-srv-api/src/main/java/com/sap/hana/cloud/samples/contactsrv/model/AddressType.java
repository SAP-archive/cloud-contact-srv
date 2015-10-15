package com.sap.hana.cloud.samples.contactsrv.model;

/**
 * The type of the {@link Address}.
 */
public enum AddressType
{
	PRIVATE("model.address_type.private"), 
	WORK("model.address_type.work");
	
	/**
	 * The I18N message key to be used.
	 */
	private final String key;

	/**
	 * Creates a new {@link AddressType} entity with the specified I18N key.
	 * 
	 * @param key The I18N message key to be used
	 */
    private AddressType(String key) 
    {
        this.key = key;
    }

    /**
     * Returns the I18N key for this object.
     * 
     * @return The I18N key for this object
     */
    public String getKey() 
    {
        return this.key;
    }
}
