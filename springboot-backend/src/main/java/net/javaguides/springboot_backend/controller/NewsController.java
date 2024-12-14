package net.javaguides.springboot_backend.controller;

import net.javaguides.springboot_backend.domain.News;
import net.javaguides.springboot_backend.domain.NewsRepository;
import net.javaguides.springboot_backend.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @GetMapping("/sitenews")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN')")
    public List<NewsDTO> getNews() {

        List<News> news = newsRepository.getAllNews();
        List<NewsDTO> newsDTO_list = new ArrayList<>();
        for (News n: news) {
            newsDTO_list.add(new NewsDTO(n.getPost_id(), n.getCreate_time(), n.getTitle(), n.getDescription()));
        }
        return newsDTO_list;
    }


    @PostMapping("/sitenews")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public NewsDTO createNews(
            @RequestBody NewsDTO dto) {

        News n = new News();
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        n.setCreate_time(currentTime);
        n.setTitle(dto.title());
        n.setDescription(dto.description());
        newsRepository.save(n);

        return new NewsDTO(n.getPost_id(), n.getCreate_time(), n.getTitle(), n.getDescription());
    }

    @DeleteMapping("/sitenews/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void  deleteNews(@PathVariable("id") int id) {
        News n = newsRepository.findNewsById(id);
        newsRepository.delete(n);

    }

}
