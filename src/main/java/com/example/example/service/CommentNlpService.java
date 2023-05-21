package com.example.example.service;

import com.example.example.client.TicketClient;
import com.example.example.model.CommentNlpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentNlpService {
    private final CommentService commentService;

    private final TicketClient ticketClient;



    public void acceptNlpProcess(UUID commentId, CommentNlpDto commentNlpDto){
        this.commentService.update(commentId, commentNlpDto);
        this.ticketClient.registerTicket(commentId);
    }
}
