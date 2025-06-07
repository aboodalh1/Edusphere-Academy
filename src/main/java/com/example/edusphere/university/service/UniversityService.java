package com.example.edusphere.university.service;
import com.example.edusphere.university.model.University;
import com.example.edusphere.university.repository.UniversityRepository;
import com.example.edusphere.university.request.UniversiyRequest;
import com.example.edusphere.university.response.UniversityResponse;
import com.example.edusphere.util.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<UniversityResponse> getAllUniversities() {
            return universityRepository.findAll().stream()
                    .map(this::mapToUniversityResponse)
                    .collect(Collectors.toList());
    }

    private UniversityResponse mapToUniversityResponse(University university) {
        UniversityResponse response = new UniversityResponse();
        response.setId(university.getId());
        response.setName(university.getName());
        return response;
    }

    public UniversityResponse getUniversityById(Long id) {
        University university= universityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("University not found with id: " + id)
        );
        UniversityResponse response = new UniversityResponse();
        response.setId(university.getId());
        response.setName(university.getName());
        return response;
    }

    public UniversityResponse createUniversity(UniversiyRequest request) {
        University university = new University();
        university.setName(request.getName());
        universityRepository.save(university);
        UniversityResponse response = new UniversityResponse();
        response.setId(university.getId());
        response.setName(university.getName());
        return response;
    }

    public Optional<University> updateUniversity(Long id, UniversiyRequest request) {
        University updatedUniversity = new University();
        return universityRepository.findById(id).map(university -> {
            university.setName(request.getName());
            return universityRepository.save(university);
        });
    }

    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }
}