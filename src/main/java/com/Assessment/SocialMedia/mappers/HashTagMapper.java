package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.entities.HashTag;

@Mapper(componentModel = "spring")
public interface HashTagMapper {

	List<HashTagResponseDTO> entitiesToResponseDTOs(List<HashTag> findAll);

}
