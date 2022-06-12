package com.sparta.week02homework.service;

import com.sparta.week02homework.config.security.jwt.JwtTokenProvider;
import com.sparta.week02homework.domain.UserRole;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.SignupUserDto;
import com.sparta.week02homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;



    public Long join(SignupUserDto userDto) {

        // 회원 ID 중복 확인
        String email = userDto.getEmail();
        Optional<Users> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }


        Users user = Users.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .roles(UserRole.USER)
                .build();

        return userRepository.save(user).getId();
    }

    public String login(Map<String, String> user) {
        Users member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }


}
