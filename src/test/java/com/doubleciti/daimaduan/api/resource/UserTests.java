package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.model.UserInfoModel;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class UserTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void register() {
        UserRegisterModel model = new UserRegisterModel();
        model.setEmail("user1@test.com");
        model.setUsername("user1");
        model.setPassword("pass1");

		ResponseEntity<UserInfoModel> entity = restTemplate.postForEntity("/user/register",
                model,
                UserInfoModel.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(entity.getBody().getEmail()).isEqualTo("user1@test.com");
		assertThat(entity.getBody().getId()).isGreaterThanOrEqualTo(1000);
	}

	@Test
    public void getFailureWithRegistration() {
        UserRegisterModel model = new UserRegisterModel();
        model.setEmail("user2@test.com");
        model.setUsername("user2");
        model.setPassword("pass2");

        ResponseEntity<UserInfoModel> entity = restTemplate.postForEntity("/user/register",
                model,
                UserInfoModel.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody().getEmail()).isEqualTo("user2@test.com");

        ResponseEntity<UserInfoModel> entity1 = restTemplate.postForEntity("/user/register",
                model,
                UserInfoModel.class);
        assertThat(entity1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void login() {
        UserLoginModel model = new UserLoginModel();
        model.setEmail("user1@test.com");
        model.setPassword("pass1");

        ResponseEntity<UserInfoModel> entity = restTemplate.postForEntity("/user/login",
                model,
                UserInfoModel.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getEmail()).isEqualTo("user1@test.com");
    }
}
