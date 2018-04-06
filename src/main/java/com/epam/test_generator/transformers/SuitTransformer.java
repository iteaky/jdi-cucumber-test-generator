package com.epam.test_generator.transformers;

import com.epam.test_generator.dto.SuitCreateDTO;
import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.entities.Suit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuitTransformer {

    public Suit fromDto(SuitCreateDTO suitCreateDTO) {
        Suit suit = new Suit();
        suit.setId(suitCreateDTO.getId());
        suit.setName(suitCreateDTO.getName());
        suit.setDescription(suitCreateDTO.getDescription());
        suit.setPriority(suitCreateDTO.getPriority());
        return suit;
    }

    public Suit fromDto(SuitDTO suitDTO) {
        return null;
    }

    public SuitDTO toDto(Suit suit) {
        SuitDTO suitDTO = new SuitDTO();
        suitDTO.setId(suit.getId());
        suitDTO.setName(suit.getName());
        suitDTO.setDescription(suit.getDescription());
        suitDTO.setPriority(suit.getPriority());
        return suitDTO;
    }

    public List<SuitDTO> toDtoList(List<Suit> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void mapDTOToEntity(SuitDTO simpleSuitDTO, Suit suit) {

    }
}
