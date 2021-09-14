package com.together.right.now.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.together.right.now.dto.UserInfoDto;
import com.together.right.now.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserInfoController {

	private final UserInfoService userInfoService;

	@Autowired
	public UserInfoController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@GetMapping("/userinfo")
	public ResponseEntity<List<UserInfoDto>> getUserInfo() {
		try {
			List<UserInfoDto> dtoList = userInfoService.getUserInfo();
			return ResponseEntity.ok(dtoList);
		} catch (RestClientException rce) {
			log.error(ExceptionUtils.getStackTrace(rce));
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, rce.getMessage(), rce);
		}
	}
}
