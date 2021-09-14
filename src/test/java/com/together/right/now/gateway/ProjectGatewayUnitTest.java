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

import com.together.right.now.dto.ProjectDto;

public class ProjectGatewayUnitTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	ProjectGateway gateway;

	String projectUrl = "http://lotsoprojects.net";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(gateway, "projectUrl", projectUrl);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getProjects() {
		ProjectDto dto1 = ProjectDto.builder().id("2").userId("22").projectId("5").build();
		ProjectDto dto2 = ProjectDto.builder().id("3").userId("33").projectId("75").build();
		List<ProjectDto> expected = Arrays.asList(dto1, dto2);
		ResponseEntity<List<ProjectDto>> response = ResponseEntity.ok(expected);

		doReturn(response).when(restTemplate).exchange(eq(projectUrl), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class));

		List<ProjectDto> actual = gateway.getProjects();
		assertEquals(expected, actual);
	}
}
