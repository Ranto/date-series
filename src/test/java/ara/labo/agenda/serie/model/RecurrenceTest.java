package ara.labo.agenda.serie.model;

import org.junit.Test;

import java.time.LocalDate;

import static ara.labo.agenda.serie.model.Frequency.DAILY;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class RecurrenceTest {

    @Test
    public void shouldSetDefaultEndDateWhenEndDateIsNull() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        Recurrence recurrence = new Recurrence(DAILY, emptyList(), startDate, null, null, 1);
        LocalDate expected = LocalDate.of(2024, 11, 9);
        assertEquals(expected, recurrence.getEndDate());
    }
}