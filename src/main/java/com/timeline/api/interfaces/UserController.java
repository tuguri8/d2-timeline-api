package com.timeline.api.interfaces;

import com.timeline.api.application.service.UserService;
import com.timeline.api.interfaces.dto.request.SignUpRequest;
import com.timeline.api.interfaces.dto.response.SignUpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping("/signup")
    public SignUpResponse signUpUser(@RequestBody SignUpRequest signUpRequest) {
        return new SignUpResponse(userService.signUpUser(signUpRequest.getUserId(),
                                                         signUpRequest.getUserName(),
                                                         signUpRequest.getPassword()));
    }
}
