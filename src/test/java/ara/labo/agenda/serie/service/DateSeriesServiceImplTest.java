package ara.labo.agenda.serie.service;

import ara.labo.agenda.serie.model.Recurrence;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ara.labo.agenda.serie.model.Frequency.DAILY;
import static ara.labo.agenda.serie.model.Frequency.MONTHLY;
import static ara.labo.agenda.serie.model.Frequency.WEEKLY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateSeriesServiceImplTest {

    private static final Integer OCCURRENCE_CASE_1 = 5;
    private static final Integer OCCURRENCE_CASE_2 = 5;
    private static final LocalDate START_DATE = LocalDate.of(2024, 5, 5); // Sunday
    private static final LocalDate END_DATE = LocalDate.of(2024, 7, 5);
    private static final List<DayOfWeek> DAYS = Arrays.asList(FRIDAY, WEDNESDAY, MONDAY);

    private static final Recurrence RECURRENCE_WITH_NULL_OCCURRENCE = new Recurrence(DAILY, emptyList(), START_DATE, null, null, 1);
    private static final Recurrence RECURRENCE_CASE_1 = new Recurrence(DAILY, emptyList(), START_DATE, END_DATE, OCCURRENCE_CASE_1, 1);
    private static final Recurrence RECURRENCE_CASE_2 = new Recurrence(WEEKLY, DAYS, START_DATE, END_DATE, OCCURRENCE_CASE_2, 1);
    private static final Recurrence RECURRENCE_CASE_3 = new Recurrence(WEEKLY, emptyList(), START_DATE, END_DATE, OCCURRENCE_CASE_2, 1);
    private static final Recurrence RECURRENCE_MONTHLY_CASE_0 = new Recurrence(MONTHLY, emptyList(), START_DATE, END_DATE, null, 1);
    private static final Recurrence RECURRENCE_MONTHLY_CASE_1 = new Recurrence(MONTHLY, emptyList(), START_DATE, null, null, 1);

    private DateSeriesServiceImpl dateSeriesService;

    @Before
    public void setUp() {
        dateSeriesService = new DateSeriesServiceImpl();
    }

    // getNextDateWithWeeklyFrequency
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenFrequencyIsNotWeekly() {
        dateSeriesService.getNextDateWithWeeklyFrequency(RECURRENCE_CASE_1, START_DATE);
    }

    @Test
    public void shouldGetCorrectDayWhenDaysIsEmpty() {
        LocalDate nextDate = dateSeriesService.getNextDateWithWeeklyFrequency(RECURRENCE_CASE_3, START_DATE);
        LocalDate expectedDate = LocalDate.of(2024, 5, 12);
        assertEquals(expectedDate, nextDate);
    }

    @Test
    public void shouldGetCorrectNextDay() {
        LocalDate nextDate = dateSeriesService.getNextDateWithWeeklyFrequency(RECURRENCE_CASE_2, START_DATE);
        LocalDate expectedDate = LocalDate.of(2024, 5, 6);
        assertEquals(expectedDate, nextDate);
    }

    // getDaysIntervalBetweenTwoDaysOfWeek
    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastBeforeNext() {
        int expected = 2;

        int interval = dateSeriesService.getDaysIntervalBetweenTwoDaysOfWeek(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 1);
        assertEquals(expected, interval);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastAfterNext() {
        int expected = 5;

        int interval = dateSeriesService.getDaysIntervalBetweenTwoDaysOfWeek(DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY, 1);
        assertEquals(expected, interval);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekLastAfterNextWithInterval() {
        int expected = 12;

        int interval = dateSeriesService.getDaysIntervalBetweenTwoDaysOfWeek(DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY, 2);
        assertEquals(expected, interval);
    }

    @Test
    public void shouldReturnCorrectIntervalDaysBetweenTwoDaysOfWeekSameDay() {
        int expected = 7;

        int interval = dateSeriesService.getDaysIntervalBetweenTwoDaysOfWeek(DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY, 1);
        assertEquals(expected, interval);
    }

    // getNextDayFromDaysList
    @Test
    public void shouldHaveCorrectNextDayCase1() {
        List<DayOfWeek> days = asList(WEDNESDAY, MONDAY);

        DayOfWeek nextDay = dateSeriesService.getNextDayFromDaysList(days, MONDAY);
        assertEquals(WEDNESDAY, nextDay);
    }

    @Test
    public void shouldHaveCorrectNextDayCase2() {
        List<DayOfWeek> days = asList(THURSDAY, TUESDAY);

        DayOfWeek nextDay = dateSeriesService.getNextDayFromDaysList(days, FRIDAY);
        assertEquals(TUESDAY, nextDay);
    }

    // isNextDateOutOfRange
    @Test
    public void shouldReturnTrueWhenDateIsOutOfRange() {
        LocalDate dateToCheck = END_DATE.plusDays(1);

        boolean isDateOutOfRange = dateSeriesService.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertTrue(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateIsEqualToEndDate() {
        LocalDate dateToCheck = END_DATE.plusDays(0);

        boolean isDateOutOfRange = dateSeriesService.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertFalse(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateIsNotOutOfRange() {
        LocalDate dateToCheck = END_DATE.minusDays(1);

        boolean isDateOutOfRange = dateSeriesService.isNextDateOutOfRange(RECURRENCE_CASE_1, dateToCheck);
        assertFalse(isDateOutOfRange);
    }

    @Test
    public void shouldReturnFalseWhenDateToCheckIsNull() {
        boolean isDateOutOfRange = dateSeriesService.isNextDateOutOfRange(RECURRENCE_CASE_1, null);
        assertFalse(isDateOutOfRange);
    }

    // isOccurrenceReached
    @Test
    public void shouldReturnFalseWhenOccurrenceIsNull() {
        boolean occurrenceReached = dateSeriesService.isOccurrenceReached(RECURRENCE_WITH_NULL_OCCURRENCE, 0);
        assertFalse(occurrenceReached);
    }

    @Test
    public void shouldReturnTrueWhenOccurrenceIsReached() {
        boolean occurrenceReached = dateSeriesService.isOccurrenceReached(RECURRENCE_CASE_1, OCCURRENCE_CASE_1);
        assertTrue(occurrenceReached);
    }

    @Test
    public void shouldReturnFalseWhenOccurrenceIsNotReached() {
        boolean occurrenceReached = dateSeriesService.isOccurrenceReached(RECURRENCE_CASE_1, OCCURRENCE_CASE_1 - 1);
        assertFalse(occurrenceReached);
    }

    // createDateListFromRecurrence
    @Test
    public void shouldCreateListMonthly() {
        List<LocalDate> series = dateSeriesService.createDateListFromRecurrence(RECURRENCE_MONTHLY_CASE_0);
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 6, 5),
                LocalDate.of(2024, 7, 5)
        );

        assertEquals(expected, series);

    }

    @Test
    public void shouldCreateListMonthlyWithEndDateNull() {
        List<LocalDate> series = dateSeriesService.createDateListFromRecurrence(RECURRENCE_MONTHLY_CASE_1);
        List<LocalDate> expected = Collections.singletonList(
                LocalDate.of(2024, 5, 5)
        );

        assertEquals(expected, series);

    }
}