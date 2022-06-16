package com.sparta.week02homework.controller;

import com.sparta.week02homework.domain.UserRole;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.SignupUserDto;
import com.sparta.week02homework.dto.UserInfoDto;
import com.sparta.week02homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @RequestMapping("/auth")
    public Authentication auth() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }


    @PostMapping("/join")
    public Long join(@Valid @RequestBody SignupUserDto signupUserDto) {
        return userService.join(signupUserDto);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        return userService.login(user);
    }

    @PostMapping("/user/userinfo")
    public UserInfoDto getUserInfo(@AuthenticationPrincipal Users userDetails) {

        String username = userDetails.getUsername();
        UserRole roles = userDetails.getRoles();

        return new UserInfoDto(username, roles);
    }

}
