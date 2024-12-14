package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.Post;
import net.javaguides.springboot_backend.domain.PostRepository;
import net.javaguides.springboot_backend.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<PostDTO> getPosts() {

        List<Post> posts = postRepository.getAllPosts();
        List<PostDTO> postDTO_list = new ArrayList<>();
        for (Post p: posts) {
            postDTO_list.add(new PostDTO(p.getPost_id(), p.getUsername(), p.getCreator_id(), p.getCreate_time(), p.getLink(), p.getTitle(), p.getDescription()));
        }
        return postDTO_list;
    }


    @PostMapping("/posts")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER')")
    public PostDTO createPost(
            @RequestBody PostDTO dto) {

        Post p = new Post();
        p.setCreator_id(dto.creator_id());
        p.setUsername(dto.username());
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        p.setCreate_time(currentTime);
        p.setLink(dto.link());
        p.setTitle(dto.title());
        p.setDescription(dto.description());
        postRepository.save(p);

        return new PostDTO(p.getPost_id(), p.getUsername(), p.getCreator_id(), p.getCreate_time(), p.getLink(), p.getTitle(), p.getDescription());
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void  updateUser(@PathVariable("id") int id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post!=null) {
            postRepository.delete(post);
        }

    }

    @GetMapping("/news")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<PostDTO> getLatestPosts() {

        List<Post> posts = postRepository.getAllPosts();
        List<PostDTO> postDTO_list = new ArrayList<>();
        for (Post p: posts) {
            postDTO_list.add(new PostDTO(p.getPost_id(), p.getUsername(), p.getCreator_id(), p.getCreate_time(), p.getLink(), p.getTitle(), p.getDescription()));
        }
        return postDTO_list;
    }

    @GetMapping("/news/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public PostDTO getPost(@PathVariable("id") int id) {
        Post p = postRepository.getPostById(id);
        PostDTO dto = new PostDTO(p.getPost_id(), p.getUsername(), p.getCreator_id(), p.getCreate_time(), p.getLink(), p.getTitle(), p.getDescription());
        return dto;
    }

    @GetMapping("/posts/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<PostDTO> getAllPostByUserId(@PathVariable("id") int id) {
        List<Post> posts = postRepository.getAllPostsById(id);
        List<PostDTO> postDTO_list = new ArrayList<>();
        for (Post p: posts) {
            postDTO_list.add(new PostDTO(p.getPost_id(), p.getUsername(), p.getCreator_id(), p.getCreate_time(), p.getLink(), p.getTitle(), p.getDescription()));
        }
        return postDTO_list;
    }
}
