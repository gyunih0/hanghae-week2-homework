package com.sparta.week02homework.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserDto {
    private String email;
    private String password;
    private String username;
}
