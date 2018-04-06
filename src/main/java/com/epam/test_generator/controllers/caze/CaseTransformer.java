package com.epam.test_generator.controllers.caze;

import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.transformers.AbstractDozerTransformer;
import org.springframework.stereotype.Component;

@Component
public class CaseTransformer extends AbstractDozerTransformer<Case, CaseDTO> {

    @Override
    protected Class<Case> getEntityClass() {
        return Case.class;
    }

    @Override
    protected Class<CaseDTO> getDTOClass() {
        return CaseDTO.class;
    }

}
