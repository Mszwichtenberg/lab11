package com.westeros.diagnostics.controllers;

import com.westeros.diagnostics.runners.IRunDiagnoses;
import com.westeros.diagnostics.services.contract.Diagnostics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("diagnostics")
@RequiredArgsConstructor
public class DiagnosticsController {

    private final IRunDiagnoses diagnosticsRunner;

    @GetMapping
    public ResponseEntity<String> checkStatus(){
        return ResponseEntity.ok("ALIVE");
    }

    @GetMapping("/check")
    public ResponseEntity<List<Diagnostics>> runDiagnostics() {
        return ResponseEntity.ok(diagnosticsRunner.runAll());
    }
}
