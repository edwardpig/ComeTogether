package com.together.right.now.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.together.right.now.dto.UserInfoDto;

@Component
public class UserGateway {

	private final RestTemplate restTemplate;

	@Value("${users.registered.url}")
	private String registeredUrl;

	@Value("${users.unregistered.url}")
	private String unregisteredUrl;

	@Autowired
	public UserGateway(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<UserInfoDto> getRegistered() {
		ResponseEntity<List<UserInfoDto>> response =
				restTemplate.exchange(registeredUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserInfoDto>>() {});
		return response.getBody();
	}

	public List<UserInfoDto> getUnregistered() {
		ResponseEntity<List<UserInfoDto>> response =
				restTemplate.exchange(unregisteredUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserInfoDto>>() {});
		return response.getBody();
	}
}
