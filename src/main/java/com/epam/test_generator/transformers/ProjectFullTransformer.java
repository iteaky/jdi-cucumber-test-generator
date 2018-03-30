package com.epam.test_generator.transformers;

import com.epam.test_generator.dto.GetProjectFullDTO;
import com.epam.test_generator.entities.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectFullTransformer extends AbstractDozerTransformer<Project, GetProjectFullDTO> {

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }

    @Override
    protected Class<GetProjectFullDTO> getDTOClass() {
        return GetProjectFullDTO.class;
    }
}
