package com.example.example.controller;

import com.example.example.domain.enumerated.CommentType;
import com.example.example.model.CommentStats;
import com.example.example.service.stats.CommentStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class CommentStatsController {

    private final CommentStatsService commentStatsService;

    @GetMapping("perCategory")
    public Map<CommentType, Long> getPerCategory() {
        return this.commentStatsService.getPerCategory();
    }

    @GetMapping("status")
    public CommentStats getStatus() {
        return this.commentStatsService.getStatus();
    }
}
