package com.epam.test_generator.entities.results.api;

import com.epam.test_generator.entities.Status;
import com.epam.test_generator.entities.results.AbstractResult;
import com.epam.test_generator.entities.results.SuitResult;
import java.util.List;
import java.util.stream.Collectors;

public interface TestResultTrait {

    void setStatus(Status status);

    void setDuration(long duration);

    void setAmountOfPassed(int amountOfPassed);

    void setAmountOfFailed(int amountOfFailed);

    void setAmountOfSkipped(int amountOfSkipped);

    default void setSuits(List<SuitResult> suits) {
        countTestResultStatistics(suits);
        setStatus(computeStatus(suits));
        setDuration(computeDuration(suits));
    }

    /**
     * Summarize amount of Passed, Skipped and Failed tests of Tests executions.
     *
     * @param results list of {@link AbstractResult}
     */
    default void countTestResultStatistics(List<? extends AbstractResult> results) {
        int amountOfPassed = 0;
        int amountOfSkipped = 0;
        int amountOfFailed = 0;

        for (AbstractResult suitResult : results) {
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

    static Status computeStatus(List<? extends AbstractResult> results) {
        final List<Status> collect = results
            .stream()
            .map(AbstractResult::getStatus)
            .collect(Collectors.toList());

        if (collect.stream().anyMatch(Status.FAILED::equals)) {
            return Status.FAILED;
        } else if (collect.stream().anyMatch(Status.PASSED::equals)) {
            return Status.PASSED;
        } else {
            return Status.SKIPPED;
        }
    }

    static long computeDuration(List<SuitResult> results) {
        return results
            .stream()
            .mapToLong(SuitResult::getDuration)
            .sum();
    }
}
