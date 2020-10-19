package net.guides.springboot2.jdbc;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class InitLogger {

	/**

	* Log4j logger

	*/

	static Logger LOGGER = Logger.getLogger(InitLogger.class);
	
	String moduleName;

	/**
	 * 
	 */
	public InitLogger() {
		super();	
		PropertyConfigurator.configure("C:\\DTJerseyClient\\config_server\\log4j.properties");
		//Configure logger
        BasicConfigurator.configure();
	}

	/**
	 * @param moduleName
	 */
	public InitLogger(String moduleName) {
		super();
		this.moduleName = moduleName;
		InitLogger.LOGGER = Logger.getLogger(this.moduleName);
		PropertyConfigurator.configure("C:\\DTJerseyClient\\config_server\\log4j.properties");
		//Configure logger
        BasicConfigurator.configure();
	}

	/**
	 * @return the lOGGER
	 */
	public Logger getLOGGER() {
		return LOGGER;
	}

	/**
	 * @param lOGGER the lOGGER to set
	 */
	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}



}
