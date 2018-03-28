package com.epam.test_generator.dao.interfaces;

import com.epam.test_generator.DatabaseConfigForTests;
import com.epam.test_generator.entities.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfigForTests.class})
@Transactional
public class TagDAOTest {

    @Autowired
    TagDAO tagDAO;

    @Test
    public void createAndRetrieve_Tag_Success() {
        Tag originalTag = retrieveTag();

        long id = tagDAO.save(originalTag).getId();

        Tag newTag = retrieveTag();
        newTag.setId(id);

        Assert.assertEquals(newTag, tagDAO.findById(id).get());
    }

    @Test
    public void remove_Tag_Success() {
        Tag originalTag = retrieveTag();

        long id = tagDAO.save(originalTag).getId();
        tagDAO.delete(originalTag);

        Assert.assertTrue(!tagDAO.existsById(id));
    }

    @Test
    public void removeById_Tag_Success() {
        Tag originalTag = retrieveTag();

        long id = tagDAO.save(originalTag).getId();
        tagDAO.deleteById(id);

        Assert.assertTrue(!tagDAO.existsById(id));
    }

    @Test
    public void addList_Tags_Success() {
        List<Tag> tags = retrieveTagList();

        List<Long> ids = tagDAO.saveAll(tags).stream().map(Tag::getId).collect(Collectors.toList());

        List<Tag> newTags = retrieveTagList();
        newTags.get(0).setId(ids.get(0));
        newTags.get(1).setId(ids.get(1));
        newTags.get(2).setId(ids.get(2));

        Assert.assertTrue(newTags.equals(tagDAO.findAll()));
    }

    @Test
    public void removeList_Tags_Success() {
        List<Tag> tags = retrieveTagList();

        tagDAO.saveAll(tags);

        tagDAO.deleteAll(tags);

        Assert.assertTrue(tagDAO.findAll().isEmpty());
    }

    private Tag retrieveTag() {
        return new Tag("tag1");
    }

    private List<Tag> retrieveTagList() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        tags.add(new Tag("tag3"));

        return tags;
    }
}
