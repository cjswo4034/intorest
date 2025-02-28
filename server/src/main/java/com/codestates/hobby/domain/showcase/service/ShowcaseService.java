package com.codestates.hobby.domain.showcase.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.hobby.domain.category.entity.Category;
import com.codestates.hobby.domain.category.service.CategoryService;
import com.codestates.hobby.domain.fileInfo.dto.BasePath;
import com.codestates.hobby.domain.fileInfo.entity.FileInfo;
import com.codestates.hobby.domain.fileInfo.service.FileInfoService;
import com.codestates.hobby.domain.member.entity.Member;
import com.codestates.hobby.domain.member.service.MemberService;
import com.codestates.hobby.domain.showcase.dto.CommentProjection;
import com.codestates.hobby.domain.showcase.dto.ShowcaseDto;
import com.codestates.hobby.domain.showcase.entity.Showcase;
import com.codestates.hobby.domain.showcase.repository.ShowcaseCommentRepository;
import com.codestates.hobby.domain.showcase.repository.ShowcaseRepository;
import com.codestates.hobby.global.support.resolver.InfiniteScrollRequest;
import com.codestates.hobby.global.exception.BusinessLogicException;
import com.codestates.hobby.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowcaseService {
	private final ShowcaseCommentRepository commentRepository;
	private final ShowcaseRepository showcaseRepository;
	private final CategoryService categoryService;
	private final FileInfoService fileInfoService;
	private final MemberService memberService;

	@Transactional
	public Showcase post(ShowcaseDto.Post post) {
		List<FileInfo> fileInfos = fileInfoService.generateSignedURLs(post.getFileInfos(), BasePath.SHOWCASES);
		Category category = categoryService.findHobbyByName(post.getCategory());
		Member member = memberService.findMemberById(post.getMemberId());

		return showcaseRepository.save(new Showcase(post.getContent(), member, category, fileInfos));
	}

	@Transactional
	public Showcase update(ShowcaseDto.Patch patch) {
		List<FileInfo> fileInfos = fileInfoService.generateSignedURLs(patch.getFileInfos(), BasePath.SHOWCASES);
		Category category = categoryService.findHobbyByName(patch.getCategory());

		Showcase showcase = findVerifiedShowcase(patch.getMemberId(), patch.getShowcaseId());
		showcase.update(category, patch.getContent(), fileInfos);

		return showcase;
	}

	@Transactional
	public void delete(long memberId, long showcaseId) {
		showcaseRepository.delete(findVerifiedShowcase(memberId, showcaseId));
	}

	@Transactional(readOnly = true)
	public Showcase findById(long showcaseId) {
		return showcaseRepository.findById(showcaseId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_SHOWCASE));
	}

	@Transactional(readOnly = true)
	public Showcase findByIdUsingFetch(long showcaseId) {
		return showcaseRepository.findByIdUsingFetch(showcaseId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_SHOWCASE));
	}

	@Transactional(readOnly = true)
	public Slice<Showcase> findAll(InfiniteScrollRequest isRequest) {
		return setThumbnailAndComment(showcaseRepository.findAllByIdLessThan(isRequest.getOffset(), isRequest.getPageable()));
	}

	@Transactional(readOnly = true)
	public Slice<Showcase> findAllByMember(long memberId, InfiniteScrollRequest isRequest) {
		return setThumbnailAndComment(
			showcaseRepository.findAllByMemberIdAndIdLessThan(memberId, isRequest.getOffset(), isRequest.getPageable())
		);
	}

	@Transactional(readOnly = true)
	public Slice<Showcase> findAllByCategory(String categoryName, InfiniteScrollRequest isRequest) {
		Category category = categoryService.findHobbyByName(categoryName);

		return setThumbnailAndComment(
			showcaseRepository.findAllByCategoryAndIdLessThan(category, isRequest.getOffset(), isRequest.getPageable())
		);
	}

	@Transactional(readOnly = true)
	public Slice<Showcase> search(String query, InfiniteScrollRequest isRequest) {
		return setThumbnailAndComment(
			showcaseRepository.findAllByContentContainsAndIdLessThan(query, isRequest.getOffset(), isRequest.getPageable())
		);
	}

	private Slice<Showcase> setThumbnailAndComment(Slice<Showcase> showcases) {
		Map<Long, Showcase> map = showcases.stream()
			.collect(Collectors.toMap(Showcase::getId, Function.identity()));

		if (map.isEmpty()) return showcases;

		setThumbnail(map);
		setLastComment(map);

		return showcases;
	}

	private void setThumbnail(Map<Long, Showcase> showcases) {
		fileInfoService.findThumbnail(BasePath.SHOWCASES, showcases.keySet())
			.forEach(fileInfo -> {
				Showcase showcase = showcases.get(fileInfo.getEntityId());
				showcase.setThumbnail(fileInfo.getFileURL());
			});
	}

	private void setLastComment(Map<Long, Showcase> showcases) {
		Map<Long, CommentProjection> projs = commentRepository.findAllLastIdByShowcaseId(showcases.keySet()).stream()
			.collect(Collectors.toMap(CommentProjection::getId, Function.identity()));

		commentRepository.findAllByIdIn(projs.keySet())
			.forEach(comment -> {
				Showcase showcase = showcases.get(comment.getShowcase().getId());
				showcase.setCommentCount(projs.get(comment.getId()).getCount());
				showcase.setComments(List.of(comment));
			});
	}

	private Showcase findVerifiedShowcase(long memberId, long showcaseId) {
		Showcase showcase = showcaseRepository.findById(showcaseId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_SHOWCASE));

		if (!showcase.isWrittenBy(memberId))
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_TO_EDIT);

		return showcase;
	}
}
