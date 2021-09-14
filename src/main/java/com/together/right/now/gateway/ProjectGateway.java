package com.together.right.now.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.together.right.now.dto.ProjectDto;

@Component
public class ProjectGateway {

	private final RestTemplate restTemplate;

	@Value("${project.membership.url}")
	private String projectUrl;

	@Autowired
	public ProjectGateway(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<ProjectDto> getProjects() {
		ResponseEntity<List<ProjectDto>> response =
				restTemplate.exchange(projectUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectDto>>() {});
		return response.getBody();
	}
}
