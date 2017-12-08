package net.www.webnutritionist.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.annotation.constraints.FieldMatch;
import net.www.webnutritionist.component.FormErrorConverter;
import net.www.webnutritionist.entity.ChartPath;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.entity.Spouse;
import net.www.webnutritionist.entity.VegetableNutritionist;
import net.www.webnutritionist.entity.VegetableNutritionistSix;
import net.www.webnutritionist.exception.FormValidationException;
import net.www.webnutritionist.form.ChartPathForm;
import net.www.webnutritionist.form.ChildForm;
import net.www.webnutritionist.form.PasswordForm;
import net.www.webnutritionist.form.SpouseForm;
import net.www.webnutritionist.form.VegetableNutritionistForm;
import net.www.webnutritionist.form.VegetableNutritionistSixForm;
import net.www.webnutritionist.form.VegetableSelectionForm;
import net.www.webnutritionist.form.VegetableSelectionSixForm;
import net.www.webnutritionist.model.CurrentProfile;
import net.www.webnutritionist.model.HeightPeople;
import net.www.webnutritionist.model.Quantity;
import net.www.webnutritionist.model.WeightCategory;
import net.www.webnutritionist.service.EditProfileService;
import net.www.webnutritionist.service.StaticDataService;
import net.www.webnutritionist.util.SecurityUtil;

@Controller
public class EditProfileController {

	@Autowired
	private EditProfileService editProfileService;

	@Autowired
	private FormErrorConverter formErrorConverter;
	
	@Autowired
	private StaticDataService staticDataService;
	
	private String rootPathMediaFile = "D:/dev-study_lesson/practic/webnutritionist/src/main/webapp";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(WeightCategory.class, WeightCategory.getPropertyEditor());
		binder.registerCustomEditor(HeightPeople.class, HeightPeople.getPropertyEditor());
		binder.registerCustomEditor(Quantity.class, Quantity.getPropertyEditor());
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getEditProfile(Model model) {
		model.addAttribute("profileForm", editProfileService.findProfileById(SecurityUtil.getCurrentProfile()));
		return gotoProfileJSP(model);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String saveEditProfile(@Valid @ModelAttribute("profileForm") Profile profileForm,
			BindingResult bindingResult, @RequestParam("profilePhoto") MultipartFile uploadPhoto) {
		if (bindingResult.hasErrors()) {
			return "edit/profile";
		} else {
			try {
				Profile profileLoader = editProfileService.findProfileById(SecurityUtil.getCurrentProfile());
				String heightProfileLoader = "";
				if(profileLoader.getHeight() != null){
					heightProfileLoader += profileLoader.getHeight().getDbValue();
				}
				editProfileService.updateProfileData(SecurityUtil.getCurrentProfile(), profileForm, uploadPhoto);
				Profile profileUpdate = editProfileService.findProfileById(SecurityUtil.getCurrentProfile());
					String heightProfileUpdate = profileUpdate.getHeight().getDbValue();
					if(!(heightProfileLoader.equals(heightProfileUpdate))){
						proccesChart(profileUpdate);
					}
				return "redirect:/edit/spouse";
			} catch (FormValidationException e) {
				bindingResult.addError(e.buildFieldError("profileForm"));
				return "edit/profile";
			}
		}
	}
	
	private String gotoProfileJSP(Model model) {
		model.addAttribute("heightPeoples", staticDataService.getAllHeightPeoples());
		model.addAttribute("weightCategorys", staticDataService.getAllWeightCategorys());
		return "edit/profile";
	}
	//************************** Game
	@RequestMapping(value = "/edit/game", method = RequestMethod.GET)
	public String getGame(){
		return gotoGameJSP();
	}
	
	private String gotoGameJSP() {
		return "edit/game";
	}
	
	//************************** Chart
		@RequestMapping(value = "/edit/chart", method = RequestMethod.GET)
		public String getChart(Model model) throws FileNotFoundException {
			//Profile profile = editProfileService.findProfileById(SecurityUtil.getCurrentProfile());
			ChartPath chartPath = editProfileService.findChartPathsById(SecurityUtil.getCurrentProfile());
			model.addAttribute("chartPath", chartPath);
			//entryModelAttribute(profile, model);
			return gotoChartJSP();
		}
		
		private String gotoChartJSP() {
			return "edit/chart";
		}
		
		private void proccesChart(Profile profile){
			PieDataset pdSetOneMonth = createDataSetOneMonth(profile);
			PieDataset pdSetSixMonth = createDataSetSixMonth(profile);
			JFreeChart chartOneMonth = createChart(pdSetOneMonth, "");
			JFreeChart chartSixMonth = createChart(pdSetSixMonth, "");
			Integer r = new Random().nextInt();
			String pathOne = "/media/chart/res"+ "OneMonth" + r + ".png";
			String pathSix = "/media/chart/res"+ "SixMonth" + r + ".png";
				ChartPathForm chartPathForm = new ChartPathForm();
				List<ChartPath> items = new ArrayList<>();
				ChartPath chartPath = new ChartPath();
				chartPath.setPathOneMonth(pathOne);
				chartPath.setPathSixMonth(pathSix);
				items.add(chartPath);
				chartPathForm.setItems(items);
				editProfileService.updateChartPaths(SecurityUtil.getCurrentProfile(), chartPathForm.getItems());
			
			try {
				ChartUtilities.saveChartAsPNG(new File(rootPathMediaFile + pathOne), chartOneMonth, 750, 400);
				ChartUtilities.saveChartAsPNG(new File(rootPathMediaFile + pathSix), chartSixMonth, 750, 400);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		private JFreeChart createChart(PieDataset dataset, String title) {
			JFreeChart chart = ChartFactory.createPieChart3D(title, dataset,true, true, false);
			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setStartAngle(290);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.5f);
			return chart;
		}

		private PieDataset createDataSetOneMonth(Profile profile) {
			DefaultPieDataset dpd = new DefaultPieDataset();
			int res = convertHeight(profile);
			String[] arrs = staticDataService.mapVegetableNutritionist();
			double oneMonth = 0.3;
			double result = res*oneMonth;
			for(String arr : arrs){
				dpd.setValue(arr + " " + result + " кг", result);
			}
			createVegetableNutritionistOneItems(arrs, result);
			return dpd;
		}
		
		private void createVegetableNutritionistOneItems(String[] arrs, double result){
			VegetableNutritionistForm vegetableNutritionistForm = new VegetableNutritionistForm();
			List<VegetableNutritionist> items = new ArrayList<>();
			for(int i = 0; i < arrs.length; i++){
				VegetableNutritionist vegetableNutritionist = new VegetableNutritionist();
				vegetableNutritionist.setName(arrs[i]);
				vegetableNutritionist.setCount(new BigDecimal(result));
				items.add(i, vegetableNutritionist);
			}			
			vegetableNutritionistForm.setItems(items);
			editProfileService.updateVegetableNutritionists(SecurityUtil.getCurrentProfile(), vegetableNutritionistForm.getItems());
			
		}
		
		private PieDataset createDataSetSixMonth(Profile profile) {
			DefaultPieDataset dpd = new DefaultPieDataset();
			int res = convertHeight(profile);
			String[] arrs = staticDataService.mapVegetableNutritionist();
			double sixMonth = 1.8;
			double result = res*sixMonth;
			for(String arr : arrs){
				dpd.setValue(arr + " " + result + " кг", result);
			}
			createVegetableNutritionistSixItems(arrs, result);
			return dpd;
		}
		
		private void createVegetableNutritionistSixItems(String[] arrs, double result){
			VegetableNutritionistSixForm vegetableNutritionistSixForm = new VegetableNutritionistSixForm();
			List<VegetableNutritionistSix> items = new ArrayList<>();
			for(int i = 0; i < arrs.length; i++){
				VegetableNutritionistSix vegetableNutritionistSix = new VegetableNutritionistSix();
				vegetableNutritionistSix.setName(arrs[i]);
				vegetableNutritionistSix.setCount(new BigDecimal(result));
				items.add(0, vegetableNutritionistSix);
			}
			vegetableNutritionistSixForm.setItems(items);
			editProfileService.updateVegetableNutritionistSixs(SecurityUtil.getCurrentProfile(), vegetableNutritionistSixForm.getItems());
			
		}
		
		private int convertHeight(Profile profile){
			return Integer.parseInt(StringUtils.remove(profile.getHeight().getDbValue(), "SM_"));
		}
		
	//**************************Spouse
	@RequestMapping(value = "/edit/spouse", method = RequestMethod.GET)
	public String getEditSpouse(Model model) {
		Spouse spouse = editProfileService.findSpouseById(SecurityUtil.getCurrentProfile());
		if(spouse == null){
			model.addAttribute("spouseForm", new SpouseForm());
		} else {
			model.addAttribute("spouseForm", new SpouseForm(spouse));
		}
		return gotoSpouseJSP();
	}
	
	@RequestMapping(value = "/edit/spouse", method = RequestMethod.POST)
	public String saveEditSpouse(@Valid @ModelAttribute("spouseForm") SpouseForm spouseForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return gotoSpouseJSP();
		} else {
			try {
			editProfileService.updateSpouse(spouseForm, SecurityUtil.getCurrentProfile());
			return "redirect:/edit/child";
			} catch  (FormValidationException e) {
				bindingResult.addError(e.buildFieldError("spouseForm"));
				return "edit/spouse";
			}
		}
	}
	
	private String gotoSpouseJSP() {
		return "edit/spouse";
	}
	
	//************************Child
	@RequestMapping(value = "/edit/child", method = RequestMethod.GET)
	public String getEditChild(Model model) {
		model.addAttribute("childForm", new ChildForm(editProfileService.listChilds(SecurityUtil.getCurrentProfile())));
		return gotoChildJSP();
	}
	
	@RequestMapping(value = "/edit/child", method = RequestMethod.POST)
	public String saveEditChild(@Valid @ModelAttribute("childForm") ChildForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return gotoChildJSP();
		} else {
			editProfileService.updateChilds(SecurityUtil.getCurrentProfile(), form.getItems());
			return "redirect:/"+gotoChildJSP();
		}
	}
	
	private String gotoChildJSP() {
		return "edit/child";
	}
//******************************** VegetableSelection
	@RequestMapping(value = "/edit/vegetable-selection", method = RequestMethod.GET)
	public String getEditVegetableSelection() {
		return gotoVegetableSelectionJSP();
	}
	
	private String gotoVegetableSelectionJSP() {
		return "edit/vegetable-selection";
	}
//************************************	VegetableSelection month one
	@RequestMapping(value = "/edit/vegetable-selection/one-month", method = RequestMethod.GET)
	public String getEditVegetableSelectionOneMonth(Model model) {
		model.addAttribute("vegetableSelectionForm", new VegetableSelectionForm(editProfileService.listVegetableSelections(SecurityUtil.getCurrentProfile())));
		model.addAttribute("vegetableNutritionistForm", new VegetableNutritionistForm(editProfileService.listVegetableNutritionists(SecurityUtil.getCurrentProfile())));
		return gotoVegetableSelectionOneMonthJSP(model);
	}
	
	@RequestMapping(value = "/edit/vegetable-selection/one-month", method = RequestMethod.POST)
	public String saveEditVegetableSelectionOneMonth(
			@Valid @ModelAttribute("vegetableSelectionForm") VegetableSelectionForm form, 
			BindingResult bindingResult, 
			Model model) {
		if (bindingResult.hasErrors()) {
			return gotoVegetableSelectionOneMonthJSP(model);
		} else {
			editProfileService.updateVegetableSelections(SecurityUtil.getCurrentProfile(), form.getItems());
			return "redirect:/" + gotoVegetableSelectionOneMonthJSP(model);
		}
	}
	
	private String gotoVegetableSelectionOneMonthJSP(Model model) {
		model.addAttribute("vegetableSelections", staticDataService.getAllQuantitys());
		return "edit/vegetable-selection/one-month";
	}
//************************************	VegetableSelection month six
		@RequestMapping(value = "/edit/vegetable-selection/six-month", method = RequestMethod.GET)
		public String getEditVegetableSelectionSixMonth(Model model) {
			model.addAttribute("vegetableSelectionSixForm", new VegetableSelectionSixForm(editProfileService.listVegetableSelectionsSix(SecurityUtil.getCurrentProfile())));
			model.addAttribute("vegetableNutritionistSixForm", new VegetableNutritionistSixForm(editProfileService.listVegetableNutritionistSixs(SecurityUtil.getCurrentProfile())));
			return gotoVegetableSelectionSixMonthJSP(model);
		}
		
		@RequestMapping(value = "/edit/vegetable-selection/six-month", method = RequestMethod.POST)
		public String saveEditVegetableSelectionSixMonth(
				@Valid @ModelAttribute("vegetableSelectionSixForm") VegetableSelectionSixForm form, 
				BindingResult bindingResult, 
				Model model) {
			if (bindingResult.hasErrors()) {
				return gotoVegetableSelectionSixMonthJSP(model);
			} else {
				editProfileService.updateVegetableSelectionsSix(SecurityUtil.getCurrentProfile(), form.getItems());
				return "redirect:/" + gotoVegetableSelectionSixMonthJSP(model);
			}
		}
		
		private String gotoVegetableSelectionSixMonthJSP(Model model) {
			model.addAttribute("vegetableSelectionsSix", staticDataService.getAllQuantitys());
			return "edit/vegetable-selection/six-month";
		}
//************************************
	@RequestMapping(value = "/edit/password", method = RequestMethod.GET)
	public String getEditPasswords(Model model) {
		model.addAttribute("passwordForm", new PasswordForm());
		return "password";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.POST)
	public String saveEditPasswords(@Valid @ModelAttribute("passwordForm") PasswordForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			formErrorConverter.convertFormErrorToFieldError(FieldMatch.class, form, bindingResult);
			return "password";
		} else {
			CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
			Profile profile = editProfileService.updateProfilePassword(currentProfile, form);
			SecurityUtil.authentificate(profile);
			return "redirect:/" + currentProfile.getUid();
		}
	}

	@RequestMapping(value = "/my-profile")
	public String getMyProfile(Model model) {
		CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
		return "redirect:/" + currentProfile.getUid();
	}
}
