package com.phr.ade.util;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;

public class VelocityHelper
{
	
	private static VelocityEngine velocityEngine;
	
	public static VelocityEngine getVelocityEngine() throws Exception
	{
		if (velocityEngine == null)
			init();
		return velocityEngine;
	}
	
	private static void init() throws Exception
	{
		velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties.put("class.resource.loader.description",
		        "Velocity Classpath Resource Loader");
		velocityProperties
		        .put("class.resource.loader.class",
		                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.init(velocityProperties);
	}
	
}
