package com.blog.BloggingProject.repository;

import com.blog.BloggingProject.model.Post;
import com.blog.BloggingProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer>{
     List<Post> findByVisibility(String visibility);
     List<Post> findByUser(User user);
}
