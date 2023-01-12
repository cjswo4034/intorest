package com.codestates.hobby.domain.showcase.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.hobby.domain.showcase.dto.ShowcaseCommentDto;
import com.codestates.hobby.domain.showcase.entity.ShowcaseComment;

@Service
public class ShowcaseCommentService {
	@Transactional
	public ShowcaseComment comment(ShowcaseCommentDto.Post post) {
		return null;
	}

	@Transactional
	public ShowcaseComment update(ShowcaseCommentDto.Patch patch) {
		return null;
	}

	@Transactional
	public void delete(long memberId, long showcaseId, long commentId) {

	}

	@Transactional(readOnly = true)
	public int getCount(long showcaseId) {
		return 0;
	}

	@Transactional(readOnly = true)
	public Page<ShowcaseComment> findAll(long showcaseId, PageRequest pageRequest) {
		return null;
	}

	@Transactional(readOnly = true)
	public Page<ShowcaseComment> findAllByMember(long memberId, PageRequest pageRequest) {
		return null;
	}
}
