package com.sparta.week02homework.dto;

import com.sparta.week02homework.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    UserRole role;
}