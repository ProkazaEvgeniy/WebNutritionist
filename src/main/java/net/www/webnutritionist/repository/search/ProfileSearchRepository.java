package net.www.webnutritionist.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.www.webnutritionist.entity.Profile;

public interface ProfileSearchRepository extends ElasticsearchRepository<Profile, Long> {

	Page<Profile> findByUidLikeOrEmailValueLike(String uid, String email, Pageable pageable);

}
