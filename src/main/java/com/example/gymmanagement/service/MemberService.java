package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.MemberRequest;
import com.example.gymmanagement.dto.MemberResponse;
import com.example.gymmanagement.entity.Member;
import com.example.gymmanagement.exception.DuplicateResourceException;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.repository.MemberRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse create(MemberRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("A member with this email already exists");
        }
        Member member = new Member();
        updateFields(member, request);
        return toResponse(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return toResponse(getMember(id));
    }

    public MemberResponse update(Long id, MemberRequest request) {
        Member member = getMember(id);
        if (memberRepository.existsByEmailAndIdNot(request.email().trim().toLowerCase(Locale.ROOT), id)) {
            throw new DuplicateResourceException("A member with this email already exists");
        }
        updateFields(member, request);
        return toResponse(memberRepository.save(member));
    }

    public void delete(Long id) {
        memberRepository.delete(getMember(id));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    private void updateFields(Member member, MemberRequest request) {
        member.setName(request.name().trim());
        member.setEmail(request.email().trim().toLowerCase(Locale.ROOT));
        member.setPhone(request.phone());
        member.setAge(request.age());
        member.setGender(request.gender());
        member.setJoinDate(request.joinDate());
    }

    private MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getPhone(),
                member.getAge(), member.getGender(), member.getJoinDate());
    }
}
