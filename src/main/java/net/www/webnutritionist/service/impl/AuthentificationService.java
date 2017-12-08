package net.www.webnutritionist.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.model.CurrentProfileImpl;
import net.www.webnutritionist.repository.storage.ProfileRepository;

@Service
public class AuthentificationService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationService.class);
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = profileRepository.findByUidOrEmailOrPhone(username, username, username);
		if (profile != null) {
			return new CurrentProfileImpl(profile);
		} else {
			LOGGER.error("Profile not found by " + username);
			throw new UsernameNotFoundException("Profile not found by " + username);
		}
	}
}
