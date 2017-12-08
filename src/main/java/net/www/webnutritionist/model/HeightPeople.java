package net.www.webnutritionist.model;

import java.beans.PropertyEditorSupport;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.www.webnutritionist.util.DataUtil;

public enum HeightPeople {

	SM_0,

	SM_155,

	SM_160,

	SM_165,

	SM_170,

	SM_175,

	SM_180,

	SM_185,

	SM_190;

	public int getSliderIntValue() {
		return ordinal();
	}

	public String getDbValue() {
		return name();
	}

	public String getCaption() {
		return DataUtil.convertNameToNumberAndSM(getDbValue());
	}

	public static PropertyEditorSupport getPropertyEditor() {
		return new PropertyEditorSupport() {
			@Override
			public void setAsText(String sliderIntValue) throws IllegalArgumentException {
				setValue(HeightPeople.values()[Integer.parseInt(sliderIntValue)]);
			}
		};
	}

	@Converter
	public static class PersistJPAConverter implements AttributeConverter<HeightPeople, String> {
		@Override
		public String convertToDatabaseColumn(HeightPeople attribute) {
			if(attribute == null) {
				return null;
			} else {
				return attribute.getDbValue();
			}
		}

		@Override
		public HeightPeople convertToEntityAttribute(String dbValue) {
			if(dbValue == null) {
				return null;
			} else {
				return HeightPeople.valueOf(dbValue.toUpperCase());
			}
		}
	}
}
