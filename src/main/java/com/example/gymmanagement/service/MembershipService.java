package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.MembershipRequest;
import com.example.gymmanagement.dto.MembershipResponse;
import com.example.gymmanagement.entity.Member;
import com.example.gymmanagement.entity.Membership;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.repository.MemberRepository;
import com.example.gymmanagement.repository.MembershipRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;

    public MembershipService(MembershipRepository membershipRepository, MemberRepository memberRepository) {
        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
    }

    public MembershipResponse create(MembershipRequest request) {
        Membership membership = new Membership();
        apply(membership, request);
        return response(membershipRepository.save(membership));
    }

    @Transactional(readOnly = true)
    public List<MembershipResponse> findAll() {
        return membershipRepository.findAll().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public MembershipResponse findById(Long id) {
        return response(getMembership(id));
    }

    public MembershipResponse update(Long id, MembershipRequest request) {
        Membership membership = getMembership(id);
        apply(membership, request);
        return response(membershipRepository.save(membership));
    }

    public void delete(Long id) {
        membershipRepository.delete(getMembership(id));
    }

    private Membership getMembership(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership", id));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    private void apply(Membership membership, MembershipRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("Membership endDate must be on or after startDate");
        }
        membership.setMember(getMember(request.memberId()));
        membership.setPlan(request.plan().trim());
        membership.setStartDate(request.startDate());
        membership.setEndDate(request.endDate());
        membership.setStatus(request.status());
    }

    private MembershipResponse response(Membership membership) {
        return new MembershipResponse(membership.getId(), membership.getMember().getId(),
                membership.getMember().getName(), membership.getPlan(), membership.getStartDate(),
                membership.getEndDate(), membership.getStatus());
    }
}
