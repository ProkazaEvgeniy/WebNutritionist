package net.www.webnutritionist.service;

import javax.annotation.Nonnull;

import net.www.webnutritionist.entity.Profile;

public interface NotificationManagerService {

	void sendRestoreAccessLink(@Nonnull Profile profile, @Nonnull String restoreLink);

	void sendPasswordChanged(@Nonnull Profile profile);
	
	void sendPasswordGenerated(@Nonnull Profile profile, @Nonnull String generatedPassword);
	
	void sendOrderMessage(@Nonnull Profile profile, @Nonnull String orderLink);
}
