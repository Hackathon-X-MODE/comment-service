package com.example.example.controller;

import com.example.example.model.CommentCreationDto;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentFilter;
import com.example.example.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Зарегистрировать отзыв")
    @PostMapping
    public void register(
            @RequestHeader("X-Ref-Order") String key,
            @RequestBody CommentCreationDto commentCreationDto
    ) {
        this.commentService.register(key, commentCreationDto);
    }

    @GetMapping("{commentId}")
    public CommentDto getComment(
            @PathVariable("commentId") UUID commentId
    ) {
        return this.commentService.getDto(commentId);
    }


    @Operation(summary = "Поиск по фильтрам")
    @GetMapping
    public List<CommentDto> getComment(
            CommentFilter commentFilter
    ) {
        return this.commentService.filter(commentFilter);
    }

    @Operation(description = "Получение по списку ID")
    @PostMapping("/list")
    public List<CommentDto> get(
            @RequestBody Set<UUID> commentIds) {
        return this.commentService.getDto(commentIds);
    }

    @PatchMapping("{commentId}")
    public CommentDto update(
            @PathVariable("commentId") UUID commentId,
            @RequestBody CommentDto commentDto
    ) {
        return this.commentService.update(commentId, commentDto);
    }

}
