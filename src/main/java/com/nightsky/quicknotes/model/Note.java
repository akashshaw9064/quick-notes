package com.nightsky.quicknotes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Note {

    @Transient
    public static final String SEQUENCE_NAME = "notes_sequence";

    @Id
    private long id;
    private String title;
    private String desc;
}
