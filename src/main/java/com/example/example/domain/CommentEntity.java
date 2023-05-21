package com.example.example.domain;

import com.example.example.domain.enumerated.CommentMood;
import com.example.example.domain.enumerated.CommentSource;
import com.example.example.domain.enumerated.CommentType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Table(name = "comment")
@Entity
@ToString
@Accessors(chain = true)
public class CommentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Embedded
    private Person person;

    @Column(name = "source", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CommentSource source;

    @Column(name = "comment", columnDefinition = "varchar(3072)", nullable = false)
    private String comment;

    private double rate;

    @Type(JsonType.class)
    @Column(name = "comment_type", columnDefinition = "json")
    private List<CommentType> commentTypes = new ArrayList<>();

    @Column(name = "comment_mood")
    @Enumerated(value = EnumType.STRING)
    private CommentMood mood;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
