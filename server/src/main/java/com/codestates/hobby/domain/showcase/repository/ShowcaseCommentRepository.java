package com.codestates.hobby.domain.showcase.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.hobby.domain.showcase.dto.CommentProjection;
import com.codestates.hobby.domain.showcase.entity.ShowcaseComment;

public interface ShowcaseCommentRepository extends JpaRepository<ShowcaseComment, Long> {
	Optional<ShowcaseComment> findByIdAndMemberIdAndShowcaseId(long commentId, long memberId, long showcaseId);

	@EntityGraph(attributePaths = "member")
	Page<ShowcaseComment> findAllByMemberIdOrderByIdDesc(long memberId, Pageable pageable);

	@EntityGraph(attributePaths = "member")
	Page<ShowcaseComment> findAllByShowcaseIdOrderByIdDesc(long showcaseId, Pageable pageable);

	@EntityGraph(attributePaths = "member")
	List<ShowcaseComment> findAllByIdIn(Set<Long> ids);

	@Query("select max(c.id) as id, count(c.id) as count from ShowcaseComment c where c.showcase.id in :showcaseId group by c.showcase.id")
	List<CommentProjection> findAllLastIdByShowcaseId(Set<Long> showcaseId);
}
