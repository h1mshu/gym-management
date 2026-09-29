package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.PaymentRequest;
import com.example.gymmanagement.dto.PaymentResponse;
import com.example.gymmanagement.entity.Member;
import com.example.gymmanagement.entity.Payment;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.repository.MemberRepository;
import com.example.gymmanagement.repository.PaymentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public PaymentService(PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    public PaymentResponse create(PaymentRequest request) {
        Payment payment = new Payment();
        apply(payment, request);
        return response(paymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        return response(getPayment(id));
    }

    public PaymentResponse update(Long id, PaymentRequest request) {
        Payment payment = getPayment(id);
        apply(payment, request);
        return response(paymentRepository.save(payment));
    }

    public void delete(Long id) {
        paymentRepository.delete(getPayment(id));
    }

    private Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    private void apply(Payment payment, PaymentRequest request) {
        payment.setMember(getMember(request.memberId()));
        payment.setAmount(request.amount());
        payment.setPaymentDate(request.paymentDate());
        payment.setStatus(request.status());
    }

    private PaymentResponse response(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getMember().getId(),
                payment.getMember().getName(), payment.getAmount(), payment.getPaymentDate(), payment.getStatus());
    }
}
