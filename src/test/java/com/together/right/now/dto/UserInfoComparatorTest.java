package com.together.right.now.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserInfoComparatorTest {

	UserInfoComparator comparator = new UserInfoComparator();

	@Test
	public void compare_nulls() {
		UserInfoDto dto = new UserInfoDto();
		dto.setId("7");

		assertEquals(0, comparator.compare(null, null));
		assertEquals(-1, comparator.compare(null, dto));
		assertEquals(1, comparator.compare(dto, null));
	}

	@Test
	public void compare_nonNull() {
		UserInfoDto small = UserInfoDto.builder().id("3").build();
		UserInfoDto big = UserInfoDto.builder().id("3333").build();

		assertEquals(0, comparator.compare(small, small));
		assertEquals(0, comparator.compare(big, big));
		assertTrue(comparator.compare(small, big) < 0);
		assertTrue(comparator.compare(big, small) > 0);
	}
}
