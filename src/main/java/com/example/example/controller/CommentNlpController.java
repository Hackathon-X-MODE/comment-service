package com.example.example.controller;

import com.example.example.model.CommentNlpDto;
import com.example.example.service.CommentUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("external")
@RequiredArgsConstructor
public class CommentNlpController {

    private final CommentUpdateService commentUpdateService;

    @PatchMapping("{commentId}")
    public void update(
            @PathVariable("commentId") UUID commentId,
            @RequestBody CommentNlpDto commentNlpDto) {
        this.commentUpdateService.updateViaNlp(commentId, commentNlpDto);
    }
}
