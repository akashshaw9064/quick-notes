package com.nightsky.quicknotes.service.impl;

import com.nightsky.quicknotes.model.DatabaseSequences;
import com.nightsky.quicknotes.service.itrfc.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public long generateSequence(String seqName) {
        DatabaseSequences counter = mongoOperations.findAndModify(Query.query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequences.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
