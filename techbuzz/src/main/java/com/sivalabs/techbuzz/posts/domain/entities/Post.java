package com.sivalabs.techbuzz.posts.domain.entities;

import com.sivalabs.techbuzz.users.domain.User;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

public class Post {
    private Long id;

    @NotEmpty
    private String title;

    private String url;

    private String content;

    private Category category;

    private User createdBy;

    private Set<Vote> votes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Post() {}

    public Post(
            final Long id,
            final String title,
            final String url,
            final String content,
            final Category category,
            final User createdBy,
            final Set<Vote> votes,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.content = content;
        this.category = category;
        this.createdBy = createdBy;
        this.votes = votes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void setCreatedBy(final User createdBy) {
        this.createdBy = createdBy;
    }

    public void setVotes(final Set<Vote> votes) {
        this.votes = votes;
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

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getContent() {
        return this.content;
    }

    public Category getCategory() {
        return this.category;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public Set<Vote> getVotes() {
        return this.votes;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
