package com.codingbit.context;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;
import reactor.util.context.Context;

class UserServiceTest {

    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        user = new User("Coding-bit");
    }

    @Test
    @DisplayName("It should throw the NoSuchElementException when the user context is not defined")
    void getUserEmail_noContext() {
        StepVerifier.create(userService.getUserEmail())
                .expectError(NoSuchElementException.class)
                .verify();
    }

    @Test
    @DisplayName("It should get the user email via context")
    void getUserEmail_withContext() {
        StepVerifier.create(userService.getUserEmail()
                .subscriberContext(Context.of(ContextType.USER.name(), user)))
                .expectNext(String.format("%s@dev.dev", user.getId()))
                .verifyComplete();
    }

    @Test
    @DisplayName("It should get the user email and log the context to the console")
    void getUserEmail_withLogging() {
        StepVerifier.create(userService.getUserEmail(user.getId())
                .subscriberContext(Context.of(ContextType.USER.name(), user)))
                .expectNext(String.format("%s@dev.dev", user.getId()))
                .verifyComplete();
    }
}