package ar.edu.itba.paw.webapp.dtos.user;



import ar.edu.itba.paw.models.Timetable;

import java.io.Serializable;
import java.time.LocalTime;

public class TimetableDto implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;

    public TimetableDto() {
        // for MessageBodyWriter
    }

    public TimetableDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimetableDto fromTimetable(Timetable timetable) {
        if(timetable == null) return new TimetableDto();
        return new TimetableDto(timetable.getBegins(), timetable.getEnd());
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
