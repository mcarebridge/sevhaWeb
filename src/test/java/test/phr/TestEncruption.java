package test.phr;

import com.phr.ade.security.Encrypt;

public class TestEncruption
{
	
	public TestEncruption()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String args[])
	{
		try
        {
	        String cookieValue = Encrypt.encodeAccount("timaaah", "1q2w3e4r");
	        
	        System.out.println(cookieValue);
	        
	        String uidpwd[] = Encrypt.decodeAccount(cookieValue);
	        
	        System.out.println(uidpwd[0] + uidpwd[1]);
        } catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
}
