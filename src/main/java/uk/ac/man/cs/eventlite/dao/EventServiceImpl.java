package uk.ac.man.cs.eventlite.dao;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

	private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

	private final static String DATA = "data/events.json";
	
	private LocalDate today = LocalDate.now();

	@Override
	public long count() {
		return eventRepository.count();
	}
	
	@Override 
	public Iterable<Event> findAll() { 	
		return eventRepository.findAll(Sort.by("date", "time", "name")); 
	}
	
	@Override
	public Iterable<Event> findAllByNameContainingIgnoreCase(String name){
		return eventRepository.findAllByNameContainingIgnoreCase(name, Sort.by("date", "time", "name"));
	}
	
	@Override
	public <S extends Event> S save(S event) {
		return(eventRepository.save(event));
	}
	
	@Override 
	public void deleteById(Long ID) {
		eventRepository.deleteById(ID);
	} 
	
	@Override 
	public Optional<Event> findById(Long ID) {
		return eventRepository.findById(ID);
	}

	@Override
	public List<Event> findAllByVenue(Venue venue) {
		return eventRepository.findAllByVenue(venue, Sort.by("date", "time", "name"));
	}
	
	@Override
	public Iterable<Event> find3Upcoming(){
		return eventRepository.findTop3ByDateGreaterThanEqualOrderByDateAscTimeAscNameAsc(today);
	}
	
}
	  
