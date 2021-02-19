package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;

public interface HashTagService {

	List<HashTagResponseDTO> getAllHashTags();

	List<TweetResponseDTO> getAllTweetsWithHashTag(String label);


}
