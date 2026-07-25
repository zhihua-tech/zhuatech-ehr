/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EhrApiIntegrationTests {
    @Autowired MockMvc mvc;

    @Test void employeeCanLoginAndReadPersonalEhrData() throws Exception {
        String token=login("demo", "Demo@2026", "EMPLOYEE");
        mvc.perform(get("/api/dashboard").header("Authorization", "Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.checkedIn").value(true));
        mvc.perform(get("/api/payroll/mine").header("Authorization", "Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data[0].employeeName").value("知华员工"));
        mvc.perform(get("/api/leaves").header("Authorization", "Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data[0].status").value("PENDING"));
    }

    @Test void hrCanReadEmployeesAndRecruitmentData() throws Exception {
        String token=login("hr", "Demo@2026", "HR");
        mvc.perform(get("/api/employees").header("Authorization", "Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].employeeNo").exists());
        mvc.perform(get("/api/recruitment/candidates").header("Authorization", "Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data[0].stage").value("INTERVIEW"));
    }

    @Test void unauthenticatedRequestsAreRejected() throws Exception {
        mvc.perform(get("/api/dashboard")).andExpect(status().isForbidden());
    }

    private String login(String username, String password, String role) throws Exception {
        String body=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\""+username+"\",\"password\":\""+password+"\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.user.role").value(role))
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.data.token");
    }
}
