package com.sama.portfolio.service;

import com.sama.portfolio.config.DebugConfig;
import com.sama.portfolio.model.Project;
import com.sama.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProjectService handles all business logic for project operations.
 * Separates business logic from HTTP request handling.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repo;
    private final DebugConfig debugConfig;

    // /**
    //  * Fetch all projects from the database.
    //  * 
    //  * @return List of all projects
    //  * @throws Exception if database operation fails
    //  */
    public List<Project> getAllProjects() {
        log.info("Service: Fetching all projects");

        List<Project> projects = repo.findAll();

        if (debugConfig.isDebugLevel()) {
            log.debug("Service: Retrieved {} projects from database", projects.size());
            if (debugConfig.isTraceLevel()) {
                projects.forEach(p -> log.trace("Service: Project details - id={}, title={}", p.getId(), p.getTitle()));
            }
        }

        return projects;
    }

    /**
     * Add a new project to the database.
     * Validates input and saves the project.
     * 
     * @param project the project to save
     * @return the saved project with generated ID
     * @throws IllegalArgumentException if project title is empty
     * @throws Exception                if database operation fails
     */
    public Project createProject(Project project) {
        // Validation logic
        validateProject(project);

        if (debugConfig.isDebugLevel()) {
            log.debug("Service: Project details - Description: {}", project.getDescription());
            log.debug("Service: Number of images: {}",
                    project.getImages() != null ? project.getImages().size() : 0);
        }

        // Save to database
        Project saved = repo.save(project);
        log.info("Service: Project saved with ID: {}", saved.getTitle());

        if (debugConfig.isDebugLevel()) {
            log.debug("Service: Saved project - Title: {}, Description: {}",
                    saved.getTitle(), saved.getDescription());
        }

        return saved;
    }

    /**
     * Validate project data before saving.
     * 
     * @param project the project to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        if (project.getTitle() == null || project.getTitle().isBlank()) {
            throw new IllegalArgumentException("Project title is required");
        }

        log.debug("Service: Project validation passed for title: {}", project.getTitle());
    }
}
