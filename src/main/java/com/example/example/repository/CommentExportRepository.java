package com.example.example.repository;

import com.example.example.domain.CommentExportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentExportRepository extends JpaRepository<CommentExportEntity, UUID> {
}
