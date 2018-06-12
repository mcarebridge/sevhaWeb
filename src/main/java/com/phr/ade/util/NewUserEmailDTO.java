package com.phr.ade.util;

import java.io.Serializable;

public class NewUserEmailDTO implements Serializable {

	private String firstName;
	private String secondName;
	private String emailAddress;
	private String launchServer;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getLaunchServer() {
		return launchServer;
	}

	public void setLaunchServer(String launchServer) {
		this.launchServer = launchServer;
	}
}
