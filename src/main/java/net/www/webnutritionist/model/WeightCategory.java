package net.www.webnutritionist.model;

import java.beans.PropertyEditorSupport;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.www.webnutritionist.util.DataUtil;

public enum WeightCategory {

	NO,

	_60,

	_60_70,

	_70_80,

	_80_90,

	_90;

	public String getCaption() {
		return DataUtil.capitalizeName(name());
	}

	public String getDbValue() {
		return name();
	}

	public WeightCategory getReverseType() {
		if (this == _60) {
			return _60_70;
		} else if (this == _60_70) {
			return _70_80;
		} else if (this == _70_80) {
			return _80_90;
		} else if (this == _80_90) {
			return _90;
		} else if (this == _90) {
			return _60;
		} else {
			throw new IllegalArgumentException(this + "4 does not have reverse category");
		}
	}

	public static PropertyEditorSupport getPropertyEditor() {
		return new PropertyEditorSupport() {
			@Override
			public void setAsText(String dbValue) throws IllegalArgumentException {
				setValue(WeightCategory.valueOf(dbValue.toUpperCase()));
			}
		};
	}

	@Converter
	public static class PersistJPAConverter implements AttributeConverter<WeightCategory, String> {
		@Override
		public String convertToDatabaseColumn(WeightCategory attribute) {
			if(attribute == null) {
				return null;
			} else {
				return attribute.getDbValue();
			}
		}

		@Override
		public WeightCategory convertToEntityAttribute(String dbValue) {
			if(dbValue == null) {
				return null;
			} else {
				return WeightCategory.valueOf(dbValue.toUpperCase());
			}
		}
	}

}
