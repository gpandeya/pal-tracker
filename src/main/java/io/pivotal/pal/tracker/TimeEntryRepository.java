package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntry);

    public TimeEntry find(Long id);

    public TimeEntry update(Long id, TimeEntry timeEntry);

    public List<TimeEntry> list();

    public void delete(Long l);
}
