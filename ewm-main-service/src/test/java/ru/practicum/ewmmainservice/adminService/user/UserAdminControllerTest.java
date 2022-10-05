package ru.practicum.ewmmainservice.adminService.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;
import ru.practicum.ewmmainservice.utils.PageParam;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAdminController.class)
class UserAdminControllerTest {
    private static final String API = "/admin/users";
    @MockBean
    UserAdminService userAdminService;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web).build();
    }
// TODO: 20.09.2022 протестировать лучше ApiError

    @Test
    void test1_1addNewUser() throws Exception {
        NewUserDto newUserDto = new NewUserDto("email@mail.com", "name");
        UserDto userDto = new UserDto(1L, "email@mail.com", "name");
        when(userAdminService.addNewUser(newUserDto))
                .thenReturn(userDto);
        mvc.perform(post("/admin/users")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newUserDto)));
        Mockito.verify(userAdminService, times(1)).addNewUser(newUserDto);
    }

    @Test
    void test1_2addNewUser_withBadEmail() throws Exception {
        NewUserDto newUserDto = new NewUserDto("emailmail.com", "name");
        mvc.perform(post("/admin/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason", is("One or more fields are not valid.")));
        Mockito.verify(userAdminService, times(0)).addNewUser(newUserDto);
    }

    @Test
    void test1_3addNewUser_UserAlredyExist() throws Exception {
        NewUserDto newUserDto = new NewUserDto("email@mail.com", "name");
        when(userAdminService.addNewUser(newUserDto))
                .thenThrow(new ModelAlreadyExistsException("param", "value", "className"));
        mvc.perform(post("/admin/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newUserDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("className param = value already exist.")));

    }

    @Test
    void test2_1findAll_withOutIds() throws Exception {
        Pageable pageable = PageRequest.of(0, 2);
        mvc.perform(get(API)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .param("from", "0")
                .param("size", "2"));
        Mockito.verify(userAdminService, times(1)).findAll(pageable);
    }

    @Test
    void test2_3findAll_withIds() throws Exception {
        Pageable pageable = PageRequest.of(0, 2);
        String[] ids = {"1", "2"};
        Long[] idsLong = {1L, 2L};
        mvc.perform(get(API)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .param("from", "0")
                .param("size", "2")
                .param("ids", ids));
        Mockito.verify(userAdminService, times(1)).findByIds(idsLong, PageParam.createPageable(0, 2));
    }


    @Test
    void test3_1delete() throws Exception {
        mvc.perform(delete(API + "/{userId}", 3)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(userAdminService, times(1)).deleteUser(3L);
    }
}