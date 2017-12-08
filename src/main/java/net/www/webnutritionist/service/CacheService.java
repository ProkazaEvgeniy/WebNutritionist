package net.www.webnutritionist.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.www.webnutritionist.entity.Profile;

public interface CacheService {

	@Nullable Profile findProfileByUid(@Nonnull String uid);
	
	void deleteProfileByUid(@Nonnull String uid);
}
