package com.example.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name = "comment_export")
@Entity
@ToString
@Accessors(chain = true)
public class CommentExportEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    private String path;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
