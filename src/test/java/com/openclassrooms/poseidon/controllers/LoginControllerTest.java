package com.openclassrooms.poseidon.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginControllerTest {

    @InjectMocks
    LoginController controller;

    @Test
    public void testShowLoginPage() {
        String view = controller.showLoginPage();
        assertEquals("login", view);
    }
}
