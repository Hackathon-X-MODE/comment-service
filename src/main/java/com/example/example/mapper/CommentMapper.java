package com.example.example.mapper;

import com.example.example.configuration.MapperConfiguration;
import com.example.example.domain.CommentEntity;
import com.example.example.model.CommentCreationDto;
import com.example.example.model.CommentDto;
import com.example.example.model.CommentNlpDto;
import com.example.example.service.CommentTypeDirectory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfiguration.class, imports = CommentTypeDirectory.class)
public interface CommentMapper {


    @Mapping(target = "commentTypes", source = "types")
    CommentEntity toEntity(CommentCreationDto commentCreationDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "mood", source = "mood", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "commentTypes", source = "commentTypes", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity update(@MappingTarget CommentEntity commentEntity, CommentNlpDto commentNlpDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "mood", source = "mood", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "commentTypes", source = "commentTypesSet", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity update(@MappingTarget CommentEntity commentEntity, CommentDto commentDto);

    @Mapping(target = "commentTypesSet", source = "commentTypes")
    @Mapping(target = "commentTypes", expression = "java(CommentTypeDirectory.toTree(commentEntity.getCommentTypes()))")
    CommentDto toDto(CommentEntity commentEntity);


}
