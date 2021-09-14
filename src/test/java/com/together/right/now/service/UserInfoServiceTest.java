package com.together.right.now.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.together.right.now.dto.ProjectDto;
import com.together.right.now.dto.UserInfoDto;
import com.together.right.now.gateway.ProjectGateway;
import com.together.right.now.gateway.UserGateway;

public class UserInfoServiceTest {

	@Mock
	ProjectGateway projectGateway;

	@Mock
	UserGateway userGateway;

	@InjectMocks
	UserInfoService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getUserInfo() {
		UserInfoDto registered1 = getUserInfoDto("9", true);
		UserInfoDto registered2 = getUserInfoDto("11", true);
		List<UserInfoDto> registeredList = Arrays.asList(registered1, registered2);
		doReturn(registeredList).when(userGateway).getRegistered();

		UserInfoDto unregistered1 = getUserInfoDto("4", false);
		UserInfoDto unregistered2 = getUserInfoDto("400", false);
		List<UserInfoDto> unregisteredList = Arrays.asList(unregistered1, unregistered2);
		doReturn(unregisteredList).when(userGateway).getUnregistered();

		List<ProjectDto> projectList = Arrays.asList(
				getProjectDto("1", "9"),
				getProjectDto("2", "11"),
				getProjectDto("3", "400"),
				getProjectDto("4", "400"));
		doReturn(projectList).when(projectGateway).getProjects();

		List<UserInfoDto> actual = service.getUserInfo();

		assertEquals(4, actual.size());
		assertEquals("4", actual.get(0).getId());
		assertEquals("9", actual.get(1).getId());
		assertEquals("11", actual.get(2).getId());
		assertEquals("400", actual.get(3).getId());

		// No projects associated with userId "4"
		assertTrue(actual.get(0).getProjectIds().isEmpty());

		// userId "9" is only associated with project "1"
		assertEquals(1, actual.get(1).getProjectIds().size());
		assertEquals("1", actual.get(1).getProjectIds().get(0));

		// userId "11" is only associated with project "2"
		assertEquals(1, actual.get(2).getProjectIds().size());
		assertEquals("2", actual.get(2).getProjectIds().get(0));

		// userId "400" is associated with projects "3" and "4"
		assertEquals(2, actual.get(3).getProjectIds().size());
		assertEquals("3", actual.get(3).getProjectIds().get(0));
		assertEquals("4", actual.get(3).getProjectIds().get(1));
	}

	@Test
	public void getProjectLists() {
		List<ProjectDto> projects = Arrays.asList(
				getProjectDto("12", "1"),
				getProjectDto("11", "2"),
				getProjectDto("10", "3"),
				getProjectDto("9", "1"),
				getProjectDto("8", "2"),
				getProjectDto("7", "4"),
				getProjectDto("6", "1"),
				getProjectDto("5", "5"),
				getProjectDto("4", "6"),
				getProjectDto("3", "1"),
				getProjectDto("2", "2"),
				getProjectDto("1", "3"));

		List<String> expectedFor1 = Arrays.asList("12", "9", "6", "3");
		List<String> expectedFor2 = Arrays.asList("11", "8", "2");
		List<String> expectedFor3 = Arrays.asList("10", "1");
		List<String> expectedFor4 = Arrays.asList("7");
		List<String> expectedFor5 = Arrays.asList("5");
		List<String> expectedFor6 = Arrays.asList("4");

		Map<String, List<String>> projectMap = service.getProjectLists(projects);

		assertEquals(expectedFor1, projectMap.get("1"));
		assertEquals(expectedFor2, projectMap.get("2"));
		assertEquals(expectedFor3, projectMap.get("3"));
		assertEquals(expectedFor4, projectMap.get("4"));
		assertEquals(expectedFor5, projectMap.get("5"));
		assertEquals(expectedFor6, projectMap.get("6"));
	}

	@Test
	public void addProjectsToUsers() {
		UserInfoDto user1 = getUserInfoDto("1", true);
		UserInfoDto user2 = getUserInfoDto("2", true);
		UserInfoDto user3 = getUserInfoDto("3", false);
		List<UserInfoDto> userList = Arrays.asList(user1, user2, user3);

		List<String> expectedForUser1 = Arrays.asList("919", "37");
		List<String> expectedForUser3 = Arrays.asList("43");

		Map<String, List<String>> projectMap = new HashMap<>();
		projectMap.put("1", expectedForUser1);
		projectMap.put("3", expectedForUser3);

		service.addProjectsToUsers(userList, projectMap);

		assertEquals(expectedForUser1, user1.getProjectIds());
		assertTrue(user2.getProjectIds().isEmpty());
		assertEquals(expectedForUser3, user3.getProjectIds());
	}

	@Test
	public void addProjectsToUsersUsingStream() {
		UserInfoDto user1 = getUserInfoDto("1", true);
		UserInfoDto user2 = getUserInfoDto("2", true);
		UserInfoDto user3 = getUserInfoDto("3", false);
		List<UserInfoDto> userList = Arrays.asList(user1, user2, user3);

		List<String> expectedForUser1 = Arrays.asList("919", "37");
		List<String> expectedForUser3 = Arrays.asList("43");

		Map<String, List<String>> projectMap = new HashMap<>();
		projectMap.put("1", expectedForUser1);
		projectMap.put("3", expectedForUser3);

		service.addProjectsToUsersUsingStream(userList, projectMap);

		assertEquals(expectedForUser1, user1.getProjectIds());
		assertTrue(user2.getProjectIds().isEmpty());
		assertEquals(expectedForUser3, user3.getProjectIds());
	}

	private UserInfoDto getUserInfoDto(String id, boolean isRegistered) {
		UserInfoDto dto = UserInfoDto.builder().id(id).build();
		if (isRegistered) {
			dto.setCity("Nashville");
		} else {
			dto.setRegistrationId("jT31xO0n5");
		}

		return dto;
	}

	private ProjectDto getProjectDto(String projectId, String userId) {
		return ProjectDto.builder().id(projectId).projectId(projectId).userId(userId).build();
	}
}
