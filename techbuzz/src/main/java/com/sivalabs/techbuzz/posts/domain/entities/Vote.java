package com.sivalabs.techbuzz.posts.domain.entities;

import java.time.LocalDateTime;

public class Vote {
    private Long id;

    private Long userId;

    private Long postId;

    private Integer value;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    public Vote() {}

    public Vote(
            final Long id,
            final Long userId,
            final Long postId,
            final Integer value,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public void setValue(final Integer value) {
        this.value = value;
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
