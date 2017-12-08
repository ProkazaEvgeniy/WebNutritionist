package net.www.webnutritionist.repository.storage;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface AbstractProfileEntityRepository<T> extends Repository<T, Long> {

	void deleteByProfileId(Long idProfile);
	
	List<T> findByProfileIdOrderByIdAsc(Long idProfile);
	
	T findByProfileId(Long idProfile);
	
	<S extends T> S saveAndFlush(S entity);
	
	void flush();
}
