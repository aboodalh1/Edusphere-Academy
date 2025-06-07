package com.example.edusphere.util.mapper;

import com.example.edusphere.student.model.Student;
import com.example.edusphere.student.request.RegisterStudentRequest;
import com.example.edusphere.student.response.StudentAuthResponse;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface ClassMapper {

    ClassMapper INSTANCE = Mappers.getMapper( ClassMapper.class );

    StudentAuthResponse entityToDto(Student student);
    Student studentDtoToEntity(RegisterStudentRequest request);

}