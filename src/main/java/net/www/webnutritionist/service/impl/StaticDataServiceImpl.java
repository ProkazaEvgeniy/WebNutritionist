package net.www.webnutritionist.service.impl;

import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.www.webnutritionist.model.HeightPeople;
import net.www.webnutritionist.model.Quantity;
import net.www.webnutritionist.model.WeightCategory;
import net.www.webnutritionist.service.StaticDataService;

@Service
public class StaticDataServiceImpl implements StaticDataService {

	@Override
	public Map<Integer, String> mapDiets() {
		String[] diets = { "Салатная диета", "Экстремальная диета «Минус два»", "Суп «Если вдруг поправилась»", "Сыроедение", "Диета манекенщицы"};
		Map<Integer, String> map = new LinkedHashMap<>();
		for (int i = 0; i < diets.length; i++) {
			map.put(i + 1, diets[i]);
		}
		return map;
	}
	
	public String[] mapVegetableNutritionist() {
		String[] vegetable = {"Картофель", "Морква", "Абрикос", "Молоко"};
		return vegetable;
	}

	@Override
	public Collection<HeightPeople> getAllHeightPeoples() {
		return EnumSet.allOf(HeightPeople.class);
	}

	@Override
	public Collection<WeightCategory> getAllWeightCategorys() {
		return EnumSet.allOf(WeightCategory.class);
	}

	@Override
	public Collection<Quantity> getAllQuantitys() {
		return EnumSet.allOf(Quantity.class);
	}

	
}
