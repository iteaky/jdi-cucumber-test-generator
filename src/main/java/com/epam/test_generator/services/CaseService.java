package com.epam.test_generator.services;

import static com.epam.test_generator.services.utils.UtilsService.caseBelongsToSuit;
import static com.epam.test_generator.services.utils.UtilsService.checkNotNull;

import com.epam.test_generator.controllers.caze.request.CreateCaseDTO;
import com.epam.test_generator.controllers.caze.request.EditCaseDTO;
import com.epam.test_generator.controllers.caze.request.UpdateCaseDTO;
import com.epam.test_generator.controllers.caze.response.CaseWithFailedStepsDTO;
import com.epam.test_generator.dao.interfaces.CaseDAO;
import com.epam.test_generator.dao.interfaces.CaseVersionDAO;
import com.epam.test_generator.dao.interfaces.RemovedIssueDAO;
import com.epam.test_generator.dao.interfaces.SuitVersionDAO;
import com.epam.test_generator.controllers.caze.response.CaseDTO;
import com.epam.test_generator.dto.CaseVersionDTO;
import com.epam.test_generator.dto.StepDTO;
import com.epam.test_generator.dto.SuitUpdateDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Event;
import com.epam.test_generator.entities.RemovedIssue;
import com.epam.test_generator.entities.Status;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.pojo.CaseVersion;
import com.epam.test_generator.services.exceptions.BadRequestException;
import com.epam.test_generator.state.machine.StateMachineAdapter;
import com.epam.test_generator.controllers.caze.CaseDTOsTransformer;
import com.epam.test_generator.transformers.CaseVersionTransformer;
import com.epam.test_generator.transformers.TagTransformer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Transactional
@Service
public class CaseService {

    @Autowired
    private CaseDTOsTransformer caseDTOsTransformer;

    @Autowired
    private Validator validator;

    @Autowired
    private CaseVersionTransformer caseVersionTransformer;

    @Autowired
    private CaseDAO caseDAO;

    @Autowired
    private SuitService suitService;

    @Autowired
    private CaseVersionDAO caseVersionDAO;

    @Autowired
    private SuitVersionDAO suitVersionDAO;

    @Autowired
    private StateMachineAdapter stateMachineAdapter;

    @Autowired
    private TagTransformer tagTransformer;

    @Autowired
    private RemovedIssueDAO removedIssueDAO;


    public List<Case> getCases() {
        return caseDAO.findAll();
    }

    public Case getCase(Long projectId, Long suitId, Long caseId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDAO.findOne(caseId);
        checkNotNull(caze);

        caseBelongsToSuit(caze, suit);

        return caze;
    }

    public CaseDTO getCaseDTO(Long projectId, Long suitId, Long caseId) {
        return caseDTOsTransformer.toDto(getCase(projectId, suitId, caseId));
    }

    /**
     * Adds case to existing suit
     *
     * @param projectId id of project where to add case
     * @param suitId    id of suit where to add case
     * @param createCaseDTO   case to add
     * @return {@link CaseDTO} of added case to suit
     */
    public CaseDTO addCaseToSuit(Long projectId, Long suitId, @Valid CreateCaseDTO createCaseDTO) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDTOsTransformer.fromDto(createCaseDTO);

        caze.setJiraParentKey(suit.getJiraKey());
        caze.setJiraProjectKey(suit.getJiraProjectKey());
        Date currentTime = Calendar.getInstance().getTime();
        caze.setCreationDate(currentTime);
        caze.setUpdateDate(currentTime);
        caze.setLastModifiedDate(LocalDateTime.now());
        caze = caseDAO.save(caze);

        suit.getCases().add(caze);

        caseVersionDAO.save(caze);
        suitVersionDAO.save(suit);

        CaseDTO caseDTO = caseDTOsTransformer.toDto(caze);

        return caseDTO;
    }

    /**
     * Adds case to existing suit using EditCaseDTO
     * @param projectId id of project where to add case
     * @param suitId id of suit where to add case
     * @param updateCaseDTO case to add
     * @return {@link CaseDTO} of added case to suit
     */
    @Deprecated
    public CaseDTO addCaseToSuit(Long projectId, Long suitId, EditCaseDTO updateCaseDTO)
        throws MethodArgumentNotValidException {
        CreateCaseDTO caseDTO = new CreateCaseDTO(updateCaseDTO.getName(),
            updateCaseDTO.getDescription(), updateCaseDTO.getPriority(),
                updateCaseDTO.getComment(),  new HashSet<>());

        BeanPropertyBindingResult beanPropertyBindingResult =
            new BeanPropertyBindingResult(caseDTO, CaseDTO.class.getSimpleName());

        validator.validate(caseDTO, beanPropertyBindingResult);
        if (beanPropertyBindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, beanPropertyBindingResult);
        }
        return addCaseToSuit(projectId, suitId, caseDTO);
    }

    /**
     * Updates case info to info specified in EditCaseDTO
     * @param projectId id of project where to update case
     * @param suitId id of suit where to update case
     * @param caseId id of case which to update
     * @param updateCaseDTO info to update
     * @return {@link SuitUpdateDTO} which contains {@link CaseDTO} and {@link List<Long>}
     * (in fact id of {@link StepDTO} with FAILED {@link Status} which belong this suit)
     */
    public CaseWithFailedStepsDTO updateCase(Long projectId, Long suitId, Long caseId, UpdateCaseDTO updateCaseDTO) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDAO.findOne(caseId);
        checkNotNull(caze);

        caseBelongsToSuit(caze, suit);

//        TODO: remove this list from CaseUpdateDTO
        final List<Long> failedStepIds = Collections.emptyList();
        caze.setUpdateDate(Calendar.getInstance().getTime());
        if (updateCaseDTO.getTags() != null) {
            caze.setTags(new HashSet<>(tagTransformer.fromDtoList(updateCaseDTO.getTags())));
        }
        caze.setDescription(updateCaseDTO.getDescription());
        caze.setPriority(updateCaseDTO.getPriority());
        caze.setStatus(updateCaseDTO.getStatus());
        caze.setName(updateCaseDTO.getName());
        caze.setLastModifiedDate(LocalDateTime.now());


        caze = caseDAO.save(caze);

        CaseDTO updatedCaseDTO = caseDTOsTransformer.toDto(caze);
        CaseWithFailedStepsDTO caseWithFailedStepsDTOwithFailedStepIds =
            new CaseWithFailedStepsDTO(updatedCaseDTO, failedStepIds);

        caseVersionDAO.save(caze);
        suitVersionDAO.save(suit);
        return caseWithFailedStepsDTOwithFailedStepIds;
    }

    /**
     * Updates all cases to specified in list of updateCaseDTOs
     * @param projectId id of project where to update cases
     * @param suitId id of suit where to update cases
     * @param editCaseDTOS list of cases to update
     * @return list {@link CaseDTO} with all changed cases
     * @throws MethodArgumentNotValidException
     */
    @Deprecated
    public List<CaseDTO> updateCases(Long projectId, long suitId, List<EditCaseDTO> editCaseDTOS)
            throws MethodArgumentNotValidException {
        List<CaseDTO> updatedCases = new ArrayList<>();
        for (EditCaseDTO editCaseDTO : editCaseDTOS) {
            switch (editCaseDTO.getAction()) {
                case DELETE:
                    if (editCaseDTO.getId() == null) {
                        throw new BadRequestException("No id in caze to remove");
                    }
                    updatedCases.add(removeCase(projectId, suitId, editCaseDTO.getId()));
                    break;
                case CREATE:
                    updatedCases.add(addCaseToSuit(projectId, suitId, editCaseDTO));
                    break;
                case UPDATE:
                    if (editCaseDTO.getId() == null) {
                        throw new BadRequestException("No id in caze to update");
                    }
                    UpdateCaseDTO updateCaseDTO = new UpdateCaseDTO(
                            editCaseDTO.getDescription(),
                            editCaseDTO.getName(),
                            editCaseDTO.getPriority(),
                            editCaseDTO.getStatus(),
                            editCaseDTO.getSteps(),
                            editCaseDTO.getComment()
                    );
                    CaseDTO updatedCaseDTO = updateCase(projectId, suitId, editCaseDTO.getId(), updateCaseDTO)
                            .getUpdatedCaseDto();
                    updatedCases.add(updatedCaseDTO);
                    break;
                default:
                    throw new BadRequestException("Wrong action argument");
            }
        }

        return updatedCases;
    }

    /**
     * Deletes one case by id
     * @param projectId id of project where to delete case
     * @param suitId id of suit where to delete case
     * @param caseId id of case to delete
     * @return removed {@link CaseDTO}
     */
    @Deprecated
    public CaseDTO removeCase(Long projectId, Long suitId, Long caseId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDAO.findOne(caseId);
        checkNotNull(caze);

        caseBelongsToSuit(caze, suit);

        saveIssueToDeleteInJira(caze);

        suit.getCases().remove(caze);
        caseDAO.delete(caseId);

        caseVersionDAO.delete(caze);
        suitVersionDAO.save(suit);

        CaseDTO removedCaseDTO = caseDTOsTransformer.toDto(caze);

        return removedCaseDTO;
    }

    /**
     * Deletes multiple cases by ids
     * @param projectId id of project where to delete cases
     * @param suitId id of suit where to delete cases
     * @param caseIds list of cases ids to delete
     * @return removed list of {@link CaseDTO}
     */
    public List<CaseDTO> removeCases(Long projectId, Long suitId, List<Long> caseIds) {
        Suit suit = suitService.getSuit(projectId, suitId);

        List<Case> removedCases = new ArrayList<>();

        suit.getCases().stream()
            .filter(caze -> caseIds.stream()
                .anyMatch(id -> id.equals(caze.getId())))
            .forEach(caze -> {
                caseDAO.delete(caze.getId());

                caseVersionDAO.delete(caze);

                removedCases.add(caze);
                saveIssueToDeleteInJira(caze);

            });

        removedCases.forEach(caze -> suit.getCases().remove(caze));
        suitVersionDAO.save(suit);

        List<CaseDTO> removedCasesDTO = caseDTOsTransformer.toDtoList(removedCases);
        return removedCasesDTO;
    }

    private void saveIssueToDeleteInJira(Case caze) {
        if (caze.getJiraKey() != null) {
            removedIssueDAO.save(new RemovedIssue(caze.getJiraKey()));
        }
    }

    public List<CaseVersionDTO> getCaseVersions(Long projectId, Long suitId, Long caseId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDAO.findOne(caseId);
        checkNotNull(caze);

        caseBelongsToSuit(caze, suit);

        List<CaseVersion> caseVersions = caseVersionDAO.findAll(caseId);

        return caseVersionTransformer.toDtoList(caseVersions);
    }

    /**
     * Restores case to previous version by caseId and commitId
     * @param projectId id of project where to restore case
     * @param suitId id of suit where to restore case
     * @param caseId id of case to restore
     * @param commitId id of commit to restore version
     * @return {@link CaseDTO} of restored by the commitId case
     */
    public CaseDTO restoreCase(Long projectId, Long suitId, Long caseId, String commitId) {
        Suit suit = suitService.getSuit(projectId, suitId);

        Case caze = caseDAO.findOne(caseId);
        checkNotNull(caze);

        caseBelongsToSuit(caze, suit);

        Case caseToRestore = caseVersionDAO.findByCommitId(caseId, commitId);
        checkNotNull(caseToRestore);

        Case restoredCase = caseDAO.save(caseToRestore);
        caseVersionDAO.save(caseToRestore);
        suitVersionDAO.save(suit);

        CaseDTO restoredCaseDTO = caseDTOsTransformer.toDto(restoredCase);

        return restoredCaseDTO;
    }

    public Status performEvent(Long projectId, Long suitId, Long caseId, Event event)
        throws Exception {
        Case cs = getCase(projectId, suitId, caseId);
        StateMachine<Status, Event> stateMachine = stateMachineAdapter.restore(cs);
        if (stateMachine.sendEvent(event)) {
            stateMachineAdapter.persist(stateMachine, cs);
            caseDAO.save(cs);

            caseVersionDAO.save(cs);
            suitVersionDAO.save(suitService.getSuit(projectId, suitId));

            return cs.getStatus();
        } else {
            throw new BadRequestException();
        }
    }
}