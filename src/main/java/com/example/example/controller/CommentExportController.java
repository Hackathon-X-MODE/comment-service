package com.example.example.controller;


import com.example.example.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentExportController {

    private final ExportService exportService;


    @GetMapping(value = "/export", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> export() {

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"export-comments-x-mode-team.zip\"")
                .body(this.exportService::export);

    }
}
