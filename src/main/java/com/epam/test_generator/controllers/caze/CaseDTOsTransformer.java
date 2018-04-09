package com.epam.test_generator.controllers.caze;

import com.epam.test_generator.controllers.caze.request.AddCaseToSuitDTO;
import com.epam.test_generator.controllers.caze.request.UpdateCaseDTO;
import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.transformers.AbstractDozerTransformer;
import com.epam.test_generator.transformers.StepTransformer;
import com.epam.test_generator.transformers.TagTransformer;
import liquibase.integration.ant.TagDatabaseTask;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
//public class CaseDTOsTransformer extends AbstractDozerTransformer<Case, CaseDTO> {

public class CaseDTOsTransformer{

    /*@Override
    protected Class<Case> getEntityClass() {
        return Case.class;
    }

    @Override
    protected Class<CaseDTO> getDTOClass() {
        return CaseDTO.class;
    }*/

    public List<CaseDTO> toDtoList(List<Case> cases) {
        return cases.stream().map(this::toDto).collect(Collectors.toList());
    }

    public CaseDTO toDto(Case caze) {
        CaseDTO caseDTO = new CaseDTO();
        caseDTO.setName(caze.getName());
        caseDTO.setDescription(caze.getDescription());
        caseDTO.setSteps(new StepTransformer().toDtoList(caze.getSteps()));
        caseDTO.setCreationDate(caze.getCreationDate());
        caseDTO.setUpdateDate(caze.getUpdateDate());
        caseDTO.setPriority(caze.getPriority());
        caseDTO.setTags(caze.getTags().stream().map(new TagTransformer()::toDto).collect(Collectors.toSet()));
        caseDTO.setStatus(caze.getStatus());
        caseDTO.setComment(caze.getComment());
        caseDTO.setJiraProjectKey(caze.getJiraProjectKey());
        caseDTO.setJiraParentKey(caze.getJiraParentKey());
        caseDTO.setJiraKey(caze.getJiraKey());
        return caseDTO;
    }

    public Case fromDto(AddCaseToSuitDTO addCaseToSuitDTO) {
        Case caze = new Case();
        caze.setName(addCaseToSuitDTO.getName());
        caze.setDescription(addCaseToSuitDTO.getDescription());
        caze.setPriority(addCaseToSuitDTO.getPriority());
        caze.setComment(addCaseToSuitDTO.getComment());
        caze.setTags(addCaseToSuitDTO.getTags().stream().map(new TagTransformer()::fromDto).collect(Collectors.toSet()));
        return caze;
    }

}
