package com.Assessment.SocialMedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;

import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.services.TweetService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("tweets")
@AllArgsConstructor
public class TweetController {

	private TweetService tweetService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDTO> getAllTweets() {
		return tweetService.getAllTweets();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDTO deleteTweet(@PathVariable("id") Long id, @RequestBody TweedleUserRequestDTO tweetUserRequestDTO) {
		return tweetService.deleteTweet(id, tweetUserRequestDTO);
	}
	

}
