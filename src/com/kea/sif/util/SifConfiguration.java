package com.kea.sif.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SifConfiguration {
	private final static Logger LOG = Logger.getLogger(SifConfiguration.class);
	private static SifConfiguration config;
	private Properties mProperties;
	public static SifConfiguration getInstance(){
		if (config == null){
			config = new SifConfiguration();
			config.mProperties = new Properties();
			try{
				InputStream inputStream = config.getClass().getClassLoader().getResourceAsStream("sif.properties");
				config.mProperties.load(inputStream);
			} catch (IOException e){
				LOG.error("fail to load configuration file", e);
			}
		}
		return config;
	}
	public String getOldPathPrefix(){
		return mProperties.getProperty("sif.data.oldPathPrefix");
	}
	public String getNewPathPrefix(){
		return mProperties.getProperty("sif.data.newPathPrefix");
	}
	public Integer getThreadCount() {
		return new Integer(mProperties.getProperty("sif.hardware.threadCounts"));
	}
}
