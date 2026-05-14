package com.sama.portfolio.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ElementCollection
    private List<String> images;

    public Project() {}

    public Project(String title, String description, List<String> images) {
        this.title = title;
        this.description = description;
        this.images = images;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<String> getImages() { return images; }
}