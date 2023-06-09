package com.example.example.service;

import com.example.example.client.OrderClient;
import com.example.example.domain.CommentEntity;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.mapper.CommentMapper;
import com.example.example.model.*;
import com.example.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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
        log.info("new comment sent to analyst");
        this.orderClient.deleteKey(key);
    }

    @Transactional
    public void append(String vendorCode, String externalOrderId, CommentAppendDto commentAppendDto) {
        final var orderId = this.orderClient.getOrderIdByExternal(vendorCode, externalOrderId);

        final var comment = this.commentRepository.saveAndFlush(
                this.commentMapper.toEntity(commentAppendDto)
                        .setOrderId(orderId)
        );
        this.nlpService.sentAnalyst(comment.getId(), comment.getComment(), comment.getCommentTypes().isEmpty());
        log.info("new comment sent to analyst");
    }

    @Transactional
    public CommentDto update(UUID commentId, CommentNlpDto commentNlpDto) {
        return this.commentMapper.toDto(
                this.commentMapper.update(this.get(commentId), commentNlpDto)
        );
    }


    @Transactional(readOnly = true)
    public CommentEntity get(UUID commentId) {
        return this.commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Can't find comment " + commentId)
        );
    }

    @Transactional(readOnly = true)
    public CommentDto getDto(UUID commentId) {
        return this.commentMapper.toDto(this.get(commentId));
    }


    @Transactional(readOnly = true)
    public List<CommentDto> getDto(Collection<UUID> ids) {
        return this.commentRepository.findAllById(ids).stream().map(this.commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> filter(CommentFilter commentFilter) {
        return this.filterWithPage(commentFilter, Pageable.unpaged()).stream().toList();
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> filterWithPage(CommentFilter commentFilter, Pageable pageable) {

        final var page = this.commentRepository.filter(commentFilter, pageable);

        return new PageImpl<>(
                page.stream().map(this.commentMapper::toDto)
                        .toList(),
                pageable, page.getTotalElements()
        );
    }
}
