package com.together.right.now.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.together.right.now.dto.UserInfoDto;
import com.together.right.now.service.UserInfoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean(reset = MockReset.BEFORE)
	private UserInfoService service;

	@SuppressWarnings("unchecked")
	@Test
	public void getUserInfo_happy() throws Exception {
		UserInfoDto dto1 = UserInfoDto.builder().id("1").lastName("Skeezix").build();
		UserInfoDto dto2 = UserInfoDto.builder().id("2").registrationId("8yVm_tTu31").build();
		List<UserInfoDto> dtoList = Arrays.asList(dto1, dto2);
		doReturn(dtoList).when(service).getUserInfo();

		String path = "/userinfo";

		MvcResult mvcResult = mockMvc
				.perform(
						get(path))
				.andExpect(status().isOk())
				.andReturn();

		UserInfoDto[] dtoArray = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserInfoDto[].class);
		List<UserInfoDto> actual = Arrays.asList(dtoArray);
		assertEquals(dtoList, actual);
		verify(service).getUserInfo();
	}

	@Test
	public void getUserInfo_exception() throws Exception {
		RestClientException rce = mock(RestClientException.class);
		String expected = "Dainbrammage!";
		doReturn(expected).when(rce).getMessage();
		doThrow(rce).when(service).getUserInfo();

		String path = "/userinfo";

		MvcResult mvcResult = mockMvc
				.perform(
						get(path))
				.andExpect(status().isInternalServerError())
				.andReturn();

		String actual = mvcResult.getResponse().getErrorMessage();
		assertEquals(expected, actual);
	}
}
