package com.sama.portfolio.controller;

import com.sama.portfolio.model.Project;
import com.sama.portfolio.repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectRepository repo;

    public ProjectController(ProjectRepository repo) {
        this.repo = repo;
    }

    // 🔽 GET ALL PROJECTS
    @GetMapping("/projects")
    public List<Project> getProjects() {
        return repo.findAll();
    }

    // 🔽 ADD PROJECT
    @PostMapping("/projects")
    public Project addProject(@RequestBody Project project) {
        return repo.save(project);
    }
}