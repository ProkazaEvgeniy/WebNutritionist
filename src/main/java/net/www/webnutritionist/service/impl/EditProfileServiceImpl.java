package net.www.webnutritionist.service.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.Constants;
import net.www.webnutritionist.annotation.ProfileDataFieldGroup;
import net.www.webnutritionist.annotation.ProfileInfoField;
import net.www.webnutritionist.entity.ChartPath;
import net.www.webnutritionist.entity.Child;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.ProfileEntity;
import net.www.webnutritionist.entity.Spouse;
import net.www.webnutritionist.entity.VegetableNutritionist;
import net.www.webnutritionist.entity.VegetableNutritionistSix;
import net.www.webnutritionist.entity.VegetableSelection;
import net.www.webnutritionist.entity.VegetableSelectionSix;
import net.www.webnutritionist.exception.FormValidationException;
import net.www.webnutritionist.form.PasswordForm;
import net.www.webnutritionist.form.SignUpForm;
import net.www.webnutritionist.form.SpouseForm;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.UploadResult;
import net.www.webnutritionist.repository.search.ProfileSearchRepository;
import net.www.webnutritionist.repository.storage.AbstractProfileEntityRepository;
import net.www.webnutritionist.repository.storage.ChartPathRepository;
import net.www.webnutritionist.repository.storage.ChildRepository;
import net.www.webnutritionist.repository.storage.SpouseRepository;
import net.www.webnutritionist.repository.storage.VegetableNutritionistRepository;
import net.www.webnutritionist.repository.storage.VegetableNutritionistSixRepository;
import net.www.webnutritionist.repository.storage.VegetableSelectionRepository;
import net.www.webnutritionist.repository.storage.VegetableSelectionSixRepository;
import net.www.webnutritionist.service.CacheService;
import net.www.webnutritionist.service.EditProfileService;
import net.www.webnutritionist.service.ImageProcessorService;
import net.www.webnutritionist.service.ImageStorageService;
import net.www.webnutritionist.service.NotificationManagerService;
import net.www.webnutritionist.util.DataUtil;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EditProfileServiceImpl extends AbstractCreateProfileService implements EditProfileService {
	private static Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);

	@Autowired
	private ProfileSearchRepository profileSearchRepository;

	@Autowired
	private ImageProcessorService imageProcessorService;

	@Autowired
	private ImageStorageService imageStorageService;

	@Autowired
	private NotificationManagerService notificationManagerService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private SpouseRepository spouseRepository;

	@Autowired
	private ChildRepository childRepository;
	
	@Autowired
	private VegetableSelectionRepository vegetableSelectionRepository;
	
	@Autowired
	private VegetableSelectionSixRepository vegetableSelectionSixRepository;
	
	@Autowired
	private VegetableNutritionistRepository vegetableNutritionistRepository;
	
	@Autowired
	private VegetableNutritionistSixRepository vegetableNutritionistSixRepository;
	
	@Autowired
	private ChartPathRepository chartPathRepository;

	private Set<String> profileCollectionsToReIndex;

	private Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> profileEntityRepositoryMap;

	@PostConstruct
	private void postConstruct() {
		profileCollectionsToReIndex = Collections.unmodifiableSet(
				new HashSet<>(Arrays.asList(new String[] { 
						"anthropometryProfile", 
						"spouse", 
						"child", 
						"vegetableSelection", 
						"vegetableSelectionSix", 
						"vegetableNutritionist", 
						"vegetableNutritionistSix", 
						"chartPath" })));

		Map<Class<? extends ProfileEntity>, AbstractProfileEntityRepository<? extends ProfileEntity>> map = new HashMap<>();
		map.put(Spouse.class, spouseRepository);
		map.put(Child.class, childRepository);
		map.put(VegetableSelection.class, vegetableSelectionRepository);
		map.put(VegetableSelectionSix.class, vegetableSelectionSixRepository);
		map.put(VegetableNutritionist.class, vegetableNutritionistRepository);
		map.put(VegetableNutritionistSix.class, vegetableNutritionistSixRepository);
		map.put(ChartPath.class, chartPathRepository);
		profileEntityRepositoryMap = Collections.unmodifiableMap(map);
	}

	protected Profile getProfile(CurrentProfile currentProfile) {
		return profileRepository.findOne(currentProfile.getId());
	}

	@Override
	public Profile findProfileById(CurrentProfile currentProfile) {
		return getProfile(currentProfile);

	}

	@Override
	@Transactional
	public Profile createNewProfile(SignUpForm signUpForm) {
		Profile profile = createNewProfile(signUpForm.getFirstName(), signUpForm.getLastName(),
				signUpForm.getPassword());
		profileRepository.save(profile);
		showProfileCreatedLogInfoIfTransactionSuccess(profile);
		return profile;
	}

	// **************** Spouse

	protected Spouse getSpouse(CurrentProfile currentProfile) {
		return spouseRepository.findByProfileId(currentProfile.getId());
	}

	@Override
	public Spouse findSpouseById(CurrentProfile currentProfile) {
		return getSpouse(currentProfile);
	}

	// ******************List Spouse

	protected List<Spouse> getSpouseList(CurrentProfile currentProfile) {
		return spouseRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	// ******************************

	@Override
	@Transactional
	public void updateSpouse(SpouseForm spouseForm, CurrentProfile currentProfile) {
		Spouse spouseLoader = getSpouse(currentProfile);
		if (spouseLoader != null) {
			if (!(spouseLoader.equals(spouseForm))) {
				if (!StringUtils.equals(spouseLoader.getName(), spouseForm.getName())) {
					spouseLoader.setName(spouseForm.getName());
				}
				if (!(spouseLoader.getBirthDay().equals(spouseForm.getBirthDay()))) {
					spouseLoader.setBirthDay(spouseForm.getBirthDay());
				}
				spouseRepository.saveAndFlush(spouseLoader);
				showSpouseCreatedLogInfoIfTransactionSuccess(spouseLoader);
			}
		} else {
			createNewSpouse(spouseForm, currentProfile);
		}
	}

	@Override
	@Transactional
	public void createNewSpouse(SpouseForm spouseForm, CurrentProfile currentProfile) {
		Profile profile = getProfile(currentProfile);
		Spouse spouse = createNewSpouse(spouseForm.getName(), spouseForm.getBirthDay(), profile);
		spouseRepository.saveAndFlush(spouse);
		showSpouseCreatedLogInfoIfTransactionSuccess(spouse);
	}

	// *******************************Spouse
	@Override
	@Transactional
	public void removeProfile(CurrentProfile currentProfile) {
		Profile profile = profileRepository.findOne(currentProfile.getId());
		List<String> imageLinksToRemove = getImageLinksToRemove(profile);
		profileRepository.delete(profile);
		removeProfileIndexIfTransactionSuccess(profile, imageLinksToRemove);
		evilcProfileCacheIfTransactionSuccess(currentProfile);
	}

	protected List<String> getImageLinksToRemove(Profile profile) {
		List<String> imageLinksToRemove = new ArrayList<>();
		imageLinksToRemove.add(profile.getLargeFoto());
		imageLinksToRemove.add(profile.getSmallFoto());
		return imageLinksToRemove;
	}

	protected void removeProfileIndexIfTransactionSuccess(final Profile profile,
			final List<String> imageLinksToRemove) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Profile by id=" + profile.getId() + " removed from storage");
				profileSearchRepository.delete(profile.getId());
				imageStorageService.remove(imageLinksToRemove.toArray(new String[imageLinksToRemove.size()]));
				LOGGER.info("Profile by id=" + profile.getId() + " removed from search index");
			}
		});
	}

	@Override
	@Transactional
	public Profile updateProfilePassword(CurrentProfile currentProfile, PasswordForm form) {
		Profile profile = profileRepository.findOne(currentProfile.getId());
		profile.setPassword(passwordEncoder.encode(form.getPassword()));
		profileRepository.save(profile);
		sendPasswordChangedIfTransactionSuccess(profile);
		return profile;
	}

	protected void sendPasswordChangedIfTransactionSuccess(final Profile profile) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				notificationManagerService.sendPasswordChanged(profile);
			}
		});
	}

	@Override
	@Transactional
	public void updateProfileData(CurrentProfile currentProfile, Profile profileForm, MultipartFile uploadPhoto) {
		Profile loadedProfile = profileRepository.findOne(currentProfile.getId());
		List<String> oldProfilePhotos = Collections.EMPTY_LIST;
		if (!uploadPhoto.isEmpty()) {
			UploadResult uploadResult = imageProcessorService.processNewProfilePhoto(uploadPhoto);
			deleteUploadedPhotosIfTransactionFailed(uploadResult);
			oldProfilePhotos = Arrays
					.asList(new String[] { loadedProfile.getLargeFoto(), loadedProfile.getSmallFoto() });
			loadedProfile.updateProfilePhotos(uploadResult.getLargeUrl(), uploadResult.getSmallUrl());
		}
		int copiedFieldsCount = DataUtil.copyFields(profileForm, loadedProfile, ProfileDataFieldGroup.class);
		boolean shouldProfileBeUpdated = !uploadPhoto.isEmpty() || copiedFieldsCount > 0;
		if (shouldProfileBeUpdated) {
			executeUpdateProfileData(currentProfile, loadedProfile, oldProfilePhotos);
		}
	}

	// *******
	@Override
	public List<Child> listChilds(CurrentProfile currentProfile) {
		return childRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	@Override
	@Transactional
	public void updateChilds(CurrentProfile currentProfile, List<Child> childs) {
		updateProfileEntities(currentProfile, childs, Child.class);
	}

	// *******
	protected void executeUpdateProfileData(CurrentProfile currentProfile, Profile loadedProfile,
			List<String> oldProfilePhotos) {
		loadedProfile.setCompleted(true);
		synchronized (this) {
			checkForDuplicatesEmailAndPhone(loadedProfile);
			profileRepository.save(loadedProfile);
			profileRepository.flush();
		}
		updateIndexProfileDataIfTransactionSuccess(currentProfile, loadedProfile, oldProfilePhotos);
		evilcProfileCacheIfTransactionSuccess(currentProfile);
	}

	protected void checkForDuplicatesEmailAndPhone(Profile profileForm) {
		Profile profileForEmail = profileRepository.findByEmail(profileForm.getEmail());
		if (profileForEmail != null && !profileForEmail.getId().equals(profileForm.getId())) {
			throw new FormValidationException("email", profileForm.getEmail());
		}
		Profile profileForPhone = profileRepository.findByPhone(profileForm.getPhone());
		if (profileForPhone != null && !profileForPhone.getId().equals(profileForm.getId())) {
			throw new FormValidationException("phone", profileForm.getPhone());
		}
	}
	
	protected void updateIndexProfileDataIfTransactionSuccess(final CurrentProfile currentProfile,
			final Profile profileForm, final List<String> oldProfilePhotos) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Profile updated");
				imageStorageService.remove(oldProfilePhotos.toArray(Constants.EMPTY_ARRAY));
				updateIndexProfileData(currentProfile, profileForm, ProfileDataFieldGroup.class);
			}
		});
	}

	protected void deleteUploadedPhotosIfTransactionFailed(final UploadResult uploadResult) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCompletion(int status) {
				if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
					imageStorageService.remove(uploadResult.getLargeUrl(), uploadResult.getSmallUrl());
				}
			}
		});
	}

	protected <T extends Annotation> void updateIndexProfileData(CurrentProfile currentProfile, Profile profileForm,
			Class<T> annotationClass) {
		Profile p = profileSearchRepository.findOne(currentProfile.getId());
		if (p == null) {
			createNewProfileIndex(profileForm);
		} else {
			DataUtil.copyFields(profileForm, p, annotationClass);
			if (StringUtils.isNotBlank(profileForm.getLargeFoto())
					|| StringUtils.isNotBlank(profileForm.getSmallFoto())) {
				p.setLargeFoto(profileForm.getLargeFoto());
				p.setSmallFoto(profileForm.getSmallFoto());
			}
			profileSearchRepository.save(p);
			LOGGER.info("Profile index updated");
		}
	}

	protected void createNewProfileIndex(Profile profileForm) {
		profileSearchRepository.save(profileForm);
		LOGGER.info("New profile index created: {}", profileForm.getUid());
	}

	protected void updateIndexProfileInfoIfTransactionSuccess(final CurrentProfile currentProfile,
			final Profile loadedProfile) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Updated info for profile: {}", loadedProfile);
				updateIndexProfileData(currentProfile, loadedProfile, ProfileInfoField.class);
				LOGGER.info("Updated info for profile index: {}", loadedProfile);
			}
		});
	}

	protected <E extends ProfileEntity> void updateProfileEntities(CurrentProfile currentProfile, List<E> updatedData,
			Class<E> entityClass) {
		String collections = DataUtil.getCollectionName(entityClass);
		AbstractProfileEntityRepository<E> repository = findProfileEntityRepository(entityClass);
		List<E> profileData = repository.findByProfileIdOrderByIdAsc(currentProfile.getId());
		DataUtil.removeEmptyElements(updatedData);
		if (Comparable.class.isAssignableFrom(entityClass)) {
			Collections.sort((List<? extends Comparable>) updatedData);
		}
		if (DataUtil.areListsEqual(updatedData, profileData)) {
			LOGGER.debug("Profile {}: nothing to update", collections);
			return;
		} else {
			executeProfileEntitiesUpdate(currentProfile, repository, updatedData);
			evilcProfileCacheIfTransactionSuccess(currentProfile);
			updateIndexProfileEntitiesIfTransactionSuccess(currentProfile, updatedData, collections);
		}
	}

	protected <E extends ProfileEntity> AbstractProfileEntityRepository<E> findProfileEntityRepository(
			Class<E> entityClass) {
		AbstractProfileEntityRepository<E> repository = (AbstractProfileEntityRepository<E>) profileEntityRepositoryMap
				.get(entityClass);
		if (repository == null) {
			throw new IllegalArgumentException("ProfileEntityRepository not found for entityClass=" + entityClass);
		}
		return repository;
	}

	protected <E extends ProfileEntity> void executeProfileEntitiesUpdate(CurrentProfile currentProfile,
			AbstractProfileEntityRepository<E> repository, List<E> updatedData) {
		repository.deleteByProfileId(currentProfile.getId());
		repository.flush();
		Profile profileProxy = profileRepository.getOne(currentProfile.getId());
		for (E entity : updatedData) {
			entity.setId(null);
			entity.setProfile(profileProxy);
			repository.saveAndFlush(entity);
		}
	}

	protected void evilcProfileCacheIfTransactionSuccess(final CurrentProfile currentProfile) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				cacheService.deleteProfileByUid(currentProfile.getUid());
			}
		});
	}

	protected <E extends ProfileEntity> void updateIndexProfileEntitiesIfTransactionSuccess(
			final CurrentProfile currentProfile, final List<E> updatedData, final String collections) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				LOGGER.info("Profile {} updated", collections);
				if (profileCollectionsToReIndex.contains(collections)) {
					updateIndexProfileEntities(currentProfile, updatedData, collections);
				}
			}
		});
	}

	protected <E> void updateIndexProfileEntities(CurrentProfile currentProfile, List<E> updatedData,
			String collections) {
		Profile profile = profileSearchRepository.findOne(currentProfile.getId());
		DataUtil.writeProperty(profile, collections, updatedData);
		profileSearchRepository.save(profile);
		LOGGER.info("Profile {} index updated", collections);
	}
	// ************************************ VegetableSelection one month

	@Override
	public List<VegetableSelection> listVegetableSelections(CurrentProfile currentProfile) {
		return vegetableSelectionRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	@Override
	@Transactional
	public void updateVegetableSelections(CurrentProfile currentProfile, List<VegetableSelection> vegetableSelections) {
		updateProfileEntities(currentProfile, vegetableSelections, VegetableSelection.class);
	}
	
	// ************************************ VegetableSelection six month
	@Override
	public List<VegetableSelectionSix> listVegetableSelectionsSix(CurrentProfile currentProfile) {
		return vegetableSelectionSixRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	@Override
	@Transactional
	public void updateVegetableSelectionsSix(CurrentProfile currentProfile, List<VegetableSelectionSix> vegetableSelections) {
		updateProfileEntities(currentProfile, vegetableSelections, VegetableSelectionSix.class);
	}
	// ************************************ VegetableNutritionist month
	@Override
	public List<VegetableNutritionist> listVegetableNutritionists(CurrentProfile currentProfile) {
		return vegetableNutritionistRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	@Override
	@Transactional
	public void updateVegetableNutritionists(CurrentProfile currentProfile,
			List<VegetableNutritionist> vegetableNutritionist) {
		updateProfileEntities(currentProfile, vegetableNutritionist, VegetableNutritionist.class);
		
	}
	// ************************************ VegetableNutritionistSix six month
	@Override
	public List<VegetableNutritionistSix> listVegetableNutritionistSixs(CurrentProfile currentProfile) {
		return vegetableNutritionistSixRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}

	@Override
	@Transactional
	public void updateVegetableNutritionistSixs(CurrentProfile currentProfile,
			List<VegetableNutritionistSix> vegetableNutritionistSix) {
		updateProfileEntities(currentProfile, vegetableNutritionistSix, VegetableNutritionistSix.class);
		
	}
	
	//************************************* ChartPath
	@Override
	public List<ChartPath> listChartPaths(CurrentProfile currentProfile, List<ChartPath> chartPath) {
		return chartPathRepository.findByProfileIdOrderByIdAsc(currentProfile.getId());
	}
	
	@Override
	public ChartPath findChartPathsById(CurrentProfile currentProfile) {
		return chartPathRepository.findByProfileId(currentProfile.getId());
	}
	
	@Override
	public ChartPath findChartPathsByProfileId(Profile profile) {
		return chartPathRepository.findByProfileId(profile.getId());
	}

	@Override
	@Transactional
	public void updateChartPaths(CurrentProfile currentProfile, List<ChartPath> chartPath) {
		updateProfileEntities(currentProfile, chartPath, ChartPath.class);
	}
}
