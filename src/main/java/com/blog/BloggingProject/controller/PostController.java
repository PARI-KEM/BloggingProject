package com.blog.BloggingProject.controller;

import com.blog.BloggingProject.model.Post;
import com.blog.BloggingProject.model.User;
import com.blog.BloggingProject.repository.PostRepo;
import com.blog.BloggingProject.repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostController(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/choose-space";
    }

    @GetMapping("/choose-space")
    public String chooseSpace() {
        return "choose-space";
    }

    // Show only current logged-in user's private posts
    @GetMapping("/user_space")
    public String userSpace(Model model, Authentication auth) {
        String username = auth.getName(); // get logged-in username
        User user=userRepo.findByUsername(username);
        List<Post> userPosts = postRepo.findByUser(user);
        model.addAttribute("posts", userPosts);
        return "user_space";
    }

    // Save post for current user
    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post, Authentication auth) {
        String username = auth.getName();
        User user=userRepo.findByUsername(username);
        post.setUser(user);
        post.setAuthor(username);
        postRepo.save(post);

        if ("Server".equalsIgnoreCase(post.getVisibility())) {
            return "redirect:/general-space";
        } else {
            return "redirect:/user_space";
        }
    }

    @GetMapping({"/view_post/{id}", "/user_space/view_post/{id}"})
    public String viewPost(@PathVariable("id") int id, Model model) {
        Post post = postRepo.findById(id).orElse(null);
        if (post == null) {
            return "error";
        }
        model.addAttribute("post", post);
        return "view_post";
    }


    // General space: all public posts
    @GetMapping("/general-space")
    public String generalSpace(Model model) {
        List<Post> publicPosts = postRepo.findByVisibility("Server");
        model.addAttribute("posts", publicPosts);
        return "general-space";
    }

    @GetMapping("/new_post")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }
}
