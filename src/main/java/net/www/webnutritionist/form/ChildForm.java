package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.Child;

public class ChildForm {
	
	@Valid
	private List<Child> items = new ArrayList<>();
	
	public ChildForm() {
		super();
	}

	public ChildForm(List<Child> items) {
		super();
		this.items = items;
	}

	public List<Child> getItems() {
		return items;
	}

	public void setItems(List<Child> items) {
		this.items = items;
	}
}
