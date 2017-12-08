package net.www.webnutritionist.component.impl;


import org.springframework.stereotype.Component;

import net.www.webnutritionist.component.DataBuilder;
import net.www.webnutritionist.entity.Order;
import net.www.webnutritionist.util.DataUtil;

@Component
public class DataBuilderImpl implements DataBuilder {
	private static final String UID_DELIMETER = "-";
	
	@Override
	public String buildProfileUid(String firstName, String lastName) {
		return DataUtil.normalizeName(firstName) + UID_DELIMETER + DataUtil.normalizeName(lastName);
	}

	@Override
	public String buildRestoreAccessLink(String appHost, String token) {
		return appHost + "/restore/" + token;
	}

	@Override
	public String rebuildUidWithRandomSuffix(String baseUid, String alphabet, int letterCount) {
		return baseUid + UID_DELIMETER + DataUtil.generateRandomString(alphabet, letterCount);
	}
	
	@Override
	public String buildNewOrderCreatedNotificationMessage(String appHost, Order order) {
		return appHost + "/order?id=" + order.getId();
	}
}
