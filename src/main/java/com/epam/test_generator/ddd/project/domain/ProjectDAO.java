package com.epam.test_generator.ddd.project.domain;

import com.epam.test_generator.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDAO extends JpaRepository<Project, Long> {
    List<Project> findByUsers(User user);

    Project findByJiraKey(String jiraKey);
}
