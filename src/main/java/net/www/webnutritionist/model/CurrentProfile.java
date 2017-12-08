package net.www.webnutritionist.model;

public interface CurrentProfile {

	Long getId();
	
	String getUid();
	
	String getEmail();
	
	boolean isAdmin();
}
