package com.sama.portfolio.controller;

import com.sama.portfolio.model.Project;
import com.sama.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProjectController handles HTTP requests for project endpoints.
 * Delegates business logic to ProjectService.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * GET /projects
     * Fetch all projects.
     * 
     * @return 200 OK with list of projects
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        log.info("Controller: GET /projects");
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    /**
     * POST /projects
     * Create a new project.
     * 
     * @param project the project data to save
     * @return 201 Created with saved project
     */
    @PostMapping("/projects")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        log.info("Controller: POST /projects");
        Project saved = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}