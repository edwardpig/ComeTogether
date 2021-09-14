package com.together.right.now.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.together.right.now.dto.UserInfoDto;

public class UserGatewayUnitTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	UserGateway gateway;

	String registeredUrl = "http://onlyregisteredusers.gov";
	String unregisteredUrl = "http://noregistrationforu.edu";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(gateway, "registeredUrl", registeredUrl);
		ReflectionTestUtils.setField(gateway, "unregisteredUrl", unregisteredUrl);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getRegistered() {
		UserInfoDto dto1 = UserInfoDto.builder().id("7").zipCode("12345").build();
		UserInfoDto dto2 = UserInfoDto.builder().id("19").lastName("Python").build();
		List<UserInfoDto> expected = Arrays.asList(dto1, dto2);
		ResponseEntity<List<UserInfoDto>> response = ResponseEntity.ok(expected);

		doReturn(response).when(restTemplate).exchange(eq(registeredUrl), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class));

		List<UserInfoDto> actual = gateway.getRegistered();

		assertEquals(expected, actual);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getUnregistered() {
		UserInfoDto dto1 = UserInfoDto.builder().id("7").emailAddress("a.b@c.d").build();
		UserInfoDto dto2 = UserInfoDto.builder().id("19").languageCode("Python").build();
		List<UserInfoDto> expected = Arrays.asList(dto1, dto2);
		ResponseEntity<List<UserInfoDto>> response = ResponseEntity.ok(expected);

		doReturn(response).when(restTemplate).exchange(eq(unregisteredUrl), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class));

		List<UserInfoDto> actual = gateway.getUnregistered();

		assertEquals(expected, actual);
	}

}
