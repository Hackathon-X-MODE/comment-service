package com.example.example.model;

import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private UUID id;

    private UUID orderId;

    private CommentMood mood;

    private double rate;

    @Schema(description = "Обновление")
    private Set<CommentType> commentTypesSet;

    private Map<CommentType, Set<CommentType>> commentTypes;


}
