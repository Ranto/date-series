package ara.labo.agenda.serie.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Recurrence {

    private static final int DEFAULT_DURATION_DAYS = 30;

    private final Frequency frequency;
    private final List<DayOfWeek> days;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer occurrence;
    private final Integer interval;

    public Recurrence(Frequency frequency, List<DayOfWeek> days, LocalDate startDate, LocalDate endDate,
                      Integer occurrences, Integer interval) {
        this.frequency = frequency;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurrence = occurrences;
        this.interval = interval;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        if (this.endDate != null) {
            return this.endDate;
        }
        return this.startDate.plusDays(DEFAULT_DURATION_DAYS);
    }

    public Integer getOccurrence() {
        return occurrence;
    }

    public Integer getInterval() {
        return interval;
    }
}
