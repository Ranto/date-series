package ara.labo.agenda.serie.service;

import ara.labo.agenda.serie.model.Recurrence;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static ara.labo.agenda.serie.model.Frequency.DAILY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateSerieServiceImplTest {

    private static final Integer OCCURRENCE_CASE1 = 5;
    private static final LocalDate START_DATE = LocalDate.of(2024, 5, 5);
    private static final LocalDate END_DATE = LocalDate.of(2024, 6, 5);

    private static final Recurrence RECURRENCE_WITH_NULL_OCCURRENCE = new Recurrence(DAILY, new ArrayList<>(), START_DATE, null, null, 1);
    private static final Recurrence RECURRENCE_CASE_1 = new Recurrence(DAILY, new ArrayList<>(), START_DATE, END_DATE, OCCURRENCE_CASE1, 1);

    private DateSerieServiceImpl dateSerieServiceImpl;

    @Before
    public void setUp() {
        dateSerieServiceImpl = new DateSerieServiceImpl();
    }

    @Test
    public void shouldReturnFalseWhenOccurrenceIsNull() {
        boolean occurrenceReached = dateSerieServiceImpl.isOccurrenceReached(RECURRENCE_WITH_NULL_OCCURRENCE, 0);
        assertFalse(occurrenceReached);
    }

    @Test
    public void shouldReturnTrueWhenOccurrenceIsReached() {
        boolean occurrenceReached = dateSerieServiceImpl.isOccurrenceReached(RECURRENCE_CASE_1, OCCURRENCE_CASE1);
        assertTrue(occurrenceReached);
    }

    @Test
    public void shouldReturnFalseWhenOccurrenceIsNotReached() {
        boolean occurrenceReached = dateSerieServiceImpl.isOccurrenceReached(RECURRENCE_CASE_1, OCCURRENCE_CASE1 - 1);
        assertFalse(occurrenceReached);
    }

    @Test
    public void shouldReturnTrueWhenDateIsOutOfRange() {
        LocalDate dateToCheck = END_DATE.plusDays(1);

        boolean isDateOutOfRange = dateSerieServiceImpl.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertTrue(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateIsEqualToEndDate() {
        LocalDate dateToCheck = END_DATE.plusDays(0);

        boolean isDateOutOfRange = dateSerieServiceImpl.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertFalse(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateIsNotOutOfRange() {
        LocalDate dateToCheck = END_DATE.minusDays(1);

        boolean isDateOutOfRange = dateSerieServiceImpl.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertFalse(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateToCheckIsNull() {
        LocalDate dateToCheck = null;

        boolean isDateOutOfRange = dateSerieServiceImpl.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertFalse(isDateOutOfRange);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastBeforeNext() {
        DayOfWeek lastDay = DayOfWeek.MONDAY;
        DayOfWeek nextDay = DayOfWeek.WEDNESDAY;
        int expected = 2;

        int interval = dateSerieServiceImpl.getDaysIntervalBetweenTwoDaysOfWeek(lastDay, nextDay, 1);
        assertEquals(expected, interval);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastAfterNext() {
        DayOfWeek lastDay = DayOfWeek.WEDNESDAY;
        DayOfWeek nextDay = DayOfWeek.MONDAY;
        int expected = 5;

        int interval = dateSerieServiceImpl.getDaysIntervalBetweenTwoDaysOfWeek(lastDay, nextDay, 1);
        assertEquals(expected, interval);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastAfterNextWithInterval() {
        DayOfWeek lastDay = DayOfWeek.WEDNESDAY;
        DayOfWeek nextDay = DayOfWeek.MONDAY;
        int expected = 12;

        int interval = dateSerieServiceImpl.getDaysIntervalBetweenTwoDaysOfWeek(lastDay, nextDay, 2);
        assertEquals(expected, interval);
    }
}