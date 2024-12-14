package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.Comment;
import net.javaguides.springboot_backend.domain.CommentRepository;
import net.javaguides.springboot_backend.domain.User;
import net.javaguides.springboot_backend.dto.CommentDTO;
import net.javaguides.springboot_backend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/news/{post_id}/comments")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<CommentDTO> getPostComments(@PathVariable("post_id") int post_id) {

        List<Comment> comments = commentRepository.getPostComments(post_id);
        List<CommentDTO> commentDTO_list = new ArrayList<>();
        for (Comment c: comments) {
            commentDTO_list.add(new CommentDTO(c.getComment_id(), c.getCreator_id(), c.getPost_id(), c.getName(), c.getCreate_time(), c.getText(), c.getUpvote(), c.getDownvote()));
        }
        return commentDTO_list;
    }


    @PostMapping("/news/{id}/comments")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public CommentDTO createComment(
            @RequestBody CommentDTO dto) {

        Comment c = new Comment();
        c.setComment_id(dto.comment_id());
        c.setCreator_id(dto.creator_id());
        c.setPost_id(dto.post_id());
        c.setName(dto.name());
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        c.setCreate_time(currentTime);
        c.setText(dto.text());
        c.setUpvote(0);
        c.setDownvote(0);
        commentRepository.save(c);

        return new CommentDTO(c.getComment_id(), c.getCreator_id(), c.getPost_id(), c.getName(), c.getCreate_time(), c.getText(), c.getUpvote(), c.getDownvote());
    }

    @PutMapping("/comments/{id}/like")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public CommentDTO upvote(@RequestBody CommentDTO commentDTO) {


        Comment c = commentRepository.findById(commentDTO.comment_id()).orElse(null);
        int vote = c.getUpvote();
        vote++;
        c.setUpvote(vote);
        commentRepository.save(c);
        return new CommentDTO(c.getComment_id(), c.getCreator_id(), c.getPost_id(), c.getName(), c.getCreate_time(), c.getText(), c.getUpvote(), c.getDownvote());
    }

    @PutMapping("/comments/{id}/dislike")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public CommentDTO downvote(@RequestBody CommentDTO commentDTO) {


        Comment c = commentRepository.findById(commentDTO.comment_id()).orElse(null);
        int vote = c.getDownvote();
        vote++;
        c.setDownvote(vote);
        commentRepository.save(c);
        return new CommentDTO(c.getComment_id(), c.getCreator_id(), c.getPost_id(), c.getName(), c.getCreate_time(), c.getText(), c.getUpvote(), c.getDownvote());
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void  deleteUserComment(@PathVariable("id") int id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment!=null) {
            commentRepository.delete(comment);
        }

    }

    @GetMapping("/comments")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<CommentDTO> getComments() {

        List<Comment> comments = commentRepository.getAllComments();
        List<CommentDTO> commentDTO_list = new ArrayList<>();
        for (Comment c: comments) {
            commentDTO_list.add(new CommentDTO(c.getComment_id(), c.getCreator_id(), c.getPost_id(), c.getName(), c.getCreate_time(), c.getText(), c.getUpvote(), c.getDownvote()));
        }
        return commentDTO_list;
    }

}
