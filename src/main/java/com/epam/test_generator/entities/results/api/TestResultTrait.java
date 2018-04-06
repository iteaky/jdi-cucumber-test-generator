package com.epam.test_generator.entities.results.api;

import com.epam.test_generator.entities.Status;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.entities.results.SuitResult;
import java.util.List;
import java.util.stream.Collectors;

public interface TestResultTrait {

    List<SuitResult> getSuits();

    void setStatus(Status status);

    void setDuration(long duration);

    void setAmountOfSkipped(int amountOfSkipped);

    void setAmountOfFailed(int amountOfFailed);

    void setAmountOfPassed(int amountOfPassed);

    default void summarizeDuration(){
        setDuration(getSuits()
            .stream()
            .mapToLong(SuitResult::getDuration)
            .sum());
    }

    default void evaluateStatus(){
        final List<Status> collect = getSuits()
            .stream()
            .map(SuitResult::getStatus)
            .collect(Collectors.toList());

        if (collect.stream().anyMatch(Status.FAILED::equals)) {
            setStatus(Status.FAILED);
        }
        else if (collect.stream().anyMatch(Status.PASSED::equals)) {
            setStatus(Status.PASSED);
        }
        else {
            setStatus(Status.SKIPPED);
        }
    }

    default void countTestResultStatistics(){
        int amountOfPassed = 0;
        int amountOfSkipped = 0;
        int amountOfFailed = 0;

        for (SuitResult suitResult : getSuits()) {
            switch (suitResult.getStatus()) {
                case PASSED:
                    amountOfPassed++;
                    break;
                case SKIPPED:
                    amountOfSkipped++;
                    break;
                case FAILED:
                    amountOfFailed++;
                    break;
            }
        }

        setAmountOfPassed(amountOfPassed);
        setAmountOfFailed(amountOfFailed);
        setAmountOfSkipped(amountOfSkipped);
    }
}
