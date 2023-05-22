package com.example.example.service.export;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.exports.config.ExportHeaderMapping;
import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentType;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentFilter;
import com.example.example.service.CommentService;
import com.example.example.service.CommentTypeDirectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

    public void export(OutputStream out) throws IOException {
        log.info("Export started...");
        final var start = Instant.now();
        this.generate(out);
        log.info("Wow took just {} seconds, look's good. oh and export finished!", Duration.between(start, Instant.now()).toSeconds());

    }

    private void generate(OutputStream out) throws IOException {
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
                                    //TODO
                                    .add("mood", CommentDto::getMood)
                                    .add("type", dto -> this.getType(dto.getCommentTypesSet()))
                                    .add("sub-type", dto -> this.getSubType(dto.getCommentTypesSet()))
                                    .add("rate", CommentDto::getRate)
                                    .add("comment", CommentDto::getComment)
                                    .add("need-resolve", dto -> String.valueOf(dto.getMood() == CommentMood.NEGATIVE || dto.getRate() <= 3))
                    )
                    .asExcel()
                    .writeTo(new FileOutputStream(tempDirPath + "/" + page + EXCEL_EXTENSION));

            files.add(new File(tempDirPath + "/" + page + EXCEL_EXTENSION));

            if (!result.hasNext()) {
                break;
            }
            page++;

        } while (true);

        log.info("zipping...");


        var zipOutputStream = new ZipOutputStream(out);

        for (File file : files) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
            file.delete();
        }

        zipOutputStream.close();
    }


    private String getType(Set<CommentType> commentTypeSet) {
        return this.getAnyType(commentTypeSet, CommentTypeDirectory::isFirstLevel);
    }

    private String getSubType(Set<CommentType> commentTypeSet) {
        return this.getAnyType(commentTypeSet, CommentTypeDirectory::isSecondLevel);
    }


    private String getAnyType(Set<CommentType> commentTypeSet, Function<CommentType, Boolean> function) {
        return commentTypeSet.stream().filter(function::apply)
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }
}
