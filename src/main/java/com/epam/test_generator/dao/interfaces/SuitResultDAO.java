package com.epam.test_generator.dao.interfaces;

import com.epam.test_generator.entities.results.SuitResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuitResultDAO extends JpaRepository<SuitResult, Long> {

}
