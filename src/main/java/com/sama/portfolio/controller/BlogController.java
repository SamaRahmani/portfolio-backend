package com.sama.portfolio.controller;

import com.sama.portfolio.model.Blog;
import com.sama.portfolio.repository.BlogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*") // allow frontend
public class BlogController {

    private final BlogRepository blogRepository;

    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // 🔥 GET all blogs
    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    // 🔥 POST blog (for testing / admin later)
    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        return blogRepository.save(blog);
    }
}