package net.www.webnutritionist.service;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import net.www.webnutritionist.model.HeightPeople;
import net.www.webnutritionist.model.Quantity;
import net.www.webnutritionist.model.WeightCategory;

public interface StaticDataService {

	@Nonnull Map<Integer, String> mapDiets();
	
	@Nonnull String[] mapVegetableNutritionist();

	@Nonnull Collection<HeightPeople> getAllHeightPeoples();

	@Nonnull Collection<WeightCategory> getAllWeightCategorys();
	
	@Nonnull Collection<Quantity> getAllQuantitys();
}
