package com.nightsky.quicknotes.service.impl;

import com.nightsky.quicknotes.model.Note;
import com.nightsky.quicknotes.model.NoteUser;
import com.nightsky.quicknotes.repo.NoteUserRepo;
import com.nightsky.quicknotes.service.itrfc.NotesUserService;
import com.nightsky.quicknotes.service.itrfc.SequenceGenerator;
import com.nightsky.quicknotes.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotesUserServiceImpl implements UserDetailsService, NotesUserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    NoteUserRepo repo;
    @Autowired
    SequenceGenerator sequenceGenerator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NoteUser noteUser = repo.findByUsername(username);
        if (noteUser == null)
            throw new UsernameNotFoundException("User with specified username was not found");

        return new User(noteUser.getUsername(), noteUser.getPassword(), new ArrayList<>());
    }

    @Override
    public List<Note> getAllNotes() {
        NoteUser user = repo.findByUsername(jwtUtil.getCurrentUser());
        return user.getNotes();
    }

    @Override
    public Note addNote(Note note) {
        //get currently logged in user
        String username = jwtUtil.getCurrentUser();
        NoteUser user = repo.findByUsername(username);
        List<Note> notes = user.getNotes();
        note.setId(sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
        notes.add(note);
        user.setNotes(notes);
        repo.save(user);
        return note;
    }

    @Override
    public NoteUser addNoteUser(NoteUser noteUser) throws Exception {
        NoteUser username = repo.findByUsername(noteUser.getUsername());
        if(username!=null)
            throw new Exception("A User with this username already exists!");
        NoteUser user = new NoteUser();
        user.setUsername(noteUser.getUsername());
        user.setPassword(passwordEncoder.encode(noteUser.getPassword()));
        user.setName(noteUser.getName());
        user.setNotes(new ArrayList<>());
        repo.save(user);
        return user;
    }

    @Override
    public void deleteNote(long id) {
        NoteUser user = repo.findByUsername(jwtUtil.getCurrentUser());
        List<Note> notes = user.getNotes();
        Note toBeDeletedNote = notes.stream().filter((n) -> n.getId() == id).findAny().get();
        notes.remove(toBeDeletedNote);
        log.info("One note was deleted by {}", user.getName());
        user.setNotes(notes);
        repo.save(user);
    }

    @Override
    public Note updateNote(Note note) {
        NoteUser user = repo.findByUsername(jwtUtil.getCurrentUser());
        List<Note> notes = user.getNotes();
        System.out.println(notes);
        Note note1 = notes.stream().filter((n) -> n.getId() == note.getId()).findAny().get();
        note1.setTitle(note.getTitle());
        note1.setDesc(note.getDesc());
        user.setNotes(notes);
        System.out.println(notes);
        repo.save(user);
        return note;
    }
}
