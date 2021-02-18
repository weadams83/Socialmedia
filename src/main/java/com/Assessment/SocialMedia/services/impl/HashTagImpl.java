package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.HashTagMapper;
import com.Assessment.SocialMedia.model.HashTagResponseDTO;
import com.Assessment.SocialMedia.repositories.HashTagRepository;
import com.Assessment.SocialMedia.services.HashTagService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HashTagImpl implements HashTagService {

	private HashTagRepository hashTagRepository;
	private HashTagMapper hTagMapper;

	@Override
	public List<HashTagResponseDTO> getAllHashTags() {
		return hTagMapper.entitiesToResponseDTOs(hashTagRepository.findAll());
	}


}
