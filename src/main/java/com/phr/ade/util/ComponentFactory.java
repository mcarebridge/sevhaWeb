package com.phr.ade.util;

public class ComponentFactory
{

    private static ComponentFactory _compFactory;

    private ComponentFactory() {}

    /**
     * 
     * @return
     */
    public static ComponentFactory getInstance()
    {
	if (_compFactory == null)
	{
	    _compFactory = new ComponentFactory();
	}

	return _compFactory;
    }

    public Object getComponent(String componentKey)
	    throws ClassNotFoundException, InstantiationException,
	    IllegalAccessException
    {

	ClassLoader googleClassLoader = ClassLoader.getSystemClassLoader();
	Class _carebridgeClass = googleClassLoader.loadClass(componentKey);
	Object _carebridgeObject = _carebridgeClass.newInstance();
	return _carebridgeObject;
    }

}
