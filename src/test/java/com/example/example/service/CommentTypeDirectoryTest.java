package com.example.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.example.domain.enumerated.CommentType.*;
import static org.assertj.core.api.Assertions.assertThat;

class CommentTypeDirectoryTest {


    @Test
    void shouldToTree() {

        final var result = CommentTypeDirectory.toTree(
                List.of(
                        //just main
                        PRODUCT,
                        //main with sub
                        DELIVERY,
                        DEADLINE,

                        //just sub
                        DELIVERY_NOTIFICATION,
                        CONFIRM_NOTIFICATION
                )
        );


        assertThat(result).contains(
                Map.entry(PRODUCT, Set.of()),
                Map.entry(DELIVERY, Set.of(DEADLINE)),
                Map.entry(NOTIFICATION, Set.of(DELIVERY_NOTIFICATION, CONFIRM_NOTIFICATION))
        );
    }

    @Test
    void shouldToTreeForAll() {
        final var result = CommentTypeDirectory.toTree(List.of(values()));

        assertThat(result.keySet().size()).isEqualTo(9);
    }
}