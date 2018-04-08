package com.epam.test_generator.transformers;

import com.epam.test_generator.dto.SuitCreateDTO;
import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.dto.SuitUpdateDTO;
import com.epam.test_generator.dto.TagDTO;
import com.epam.test_generator.entities.Suit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SuitTransformer {

    @Autowired
    private TagTransformer tagTransformer;

    public Suit fromDto(SuitCreateDTO suitCreateDTO) {
        Suit suit = new Suit();
        suit.setId(suitCreateDTO.getId());
        suit.setName(suitCreateDTO.getName());
        suit.setDescription(suitCreateDTO.getDescription());
        suit.setPriority(suitCreateDTO.getPriority());
        suit.setJiraProjectKey(suitCreateDTO.getJiraProjectKey());
        return suit;
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

    public void mapDTOToEntity(SuitUpdateDTO suitUpdateDTO, Suit suit) {
        if (suitUpdateDTO.getName() != null) {
            suit.setName(suitUpdateDTO.getName());
        }
        if (suitUpdateDTO.getDescription() != null) {
            suit.setDescription(suitUpdateDTO.getDescription());
        }
        if (suitUpdateDTO.getPriority() != null) {
            suit.setPriority(suitUpdateDTO.getPriority());
        }
        if (suitUpdateDTO.getTags().size() != 0) {
            List<TagDTO> tagDTOs = new ArrayList<>(suitUpdateDTO.getTags());
            suit.setTags(new HashSet<>(tagTransformer.fromDtoList(tagDTOs)));
        }
    }
}
