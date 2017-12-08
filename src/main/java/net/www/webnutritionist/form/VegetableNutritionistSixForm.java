package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.VegetableNutritionistSix;

public class VegetableNutritionistSixForm {

	@Valid
	private List<VegetableNutritionistSix> items = new ArrayList<>();
	
	public VegetableNutritionistSixForm() {
		super();
	}

	public VegetableNutritionistSixForm(List<VegetableNutritionistSix> items) {
		super();
		this.items = items;
	}

	public List<VegetableNutritionistSix> getItems() {
		return items;
	}

	public void setItems(List<VegetableNutritionistSix> items) {
		this.items = items;
	}
}
