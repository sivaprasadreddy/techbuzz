package com.sivalabs.techbuzz.posts.domain.entities;

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

    public Category(
            final Long id,
            final String name,
            final String slug,
            final String description,
            final String image,
            final Integer displayOrder,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.image = image;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setDisplayOrder(final Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
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
