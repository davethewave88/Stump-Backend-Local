package net.javaguides.springboot_backend.dto;

import java.sql.Timestamp;

public record NewsDTO (
        int post_id,
        Timestamp create_time,
        String title,
        String description) { }