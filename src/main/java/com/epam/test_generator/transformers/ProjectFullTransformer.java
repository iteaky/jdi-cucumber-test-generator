package com.epam.test_generator.transformers;

import com.epam.test_generator.ddd.project.application.GetProjectFullDTO;
import com.epam.test_generator.ddd.project.domain.Project;
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
