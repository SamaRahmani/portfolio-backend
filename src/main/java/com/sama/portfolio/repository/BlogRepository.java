package com.sama.portfolio.repository;

import com.sama.portfolio.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}