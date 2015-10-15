package com.sap.hana.cloud.samples.contactsrv.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Detects the current landscape the application is run on and initializes the Spring {@link ApplicationContext} accordingly.
 * 
 * @see ApplicationContextInitializer
 */
public class EnvironmentContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentContextInitializer.class);
	
	public static enum RuntimeEnvironment { LOCAL, CLOUD };
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext)
	{
		RuntimeEnvironment stage = getEnvironment();
		
		applicationContext.getEnvironment().setActiveProfiles(stage.name().toLowerCase());
	}
	
	/**
	 * Returns the {@link RuntimeEnvironment} the application runs in. Defaults to <code>CLOUD</code>.
	 * 
	 * @return The {@link RuntimeEnvironment} the application runs in
	 */
	public static RuntimeEnvironment getEnvironment()
	{
		RuntimeEnvironment retVal = RuntimeEnvironment.CLOUD;
		
		String landscape = System.getenv("HC_LANDSCAPE"); // NEO
		String vcap = System.getenv("VCAP_APPLICATION");  // Cloud Foundry
		
		if (landscape == null && vcap == null)
		{
			retVal = RuntimeEnvironment.LOCAL;
		}
		
		logger.info("Application running in '{}' environment", retVal.name().toLowerCase());
		
		return retVal;
	}
}