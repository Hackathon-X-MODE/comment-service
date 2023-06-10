package com.example.example.model;

import com.example.example.domain.enumerated.CommentMood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentFilter {

    private UUID orderId;

    private CommentMood mood;
}
