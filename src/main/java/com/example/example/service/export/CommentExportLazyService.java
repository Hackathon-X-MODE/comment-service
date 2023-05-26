package com.example.example.service.export;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentExportLazyService {


    private final CommentExportService commentExportService;

    private final ExportService exportService;



    public UUID start() throws IOException {
       final var id =  this.commentExportService.create();
       this.exportService.export(id);
       return id;
    }

}
