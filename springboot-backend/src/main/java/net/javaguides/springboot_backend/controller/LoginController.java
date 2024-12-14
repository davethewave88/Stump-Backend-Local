package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.User;
import net.javaguides.springboot_backend.domain.UserRepository;
import net.javaguides.springboot_backend.dto.LoginDTO;
import net.javaguides.springboot_backend.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public LoginDTO token(Authentication authentication) {
        String name = authentication.getName();
        System.out.println("login authentication "+name);
        User user = userRepository.findByEmail(name);
        String token = tokenService.generateToken(authentication);
        return new LoginDTO(token, user.getType(), user.getName(), user.getUser_id());
    }

}
