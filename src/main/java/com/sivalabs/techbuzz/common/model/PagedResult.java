package com.sivalabs.techbuzz.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
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
}
