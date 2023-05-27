package com.example.example.service.export;

import com.example.example.domain.CommentExportEntity;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.exception.EntityNotReadyException;
import com.example.example.repository.CommentExportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentExportService {

    private final CommentExportRepository commentExportRepository;

    @Transactional
    public UUID create() {
        return this.commentExportRepository.saveAndFlush(
                new CommentExportEntity()
        ).getId();
    }

    @Transactional(readOnly = true)
    public String getReport(UUID exportId) {
        final var entity = this.get(exportId);

        if (entity.getPath() == null) {
            throw new EntityNotReadyException("Report " + exportId + " not ready yet");
        }

        return entity.getPath();
    }


    @Transactional
    public void update(UUID exportId, String path) {
        this.get(exportId).setPath(path);
    }

    @Transactional(readOnly = true)
    public CommentExportEntity get(UUID exportId) {
        return this.commentExportRepository.findById(exportId).orElseThrow(
                () -> new EntityNotFoundException("Can't find export by " + exportId)
        );
    }

    @Transactional(readOnly = true)
    public List<CommentExportEntity> getAll() {
        return this.commentExportRepository.findAll(
                Sort.by("createDate").descending()
        );
    }
}
