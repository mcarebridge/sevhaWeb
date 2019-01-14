package test.phr;

public class TestStringCompare {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String serverName = "_usa.care";
		
		if(serverName.contains("sevha.com")) {
			serverName = "sevha";	
		}else if(serverName.contains("usa.care")) {
			serverName = "usa.care";
		}

		System.out.println("Server Name : " + serverName);
	}

}
