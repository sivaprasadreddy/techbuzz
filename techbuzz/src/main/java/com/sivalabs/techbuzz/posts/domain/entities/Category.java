package com.sivalabs.techbuzz.posts.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
    @SequenceGenerator(name = "category_id_generator", sequenceName = "category_id_seq", allocationSize = 5)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String slug;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String description;

    private String image;
    private Integer displayOrder;

    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Column(insertable = false)
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

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
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
