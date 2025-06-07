package com.example.edusphere.student.controller;

import com.example.edusphere.student.request.LoginRequest;
import com.example.edusphere.student.request.RegisterStudentRequest;
import com.example.edusphere.student.response.StudentAuthResponse;
import com.example.edusphere.student.service.StudentService;
import com.example.edusphere.university.request.RegenerateCodeRequest;
import com.example.edusphere.util.exception.APIException;
import com.example.edusphere.util.response.MyAPIResponse;
import com.example.edusphere.verification.request.VerificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/auth")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Register new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterStudentRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"firstName\": \"abdAllah\", \"lastName\": \"Alharisi\", \"phoneNumber\": \"+963930704986\", \"birthDate\": \"2004-05-02\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registration successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StudentAuthResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                              "id": "1",
                                              "firstName": "AbdAllah",
                                              "lastName": "Alharisi",
                                              "email": "abd@gmail.com",
                                              "token": "teFFetgwQQRrfswadfeSKIg..."
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email already exists",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIException.class),
                                    examples = @ExampleObject("""
                                            {
                                              "message": "Email already exists",
                                              "status": "BAD_REQUEST",
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<MyAPIResponse<?>> register(@RequestBody @Valid RegisterStudentRequest request) {

        return ResponseEntity.ok(new MyAPIResponse<>(true, 200, studentService.studentRegister(request)));


    }

    @Operation(summary = "Verify Code",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterStudentRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"otp\": \"code..1234\", \"phoneNumber\": \"+963930704986\"}"
                            )
                    )
            )
    )
    @PostMapping("/verifyCode")
    public ResponseEntity<MyAPIResponse<?>> verifyCode(@RequestBody @Valid VerificationRequest request) {

        return ResponseEntity.ok(new MyAPIResponse<>(true, 200, studentService.verifyCode(request)));


    }

    @Operation(summary = "Resend Code",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterStudentRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"phoneNumber\": \"+963930704986\"}"
                            )
                    )
            )
    )
    @PostMapping("/resendCode")
    public ResponseEntity<MyAPIResponse<?>> resendCode(@RequestBody @Valid RegenerateCodeRequest request) {

        return ResponseEntity.ok(new MyAPIResponse<>(true, 200, studentService.regenerateCode(request)));

    }

    @Operation(summary = "Login to Student Account",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"phoneNumber\": \"+963930704986\"}"
                            )
                    )))
    @PostMapping("/login")
    public ResponseEntity<MyAPIResponse<?>> login(@RequestBody RegenerateCodeRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(new MyAPIResponse<>(true, 200, studentService.regenerateCode(request)));
    }

    @Operation(summary = "Login Verify OTP",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"phoneNumber\": \"+963930704986\", \"OTP\": \"code..123\"}"
                            )
                    )))
    @PostMapping("/verifyLogin")
    public ResponseEntity<MyAPIResponse<?>> verifyLogin(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(new MyAPIResponse<>(true, 200, studentService.studentLogin(request, httpServletRequest)));
    }

}
