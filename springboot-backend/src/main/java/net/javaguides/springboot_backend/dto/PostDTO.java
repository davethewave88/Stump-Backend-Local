package net.javaguides.springboot_backend.dto;

import java.sql.Timestamp;

public record PostDTO (
        int post_id,
        String username,
        int creator_id,
        Timestamp create_time,
        String link,
        String title,
        String description) { }