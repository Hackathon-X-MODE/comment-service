package com.example.example.service.export;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.exports.config.ExportHeaderMapping;
import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentType;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentFilter;
import com.example.example.service.CommentService;
import com.example.example.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportService {
    private static final int PER_PAGE = 50_000;
    private static final String EXCEL_EXTENSION = ".xlsx";

    private final CommentService commentService;

    private final CommentExportService commentExportService;

    private final FileStorageService fileStorageService;

    @Async
    public void export(UUID out) throws IOException, InterruptedException {
        log.info("Export started...");
        final var start = Instant.now();
        this.generate(out);
        log.info("Wow took just {} seconds, look's good. oh and export finished!", Duration.between(start, Instant.now()).toSeconds());
    }


    public void export(OutputStream outputStream, UUID id) throws IOException {
        final var path = this.commentExportService.getReport(id);
        Files.copy(new File(path).toPath(), outputStream);
    }

    private void generate(UUID out) throws IOException, InterruptedException {
        final var files = new ArrayList<File>();
        final var tempDirPath = Files.createTempDirectory("tmpDirPrefix").toFile().getAbsolutePath();
        var page = 0;
        do {

            final var pageable = Pageable.ofSize(PER_PAGE).withPage(page);
            pageable.getSortOr(Sort.by("id").ascending());

            final var result = this.commentService.filterWithPage(CommentFilter.builder().build(),
                    pageable
            );

            Windmill
                    .export(result.stream().toList())
                    .withHeaderMapping(
                            new ExportHeaderMapping<CommentDto>()
                                    .add("id", CommentDto::getId)
                                    .add("create_date", CommentDto::getCreateDate)
                                    .add("source", CommentDto::getSource)
                                    .add("rate", CommentDto::getRate)
                                    .add("mood", CommentDto::getMood)
                                    .add("comment", CommentDto::getComment)
                                    .add("type", dto -> this.getType(dto.getCommentTypes()))
                                    .add("sub-type", dto -> this.getSubType(dto.getCommentTypes()))
                                    .add("need-resolve", dto -> String.valueOf(dto.getMood() == CommentMood.NEGATIVE || dto.getRate() <= 3))
                    )
                    .asExcel()
                    .writeTo(new FileOutputStream(tempDirPath + "/" + page + EXCEL_EXTENSION));

            files.add(new File(tempDirPath + "/" + page + EXCEL_EXTENSION));

            if (!result.hasNext()) {
                break;
            }
            page++;
            log.info("And another page...");

        } while (true);

        log.info("zipping {} files", files.size());
        for (File file : files) {
            log.info(file.getAbsolutePath());
        }

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(tempDirPath + "/result.zip"))) {
            for (final var file : files) {
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                Files.copy(file.toPath(), zipOut);
                file.delete();
            }
        }


        this.commentExportService.update(out,
                this.fileStorageService.upload("comments/"+out+".zip", new FileInputStream(tempDirPath + "/result.zip"))
        );
    }


    private String getType(Map<CommentType, Set<CommentType>> commentTypeSet) {
        return this.typeToString(commentTypeSet.keySet());
    }

    private String getSubType(Map<CommentType, Set<CommentType>> commentTypeSet) {
        return this.typeToString(commentTypeSet.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()));
    }


    private String typeToString(Set<CommentType> commentTypeSet) {
        return commentTypeSet.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }
}
