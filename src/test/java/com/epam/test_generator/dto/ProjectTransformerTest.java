package com.epam.test_generator.dto;

import static org.mockito.Matchers.any;

import com.epam.test_generator.controllers.Project.ProjectTransformer;
import com.epam.test_generator.controllers.Project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.Project.request.ProjectUpdateDTO;
import com.epam.test_generator.controllers.Project.responce.ProjectDTO;
import com.epam.test_generator.controllers.Project.responce.ProjectFullDTO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Status;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.transformers.UserTransformer;
import java.util.Collections;
import javax.persistence.Temporal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectTransformerTest {

    @Mock
    private UserTransformer userTransformer;

    @InjectMocks
    private ProjectTransformer projectTransformer;

    private static final Long ID = 1L;
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String JIRA_KEY = "KEY";

    @Test
    public void fromDTO_ProjectCreateDTO_Success() {
        Project expectedProject = new Project();
        expectedProject.setName(NAME);
        expectedProject.setDescription(DESCRIPTION);
        expectedProject.setJiraKey(JIRA_KEY);

        ProjectCreateDTO projectCreateDTO = new ProjectCreateDTO();
        projectCreateDTO.setName(NAME);
        projectCreateDTO.setDescription(DESCRIPTION);
        projectCreateDTO.setJiraKey(JIRA_KEY);

        Project resultProject = projectTransformer.fromDto(projectCreateDTO);
        Assert.assertEquals(expectedProject, resultProject);
    }

    @Test
    public void toDTO_Project_Success() {
        Project project = new Project();
        project.setId(ID);
        project.setName(NAME);
        project.setDescription(DESCRIPTION);
        project.setJiraKey(JIRA_KEY);
        project.setActive(true);
        project.setUsers(Collections.emptySet());

        ProjectDTO expectedProjectDTO = new ProjectDTO();
        expectedProjectDTO.setId(ID);
        expectedProjectDTO.setName(NAME);
        expectedProjectDTO.setDescription(DESCRIPTION);
        expectedProjectDTO.setJiraKey(JIRA_KEY);
        expectedProjectDTO.setActive(true);
        expectedProjectDTO.setUsers(Collections.emptySet());

        ProjectDTO resultProjectDTO = projectTransformer.toDto(project);
        Assert.assertEquals(expectedProjectDTO, resultProjectDTO);
    }

    @Test
    public void toFullDTO_Project_Success() {
        Suit suit = new Suit();
        suit.setName(NAME);
        Project project = new Project();
        project.setId(ID);
        project.setName(NAME);
        project.setDescription(DESCRIPTION);
        project.setJiraKey(JIRA_KEY);
        project.setActive(true);
        project.setUsers(Collections.emptySet());
        project.setSuits(Collections.singletonList(suit));

        SuitDTO suitDTO = new SuitDTO();
        suitDTO.setName(NAME);
        ProjectFullDTO expectedProjectDTO = new ProjectFullDTO();
        expectedProjectDTO.setId(ID);
        expectedProjectDTO.setName(NAME);
        expectedProjectDTO.setDescription(DESCRIPTION);
        expectedProjectDTO.setJiraKey(JIRA_KEY);
        expectedProjectDTO.setActive(true);
        expectedProjectDTO.setUsers(Collections.emptySet());
        expectedProjectDTO.setSuits(Collections.singletonList(suitDTO));

        ProjectFullDTO resultProjectDTO = projectTransformer.toFullDto(project);
        Assert.assertEquals(expectedProjectDTO, resultProjectDTO);
    }

    @Test
    public void updateFromDto_ProjectUpdateDTO_Project_Success() {
        Suit suit = new Suit();
        suit.setName(NAME);
        suit.setPriority(1);
        suit.setStatus(Status.NOT_DONE);
        suit.setRowNumber(1);
        suit.setDescription(DESCRIPTION);

        Project expectedProject = new Project();
        expectedProject.setName(NAME);
        expectedProject.setDescription(DESCRIPTION);
        expectedProject.setJiraKey(JIRA_KEY);
        expectedProject.setActive(true);
        expectedProject.setUsers(Collections.emptySet());
        expectedProject.setSuits(Collections.singletonList(suit));

        Project updateProject = new Project();

        SuitDTO suitDTO = new SuitDTO();
        suitDTO.setName(NAME);
        suitDTO.setPriority(1);
        suitDTO.setStatus(Status.NOT_DONE);
        suitDTO.setRowNumber(1);
        suitDTO.setDescription(DESCRIPTION);

        ProjectUpdateDTO projectDTO = new ProjectUpdateDTO();
        projectDTO.setName(NAME);
        projectDTO.setDescription(DESCRIPTION);
        projectDTO.setJiraKey(JIRA_KEY);
        projectDTO.setActive(true);
        projectDTO.setUsers(Collections.emptySet());
        projectDTO.setSuits(Collections.singletonList(suitDTO));

        Project resultProject = projectTransformer.updateFromDto(projectDTO, updateProject);
        Assert.assertEquals(expectedProject, resultProject);
    }

}
