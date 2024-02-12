package com.note.notetutorial.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.note.notetutorial.enumeration.Level;
import com.note.notetutorial.model.Note;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findByLevel(Level level);
    void deleteNoteById(Long id);
}
