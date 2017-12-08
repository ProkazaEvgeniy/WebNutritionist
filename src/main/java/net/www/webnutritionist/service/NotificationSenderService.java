package net.www.webnutritionist.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.model.NotificationMessage;


public interface NotificationSenderService {

	void sendNotification(@Nonnull NotificationMessage message);

	@Nullable String getDestinationAddress(@Nonnull Profile profile);
	
	void sendNotificationMessage(@Nonnull NotificationMessage message);
	
}
