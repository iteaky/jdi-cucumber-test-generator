package com.epam.test_generator.entities.results;

import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.results.api.TestResultTrait;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TestResult extends AbstractResult implements TestResultTrait {

    private LocalDate date;

    private Long duration;

    private String executedBy;

    private int amountOfPassed;

    private int amountOfFailed;

    private int amountOfSkipped;

    public TestResult() {
        date = LocalDate.now();
    }

    @ManyToOne
    private Project project;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<SuitResult> suits;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public int getAmountOfPassed() {
        return amountOfPassed;
    }

    public void setAmountOfPassed(int amountOfPassed) {
        this.amountOfPassed = amountOfPassed;
    }

    public int getAmountOfFailed() {
        return amountOfFailed;
    }

    public void setAmountOfFailed(int amountOfFailed) {
        this.amountOfFailed = amountOfFailed;
    }

    public int getAmountOfSkipped() {
        return amountOfSkipped;
    }

    public void setAmountOfSkipped(int amountOfSkipped) {
        this.amountOfSkipped = amountOfSkipped;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<SuitResult> getSuits() {
        return suits;
    }

    public void setSuits(List<SuitResult> suits) {
        this.suits = suits;
        TestResultTrait.super.setSuits(suits);
    }

    public Project getProject() {
        return project;
    }
}
