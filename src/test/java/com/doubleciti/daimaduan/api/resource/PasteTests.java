package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
public class PasteTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createOnePaste() {
        final String email = "paste1@test.com";
        final String username = "paste1";
        final String password = "password";

        UserRegisterModel userRegisterModel = new UserRegisterModel();
        userRegisterModel.setEmail(email);
        userRegisterModel.setUsername(username);
        userRegisterModel.setPassword(password);

        HttpEntity<UserRegisterModel> registerRequest = new HttpEntity<>(userRegisterModel);
        restTemplate.exchange("/user/register",
                HttpMethod.POST,
                registerRequest,
                UserInfoModel.class);

        UserLoginModel userLoginModel = new UserLoginModel();
        userLoginModel.setEmail(email);
        userLoginModel.setPassword(password);

        HttpEntity<UserLoginModel> loginRequest = new HttpEntity<>(userLoginModel);
        HttpEntity<UserInfoModel> response = restTemplate.exchange("/user/login",
                HttpMethod.POST,
                loginRequest,
                UserInfoModel.class);

        PasteCreateModel pasteCreateModel = new PasteCreateModel();
        pasteCreateModel.setTitle("This is just a test!");

        CodeModel codeModel1 = new CodeModel();
        codeModel1.setTitle("This is code1");
        codeModel1.setContent("package com.doubleciti.daimaduan.api.domain;");

        CodeModel codeModel2 = new CodeModel();
        codeModel2.setTitle("This is code2");
        codeModel2.setContent("package com.doubleciti.daimaduan.api.resource;");

        List<CodeModel> codeModelList = new ArrayList<>();
        codeModelList.add(codeModel1);
        codeModelList.add(codeModel2);

        pasteCreateModel.setCodes(codeModelList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(COOKIE, response.getHeaders().getFirst(SET_COOKIE));
        HttpEntity<PasteCreateModel> pasteCreateRequest = new HttpEntity<>(pasteCreateModel, headers);
        ResponseEntity<PasteModel> entity = restTemplate.exchange("/resources/pastes",
                HttpMethod.POST,
                pasteCreateRequest,
                PasteModel.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody().getUser().getEmail()).isEqualTo(email);
        assertThat(entity.getBody().getCodes().size()).isEqualTo(2);
    }
}
