package com.example.example.service;


import com.example.example.client.OrderClient;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentFilter;
import com.example.example.model.order.OrderWithMetaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentAdvanceService {

    private final CommentService commentService;

    private final OrderClient orderClient;


    public Page<CommentDto> filterWithPage(CommentFilter commentFilter, Pageable pageable) {
        final var data = this.commentService.filterWithPage(commentFilter, pageable);
        final var orders = data.map(CommentDto::getOrderId).toSet();
        final var orderToData = this.orderClient.getOrders(orders).stream()
                .collect(Collectors.toMap(OrderWithMetaDto::getId, Function.identity()));
        data.forEach(commentDto -> commentDto.setVendorId(orderToData.get(commentDto.getOrderId()).getVendorId()));

        return data;
    }
}
