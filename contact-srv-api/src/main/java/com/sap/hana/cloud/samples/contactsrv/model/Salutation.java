package com.sap.hana.cloud.samples.contactsrv.model;

/**
 * The salutation of a {@link Contact}.
 */
public enum Salutation
{
	// Ladies first! ;)
	MS("model.salutation.ms"), 
	MISSES("model.salutation.misses"), 
	MRS("model.salutation.mrs"), 
	MR("model.salutation.mr"), 
	FAMILY("model.salutation.family");
	
	/**
	 * The I18N message key to be used.
	 */
	private final String key;

	/**
	 * Creates a new {@link Salutation} entity with the specified I18N key.
	 * 
	 * @param key The I18N message key to be used
	 */
    private Salutation(String key) 
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
