package net.www.webnutritionist.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.www.webnutritionist.entity.ChartPath;

public class ChartPathForm {

	@Valid
	private List<ChartPath> items = new ArrayList<>();
	
	public ChartPathForm() {
		super();
	}

	public ChartPathForm(List<ChartPath> items) {
		super();
		this.items = items;
	}

	public List<ChartPath> getItems() {
		return items;
	}

	public void setItems(List<ChartPath> items) {
		this.items = items;
	}
	
}
