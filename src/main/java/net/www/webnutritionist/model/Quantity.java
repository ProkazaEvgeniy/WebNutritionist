package net.www.webnutritionist.model;

import java.beans.PropertyEditorSupport;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.www.webnutritionist.util.DataUtil;

public enum Quantity {

	_0, _10, _20, _30, _40, _50, _60, _70, _80, _90, _100;
	
	public int getSliderIntValue() {
		return ordinal();
	}

	public String getDbValue() {
		return name();
	}

	public String getCaption() {
		return DataUtil.convertNameToNumberAnd_(getDbValue());
	}

	public static PropertyEditorSupport getPropertyEditor() {
		return new PropertyEditorSupport() {
			@Override
			public void setAsText(String sliderIntValue) throws IllegalArgumentException {
				setValue(Quantity.values()[Integer.parseInt(sliderIntValue)]);
			}
		};
	}

	@Converter
	public static class PersistJPAConverter implements AttributeConverter<Quantity, String> {
		@Override
		public String convertToDatabaseColumn(Quantity attribute) {
			if(attribute == null) {
				return null;
			} else {
				return attribute.getDbValue();
			}
		}

		@Override
		public Quantity convertToEntityAttribute(String dbValue) {
			if(dbValue == null) {
				return null;
			} else {
				return Quantity.valueOf(dbValue.toUpperCase());
			}
		}
	}
}
