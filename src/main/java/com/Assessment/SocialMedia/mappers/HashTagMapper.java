package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.model.HashTagResponseDTO;


@Mapper(componentModel = "spring")
public interface HashTagMapper {

	List<HashTagResponseDTO> entitiesToResponseDTOs(List<HashTag> findAll);
	
//	HashTag DTOtoEntity(HashTagDTO hashTagDTO);
//	List<HashTagResponseDTO> entitiesToResponseDTOs(List<HashTag> hTag);
//	HashTagResponseDTO entityToResponseDTO(HashTag hashTag);
//	HashTag requestDTOtoEntity(HashTagRequestDTO hashTagRequestDTO);


}
