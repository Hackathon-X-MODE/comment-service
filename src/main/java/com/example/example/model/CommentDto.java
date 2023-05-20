package com.example.example.model;

import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private UUID id;

    private CommentMood mood;

    private List<CommentType> commentTypes;
}
