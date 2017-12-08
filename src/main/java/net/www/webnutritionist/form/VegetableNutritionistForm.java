package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.VegetableNutritionist;

public class VegetableNutritionistForm {

	@Valid
	private List<VegetableNutritionist> items = new ArrayList<>();
	
	public VegetableNutritionistForm() {
		super();
	}

	public VegetableNutritionistForm(List<VegetableNutritionist> items) {
		super();
		this.items = items;
	}

	public List<VegetableNutritionist> getItems() {
		return items;
	}

	public void setItems(List<VegetableNutritionist> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return String.format("VegetableNutritionistForm [items=%s]", items);
	}
	
}
