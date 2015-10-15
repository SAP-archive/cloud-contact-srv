package com.sap.hana.cloud.samples.contactsrv.model;

/**
 * The communication type.
 */
public enum CommunicationType
{
	HOME("model.communication_type.home"), 
	WORK("model.communication_type.work"), 
	CELL("model.communication_type.cell");
	
	/**
	 * The I18N message key to be used.
	 */
	private final String key;

	/**
	 * Creates a new {@link CommunicationType} entity with the specified I18N key.
	 * 
	 * @param key The I18N message key to be used
	 */
    private CommunicationType(String key) 
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
