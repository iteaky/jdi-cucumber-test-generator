package com.epam.test_generator.services;

import static com.epam.test_generator.services.utils.UtilsService.caseBelongsToSuit;
import static com.epam.test_generator.services.utils.UtilsService.checkNotNull;
import static com.epam.test_generator.services.utils.UtilsService.stepBelongsToCase;

import com.epam.test_generator.controllers.step.request.StepCreateDTO;
import com.epam.test_generator.controllers.step.request.StepUpdateDTO;
import com.epam.test_generator.dao.interfaces.CaseVersionDAO;
import com.epam.test_generator.dao.interfaces.StepDAO;
import com.epam.test_generator.dao.interfaces.SuitVersionDAO;
import com.epam.test_generator.controllers.step.response.StepDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Step;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.transformers.StepTransformer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StepService {

    @Autowired
    private StepDAO stepDAO;

    @Autowired
    private CaseService caseService;

    @Autowired
    private SuitService suitService;

    @Autowired
    private CaseVersionDAO caseVersionDAO;

    @Autowired
    private SuitVersionDAO suitVersionDAO;

    @Autowired
    private StepTransformer stepTransformer;

    /**
     *
     * @param projectId id of project
     * @param suitId id of suit
     * @param caseId id of case where to get steps
     * @return List of all StepDTOs for specified case
     */
    public List<StepDTO> getStepsByCaseId(Long projectId, Long suitId, Long caseId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseService.getCase(projectId, suitId, caseId);

        caseBelongsToSuit(caze, suit);

        return stepTransformer.toDtoList(caze.getSteps());
    }

    /**
     *
     * @param projectId id of project
     * @param suitId id of suit
     * @param caseId id of case
     * @param stepId id of step
     * @return StepDTO of specified step
     */
    public StepDTO getStep(Long projectId, Long suitId, Long caseId, Long stepId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseService.getCase(projectId, suitId, caseId);

        caseBelongsToSuit(caze, suit);

        Step step = stepDAO.findOne(stepId);
        checkNotNull(step);

        stepBelongsToCase(step, caze);

        return stepTransformer.toDto(step);
    }

    /**
     * Adds step specified in StepCreateDTO to case by id
     * @param projectId id of project
     * @param suitId id of suit
     * @param caseId id of case
     * @param stepCreateDTO DTO where step specified
     * @return id of added step
     */
    public Long addStepToCase(Long projectId, Long suitId, Long caseId, StepCreateDTO stepCreateDTO) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseService.getCase(projectId, suitId, caseId);

        caseBelongsToSuit(caze, suit);

        Step step = stepTransformer.fromDto(stepCreateDTO);

        step = stepDAO.save(step);
        caze.getSteps().add(step);

        caseVersionDAO.save(caze);
        suitVersionDAO.save(suit);

        return step.getId();
    }

    /**
     * Updates step specified in StepUpdateDTO by id
     * @param projectId id of project
     * @param suitId id of suit
     * @param caseId id of case
     * @param stepUpdateDTO DTO where step specified
     * @param stepId id of step which to update
     */
    public void updateStep(Long projectId, Long suitId, Long caseId, Long stepId,
                           StepUpdateDTO stepUpdateDTO) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseService.getCase(projectId, suitId, caseId);

        caseBelongsToSuit(caze, suit);

        Step step = stepDAO.findOne(stepId);
        checkNotNull(step);

        stepBelongsToCase(step, caze);

        step = stepTransformer.updateFromDto(stepUpdateDTO, step);

        stepDAO.save(step);

        caseVersionDAO.save(caze);
        suitVersionDAO.save(suit);
    }

    /**
     * Deletes step from case by id
     * @param projectId id of project
     * @param suitId id of suit
     * @param caseId id of case
     * @param stepId id of step to delete
     */
    public void removeStep(Long projectId, Long suitId, Long caseId, Long stepId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseService.getCase(projectId, suitId, caseId);

        caseBelongsToSuit(caze, suit);

        Step step = stepDAO.findOne(stepId);
        checkNotNull(step);

        stepBelongsToCase(step, caze);

        caze.getSteps().remove(step);
        stepDAO.delete(stepId);

        caseVersionDAO.save(caze);
        suitVersionDAO.save(suit);
    }
}
