package net.www.webnutritionist.repository.storage;

import org.springframework.data.repository.CrudRepository;

import net.www.webnutritionist.entity.ProfileRestore;

public interface ProfileRestoreRepository extends CrudRepository<ProfileRestore, Long> {
	
	ProfileRestore findByToken(String token);
}
