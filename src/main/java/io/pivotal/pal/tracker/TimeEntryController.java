package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    private final CounterService counter;
    private final GaugeService gauge;
    private TimeEntryRepository _timeEntryRepository;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            CounterService counter,
            GaugeService gauge
    ) {
        this._timeEntryRepository = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry newTimeEntry = _timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", _timeEntryRepository.list().size());
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(newTimeEntry, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable Long id) {
        TimeEntry newTimeEntry =  _timeEntryRepository.find(id);

        ResponseEntity<TimeEntry> responseEntity = null;
        if (newTimeEntry != null) {
            counter.increment("TimeEntry.read");
            responseEntity = new ResponseEntity<TimeEntry>(newTimeEntry, HttpStatus.OK);
        }
        else {

            responseEntity = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

//    @PostMapping("")
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry newTimeEntry = _timeEntryRepository.update(id, timeEntry);
        ResponseEntity<TimeEntry> responseEntity = null;
        if (newTimeEntry != null) {
            counter.increment("TimeEntry.updated");
            responseEntity = new ResponseEntity<>(newTimeEntry, HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> newList= _timeEntryRepository.list();
        counter.increment("TimeEntry.listed");
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<List<TimeEntry>>(newList, HttpStatus.OK);
        return responseEntity;
    }

  @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        _timeEntryRepository.delete(id);
      counter.increment("TimeEntry.deleted");
      gauge.submit("timeEntries.count", _timeEntryRepository.list().size());
       return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);

    }
}
