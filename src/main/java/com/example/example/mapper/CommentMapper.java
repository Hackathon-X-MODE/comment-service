package com.example.example.mapper;

import com.example.example.configuration.MapperConfiguration;
import com.example.example.domain.CommentEntity;
import com.example.example.model.CommentCreationDto;
import com.example.example.model.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfiguration.class)
public interface CommentMapper {


    @Mapping(target = "commentTypes", source = "types")
    CommentEntity toEntity(CommentCreationDto commentCreationDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mood", source = "mood", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "commentTypes", source = "commentTypes", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommentEntity update(@MappingTarget CommentEntity commentEntity, CommentDto commentDto);

    CommentDto toDto(CommentEntity commentEntity);
}
