package com.sparta.spring_jwt.service;

import com.sparta.spring_jwt.models.repository.UserRepository;
import com.sparta.spring_jwt.models.user.User;
import com.sparta.spring_jwt.models.userDto.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String confirm_password = requestDto.getConfirm_password();
        String usernamePattern = "^[a-zA-z0-9]{3,}$";
        if(!Pattern.matches(usernamePattern,username) || password.length()<4 || !password.equals(confirm_password) || password.contains(username)){
            throw  new IllegalArgumentException("아이디와 비밀번호를 확인해주세요");
        }

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw  new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 패스워드 암호화
        String encode_password = passwordEncoder.encode(requestDto.getPassword());


        User user = new User(username, encode_password);
        userRepository.save(user);

        return user;
    }




}