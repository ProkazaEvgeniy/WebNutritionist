package net.www.webnutritionist.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.component.DataBuilder;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.Spouse;
import net.www.webnutritionist.exception.CantCompleteClientRequestException;
import net.www.webnutritionist.repository.storage.ProfileRepository;
import net.www.webnutritionist.util.DataUtil;

public abstract class AbstractCreateProfileService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ProfileRepository profileRepository;
	
	@Value("${generate.uid.suffix.length}")
	private int generateUidSuffixLength;

	@Value("${generate.uid.alphabet}")
	private String generateUidAlphabet;

	@Value("${generate.uid.max.try.count}")
	private int maxTryCountToGenerateUid;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected DataBuilder dataBuilder;
	
	protected String buildProfileUid(String firstName, String lastName) throws CantCompleteClientRequestException {
		String baseUid = dataBuilder.buildProfileUid(firstName, lastName);
		String uid = baseUid;
		for (int i = 0; profileRepository.countByUid(uid) > 0; i++) {
			uid = dataBuilder.rebuildUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixLength);
			if (i >= maxTryCountToGenerateUid) {
				throw new CantCompleteClientRequestException("Can't generate unique uid for profile: " + uid);
			}
		}
		return uid;
	}
	
	protected Profile createNewProfile(String firstName, String lastName, String password) {
		Profile profile = new Profile();
		profile.setUid(buildProfileUid(firstName, lastName));
		profile.setFirstName(DataUtil.capitalizeName(firstName));
		profile.setLastName(DataUtil.capitalizeName(lastName));
		profile.setPassword(passwordEncoder.encode(password));
		profile.setCompleted(false);
		profile.setRole(Constants.USER);
		return profile;
	}
	
	protected Spouse createNewSpouse(String name, Date birthDay, Profile profile) {
		Spouse spouse = new Spouse();
		spouse.setName(DataUtil.capitalizeName(name));
		spouse.setBirthDay(birthDay);
		spouse.setProfile(profile);
		return spouse;
	}
	
	protected void showProfileCreatedLogInfoIfTransactionSuccess(final Profile profile) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New profile created: {}", profile.getUid());
			}
		});
	}
	
	protected void showSpouseCreatedLogInfoIfTransactionSuccess(final Spouse spouse) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("New profile created: {}", spouse.getName());
			}
		});
	}
}
