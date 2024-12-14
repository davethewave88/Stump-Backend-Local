package net.javaguides.springboot_backend.dto;

public record LoginDTO(String jwt, String role, String name, int id) {
}