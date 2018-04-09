package com.epam.test_generator.controllers.test_result;

import com.epam.test_generator.controllers.test_result.response.TestResultDTO;
import com.epam.test_generator.entities.TestResult;

public class TestResultTransformer {
    public static TestResultDTO toDto(TestResult testResult) {
        TestResultDTO dto = new TestResultDTO();
        dto.setDate(testResult.getDate());
        dto.setDuration(testResult.getDuration());
        dto.setExecutedBy(testResult.getExecutedBy());
        dto.setStatus(testResult.getStatus());
        dto.setAmountOfPassed(testResult.getAmountOfPassed());
        dto.setAmountOfFailed(testResult.getAmountOfFailed());
        dto.setAmountOfSkipped(testResult.getAmountOfSkipped());

        /* TODO: Add suits after merge with EPMLSTRTAI-703
        dto.setSuits(testResult.getSuits()
                .stream()
                .map(SuitTransformer::toDto)
                .collect(Collectors.toList()));
        */

        return dto;
    }
}
