package com.sivalabs.techbuzz.posts.domain.models;

import java.time.LocalDateTime;

public class Vote {
    private Long id;
    private Long userId;
    private Long postId;
    private Integer value;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public Vote() {}

    public Vote(Long id, Long userId, Long postId, Integer value, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setValue(Integer value) {
        this.value = value;
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

    public Long getUserId() {
        return this.userId;
    }

    public Long getPostId() {
        return this.postId;
    }

    public Integer getValue() {
        return this.value;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
