package test.phr;

import java.util.StringTokenizer;

public class TestStringManipulation
{
	
	public static void main(String[] args)
	{
		String _a = "(RXSKIPPED:{[Dheeraj:9881731219][9020003:Atorvast][9000001:Test Drug1][2160010:ROPARK XI]}{[Madhu:9881731219][9020003:Atorvast][9000001:Test Drug1][2160010:ROPARK XI]})";
		
		StringTokenizer _st = new StringTokenizer(_a, "()");
		
		String _a1 = (String) _st.nextElement();
		
		System.out.println(_a1);
		
		_a1 = _a1.substring(_a1.indexOf(":") + 1, _a1.length());
		
		System.out.println(_a1);
		
		StringTokenizer _st1 = new StringTokenizer(_a1, "{}");
		
		String _b1 = (String) _st1.nextElement();
		
		System.out.println(_b1);
		
		_b1 = (String) _st1.nextElement();
		
		System.out.println(_b1);
	}
	
}
