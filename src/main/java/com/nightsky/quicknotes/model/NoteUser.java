package com.nightsky.quicknotes.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "note_users")
public class NoteUser {
    @Id
    private String username;
    private String password;
    private String name;
    private List<Note> notes;
}
