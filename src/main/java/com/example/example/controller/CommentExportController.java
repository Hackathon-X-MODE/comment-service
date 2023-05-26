package com.example.example.controller;


import com.example.example.domain.CommentExportEntity;
import com.example.example.service.export.CommentExportLazyService;
import com.example.example.service.export.CommentExportService;
import com.example.example.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentExportController {

    private final ExportService exportService;
    private final CommentExportService commentExportService;
    private final CommentExportLazyService commentExportLazyService;


    @PostMapping(value = "/exports")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UUID export() throws IOException {
        return this.commentExportLazyService.start();
    }


    @GetMapping(value = "/exports")
    public List<CommentExportEntity> getAll() {
        return this.commentExportService.getAll();
    }


    @GetMapping(value = "/exports/{id}")
    public ResponseEntity<StreamingResponseBody> export(@PathVariable("id") UUID id) {

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"export-comments-x-mode-team.zip\"")
                .body(out -> this.exportService.export(out, id));

    }

}
