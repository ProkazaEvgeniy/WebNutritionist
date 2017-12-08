package net.www.webnutritionist.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.www.webnutritionist.model.NotificationMessage;


public interface NotificationTemplateService {

	@Nonnull NotificationMessage createNotificationMessage (@Nonnull String templateName, @Nullable Object model);
}
