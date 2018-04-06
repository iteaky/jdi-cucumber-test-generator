package com.epam.test_generator.controllers.CaseVersion;

import com.epam.test_generator.controllers.CaseVersion.response.CaseVersionGetDTO;
import com.epam.test_generator.pojo.CaseVersion;

import java.util.stream.Collectors;

public class CaseVersionTransformer {
    public static CaseVersionGetDTO toDTO(CaseVersion caseVersion) {
        return new CaseVersionGetDTO(
                caseVersion.getCommitId(),
                caseVersion.getUpdatedDate().toString(),
                caseVersion.getAuthor(),
                caseVersion.getPropertyDifferences()
                    .stream()
                    .map(PropertyDifferenceTransformer::toDTO)
                    .collect(Collectors.toList())
        );
    }
}
