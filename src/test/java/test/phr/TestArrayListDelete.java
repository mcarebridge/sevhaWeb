package test.phr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.phr.ade.meta.SubstituteMeta;
import com.phr.ade.model.Profile;
import com.phr.ade.model.Substitute;

public class TestArrayListDelete
{
	public static void main(String[] args)
	{
		ArrayList<String> s = new ArrayList<String>();
		
		s.add("1");
		s.add("2");
		
		ArrayList<String> t = new ArrayList<String>();
		
		t.add("1");
		
		s.removeAll(t);
		
		for (Iterator iterator = s.iterator(); iterator.hasNext();)
        {
	        String string1 = (String) iterator.next();
	        System.out.println(string1);
	        
        }
	}
}
