package com.example.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentStats {

    private long negative;

    private long positive;

    private long neutral;
}
