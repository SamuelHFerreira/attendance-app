package br.com.buildin.attendance.service.body;

import java.io.Serializable;

/**
 * Created by samuelferreira on 21/03/17.
 */
public class StartQueueBody implements Serializable {
    private static final long serialVersionUID = 253876767554726377L;

    private Long attendanceId;


    public StartQueueBody() {
    }

    public StartQueueBody(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }
}