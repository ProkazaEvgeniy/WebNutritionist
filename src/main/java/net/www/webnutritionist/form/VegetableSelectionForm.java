package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.VegetableSelection;

public class VegetableSelectionForm {

	@Valid
	private List<VegetableSelection> items = new ArrayList<>();
	
	public VegetableSelectionForm() {
		super();
	}

	public VegetableSelectionForm(List<VegetableSelection> items) {
		super();
		this.items = items;
	}

	public List<VegetableSelection> getItems() {
		return items;
	}

	public void setItems(List<VegetableSelection> items) {
		this.items = items;
	}
}
