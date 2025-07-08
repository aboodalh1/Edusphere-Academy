package com.example.edusphere.college.service;

import com.example.edusphere.college.model.College;
import com.example.edusphere.college.repository.CollegeRepository;
import com.example.edusphere.college.request.CollegeRequest;
import com.example.edusphere.university.model.University;
import com.example.edusphere.university.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final UniversityRepository universityRepository;

    public CollegeService(CollegeRepository collegeRepository, UniversityRepository universityRepository) {
        this.collegeRepository = collegeRepository;
        this.universityRepository = universityRepository;
    }

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    public Optional<College> getCollegeById(Long id) {
        return collegeRepository.findById(id);
    }

    public Optional<College> createCollege(CollegeRequest request) {
        return universityRepository.findById(request.getUniversityId()).map(university -> {
            College college = new College();
            college.setName(request.getCollegeName());
            college.setUniversity(university);
            return collegeRepository.save(college);
        });
    }

    public Optional<College> updateCollege(Long id, CollegeRequest updatedCollege) {
        return collegeRepository.findById(id).flatMap(existingCollege -> {
            Optional<University> university = universityRepository.findById(updatedCollege.getUniversityId());
            if (university.isPresent()) {
                existingCollege.setName(updatedCollege.getCollegeName());
                existingCollege.setUniversity(university.get());
                return Optional.of(collegeRepository.save(existingCollege));
            } else {
                return Optional.empty();
            }
        });
    }

    public void deleteCollege(Long id) {
        collegeRepository.deleteById(id);
    }
}