package com.sivalabs.techbuzz.posts.domain.models;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class Category {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    @NotEmpty
    private String description;

    private String image;
    private Integer displayOrder;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    public Category() {}

    public Category(Long id) {
        this.id = id;
    }

    public Category(
            Long id,
            String name,
            String slug,
            String description,
            String image,
            Integer displayOrder,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.image = image;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSlug() {
        return this.slug;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
