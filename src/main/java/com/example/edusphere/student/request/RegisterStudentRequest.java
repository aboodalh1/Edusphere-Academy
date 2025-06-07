package com.example.edusphere.student.request;

import com.example.edusphere.util.annotation.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterStudentRequest {

    @NotBlank(message = "Name is Required")
    @Size(min = 3,max = 50,message = "Name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name couldn't be blank")
    private String lastName;

    @NotBlank(message = "Phone Number Required")
    @ValidPhoneNumber
    private String phoneNumber;

    @NotNull(message = "Birth date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate( LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank(message = "Name is Required") @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Name is Required") @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "last name couldn't be blank") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "last name couldn't be blank") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Phone Number Required") String getPhoneNumber() {
        return phoneNumber;
    }


}
