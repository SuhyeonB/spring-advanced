package org.example.expert.domain.user.service;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("get User by ID")
    void getUser() {
        // given
        String email = "john@email.com";
        long userId = 1L;
        User user = new User(email, "a1234", UserRole.USER);

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        // when
        UserResponse userResponse = userService.getUser(userId);

        // then
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(userId);
        assertThat(userResponse.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("fail to getUser")
    void getUser2() {
        // given
        long userId = 1L;

        given(userRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertThrows(InvalidRequestException.class,
                () -> userService.getUser(userId),
                "User not found");
    }

//    @Test
//    void changePassword() {
//        // given
//        String oldPassword = "a1234567";
//        String newPassword = "A1234567";
//        long userId = 1L;
//
//        User user = new User("john@email.com", oldPassword, UserRole.USER);
//
//        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
//
//        System.out.println("Stored Password: " + user.getPassword());
//
//        // when
//        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);
//        userService.changePassword(userId, request);
//
//        // then
//
//    }
}