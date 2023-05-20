package com.example.example.service;

import com.example.example.client.OrderClient;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.mapper.CommentMapper;
import com.example.example.model.CommentCreationDto;
import com.example.example.model.CommentDto;
import com.example.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final OrderClient orderClient;

    private final NlpService nlpService;

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;


    @Transactional
    public void register(String key, CommentCreationDto commentCreationDto) {
        final var orderId = this.orderClient.getOrderIdByKey(key);

        final var comment = this.commentRepository.saveAndFlush(
                this.commentMapper.toEntity(commentCreationDto)
                        .setOrderId(orderId)
        );
        this.nlpService.sentAnalyst(comment.getId(), comment.getComment(), comment.getCommentTypes().isEmpty());

        this.orderClient.deleteKey(key);
    }

    @Transactional
    public CommentDto update(UUID commentId, CommentDto commentDto) {

        final var commentEntity = this.commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Can't find comment " + commentId)
        );

        return this.commentMapper.toDto(this.commentMapper.update(commentEntity, commentDto));
    }
}
