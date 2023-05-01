package com.sivalabs.techbuzz.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.domain.Page;

public class PagedResult<T> {
    private List<T> data;
    private long totalElements;
    private int pageNumber;
    private int totalPages;

    @JsonProperty("isFirst")
    private boolean isFirst;

    @JsonProperty("isLast")
    private boolean isLast;

    @JsonProperty("hasNext")
    private boolean hasNext;

    @JsonProperty("hasPrevious")
    private boolean hasPrevious;

    public PagedResult(Page<T> page) {
        this.setData(page.getContent());
        this.setTotalElements(page.getTotalElements());
        this.setPageNumber(page.getNumber() + 1); // 1 - based page numbering
        this.setTotalPages(page.getTotalPages());
        this.setFirst(page.isFirst());
        this.setLast(page.isLast());
        this.setHasNext(page.hasNext());
        this.setHasPrevious(page.hasPrevious());
    }

    public void setData(final List<T> data) {
        this.data = data;
    }

    public void setTotalElements(final long totalElements) {
        this.totalElements = totalElements;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("isFirst")
    public void setFirst(final boolean isFirst) {
        this.isFirst = isFirst;
    }

    @JsonProperty("isLast")
    public void setLast(final boolean isLast) {
        this.isLast = isLast;
    }

    @JsonProperty("hasNext")
    public void setHasNext(final boolean hasNext) {
        this.hasNext = hasNext;
    }

    @JsonProperty("hasPrevious")
    public void setHasPrevious(final boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public List<T> getData() {
        return this.data;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public boolean isHasPrevious() {
        return this.hasPrevious;
    }
}
