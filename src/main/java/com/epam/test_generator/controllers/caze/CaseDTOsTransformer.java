package com.epam.test_generator.controllers.caze;

import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.transformers.AbstractDozerTransformer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseDTOsTransformer extends AbstractDozerTransformer<Case, CaseDTO> {

    @Override
    protected Class<Case> getEntityClass() {
        return Case.class;
    }

    @Override
    protected Class<CaseDTO> getDTOClass() {
        return CaseDTO.class;
    }

    /*public List<CaseDTO> toDtoList(List<Case> cases) {
        return cases.stream().map(this::toDto).collect(Collectors.toList());
    }

    public CaseDTO toDTO(Case caze) {
        CaseDTO caseDTO = new CaseDTO();
        caseDTO.setName(caze.getName());
        caseDTO.setDescription(caze.getDescription());
        caseDTO.setEmail(caze.getEmail());
        caseDTO.setAttempts(caze.getAttempts());
        caseDTO.setLocked(caze.isLocked());
        if (caze.getRole() != null) {
            caseDTO.setRole(caze.getRole().getName());
        }
        return caseDTO;
    }*/

}
