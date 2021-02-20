package com.Assessment.SocialMedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.services.HashTagService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("tags")
@AllArgsConstructor
public class HashTagController {

	private HashTagService hashTagService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<HashTagResponseDTO> getAllHashTags() {
		return hashTagService.getAllHashTags();
	}
	
	@GetMapping("/{label}")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetResponseDTO> getAllTweetsWithHashTag(@PathVariable("label") String label){
		return hashTagService.getAllTweetsWithHashTag(label);
	}
	


	
	
}


