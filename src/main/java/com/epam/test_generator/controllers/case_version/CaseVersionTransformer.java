package com.epam.test_generator.controllers.case_version;

import com.epam.test_generator.controllers.case_version.response.CaseVersionDTO;
import com.epam.test_generator.pojo.CaseVersion;

import java.util.stream.Collectors;

public class CaseVersionTransformer {
    public static CaseVersionDTO toDTO(CaseVersion caseVersion) {
        return new CaseVersionDTO(
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
