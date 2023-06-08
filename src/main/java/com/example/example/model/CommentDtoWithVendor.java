package com.example.example.model;

import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentSource;
import com.example.example.domain.enumerated.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoWithVendor {


    private UUID id;

    private CommentSource source;

    private UUID orderId;

    private UUID vendorId;

    private CommentMood mood;

    private double rate;

    private String comment;

    @Schema(description = "Обновление")
    private Set<CommentType> commentTypesSet;

    private Map<CommentType, Set<CommentType>> commentTypes;


    private LocalDateTime createDate;


}
