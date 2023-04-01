package com.nightsky.quicknotes.repo;

import com.nightsky.quicknotes.model.NoteUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteUserRepo extends MongoRepository<NoteUser, String> {
    public NoteUser findByUsername(String username);
}
