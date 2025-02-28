package com.codestates.hobby.domain.member.controller;

import com.codestates.hobby.domain.member.dto.MemberDto;
import com.codestates.hobby.domain.member.entity.Member;
import com.codestates.hobby.domain.member.mapper.MemberMapper;
import com.codestates.hobby.domain.member.service.MemberService;

import com.codestates.hobby.global.support.resolver.CustomPageRequest;
import com.codestates.hobby.global.dto.MultiResponseDto;
import com.codestates.hobby.global.exception.BusinessLogicException;
import com.codestates.hobby.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;
    private final MemberMapper mapper;

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody MemberDto.Post post) {
        Member member = service.create(post);

        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patch(@PathVariable("member-id") long memberId,
                                @AuthenticationPrincipal Member loginMember,
                                @Valid @RequestBody MemberDto.Patch patch) {
        patch.setMemberId(memberId);
        Member member = service.edit(patch, loginMember.getId());

        return new ResponseEntity(member.getId(), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity delete(@PathVariable("member-id") long memberId,
                                 @AuthenticationPrincipal Member loginMember) {
        if(memberId != loginMember.getId()) throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);

        service.delete(memberId);

        return new ResponseEntity(memberId, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity get(@PathVariable("member-id") long memberId) {
        Member member = service.find(memberId);

        return new ResponseEntity(mapper.MemberToResponseDto(member), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll(CustomPageRequest pageRequest) {
        Page<Member> series = service.findAll(pageRequest.to());

        Page<MemberDto.Response> responses = series.map(mapper::MemberToResponseDto);

        return new ResponseEntity(new MultiResponseDto<>(responses), HttpStatus.OK);
    }
}
