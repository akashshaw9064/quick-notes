package com.nightsky.quicknotes.service.itrfc;

import com.nightsky.quicknotes.model.Note;
import com.nightsky.quicknotes.model.NoteUser;

import java.util.List;

public interface NotesUserService {

    public List<Note> getAllNotes();

    public Note addNote(Note note);

    public NoteUser addNoteUser(NoteUser noteUser) throws Exception;

    public void deleteNote(long id);

    public Note updateNote(Note note);
}

