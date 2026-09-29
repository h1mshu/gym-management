package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.AttendanceRequest;
import com.example.gymmanagement.dto.AttendanceResponse;
import com.example.gymmanagement.entity.Attendance;
import com.example.gymmanagement.entity.Member;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.repository.AttendanceRepository;
import com.example.gymmanagement.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
    }

    public AttendanceResponse create(AttendanceRequest request) {
        Attendance attendance = new Attendance();
        apply(attendance, request);
        return response(attendanceRepository.save(attendance));
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponse> findAll() {
        return attendanceRepository.findAll().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public AttendanceResponse findById(Long id) {
        return response(getAttendance(id));
    }

    public AttendanceResponse update(Long id, AttendanceRequest request) {
        Attendance attendance = getAttendance(id);
        apply(attendance, request);
        return response(attendanceRepository.save(attendance));
    }

    public void delete(Long id) {
        attendanceRepository.delete(getAttendance(id));
    }

    private Attendance getAttendance(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", id));
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
    }

    private void apply(Attendance attendance, AttendanceRequest request) {
        attendance.setMember(getMember(request.memberId()));
        attendance.setAttendanceDate(request.attendanceDate());
        attendance.setCheckInTime(request.checkInTime());
    }

    private AttendanceResponse response(Attendance attendance) {
        return new AttendanceResponse(attendance.getId(), attendance.getMember().getId(),
                attendance.getMember().getName(), attendance.getAttendanceDate(), attendance.getCheckInTime());
    }
}
