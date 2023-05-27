package com.example.example.model;

import com.example.example.domain.Person;
import com.example.example.domain.enumerated.CommentSource;
import com.example.example.domain.enumerated.CommentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreationDto {

    private String code;

    private CommentSource source;

    private String comment;

    private double rate;

    private List<CommentType> types;

    private Person person;
}
