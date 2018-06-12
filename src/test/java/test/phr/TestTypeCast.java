package test.phr;

import com.phr.ade.persistence.ICareDAO;
import com.phr.ade.util.ComponentFactory;

public class TestTypeCast
{

    public static void main(String[] args)
    {

	ComponentFactory _factory = ComponentFactory.getInstance();
	try
	{
	    Object _obj = _factory
		    .getComponent("com.phr.ade.persistence.CareDAO");
	    ICareDAO _careDAO = (ICareDAO) _obj;
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException e)
	{
	    e.printStackTrace();
	}
    }
}
