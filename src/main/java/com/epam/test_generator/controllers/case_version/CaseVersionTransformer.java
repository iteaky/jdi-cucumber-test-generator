package com.epam.test_generator.controllers.case_version;

import com.epam.test_generator.controllers.case_version.response.CaseVersionDTO;
import com.epam.test_generator.pojo.CaseVersion;

import java.util.stream.Collectors;

public class CaseVersionTransformer {
    public static CaseVersionDTO toDto(CaseVersion caseVersion) {
        return new CaseVersionDTO(
                caseVersion.getCommitId(),
                caseVersion.getUpdatedDate().toString(),
                caseVersion.getAuthor(),
                caseVersion.getPropertyDifferences()
                    .stream()
                    .map(PropertyDifferenceTransformer::toDto)
                    .collect(Collectors.toList())
        );
    }
}
