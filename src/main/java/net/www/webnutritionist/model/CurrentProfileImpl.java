package net.www.webnutritionist.model;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.entity.Profile;

public class CurrentProfileImpl extends User implements CurrentProfile {
	private static final long serialVersionUID = 3850489832510630519L;
	private final Long id;
	private final String fullName;
	private final String role;
	private final String email;
	
	public CurrentProfileImpl(Profile profile) {
		super(profile.getUid(), profile.getPassword(), true, true, true, true, 
				Collections.singleton(createSimpleGrantedAuthority(profile.getRole())));
		this.id = profile.getId();
		this.fullName = profile.getFullName();
		this.role = profile.getRole();
		this.email = profile.getEmail();
	}
	
	 static SimpleGrantedAuthority createSimpleGrantedAuthority(String role) {
		if(role.equals(Constants.ADMIN)) {
			return new SimpleGrantedAuthority(Constants.ADMIN);
		} else {
			return new SimpleGrantedAuthority(Constants.USER);
		}
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public String getUid() {
		return getUsername();
	}
	
	public String getFullName() {
		return fullName;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return String.format("CurrentProfile [id=%s, username=%s, role=%s]", id, getUsername(), role);
	}
	
	@Override
	public boolean isAdmin() {
		return role.equals(Constants.ADMIN);
	}
	
	@Override
	public String getEmail() {
		return email;
	}
}
