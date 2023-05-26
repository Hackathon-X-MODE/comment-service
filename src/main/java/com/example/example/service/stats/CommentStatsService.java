package com.example.example.service.stats;

import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentType;
import com.example.example.model.CommentStats;
import com.example.example.repository.CommentRepository;
import com.example.example.service.CommentTypeDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentStatsService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public CommentStats getStatus() {
        return CommentStats.builder()
                .negative(this.commentRepository.countAllByMood(CommentMood.NEGATIVE))
                .neutral(this.commentRepository.countAllByMood(CommentMood.NEUTRAL))
                .positive(this.commentRepository.countAllByMood(CommentMood.POSITIVE))
                .build();
    }

    public Map<CommentType, Long> getPerCategory() {
        final var category = CommentTypeDirectory.getMapTypes().keySet();
        return Map.of();
    }
}
