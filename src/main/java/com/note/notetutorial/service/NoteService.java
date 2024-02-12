package com.note.notetutorial.service;

import java.time.LocalDateTime;
import static java.util.Collections.singleton;
import java.util.List;
import static java.util.Optional.ofNullable;
import java.util.Optional;
import static java.util.Collections.*;

import org.springframework.stereotype.Service;
import static org.springframework.http.HttpStatus.*;

import com.note.notetutorial.model.HttpResponse;
import com.note.notetutorial.model.Note;
import com.note.notetutorial.repo.NoteRepo;
import static com.note.notetutorial.util.DateUtil.dateTimeFormatter;
import com.note.notetutorial.enumeration.Level;
import com.note.notetutorial.exception.NoteNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoteService {
	
	private final NoteRepo noteRepo;
	
	/* method to get all notes */
    public HttpResponse<Note> getNotes() {
        log.info("Fetching all the notes from the database");
        return HttpResponse.<Note>builder()
                .notes(noteRepo.findAll())
                .message(noteRepo.count() > 0 ? noteRepo.count() + " notes retrieved" : "No notes to display")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    /*method to filter notes*/
    public HttpResponse<Note> filterNotes(Level level) {
        List<Note> notes = noteRepo.findByLevel(level);
        log.info("Filtering notes by level {}", level);
        return HttpResponse.<Note>builder()
                .notes(notes)
                .message(notes.size() + " notes are of " + level + " importance")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }
    
    /*method to save a new note*/
    public HttpResponse<Note> saveNote(Note note) {
        log.info("Saving new note to the database");
        note.setCreatedAt(LocalDateTime.now());
        // Note newNote = noteRepo.save(note);  //redundant
        return HttpResponse.<Note>builder()
                .notes(singleton(noteRepo.save(note)))
                .message("Note created successfully")
                .status(CREATED)
                .statusCode(CREATED.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }    
    
    /*method to save a new note*/
    public HttpResponse<Note> updateNote(Note note) throws NoteNotFoundException {
        log.info("Updating note to the database");

        Optional<Note> optionalNote = ofNullable(noteRepo.findById(note.getId())
        		.orElseThrow(() -> new NoteNotFoundException("The note was not found on the server")));

        Note updateNote = optionalNote.get();
        updateNote.setId(note.getId());
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());
        updateNote.setLevel(note.getLevel());
        //updateNote.setCreatedAt(note.getCreatedAt()); //LocalDateTime.now()
        System.out.println("Created At = " + note.getCreatedAt());
        
        noteRepo.save(updateNote);
        
        return HttpResponse.<Note>builder()
                .notes(singleton(noteRepo.save(note)))
                .message("Note updated successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
//                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }    
    
    
    /*method to delete a note*/
    public HttpResponse<Note> deleteNote(Long id) throws NoteNotFoundException {
        log.info("Deleting note from the database by id {}", id);
        
        Optional<Note> optionalNote = ofNullable(noteRepo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the server")));

        optionalNote.ifPresent(noteRepo::delete);
        
        return HttpResponse.<Note>builder()
                .notes(singleton(optionalNote.get()))
                .message("Note deleted successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }
}
