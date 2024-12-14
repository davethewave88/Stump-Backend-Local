package net.javaguides.springboot_backend.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NewsRepository extends CrudRepository<News, Integer> {

    @Query(value = "select * from sitenews order by post_id desc limit 5", nativeQuery = true)
    List<News> getAllNews();

    @Query(value = "select * from sitenews where :id = post_id", nativeQuery = true)
    News findNewsById(int id);

}
