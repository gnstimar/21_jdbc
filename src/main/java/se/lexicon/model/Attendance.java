package se.lexicon.model;

import java.time.LocalDateTime;

public class Attendance {
    private int id;
    private Student student;
    private LocalDateTime attendanceDate;
    private AttendanceStatus attendanceStatus;

    public Attendance(int id, Student student, LocalDateTime attendanceDate, AttendanceStatus attendanceStatus) {
        this.id = id;
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance(Student student, LocalDateTime attendanceDate, AttendanceStatus attendanceStatus) {
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance(Student student, AttendanceStatus attendanceStatus) {
        this.student = student;
        this.attendanceStatus = attendanceStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", attendanceDate=" + attendanceDate +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
