package ru.kirill.hotelreserve.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.kirill.hotelreserve.integration.IntegrationTestBase;
import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.util.TestObjectBuilder;
import java.lang.reflect.Constructor;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.kirill.hotelreserve.dto.UserRequest.Fields.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CRUDControllerTest extends IntegrationTestBase {

    private static final String ENDPOINT = "/api/v1/users";
    private static final String CORRECT_ID = "/1";
    private static final String WRONG_ID = "/1000";
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 3;
    private static final String JS_PTH = "$.";
    private static final Constructor<MockHttpServletRequestBuilder> REQUEST_CONSTRUCTOR;

    static {
        try {
            REQUEST_CONSTRUCTOR = MockHttpServletRequestBuilder.class
                    .getDeclaredConstructor(HttpMethod.class, String.class, Object[].class);
            REQUEST_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void shouldGetAll() {
        mockMvc
                .perform(get(ENDPOINT))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldGetPage() {
        mockMvc
                .perform(get(ENDPOINT)
                        .param("page", PAGE.toString())
                        .param("size", SIZE.toString())
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data", hasSize(SIZE))
                );
    }

    @Test
    @SneakyThrows
    void shouldGetByCorrectId() {
        mockMvc
                .perform(get(ENDPOINT + CORRECT_ID))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldNotGetByWrongId() {
        mockMvc
                .perform(get(ENDPOINT + WRONG_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldCreateWithCorrectRequest() {
        UserRequest request = TestObjectBuilder.getCorrectUserRequest();
        checkPostRequestResponseAndStatus(request, POST, "", status().isCreated());
    }


    @Test
    @SneakyThrows
    void shouldNotCreateWithWrongRequest() {
        UserRequest request = TestObjectBuilder.getWrongUserRequest();
        postRequest(request, POST, "")
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldUpdateWithCorrectIdAndRequest() {
        UserRequest request = TestObjectBuilder.getCorrectUserRequest();
        checkPostRequestResponseAndStatus(request, PUT, CORRECT_ID, status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldNotUpdateWithWrongId() {
        UserRequest request = TestObjectBuilder.getCorrectUserRequest();
        postRequest(request, PUT, WRONG_ID)
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldNotUpdateWithWrongRequest() {
        UserRequest request = TestObjectBuilder.getWrongUserRequest();
        postRequest(request, PUT, CORRECT_ID)
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void shouldDeleteWithCorrectId() {
        mockMvc
                .perform(delete(ENDPOINT + CORRECT_ID))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldNotDeleteWithWrongId() {
        mockMvc
                .perform(delete(ENDPOINT + WRONG_ID))
                .andExpect(status().isNotFound());
    }


    @SneakyThrows
    private void checkPostRequestResponseAndStatus(UserRequest request, HttpMethod put, String path,
                                                   ResultMatcher status) {
        postRequest(request, put, path)
                .andExpectAll(
                        status,
                        jsonPath(JS_PTH + email).value(request.getEmail()),
                        jsonPath(JS_PTH + firstName).value(request.getFirstName()),
                        jsonPath(JS_PTH + lastName).value(request.getLastName()),
                        jsonPath(JS_PTH + role).value(request.getRole()),
                        jsonPath(JS_PTH + rawPassword).doesNotExist()
                );
    }

    @NotNull
    @SneakyThrows
    private ResultActions postRequest(UserRequest request, HttpMethod httpMethod, String path, Object... vars) {
        String requestBody = objectMapper.writeValueAsString(request);
        return mockMvc
                .perform(REQUEST_CONSTRUCTOR
                        .newInstance(httpMethod, ENDPOINT + path, vars)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                );
    }
}
