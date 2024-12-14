package net.javaguides.springboot_backend.dto;

public record UserDTO (
        int user_id,
        String name,
        String email,
        String type,
        String bio) { }