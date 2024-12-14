package net.javaguides.springboot_backend.dto;

import java.sql.Timestamp;

public record CommentDTO (
        int comment_id,
        int creator_id,
        int post_id,
        String name,
        Timestamp create_time,
        String text,
        int upvote,
        int downvote
        ) { }