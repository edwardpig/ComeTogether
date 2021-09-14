package com.together.right.now.dto;

import java.util.Comparator;

public class UserInfoComparator implements Comparator<UserInfoDto> {

	@Override
	public int compare(UserInfoDto dto1, UserInfoDto dto2) {
		if (null == dto1) {
			return null == dto2 ? 0 : -1;
		}

		if (null == dto2) {
			return 1;
		}

		int id1 = Integer.valueOf(dto1.getId()).intValue();
		int id2 = Integer.valueOf(dto2.getId()).intValue();

		return id1 - id2;
	}

}
