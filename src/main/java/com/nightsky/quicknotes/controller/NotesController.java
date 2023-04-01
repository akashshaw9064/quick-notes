package com.nightsky.quicknotes.controller;

import com.nightsky.quicknotes.model.AuthRequest;
import com.nightsky.quicknotes.model.Note;
import com.nightsky.quicknotes.model.NoteUser;
import com.nightsky.quicknotes.service.itrfc.AuthService;
import com.nightsky.quicknotes.service.itrfc.NotesUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true" ,maxAge = 3600)
@RestController
public class NotesController {
    @Autowired
    private NotesUserService notesUserService;

    @Autowired
    AuthService authService;

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping("/authentication")
    public String authenticate(@RequestBody AuthRequest request) throws Exception {
        return authService.authenticate(request);
    }

    @PostMapping("/new-user")
    public NoteUser addUser(@RequestBody NoteUser user) throws Exception {
        return notesUserService.addNoteUser(user);
    }

    @PostMapping("/new-note")
    public Note addNote(@RequestBody Note note) {
        return notesUserService.addNote(note);
    }

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return notesUserService.getAllNotes();
    }

    @DeleteMapping("/delete-note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable long id) {
        notesUserService.deleteNote(id);
        return ResponseEntity.ok().body("SUCCESS");
    }

    @PutMapping("/update-note")
    public Note updateNote(@RequestBody Note note) {
        return notesUserService.updateNote(note);
    }
}
