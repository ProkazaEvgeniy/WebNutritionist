package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.VegetableSelectionSix;

public class VegetableSelectionSixForm {

	@Valid
	private List<VegetableSelectionSix> items = new ArrayList<>();
	
	public VegetableSelectionSixForm() {
		super();
	}

	public VegetableSelectionSixForm(List<VegetableSelectionSix> items) {
		super();
		this.items = items;
	}

	public List<VegetableSelectionSix> getItems() {
		return items;
	}

	public void setItems(List<VegetableSelectionSix> items) {
		this.items = items;
	}
}
