package com.example.edusphere.wallet.controller;

import com.example.edusphere.wallet.Wallet;
import com.example.edusphere.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@Tag(name = "Wallet Controller", description = "Manage student wallets")
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
    public ResponseEntity<Wallet> createWallet(@PathVariable UUID studentId) {
        Wallet wallet = walletService.createWalletForStudent(studentId);
        return ResponseEntity.ok(wallet);
    }


    @Operation(
            summary = "Get wallet by student ID",
            description = "Fetches the wallet associated with the given student UUID."
    )
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Wallet> getWalletByStudent(@PathVariable UUID studentId) {
        Wallet wallet = walletService.getWalletByStudentId(studentId);
        return ResponseEntity.ok(wallet);
    }
}
