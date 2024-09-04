package ara.labo.agenda.serie.service;

import ara.labo.agenda.serie.model.Recurrence;

import java.time.LocalDate;
import java.util.List;

public interface DateSeriesService {
    List<LocalDate> createDateListFromRecurrence(Recurrence recurrence);
}
