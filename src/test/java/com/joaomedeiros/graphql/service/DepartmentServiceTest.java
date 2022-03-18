package com.joaomedeiros.graphql.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:departments.json")
    private Resource resourceDepartments;

    @Value("classpath:department.json")
    private Resource resourceDepartment;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldListAllDepartments() throws Exception {
        TypeReference<Map<String,Object>> typeRef = new TypeReference<>() {};
        Map<String, Object> map = objectMapper.readValue(resourceDepartments.getFile(), typeRef);

        MvcResult result = mockMvc.perform(post("/graphql")
                        .content("{\"query\":\"{ departments { id name } }\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getAsyncResult().toString();

        assertEquals(responseJson, map.toString());
    }

    @Test
    void shouldListOnlyOneDepartment() throws Exception {
        TypeReference<Map<String,Object>> typeRef = new TypeReference<>() {};
        Map<String, Object> map = objectMapper.readValue(resourceDepartment.getFile(), typeRef);

        MvcResult result = mockMvc.perform(post("/graphql")
                        .content("{\"query\":\"{ department(id: 1) { id name } }\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getAsyncResult().toString();

        assertEquals(responseJson, map.toString());
    }
}