package com.example.edusphere.wallet.controller;

import com.example.edusphere.wallet.Wallet;
import com.example.edusphere.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@Tag(name = "Wallet Controller", description = "Manage student wallets")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(
            summary = "Create wallet for a student",
            description = "Creates a new wallet for the student with the specified UUID. Fails if wallet already exists."
    )
    @PostMapping("/create/{studentId}")
    public ResponseEntity<Wallet> createWallet(@RequestHeader("Authorization") String authHeader) {
        Wallet wallet = walletService.createWalletForStudent(authHeader);
        return ResponseEntity.ok(wallet);
    }


    @Operation(
            summary = "Get wallet by student ID",
            description = "Fetches the wallet associated with the given student UUID."
    )
    @GetMapping("/student")
    public ResponseEntity<Wallet> getWalletByStudent(@RequestHeader("Authorization") String authHeader) {
        Wallet wallet = walletService.getWalletByStudentId(authHeader);
        return ResponseEntity.ok(wallet);
    }
}
