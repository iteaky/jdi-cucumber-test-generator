package com.epam.test_generator.dao.impl;

import com.epam.test_generator.entities.Case;
import com.epam.test_generator.pojo.JiraSubTask;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JiraSubStroryDAOTest {

    @Mock
    private JiraClient client;

    @Mock
    private Case caze;

    @Mock
    private Issue issue;

    @InjectMocks
    private JiraSubStroryDAO jiraSubStroryDAO;

    private static final String JIRA_KEY = "key";
    private static final String JIRA_FILTER = "filter";

    @Before
    public void setUp() throws Exception { }

    @Test
    public void getSubStoryByJiraKey_JiraSubTask_Success() throws Exception {
        when(client.getIssue(anyString())).thenReturn(issue);

        JiraSubTask expectedSubTask = new JiraSubTask(issue);
        JiraSubTask resultSubTask = jiraSubStroryDAO.getSubStoryByJiraKey(JIRA_KEY);
        Assert.assertEquals(expectedSubTask, resultSubTask);
    }

    @Test(expected = JiraException.class)
    public void getSubStoryUnvalidJiraKey_JiraSubTask_MalformedParametersException() throws Exception {
        when(client.getIssue(anyString())).thenThrow(new JiraException(""));
        jiraSubStroryDAO.getSubStoryByJiraKey(JIRA_KEY);
    }

    @Test(expected = JiraException.class)
    public void getUnexistedSubStoryByJiraKey_JiraSubtask_Null() throws Exception{
        when(client.getIssue(anyString())).thenThrow(new JiraException("", new RestException("",404,"")));
        JiraSubTask subTask = jiraSubStroryDAO.getSubStoryByJiraKey(JIRA_KEY);
        Assert.assertNull(subTask);
    }

    @Test
    public void getJiraSubtoriesByFilter_JiraSubTasks_Success() throws JiraException {
        Issue.SearchResult searchResult = new Issue.SearchResult();
        searchResult.issues = Collections.singletonList(issue);
        when(client.searchIssues(anyString(), anyInt())).thenReturn(searchResult);
        List<JiraSubTask> expectedStories = Collections.singletonList(new JiraSubTask(issue));

        List<JiraSubTask> resultStories = jiraSubStroryDAO.getJiraSubtoriesByFilter(JIRA_FILTER);

        Assert.assertEquals(expectedStories, resultStories);
    }

    @Test(expected = JiraException.class)
    public void getJiraSubtoriesByInvalidFilter_JiraSubTasks_MalformedParametersException() throws Exception {
        when(client.searchIssues(anyString(), anyInt())).thenThrow(new JiraException("a"));
        jiraSubStroryDAO.getJiraSubtoriesByFilter(JIRA_FILTER);
    }

    @Test
    public void getNonexistentJiraStoriesByInvalidFilter_JadaSubTask_Success() throws JiraException {
        when(client.searchIssues(anyString(), anyInt())).thenThrow(new JiraException("a", new RestException("a", 404, "bad")));
        List<JiraSubTask> subTasks = jiraSubStroryDAO.getJiraSubtoriesByFilter(JIRA_KEY);
        Assert.assertTrue(subTasks.isEmpty());
    }

    @Test(expected = JiraException.class)
    public void updateSubStoryByJiraKey() throws JiraException {
        when(client.getIssue(anyString())).thenReturn(issue);
        when(client.getIssue(anyString()).update()).thenCallRealMethod();
        jiraSubStroryDAO.updateSubStoryByJiraKey(caze);
    }

    @Test(expected = JiraException.class)
    public void createSubStory() throws JiraException {
        when(client.createIssue(anyString(),anyString())).thenCallRealMethod();
        jiraSubStroryDAO.createSubStory(caze);
    }
}