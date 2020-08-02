package com.epam.authservice.controller;

import com.epam.authservice.dto.request.UserRequestDto;
import com.epam.authservice.model.Role;
import com.epam.authservice.model.User;
import com.epam.authservice.service.repository.UserRepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(SpringExtension.class)
//@WebMvcTest(UserController.class)
@SpringBootTest//(properties = {"security.basic.enabled=false"})
@AutoConfigureMockMvc(addFilters = false)
@TestExecutionListeners(mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS, listeners = {
        WithSecurityContextTestExecutionListener.class
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserRepositoryService userRepositoryService;

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "USER")
    public void getAllShouldReturn403() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void getAllShouldReturnOK() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getByAccountShouldReturnOK() throws Exception {
        when(userRepositoryService.findByAccount(any())).thenReturn(new User());
        this.mockMvc.perform(get("/users/byAccount")
                .param("account", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void createShouldReturnOK() throws Exception {
        final UserRequestDto user = new UserRequestDto();
        user.setAccount("test");
        user.setRoles(Set.of(Role.USER));
        when(userRepositoryService.save(any(User.class))).then(returnsFirstArg());
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void createShouldReturn400() throws Exception {
        final UserRequestDto user = new UserRequestDto();
        user.setAccount("test");
        user.setRoles(null);
        when(userRepositoryService.save(any(User.class))).then(returnsFirstArg());
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturnOK() throws Exception {
        final UserRequestDto user = new UserRequestDto();
        user.setAccount("test");
        user.setRoles(Set.of(Role.USER));
        when(userRepositoryService.update(any(User.class))).then(returnsFirstArg());
        this.mockMvc.perform(put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShouldReturn400() throws Exception {
        final UserRequestDto user = new UserRequestDto();
        user.setAccount(null);
        user.setRoles(Set.of(Role.USER));
        when(userRepositoryService.update(any(User.class))).then(returnsFirstArg());
        this.mockMvc.perform(put("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteShouldReturnOK() throws Exception {
        this.mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());
    }
}