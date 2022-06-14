package com.sparta.week02homework.dto;


import lombok.*;

import javax.validation.constraints.*;


@Getter
@Setter
public class SignupUserDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "형식에 맞지 않은 이메일 주소입니다.")
    private String email;

    @NotEmpty(message = "비밀번호을 입력해주세요.")
    @Min(value = 4, message = "비밀번호는 최소 4자 이상이어야합니다.") // Min은 숫자만 받는다
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{3,20}$", message = "닉네임은 최소 3자 이상, 알파벳 대소문자와, 숫자가 포함되어야 합니다.")
    private String username;
}
