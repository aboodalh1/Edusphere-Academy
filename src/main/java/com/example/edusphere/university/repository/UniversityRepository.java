package com.example.edusphere.university.repository;

import com.example.edusphere.university.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findUniversitiesByName(String universityName);

    Optional<University> findUniversitiesById(Long universityId);

}
