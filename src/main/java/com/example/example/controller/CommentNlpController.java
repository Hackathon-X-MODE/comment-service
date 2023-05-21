package com.example.example.controller;

import com.example.example.model.CommentNlpDto;
import com.example.example.service.CommentNlpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("external")
@RequiredArgsConstructor
public class CommentNlpController {

    private final CommentNlpService commentNlpService;

    @PatchMapping("{commentId}")
    public void update(
            @PathVariable("commentId") UUID commentId,
            @RequestBody CommentNlpDto commentNlpDto) {
        this.commentNlpService.acceptNlpProcess(commentId, commentNlpDto);
    }
}
