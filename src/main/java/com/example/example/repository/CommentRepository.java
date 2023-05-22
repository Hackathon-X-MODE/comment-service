package com.example.example.repository;

import com.example.example.domain.CommentEntity;
import com.example.example.model.CommentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Objects;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID>, JpaSpecificationExecutor<CommentEntity> {
    default Page<CommentEntity> filter(CommentFilter commentFilter, Pageable pageable) {
        var specification = Specification.<CommentEntity>where(null);

        if (Objects.nonNull(commentFilter.getOrderId())) {
            specification = specification.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderId"), commentFilter.getOrderId())
            );
        }

        return this.findAll(specification, pageable);
    }
}
