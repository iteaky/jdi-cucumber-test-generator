package com.epam.test_generator.entities.results;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class SuitResult extends AbstractResult implements ResultTrait {

    private String name;
    private long duration;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<CaseResult> caseResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<CaseResult> getCaseResults() {
        return caseResults;
    }

    public void setCaseResults(List<CaseResult> caseResults) {
        this.caseResults = caseResults;
        setStatus(ResultTrait.computeStatus(caseResults));
        setDuration(ResultTrait.computeDuration(caseResults));
    }
}
