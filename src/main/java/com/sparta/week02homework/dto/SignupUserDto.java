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
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String username;

    @AssertTrue(message = "비밀번호안에 이메일값이 포함되어 있습니다.") // return true면 포함X false면 포함
    public boolean isEmailInPassword() {
        if (this.email == null || this.password == null){
            throw new IllegalArgumentException("요청 값이 없습니다");
        }
        return !this.password.contains(this.email.split("@")[0]);
    }

}
