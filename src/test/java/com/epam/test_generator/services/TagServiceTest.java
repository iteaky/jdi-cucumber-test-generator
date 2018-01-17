package com.epam.test_generator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.epam.test_generator.dao.interfaces.CaseVersionDAO;
import com.epam.test_generator.dao.interfaces.TagDAO;
import com.epam.test_generator.dto.CaseDTO;
import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.dto.TagDTO;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.entities.Tag;
import com.epam.test_generator.services.exceptions.NotFoundException;
import com.epam.test_generator.transformers.CaseTransformer;
import com.epam.test_generator.transformers.SuitTransformer;
import com.epam.test_generator.transformers.TagTransformer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    private static final long SIMPLE_TAG_ID = 3L;
    private static final long SIMPLE_CASE_ID = 2L;
    private static final long SIMPLE_SUIT_ID = 1L;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private SuitTransformer suitTransformer;

    @Mock
    private TagTransformer tagTransformer;
    @Mock
    private CaseTransformer caseTransformer;

    @Mock
    private CaseVersionDAO caseVersionDAO;

    @InjectMocks
    private TagService tagService;

    @Mock
    private SuitService suitService;

    @Mock
    private CaseService caseService;

	private Set<TagDTO> expectedTagsDTOSet;

	private Tag expectedTag;
    private TagDTO expectedTagDTO;
    private Suit suit;
    private SuitDTO suitDTO;
    private Case caze;
    private CaseDTO caseDTO;

    @Before
    public void setUp() {
        final Set<Tag> expectedTagsSet = new HashSet<>();

        expectedTag = new Tag("tag1");
        expectedTagsSet.add(expectedTag);

        expectedTagDTO = new TagDTO("tag1");
        expectedTagsDTOSet = new HashSet<>();
        expectedTagsDTOSet.add(expectedTagDTO);

        final List<Case> expectedCaseList = new ArrayList<>();

        expectedCaseList.add(new Case(1L, "name1", "case1", new ArrayList<>(), 1, expectedTagsSet));
        expectedCaseList.add(new Case(2L, "name2", "case2", new ArrayList<>(), 1, expectedTagsSet));

        suit = new Suit(1L, "suit1", "desc1", expectedCaseList, 1, expectedTagsSet);
        caze = new Case(2L, "name1", "desc2", new ArrayList<>(), 1, expectedTagsSet);
    }

    @Test
    public void getAllTagsFromAllCasesInSuitTest() {
        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        //when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);

        when(tagTransformer.toDto(any(Tag.class))).thenReturn(expectedTagDTO);

        Set<TagDTO> actual = tagService.getAllTagsFromAllCasesInSuit(SIMPLE_SUIT_ID);
        assertEquals(expectedTagsDTOSet, actual);

        verify(suitService).getSuit(eq(SIMPLE_SUIT_ID));
        verify(tagTransformer, times(2)).toDto(any(Tag.class));
    }

	@Test(expected = NotFoundException.class)
	public void getAllTagsFromAllCasesInSuitTest_expectNotFoundExceptionFromSuit() {
        doThrow(NotFoundException.class).when(suitService).getSuit(SIMPLE_SUIT_ID);

        tagService.getAllTagsFromAllCasesInSuit(SIMPLE_SUIT_ID);
    }

    @Test
    public void addTagToCaseTest() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("name");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(null);
        tagDTO.setName("name");

        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        //when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        when(caseService.getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID)).thenReturn(caze);
        //when(caseTransformer.fromDto(any(CaseDTO.class))).thenReturn(caze);
        when(tagTransformer.fromDto(any(TagDTO.class))).thenReturn(tag);
        when(tagDAO.save(any(Tag.class))).thenReturn(tag);

        Long actualLong = tagService.addTagToCase(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, tagDTO);
        assertEquals(tag.getId(), actualLong);
        assertTrue(caze.getTags().contains(tag));

        verify(suitService).getSuit(eq(SIMPLE_SUIT_ID));
        verify(caseService).getCase(eq(SIMPLE_SUIT_ID),eq(SIMPLE_CASE_ID));
        verify(tagTransformer).fromDto(any(TagDTO.class));
        verify(tagDAO).save(any(Tag.class));
        verify(caseVersionDAO).save(eq(caze));
    }

	@Test(expected = NotFoundException.class)
	public void addTagToCaseTest_expectNotFoundExceptionFromSuit() {
        doThrow(NotFoundException.class).when(suitService).getSuit(SIMPLE_SUIT_ID);

        tagService.addTagToCase(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, new TagDTO());
    }

	@Test(expected = NotFoundException.class)
	public void addTagToCaseTest_expectNotFoundExceptionFromCase() {
        when(suitService.getSuit(anyLong())).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        doThrow(NotFoundException.class).when(caseService).getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID);

        tagService.addTagToCase(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, new TagDTO());
    }

    @Test
    public void updateTagTest() {
        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        when(caseService.getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID)).thenReturn(caze);
        when(caseTransformer.fromDto(any(CaseDTO.class))).thenReturn(caze);
        when(tagDAO.findOne(anyLong())).thenReturn(expectedTag);
        when(tagDAO.save(any(Tag.class))).thenReturn(expectedTag);

        tagService.updateTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID, expectedTagDTO);
        assertTrue(caze.getTags().contains(expectedTag));

        verify(suitService).getSuit(eq(SIMPLE_SUIT_ID));
        verify(caseService).getCase(eq(SIMPLE_SUIT_ID),eq(SIMPLE_CASE_ID));
        verify(tagDAO).findOne(eq(SIMPLE_TAG_ID));
        verify(tagDAO).save(any(Tag.class));
        verify(caseVersionDAO).save(eq(caze));
    }

	@Test(expected = NotFoundException.class)
	public void updateTagTest_expectNotFoundExceptionFromSuit() {
        doThrow(NotFoundException.class).when(suitService).getSuit(SIMPLE_SUIT_ID);

        tagService.updateTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID, new TagDTO());
    }

	@Test(expected = NotFoundException.class)
	public void updateTagTest_expectNotFoundExceptionFromCase() {
        when(suitService.getSuit(anyLong())).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        doThrow(NotFoundException.class).when(caseService).getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID);

        tagService.updateTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID, new TagDTO());
    }

	@Test(expected = NotFoundException.class)
	public void updateTagTest_expectNotFoundExceptionFromTag() {
        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        when(caseService.getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID)).thenReturn(caze);
        when(caseTransformer.fromDto(any(CaseDTO.class))).thenReturn(caze);
		when(tagDAO.findOne(anyLong())).thenReturn(null);

        tagService.updateTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID, new TagDTO());
    }

    @Test
    public void removeTagTest() {
        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        when(caseService.getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID)).thenReturn(caze);
        when(caseTransformer.fromDto(any(CaseDTO.class))).thenReturn(caze);
        when(tagDAO.findOne(anyLong())).thenReturn(expectedTag);

        tagService.removeTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID);

        verify(tagDAO).delete(SIMPLE_TAG_ID);
        verify(caseVersionDAO).save(eq(caze));
    }

	@Test(expected = NotFoundException.class)
	public void removeTagTest_expectNotFoundExceptionFromSuit() {
        doThrow(NotFoundException.class).when(suitService).getSuit(SIMPLE_SUIT_ID);

        tagService.removeTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID);
    }

	@Test(expected = NotFoundException.class)
	public void removeTagTest_expectNotFoundExceptionFromCase() {
        when(suitService.getSuit(anyLong())).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        doThrow(NotFoundException.class).when(caseService).getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID);

        tagService.removeTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID);
    }

	@Test(expected = NotFoundException.class)
	public void removeTagTest_expectNotFoundExceptionFromTag() {
        when(suitService.getSuit(SIMPLE_SUIT_ID)).thenReturn(suit);
        when(suitTransformer.fromDto(any(SuitDTO.class))).thenReturn(suit);
        when(caseService.getCase(SIMPLE_SUIT_ID,SIMPLE_CASE_ID)).thenReturn(caze);
        when(caseTransformer.fromDto(any(CaseDTO.class))).thenReturn(caze);
		when(tagDAO.findOne(anyLong())).thenReturn(null);

        tagService.removeTag(SIMPLE_SUIT_ID, SIMPLE_CASE_ID, SIMPLE_TAG_ID);
    }

}