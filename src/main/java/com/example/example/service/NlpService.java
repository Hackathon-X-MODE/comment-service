package com.example.example.service;

import com.example.example.configuration.AMQPConfiguration;
import com.example.example.model.nlp.CommentAnalystRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NlpService {

    /**
     * Component for send AMQP requests
     */
    private final AmqpTemplate amqpTemplate;


    public void sentAnalyst(UUID commentId, String comment, boolean needTags) {
        this.amqpTemplate.convertAndSend(
                AMQPConfiguration.COMMENTS_KEY,
                CommentAnalystRequest.builder()
                        .id(commentId)
                        .comment(comment)
                        .needTypes(needTags)
                        .build()
        );
    }
}
