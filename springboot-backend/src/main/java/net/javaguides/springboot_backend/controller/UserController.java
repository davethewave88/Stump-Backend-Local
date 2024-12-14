package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.Post;
import net.javaguides.springboot_backend.domain.User;
import net.javaguides.springboot_backend.domain.UserRepository;
import net.javaguides.springboot_backend.dto.PostDTO;
import net.javaguides.springboot_backend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    /**
     list all users
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<UserDTO> findAllUsers() {

        List<User> users = userRepository.getAllUsers();
        List<UserDTO> userDTO_list = new ArrayList<>();
        for (User u: users) {
            userDTO_list.add(new UserDTO(u.getUser_id(), u.getName(), u.getEmail(), u.getType(), u.getBio()));
        }
        return userDTO_list;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public UserDTO getUser(@PathVariable("id") int id) {
        User u = userRepository.findById(id).orElse(null);
        UserDTO dto = new UserDTO(u.getUser_id(), u.getName(), u.getEmail(), u.getType(), u.getBio());
        return dto;
    }

    @PutMapping("/user")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {


        User u = userRepository.findById(userDTO.user_id()).orElse(null);
        u.setBio(userDTO.bio());
        userRepository.save(u);
        return new UserDTO(u.getUser_id(), u.getName(), u.getEmail(), u.getType(), u.getBio());
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User u = new User();
        u.setName(userDTO.name());
        u.setEmail(userDTO.email());

        // create password and encrypt it
        String password = userDTO.name()+"2024";
        String enc_password = encoder.encode(password);
        u.setPassword(enc_password);

        u.setType(userDTO.type());
        if (!userDTO.type().equals("USER") &&
                !userDTO.type().equals("ADMIN")) {
            // invalid type
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "invalid user type");
        }

        u.setBio(userDTO.bio());
        userRepository.save(u);
        return new UserDTO(u.getUser_id(), u.getName(), u.getEmail(), u.getType(), u.getBio());
    }


    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void  deleteUser(@PathVariable("id") int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!=null) {
            userRepository.delete(user);
        }

    }

    @PostMapping("/create")
    public UserDTO newUser(@RequestBody UserDTO userDTO) {
        User u = new User();
        u.setName(userDTO.name());
        u.setEmail(userDTO.email());
        u.setBio(userDTO.bio());

        // create password and encrypt it
        String password = userDTO.name()+"2024";
        String enc_password = encoder.encode(password);
        u.setPassword(enc_password);

        u.setType("USER");

        userRepository.save(u);
        return new UserDTO(u.getUser_id(), u.getName(), u.getEmail(), u.getType(), u.getBio());
    }

}