package net.www.webnutritionist.service.impl;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import net.www.webnutritionist.component.DataBuilder;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.ProfileRestore;
import net.www.webnutritionist.exception.CantCompleteClientRequestException;
import net.www.webnutritionist.repository.search.ProfileSearchRepository;
import net.www.webnutritionist.repository.storage.ProfileRepository;
import net.www.webnutritionist.repository.storage.ProfileRestoreRepository;
import net.www.webnutritionist.service.CacheService;
import net.www.webnutritionist.service.FindProfileService;
import net.www.webnutritionist.service.NotificationManagerService;
import net.www.webnutritionist.util.SecurityUtil;

@Service
public class FindProfileServiceImpl implements FindProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FindProfileServiceImpl.class);

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ProfileSearchRepository profileSearchRepository;

	@Autowired
	private ProfileRestoreRepository profileRestoreRepository;

	@Autowired
	private NotificationManagerService notificationManagerService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	protected DataBuilder dataBuilder;

	@Value("${application.host}")
	private String appHost;

	@Override
	public Profile findByUid(String uid) {
		return cacheService.findProfileByUid(uid.toLowerCase());
	}

	@Override
	public Page<Profile> findAll(Pageable pageable) {
		return profileRepository.findAllByCompletedTrue(pageable);
	}

	@Override
	public Page<Profile> findBySearchQuery(String query, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(query)
						.field("uid")
						.field("email")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.operator(MatchQueryBuilder.Operator.AND))
				.withSort(SortBuilders.fieldSort("uid").order(SortOrder.DESC))
				.build();
		searchQuery.setPageable(pageable);
		return profileSearchRepository.search(searchQuery);
	}

	@Override
	@Transactional
	public void restoreAccess(String anyUnigueId) {
		Profile profile = profileRepository.findByUidOrEmailOrPhone(anyUnigueId, anyUnigueId, anyUnigueId);
		if (profile != null) {
			ProfileRestore restore = profileRestoreRepository.findOne(profile.getId());
			if (restore == null) {
				restore = new ProfileRestore();
				restore.setId(profile.getId());
			}
			restore.setToken(SecurityUtil.generateNewRestoreAccessToken());
			profileRestoreRepository.save(restore);
			sentRestoreLinkNotificationIfTransactionSuccess(profile, restore);
		} else {
			LOGGER.error("Profile not found by anyIdAcount:" + anyUnigueId);
		}
	}
	
	protected void sentRestoreLinkNotificationIfTransactionSuccess(final Profile profile, ProfileRestore restore){
		final String restoreLink = dataBuilder.buildRestoreAccessLink(appHost, restore.getToken());
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				notificationManagerService.sendRestoreAccessLink(profile, restoreLink);
			}
		});
	}

	@Override
	@Transactional
	public Profile findByRestoreToken(String token) {
		ProfileRestore restore = profileRestoreRepository.findByToken(token);
		if (restore == null) {
			throw new CantCompleteClientRequestException("Invalid token");
		}
		profileRestoreRepository.delete(restore);
		return restore.getProfile();
	}
	
	@Override
	@Transactional
	public Iterable<Profile> findAllForIndexing() {
		Iterable<Profile> all = profileRepository.findAll();
		return all;
	}
}
