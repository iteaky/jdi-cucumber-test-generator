package com.epam.test_generator.services;

import com.epam.test_generator.dao.interfaces.CaseDAO;
import com.epam.test_generator.dao.interfaces.SuitDAO;
import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.transformers.CaseTransformer;
import com.epam.test_generator.transformers.SuitTransformer;
import com.epam.test_generator.file_generator.FileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class SuitService {

    @Autowired
    private FileGenerator fileGenerator;

    @Autowired
    private SuitDAO suitDAO;

    @Autowired
    private CaseDAO caseDAO;

    @Autowired
    private SuitTransformer suitTransformer;

    @Autowired
    private CaseTransformer caseTransformer;

    public List<SuitDTO> getSuits() {
        return suitDAO.findAll().stream().map(suit -> suitTransformer.toDto(suit))
                .collect(Collectors.toList());
    }

    public SuitDTO getSuit(long suitId) {
        if (suitDAO.getOne(suitId) == null) {
            return null;
        }

        return suitTransformer.toDto(suitDAO.getOne(suitId));
    }

    public Long addSuit(SuitDTO suitDTO) {
        Suit suit = suitDAO.save(suitTransformer.fromDto(suitDTO));

        return suit.getId();
    }

    public void updateSuit(long suitId, SuitDTO suitDTO) {
        Suit suit = suitDAO.getOne(suitId);
        suitTransformer.mapDTOToEntity(suitDTO, suit);

        suitDAO.save(suit);
    }

    public void removeSuit(long suitId) {
        suitDAO.delete(suitId);
    }

    public String generateFile(Long suitId, List<Long> caseIds) throws IOException {
        Suit suit = suitDAO.getOne(suitId);
        List<Case> cases = caseIds.stream().map(id -> caseDAO.getOne(id)).collect(Collectors.toList());

        return fileGenerator.generate(suitTransformer.toDto(suit), caseTransformer.toDtoList(cases));
    }
}