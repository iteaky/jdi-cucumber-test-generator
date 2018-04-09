package com.epam.test_generator.dto;

import com.epam.test_generator.controllers.tag.TagTransformer;
import com.epam.test_generator.controllers.tag.response.TagDTO;
import com.epam.test_generator.entities.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TagTransformerTest {

    @InjectMocks
    private TagTransformer tagTransformer;

    private Tag tag;
    private static final String NAME = "NAME";
    private static final Long ID = 1L;

    @Before
    public void setUp() throws Exception {
        tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);
    }

    @Test
    public void toDto_Tag_Success() {
        TagDTO expectedTagDto = new TagDTO();
        expectedTagDto.setName(NAME);
        expectedTagDto.setId(ID);

        TagDTO resultTagDto = tagTransformer.toDto(tag);
        Assert.assertEquals(expectedTagDto, resultTagDto);
    }

}
