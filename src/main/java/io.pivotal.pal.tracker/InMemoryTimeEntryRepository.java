package io.pivotal.pal.tracker;


import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map timeEntries = new HashMap<>();

public TimeEntry create(TimeEntry timeEntry) {
    long id = timeEntries.size()+1;
    timeEntry.setId(id);
    timeEntries.put(id,timeEntry);

    return timeEntry;
}

    @Override
    public TimeEntry find(long l) {
        return (TimeEntry) timeEntries.get(l);
    }

    @Override
    public List<TimeEntry> list() {

        List entries = new ArrayList(timeEntries.values());

        return entries;


    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        if(timeEntries.containsKey(eq)){
            any.setId(eq);
            //boolean replaced = timeEntries.replace(eq,timeEntries.get(eq),any);
            timeEntries.replace(eq,any);
            return (TimeEntry) timeEntries.get(eq);
            /*if(replaced)
                return any;*/
        }
        return null;
    }

    @Override
    public List<TimeEntry> delete(long l) {
        if (timeEntries.containsKey(l))
            timeEntries.remove(l);

        List entries = new ArrayList(timeEntries.values());

        return entries;

    }
}
