package com.Assessment.SocialMedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetFeedResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.services.TweedleUserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class TweedleUserController {
	private TweedleUserService userServ;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<TweedleUserResponseDTO> getAllUsers(){
		return userServ.getAllUsers();
	}
	
	@RequestMapping(value = "/@{username}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public TweedleUserResponseDTO getUser(@PathVariable("username") String userName) {
		return userServ.getUser(userName);
	}
	
	@RequestMapping(value = "/@{username}/feed", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetFeedResponseDTO> getUserFeed(@PathVariable("username") String userName) {
		return userServ.getUserFeed(userName);
	}
	
	@RequestMapping(value = "/@{username}/tweets", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetFeedResponseDTO> getUserTweets(@PathVariable("username") String userName) {
		return userServ.getUserTweets(userName);
	}
	
	@RequestMapping(value = "/@{username}/mentions", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetResponseDTO> getUserMentions(@PathVariable("username") String userName) {
		return userServ.getUserMentions(userName);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public TweedleUserResponseDTO postUser(@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.postUser(tUserRequestDTO);
	}
	
	@RequestMapping(value = "/@{username}/follow", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void postUserFollow(@PathVariable("username") String username,@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		userServ.postUserFollow(username,tUserRequestDTO);
	}
	
	@RequestMapping(value = "/@{username}/unfollow", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void postUserUnfollow(@PathVariable("username") String username,@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		userServ.postUserUnfollow(username,tUserRequestDTO);
	}
	
	@RequestMapping(value = "/@{username}", method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TweedleUserResponseDTO patchUser(@PathVariable("username") String userName, @RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.patchUser(userName,tUserRequestDTO);
	}
	
	@RequestMapping(value = "/@{username}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public TweedleUserResponseDTO deleteUser(@PathVariable("username") String userName, @RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.deleteUser(userName,tUserRequestDTO);
	}
	
}
