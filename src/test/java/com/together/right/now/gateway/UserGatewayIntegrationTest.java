package com.together.right.now.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.together.right.now.dto.UserInfoDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserGatewayIntegrationTest {

	@Autowired
	UserGateway gateway;

	@Test
	public void getRegistered() {
		UserInfoDto expected = UserInfoDto.builder()
				.id("1")
				.city("Jaydashire")
				.company("Goyette - Renner")
				.country("South Africa")
				.firstName("firstName 1")
				.lastName("lastName 1")
				.organizationType("organizationType 1")
				.phone("524.276.1570 x487")
				.state("SD")
				.zipCode("68048")
				.disclaimerAccepted(false)
				.languageCode("en")
				.emailAddress("last1@mail.com")
				.build();

		List<UserInfoDto> dtoList = gateway.getRegistered();

		assertEquals(expected, dtoList.get(0));
	}

	@Test
	public void getUnregistered() {
		UserInfoDto expected = UserInfoDto.builder()
				.id("21")
				.emailAddress("email1@somewhere.com")
				.languageCode("en")
				.registrationId("jwsMJNOk3oM3hVM5bGcF1")
				.registrationIdGeneratedTime("156165026851")
				.build();

		List<UserInfoDto> dtoList = gateway.getUnregistered();

		assertEquals(expected, dtoList.get(0));
	}
}
