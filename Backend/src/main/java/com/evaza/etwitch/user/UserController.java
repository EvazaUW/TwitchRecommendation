package com.evaza.etwitch.user;

import com.evaza.etwitch.model.RegisterBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
//    @ResponseStatus(value = HttpStatus.OK)    // 这句话可以不要
    public void register(@RequestBody RegisterBody body) throws UserAlreadyExistException {
        userService.register(body.username(), body.password(), body.firstName(), body.lastName());
    }
}
