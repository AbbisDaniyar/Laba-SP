package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CsvImportResult {
    private int successCount;
    private int failedCount;
    private List<String> errors = new ArrayList<>();

    public CsvImportResult(int successCount, int failedCount, List<String> errors) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.errors = errors != null ? errors : new ArrayList<>();
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }
}