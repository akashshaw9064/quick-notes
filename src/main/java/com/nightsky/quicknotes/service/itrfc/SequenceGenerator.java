package com.nightsky.quicknotes.service.itrfc;

import org.springframework.stereotype.Service;

public interface SequenceGenerator {
    public long generateSequence(String seqName);
}
