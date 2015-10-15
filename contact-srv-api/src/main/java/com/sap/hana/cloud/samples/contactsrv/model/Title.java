package com.sap.hana.cloud.samples.contactsrv.model;

/**
 * The title of a {@link Contact}.
 */
public enum Title
{
	DR("model.title.dr"), 
	PROF("model.title.prof");

	/**
	 * The I18N message key to be used.
	 */
	private final String key;

	/**
	 * Creates a new {@link Title} entity with the specified I18N key.
	 * 
	 * @param key The I18N message key to be used
	 */
    private Title(String key) 
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
