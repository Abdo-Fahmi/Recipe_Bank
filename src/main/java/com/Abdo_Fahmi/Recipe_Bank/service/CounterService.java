package com.Abdo_Fahmi.Recipe_Bank.service;

import com.Abdo_Fahmi.Recipe_Bank.model.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

// This class is used to auto generate and increment the id field in the database
@Service
public class CounterService {
    @Autowired
    private MongoOperations mongoOperations;

    public String getNextSequence(String seqName) {
        Counter counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Counter.class
        );
        long seq = counter != null ? counter.getSeq() : 1;
        return seqName + "-" + seq;
    }
}
