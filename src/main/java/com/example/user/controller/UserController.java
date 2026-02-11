package com.example.user.controller;

import com.example.schedule.dto.GetScheduleResponse;
import com.example.user.dto.*;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    // 유저 생성
//    @PostMapping("/users")
//    public ResponseEntity<CreateUserResponse> save(
//            @RequestBody CreateUserRequest request
//    ){
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
//    }

    // 유저 생성 -> 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request
    ){
        SignupResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ){
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser",sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    // 유저 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    // 유저 수정, Session 변경
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser,request));
    }

    // 유저 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ){
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
