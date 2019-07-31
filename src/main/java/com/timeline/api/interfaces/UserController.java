package com.timeline.api.interfaces;

import com.timeline.api.application.service.UserService;
import com.timeline.api.interfaces.dto.request.SignUpRequest;
import com.timeline.api.interfaces.dto.response.SignUpResponse;
import com.timeline.api.interfaces.dto.response.UserListResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/user/search")
    public List<UserListResponse> getUserList(@RequestParam("name") String name) {
        return userService.getUserList(name);
    }

    @PostMapping("/user/signup")
    public SignUpResponse signUpUser(@RequestBody SignUpRequest signUpRequest) {
        return new SignUpResponse(userService.signUpUser(signUpRequest.getUserId(),
                                                         signUpRequest.getUserName(),
                                                         signUpRequest.getPassword()));
    }
}
