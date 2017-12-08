package net.www.webnutritionist.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.entity.ChartPath;
import net.www.webnutritionist.entity.Child;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.Spouse;
import net.www.webnutritionist.entity.VegetableNutritionist;
import net.www.webnutritionist.entity.VegetableNutritionistSix;
import net.www.webnutritionist.entity.VegetableSelection;
import net.www.webnutritionist.entity.VegetableSelectionSix;
import net.www.webnutritionist.form.PasswordForm;
import net.www.webnutritionist.form.SignUpForm;
import net.www.webnutritionist.form.SpouseForm;
import net.www.webnutritionist.model.CurrentProfile;


public interface EditProfileService {

	@Nullable Profile findProfileById(@Nonnull CurrentProfile currentProfile);
	
	@Nonnull Profile createNewProfile(@Nonnull SignUpForm signUpForm);

	void removeProfile(@Nonnull CurrentProfile currentProfile);

	@Nonnull Profile updateProfilePassword(@Nonnull CurrentProfile currentProfile, @Nonnull PasswordForm form);

	void updateProfileData(@Nonnull CurrentProfile currentProfile, @Nonnull Profile data, @Nonnull MultipartFile uploadPhoto);
	
	void createNewSpouse(@Nonnull SpouseForm spouseForm, @Nonnull CurrentProfile currentProfile);
	
	@Nullable Spouse findSpouseById(@Nonnull CurrentProfile currentProfile);
	
	void updateSpouse(@Nonnull SpouseForm spouseForm, @Nonnull CurrentProfile currentProfile);

	@Nonnull List<Child> listChilds(@Nonnull CurrentProfile currentProfile);
	
	void updateChilds(@Nonnull CurrentProfile currentProfile, @Nonnull List<Child> childs);
	
	@Nonnull List<VegetableSelection> listVegetableSelections(@Nonnull CurrentProfile currentProfile);
	
	void updateVegetableSelections(@Nonnull CurrentProfile currentProfile, @Nonnull List<VegetableSelection> vegetableSelections);

	@Nonnull List<VegetableSelectionSix> listVegetableSelectionsSix(@Nonnull CurrentProfile currentProfile);
	
	void updateVegetableSelectionsSix(@Nonnull CurrentProfile currentProfile, @Nonnull List<VegetableSelectionSix> vegetableSelections);
	
	@Nonnull List<VegetableNutritionist> listVegetableNutritionists(@Nonnull CurrentProfile currentProfile);
	
	void updateVegetableNutritionists(@Nonnull CurrentProfile currentProfile, @Nonnull List<VegetableNutritionist> vegetableNutritionist);
	
	@Nonnull List<VegetableNutritionistSix> listVegetableNutritionistSixs(@Nonnull CurrentProfile currentProfile);
	
	void updateVegetableNutritionistSixs(@Nonnull CurrentProfile currentProfile, @Nonnull List<VegetableNutritionistSix> vegetableNutritionistSix);
	
	@Nonnull List<ChartPath> listChartPaths(@Nonnull CurrentProfile currentProfile, @Nonnull List<ChartPath> chartPath);
	
	@Nonnull ChartPath findChartPathsById(@Nonnull CurrentProfile currentProfile);
	
	@Nonnull ChartPath findChartPathsByProfileId(@Nonnull Profile profile);
	
	void updateChartPaths(@Nonnull CurrentProfile currentProfile, @Nonnull List<ChartPath> chartPath);
 
}
