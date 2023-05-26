package com.example.example.service;

import com.example.example.domain.enumerated.CommentType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommentTypeDirectory {

    private static final Map<CommentType, Set<CommentType>> MAP_TYPES = Map.of(
            CommentType.PRODUCT_DESCRIPTION, Set.of(),
            CommentType.PREPARE_ORDER, Set.of(
                    CommentType.SELECT_POSTAMAT,
                    CommentType.SEARCH_POSTAMAT_AT_HOUSE
            ),
            CommentType.GETTING_ORDER, Set.of(
                    CommentType.PAY_ORDER,
                    CommentType.OPEN_POSTAMAT
            ),
            CommentType.GOT_ORDER, Set.of(
                    CommentType.PACKING,
                    CommentType.COMPLETENESS
            ),
            CommentType.PRODUCT, Set.of(
                    CommentType.QUALITY,
                    CommentType.DESCRIPTION
            ),
            CommentType.POST_BOX, Set.of(
                    CommentType.WORK_POSTAMAT,
                    CommentType.LOCATION_POSTAMAT,
                    CommentType.VIEW_POSTAMAT
            ),
            CommentType.DELIVERY, Set.of(
                    CommentType.DEADLINE,
                    CommentType.COAST_DELIVERY,
                    CommentType.DELIVERY_GUY_REPORT
            ),
            CommentType.NOTIFICATION, Set.of(
                    CommentType.CONFIRM_NOTIFICATION,
                    CommentType.DELIVERY_NOTIFICATION,
                    CommentType.READY_NOTIFICATION
            ),
            CommentType.OTHER, Set.of()
    );


    public static Map<CommentType, Set<CommentType>> getMapTypes() {
        return MAP_TYPES;
    }

    public static Map<CommentType, Set<CommentType>> toTree(Collection<CommentType> commentTypes) {
        final var result = new HashMap<CommentType, Set<CommentType>>();


        for (final var type : commentTypes) {
            if (isFirstLevel(type)) {
                push(result, type, null);
            } else {
                final var firstLevel = getFirstLevel(type);
                push(result, firstLevel, type);
            }
        }
        return result;
    }


    private static void push(Map<CommentType, Set<CommentType>> storage, CommentType first, CommentType second) {
        if (storage.containsKey(first)) {
            if (second != null) {
                storage.get(first).add(second);
            }
        } else {
            final var set = new HashSet<CommentType>();
            if (second != null) {
                set.add(second);
            }
            storage.put(first, set);
        }
    }

    private static CommentType getFirstLevel(CommentType commentType) {
        for (final var entry : MAP_TYPES.entrySet()) {
            if (entry.getValue().contains(commentType)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Unsupported " + commentType);
    }

    public static boolean isFirstLevel(CommentType commentType) {
        return MAP_TYPES.containsKey(commentType);
    }

    public static boolean isSecondLevel(CommentType commentType) {
        return !isFirstLevel(commentType);
    }

}
