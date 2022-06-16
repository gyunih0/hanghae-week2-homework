package com.sparta.week02homework.service;

import com.sparta.week02homework.config.security.jwt.JwtTokenProvider;
import com.sparta.week02homework.domain.UserRole;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.SignupUserDto;
import com.sparta.week02homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * Authentication(JWT Token) 으로 부터 User 정보 찾기
     */
    public Users findUserByAuthUser(Users userDetails) {
        return userRepository.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
    }
    public void loginCheck(Users userDetails) {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
    }


    /**
     * 회원 가입
     * @param userDto
     * @return
     */
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


    /**
     * 로그인
     * @param user
     * @return
     */
    public Map<String, String> login(Map<String, String> user) {
        Map<String, String> token = new HashMap<>();

        Users member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        token.put("Access-Token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        return token;
    }

}
