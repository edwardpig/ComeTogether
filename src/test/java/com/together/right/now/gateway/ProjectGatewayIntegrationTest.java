package com.together.right.now.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.together.right.now.dto.ProjectDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectGatewayIntegrationTest {

	@Autowired
	ProjectGateway gateway;

	@Test
	public void getProjects() {
		List<ProjectDto> dtoList = gateway.getProjects();

		ProjectDto dto = dtoList.get(0);
		assertEquals("1", dto.getId());
		assertEquals("1", dto.getProjectId());
		assertEquals("1", dto.getUserId());
	}
}
