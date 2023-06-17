package com.sivalabs.techbuzz.posts.domain.models;

import com.sivalabs.techbuzz.users.domain.models.User;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;
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
            Long id,
            String title,
            String url,
            String content,
            Category category,
            User createdBy,
            Set<Vote> votes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
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

    public boolean canEditByUser(User loginUser) {
        return loginUser != null
                && (Objects.equals(this.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator());
    }

    public boolean isUpVotedByUser(User loginUser) {
        return this.isVotedByUser(loginUser, 1);
    }

    public boolean isDownVotedByUser(User loginUser) {
        return this.isVotedByUser(loginUser, -1);
    }

    private boolean isVotedByUser(User loginUser, int vote) {
        if (loginUser == null || this.getVotes() == null) {
            return false;
        }
        return this.getVotes().stream()
                .anyMatch(v -> Objects.equals(v.getUserId(), loginUser.getId()) && v.getValue() == vote);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
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
