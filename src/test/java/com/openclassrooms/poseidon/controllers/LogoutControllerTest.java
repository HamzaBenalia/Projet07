package com.openclassrooms.poseidon.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutControllerTest {

    @Autowired
    private MockMvc mvc;
//
//    @Test
//    public void testLogout() throws Exception {
//        // Given
//        HttpSession session = mvc.perform(get("/user")) // Remplacez par une URL qui nécessite une authentification
//                .andExpect(status().isOk())
//                .andReturn()
//                .getRequest()
//                .getSession();
//
//        assertNotNull(session);
//
//        // When
//        MvcResult mvcResult = mvc.perform(get("/app-logout").session((MockHttpSession) session))
//                .andExpect(status().isOk()) // Expect HTTP 200 status
//                .andReturn();
//
//        // Then
//        // Verify that the session is invalidated
//        assertTrue(mvcResult.getRequest().getSession(false) == null || mvcResult.getRequest().getSession(false).getAttributeNames().hasMoreElements() == false);
//
//        // The SecurityContext might not be cleared in the test
//    }
}


