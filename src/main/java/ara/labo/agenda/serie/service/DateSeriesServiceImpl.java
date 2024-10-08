package ara.labo.agenda.serie.service;

import ara.labo.agenda.serie.model.Recurrence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ara.labo.agenda.serie.model.Frequency.WEEKLY;

public class DateSeriesServiceImpl implements DateSeriesService {

    public DateSeriesServiceImpl() {
    }

    @Override
    public List<LocalDate> createDateListFromRecurrence(Recurrence recurrence) {
        LocalDate startDate = recurrence.getStartDate();
        List<LocalDate> dateList = new ArrayList<>();

        LocalDate nextDate = startDate;
        int occurrenceCount = 0;

        do {
            dateList.add(nextDate);
            switch (recurrence.getFrequency()) {
                case DAILY:
                    nextDate = nextDate.plusDays(recurrence.getInterval());
                    break;
                case WEEKLY:
                    nextDate = getNextDateWithWeeklyFrequency(recurrence, nextDate);
                    break;
                case MONTHLY:
                    nextDate = nextDate.plusMonths(recurrence.getInterval());
                    break;
            }

            occurrenceCount++;
        } while (!isListFinished(recurrence, occurrenceCount, nextDate));

        return dateList;
    }


    boolean isOccurrenceReached(Recurrence recurrence, int currentOccurrence) {
        if (recurrence.getOccurrence() == null) {
            return false;
        }
        return currentOccurrence >= recurrence.getOccurrence();
    }

    boolean isNextDateOutOfRange(Recurrence recurrence, LocalDate dateToCheck) {
        if (dateToCheck == null) {
            return false;
        }
        return dateToCheck.isAfter(recurrence.getEndDate());
    }

    boolean isListFinished(Recurrence recurrence, int currentOccurrence, LocalDate lastAddedDate) {
        return isOccurrenceReached(recurrence, currentOccurrence) || isNextDateOutOfRange(recurrence, lastAddedDate);
    }

    LocalDate getNextDateWithWeeklyFrequency(Recurrence recurrence, LocalDate lastDate) {
        if (!WEEKLY.equals(recurrence.getFrequency())) {
            throw new IllegalArgumentException("Frequency must be WEEKLY");
        }

        if (recurrence.getDays().isEmpty()) {
            return lastDate.plusDays(recurrence.getInterval() * 7L);
        }

        DayOfWeek lastSelectedDayOfWeek = lastDate.getDayOfWeek();
        DayOfWeek nextDayFromDaysList = getNextDayFromDaysList(recurrence.getDays(), lastSelectedDayOfWeek);

        return lastDate.plusDays(getDaysIntervalBetweenTwoDaysOfWeek(lastSelectedDayOfWeek, nextDayFromDaysList, recurrence.getInterval()));
    }

    DayOfWeek getNextDayFromDaysList(List<DayOfWeek> days, DayOfWeek lastSelectedDay) {
        Collections.sort(days);
        for (DayOfWeek day : days) {
            if (lastSelectedDay.compareTo(day) < 0) {
                return day;
            }
        }
        return days.get(0);
    }

    int getDaysIntervalBetweenTwoDaysOfWeek(DayOfWeek lastDayOfWeek, DayOfWeek nextDayOfWeek, int recurrenceInterval) {
        int interval = nextDayOfWeek.getValue() - lastDayOfWeek.getValue();
        if (interval <= 0) {
            return nextDayOfWeek.getValue() + (7 * recurrenceInterval) - lastDayOfWeek.getValue();
        }
        return interval;
    }


}
