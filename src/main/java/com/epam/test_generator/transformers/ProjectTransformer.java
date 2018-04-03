package com.epam.test_generator.transformers;

import com.epam.test_generator.ddd.project.application.CreateProjectDTO;
import com.epam.test_generator.ddd.project.domain.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectTransformer extends AbstractDozerTransformer<Project, CreateProjectDTO> {

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }

    @Override
    protected Class<CreateProjectDTO> getDTOClass() {
        return CreateProjectDTO.class;
    }



}
