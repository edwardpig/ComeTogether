package com.together.right.now.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

	private String id;
	private String city;
	private String company;
	private String country;
	private String firstName;
	private String lastName;
	private String organizationType;
	private String phone;
	private String state;
	private String zipCode;
	private Boolean disclaimerAccepted;
	private String languageCode;
	private String emailAddress;
	private String registrationId;
	private String registrationIdGeneratedTime;

	@Builder.Default
	private List<String> projectIds = new ArrayList<>();
}
