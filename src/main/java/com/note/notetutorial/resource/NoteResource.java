package com.note.notetutorial.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.note.notetutorial.service.NoteService;
import static com.note.notetutorial.util.DateUtil.dateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.note.notetutorial.exception.NoteNotFoundException;
import com.note.notetutorial.model.HttpResponse;
import com.note.notetutorial.model.Note;

import java.net.URI;

import java.time.LocalDateTime;
import com.note.notetutorial.enumeration.Level;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/note")
@RequiredArgsConstructor
public class NoteResource {
	
	private final NoteService noteService;

	@GetMapping("/all")
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }
	
	@PostMapping("/add")
    public ResponseEntity<HttpResponse<Note>> saveNote(@RequestBody @Valid Note note) {
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/note/all").toUriString())
        ).body(noteService.saveNote(note));
    }
	
	@GetMapping("/filter")
    public ResponseEntity<HttpResponse<Note>> filterNotes(@RequestParam(value = "level") Level level) {
        return ResponseEntity.ok().body(noteService.filterNotes(level));
    }
	
	@PutMapping("/update")
    public ResponseEntity<HttpResponse<Note>> updateNote(@RequestBody @Valid Note note) throws NoteNotFoundException {
        return ResponseEntity.ok().body(noteService.updateNote(note));
    }
	
	@DeleteMapping("/delete/{noteId}")
    public ResponseEntity<HttpResponse<Note>> updateNote(@PathVariable(value = "noteId") Long id) throws NoteNotFoundException {
        return ResponseEntity.ok().body(noteService.deleteNote(id));
    }
	
    @RequestMapping("/error")
    public ResponseEntity<HttpResponse<?>> handleError(HttpServletRequest request) {
        return new ResponseEntity<>(
        		HttpResponse.builder()
                	.reason("There is no mapping for a " + request.getMethod() + " request for this path on the server")
                    .developerMessage("There is no mapping for a " + request.getMethod() + " request for this path on the server")
                    .status(NOT_FOUND)
                    .statusCode(NOT_FOUND.value())
                    .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                    .build(), 
                    NOT_FOUND);
    }
}
