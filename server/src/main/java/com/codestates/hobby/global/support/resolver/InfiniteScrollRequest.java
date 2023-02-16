package com.codestates.hobby.global.support.resolver;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class InfiniteScrollRequest {
	private final long offset;
	private final int size;

	public InfiniteScrollRequest() {
		this(-1, 9);
	}

	public InfiniteScrollRequest(long offset, int size) {
		this.offset = offset;
		this.size = size;
	}

	public PageRequest getPageable() {
		return PageRequest.ofSize(size).withSort(Sort.by("id").descending());
	}

	public long getOffset() {
		return offset > 0 ? offset : Long.MAX_VALUE;
	}
}
