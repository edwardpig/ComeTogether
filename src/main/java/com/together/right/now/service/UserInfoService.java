package com.together.right.now.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.together.right.now.dto.ProjectDto;
import com.together.right.now.dto.UserInfoComparator;
import com.together.right.now.dto.UserInfoDto;
import com.together.right.now.gateway.ProjectGateway;
import com.together.right.now.gateway.UserGateway;

@Component
public class UserInfoService {

	private final ProjectGateway projectGateway;
	private final UserGateway userGateway;
	private final UserInfoComparator comparator = new UserInfoComparator();

	@Autowired
	public UserInfoService(ProjectGateway projectGateway, UserGateway userGateway) {
		this.projectGateway = projectGateway;
		this.userGateway = userGateway;
	}

	public List<UserInfoDto> getUserInfo() {
		List<UserInfoDto> userList = new ArrayList<>();
		userList.addAll(userGateway.getRegistered());
		userList.addAll(userGateway.getUnregistered());
		userList.sort(comparator);

		List<ProjectDto> projects = projectGateway.getProjects();
		Map<String, List<String>> projectMap = getProjectLists(projects);
		addProjectsToUsers(userList, projectMap);

		return userList;
	}

	protected Map<String, List<String>> getProjectLists(List<ProjectDto> projects) {
		Map<String, List<String>> projectMap = new HashMap<>();

		for (ProjectDto dto : projects) {
			List<String> projectList = projectMap.get(dto.getUserId());

			if (null == projectList) {
				projectList = new ArrayList<>();
				projectMap.put(dto.getUserId(), projectList);
			}

			projectList.add(dto.getProjectId());
		}

		return projectMap;
	}

	protected void addProjectsToUsers(List<UserInfoDto> userList, Map<String, List<String>> projectMap) {
		for (UserInfoDto dto : userList) {
			if (null != projectMap.get(dto.getId())) {
				dto.getProjectIds().addAll(projectMap.get(dto.getId()));
			}
		}
	}

	protected void addProjectsToUsersUsingStream(List<UserInfoDto> userList, Map<String, List<String>> projectMap) {
		userList.stream().forEach(u -> {
			if (null != projectMap.get(u.getId())) {
				u.getProjectIds().addAll(projectMap.get(u.getId()));
			}
		});
	}
}
