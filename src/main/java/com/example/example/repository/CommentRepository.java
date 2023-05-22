package com.example.example.repository;

import com.example.example.domain.CommentEntity;
import com.example.example.model.CommentFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID>, JpaSpecificationExecutor<CommentEntity> {
    default List<CommentEntity> filter(CommentFilter commentFilter) {
        var specification = Specification.<CommentEntity>where(null);

        if (Objects.nonNull(commentFilter.getOrderId())) {
            specification = specification.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderId"), commentFilter.getOrderId())
            );
        }

        return this.findAll(specification);
    }
}
