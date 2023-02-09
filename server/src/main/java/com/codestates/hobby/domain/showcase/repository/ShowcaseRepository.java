package com.codestates.hobby.domain.showcase.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.hobby.domain.category.entity.Category;
import com.codestates.hobby.domain.showcase.entity.Showcase;

public interface ShowcaseRepository extends JpaRepository<Showcase, Long> {
	@Query(value = "select max(id) from showcase", nativeQuery = true)
	long findLastShowcaseId();

	@Query("select s from Showcase s join fetch s.member m join fetch s.fileInfos where s.id = ?1")
	Optional<Showcase> findByIdUsingFetch(long showcaseId);

	@EntityGraph(attributePaths = "member")
	Slice<Showcase> findAllByMemberIdAndIdLessThan(long memberId, long showcaseId, Pageable pageable);

	@EntityGraph(attributePaths = "member")
	Slice<Showcase> findAllByIdLessThan(long showcaseId, Pageable pageable);

	@EntityGraph(attributePaths = "member")
	Slice<Showcase> findAllByCategoryAndIdLessThan(Category category, long showcaseId, Pageable pageable);

	@EntityGraph(attributePaths = "member")
	Slice<Showcase> findAllByContentContainsAndIdLessThan(String query, long showcaseId, Pageable pageable);
}
