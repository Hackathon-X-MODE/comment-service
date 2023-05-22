package com.example.example.service;

import com.example.example.client.TicketClient;
import com.example.example.mapper.CommentMapper;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentNlpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {

    private final CommentService commentService;

    private final TicketClient ticketClient;

    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto updateViaNlp(UUID commentId, CommentNlpDto commentNlpDto) {
        final var comment = this.commentService.get(commentId);
        return this.afterUpdate(() -> this.commentMapper.toDto(
                this.commentMapper.update(comment, commentNlpDto)
        ));
    }

    @Transactional
    public CommentDto update(UUID commentId, CommentDto commentDto) {
        final var comment = this.commentService.get(commentId);
        return this.afterUpdate(() -> this.commentMapper.toDto(
                this.commentMapper.update(comment, commentDto)
        ));
    }


    private CommentDto afterUpdate(Supplier<CommentDto> commentDtoSupplier) {
        final var dto = commentDtoSupplier.get();
        this.ticketClient.registerTicket(commentDtoSupplier.get());
        return dto;
    }
}
