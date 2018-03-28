package com.epam.test_generator.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.test_generator.dao.interfaces.StepSuggestionDAO;
import com.epam.test_generator.dto.StepSuggestionCreateDTO;
import com.epam.test_generator.dto.StepSuggestionDTO;
import com.epam.test_generator.dto.StepSuggestionUpdateDTO;
import com.epam.test_generator.entities.StepSuggestion;
import com.epam.test_generator.entities.StepType;
import com.epam.test_generator.services.exceptions.NotFoundException;
import com.epam.test_generator.transformers.StepSuggestionTransformer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class StepSuggestionServiceTest {

    private static final long SIMPLE_AUTOCOMPLETE_ID = 1L;
    private static final long SIMPLE_STEP_SUGGESTION_ID = 1L;
    private static final int PAGE_SIZE = 1;

    @Mock
    private StepSuggestionTransformer stepSuggestionTransformer;
    @Mock
    private StepSuggestionDAO stepSuggestionDAO;
    @InjectMocks
    private StepSuggestionService stepSuggestionService;
    private List<StepSuggestion> listSteps;
    private List<StepSuggestionDTO> expectedListSteps;

    @Before
    public void setUp() {
        listSteps = new ArrayList<>();
        listSteps.add(new StepSuggestion(1L, "StepSuggestion 1", StepType.GIVEN, 0L));
        listSteps.add(new StepSuggestion(2L, "StepSuggestion 2", StepType.THEN, 0L));

        expectedListSteps = new ArrayList<>();
        expectedListSteps.add(new StepSuggestionDTO(1L, "StepSuggestion 1", StepType.GIVEN, 0L));
        expectedListSteps.add(new StepSuggestionDTO(2L, "StepSuggestion 2", StepType.THEN, 0L));
    }

    @Test
    public void get_StepsSuggestions_Success() {
        when(stepSuggestionDAO.findAll()).thenReturn(listSteps);
        when(stepSuggestionTransformer.toDtoList(listSteps)).thenReturn(expectedListSteps);

        List<StepSuggestionDTO> getListStepsSuggestion = stepSuggestionService
            .getStepsSuggestions(null, null, null);
        assertEquals(true,
            Arrays.deepEquals(expectedListSteps.toArray(), getListStepsSuggestion.toArray()));

        verify(stepSuggestionDAO).findAll();
    }

    @Test
    public void get_StepsSuggestionsPage_Success() {
        Page<StepSuggestion> testPage = new PageImpl<>(listSteps.subList(0, 1));

        when(stepSuggestionDAO.findAll(any(Pageable.class))).thenReturn(testPage);
        when(stepSuggestionTransformer.toDtoList(testPage.getContent()))
            .thenReturn(expectedListSteps.subList(0, 1));

        List<StepSuggestionDTO> getListStepsSuggestion = stepSuggestionService
            .getStepsSuggestions(null, 1, PAGE_SIZE);
        assertEquals(expectedListSteps.subList(0, 1), getListStepsSuggestion);

        verify(stepSuggestionDAO).findAll(any(Pageable.class));
        verify(stepSuggestionTransformer).toDtoList(testPage.getContent());
    }

    @Test
    public void get_StepsSuggestionsByTypePage1_Success() {
        Page<StepSuggestion> testPage = new PageImpl<>(listSteps.subList(0, 1));

        when(stepSuggestionDAO.findAll(any(Pageable.class))).thenReturn(testPage);
        when(stepSuggestionTransformer.toDtoList(testPage.getContent()))
            .thenReturn(expectedListSteps.subList(0, 1));

        List<StepSuggestionDTO> getListStepsSuggestion = stepSuggestionService
            .getStepsSuggestions(StepType.GIVEN, 1, PAGE_SIZE);
        assertEquals(expectedListSteps.subList(0, 1), getListStepsSuggestion);

        verify(stepSuggestionDAO).findAll(any(Pageable.class));
        verify(stepSuggestionTransformer).toDtoList(testPage.getContent());
    }

    @Test
    public void get_StepsSuggestionsByTypePage2_Success() {
        Page<StepSuggestion> testPage = new PageImpl<>(listSteps.subList(1, 2));

        when(stepSuggestionDAO.findAll(any(Pageable.class))).thenReturn(testPage);
        when(stepSuggestionTransformer.toDtoList(testPage.getContent()))
            .thenReturn(expectedListSteps.subList(1, 2));

        List<StepSuggestionDTO> getListStepsSuggestion = stepSuggestionService
            .getStepsSuggestions(StepType.THEN, 2, PAGE_SIZE);
        assertEquals(expectedListSteps.subList(1, 2), getListStepsSuggestion);

        verify(stepSuggestionDAO).findAll(any(Pageable.class));
        verify(stepSuggestionTransformer).toDtoList(testPage.getContent());
    }

    @Test
    public void get_StepSuggestionById_Success() {
        StepSuggestion expected = new StepSuggestion(SIMPLE_STEP_SUGGESTION_ID, "StepSuggestion 1",
            StepType.GIVEN, 0L);
        StepSuggestionDTO expectedDTO = new StepSuggestionDTO(SIMPLE_STEP_SUGGESTION_ID,
            "StepSuggestion 1", StepType.GIVEN, 0L);

        when(stepSuggestionDAO.findById(anyLong())).thenReturn(Optional.ofNullable(expected));
        when(stepSuggestionTransformer.toDto(any(StepSuggestion.class))).thenReturn(expectedDTO);

        assertEquals(stepSuggestionService.getStepsSuggestion(SIMPLE_STEP_SUGGESTION_ID),
            expectedDTO);

        verify(stepSuggestionDAO).findById(anyLong());
        verify(stepSuggestionTransformer).toDto(any(StepSuggestion.class));
    }

    @Test(expected = NotFoundException.class)
    public void get_StepSuggestionById_NotFoundException() {
        when(stepSuggestionDAO.findById(anyLong())).thenReturn(null);

        stepSuggestionService.getStepsSuggestion(SIMPLE_STEP_SUGGESTION_ID);
    }

    @Test
    public void get_StepSuggestionByType_Success() {
        StepSuggestionDTO expectedDTO = new StepSuggestionDTO(2L, "StepSuggestion 2",
            StepType.WHEN, 0L);
        StepSuggestion expected = new StepSuggestion(2L, "StepSuggestion 2", StepType.WHEN, 0L);

        when(stepSuggestionDAO.findAll()).thenReturn(Collections.singletonList(expected));
        when(stepSuggestionTransformer.toDtoList(anyList()))
            .thenReturn(Collections.singletonList(expectedDTO));

        List<StepSuggestionDTO> actual = stepSuggestionService.getStepsSuggestionsByType
            (StepType.GIVEN);

        assertEquals(Collections.singletonList(expectedDTO), actual);

        verify(stepSuggestionDAO).findAll();
    }

    @Test
    public void add_StepSuggestion_Success() {
        StepSuggestion newStepSuggestion = new StepSuggestion(3L, "StepSuggestion 3", StepType.AND,
            0L);
        StepSuggestionCreateDTO newStepSuggestionDTO = new StepSuggestionCreateDTO(
            "StepSuggestion 3",
            StepType.AND);

        when(stepSuggestionDAO.save(any(StepSuggestion.class))).thenReturn(newStepSuggestion);

        Long id = stepSuggestionService.addStepSuggestion(newStepSuggestionDTO);
        assertEquals(newStepSuggestion.getId(), id);

        verify(stepSuggestionDAO).save(any(StepSuggestion.class));
    }

    @Test
    public void update_StepSuggestion_Success() {
        StepSuggestionUpdateDTO expectedUpdateDTO = new StepSuggestionUpdateDTO("StepSuggestion 2",
            StepType.WHEN, 0L);
        StepSuggestionDTO expectedDTO = new StepSuggestionDTO(2L, "StepSuggestion 2",
            StepType.WHEN, 0L);
        StepSuggestion expected = new StepSuggestion(2L, "StepSuggestion 2", StepType.WHEN, 0L);

        when(stepSuggestionDAO.findById(anyLong())).thenReturn(Optional.ofNullable(expected));
        when(stepSuggestionService.getStepsSuggestion(anyLong())).thenReturn(expectedDTO);

        stepSuggestionService.updateStepSuggestion(SIMPLE_STEP_SUGGESTION_ID, expectedUpdateDTO);
        StepSuggestionDTO actual = stepSuggestionService.getStepsSuggestion(2L);
        assertEquals(expectedDTO, actual);

        verify(stepSuggestionDAO).save(any(StepSuggestion.class));
    }

    @Test(expected = NotFoundException.class)
    public void update_StepSuggestion_NotFoundException() {
        when(stepSuggestionDAO.findById(anyLong())).thenReturn(null);

        stepSuggestionService
            .updateStepSuggestion(SIMPLE_STEP_SUGGESTION_ID, any(StepSuggestionUpdateDTO.class));
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void update_StepSuggestion_OptimisticLockingFailureException() {
        StepSuggestion stepSuggestion = new StepSuggestion(
            SIMPLE_STEP_SUGGESTION_ID, "content", StepType.GIVEN, 0L);
        StepSuggestionUpdateDTO stepSuggestionUpdateDTO =
            new StepSuggestionUpdateDTO("updated", StepType.GIVEN, 1L);
        when(stepSuggestionDAO.findById(anyLong()).get()).thenReturn(stepSuggestion);

        stepSuggestionService
            .updateStepSuggestion(SIMPLE_STEP_SUGGESTION_ID, stepSuggestionUpdateDTO);
    }

    @Test
    public void remove_StepSuggestion_Success() {
        when(stepSuggestionDAO.findById(anyLong()))
            .thenReturn(Optional.ofNullable(listSteps.get(0)));

        stepSuggestionService.removeStepSuggestion(SIMPLE_AUTOCOMPLETE_ID);

        verify(stepSuggestionDAO).findById(anyLong());
        verify(stepSuggestionDAO).deleteById(SIMPLE_AUTOCOMPLETE_ID);
    }

    @Test(expected = NotFoundException.class)
    public void remove_StepSuggestion_NotFoundException() {
        when(stepSuggestionDAO.findById(anyLong())).thenReturn(null);

        stepSuggestionService.removeStepSuggestion(SIMPLE_AUTOCOMPLETE_ID);
    }

}