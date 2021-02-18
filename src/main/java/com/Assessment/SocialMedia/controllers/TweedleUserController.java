package com.Assessment.SocialMedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<TweedleUserResponseDTO> getAllUsers(){
		return userServ.getAllUsers();
	}
	
	@GetMapping("/@{username}")
	@ResponseStatus(HttpStatus.FOUND)
	public TweedleUserResponseDTO getUser(@PathVariable("username") String userName) {
		return userServ.getUser(userName);
	}
	
	@GetMapping("/@{username}/feed")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetFeedResponseDTO> getUserFeed(@PathVariable("username") String userName) {
		return userServ.getUserFeed(userName);
	}
	
	@GetMapping("/@{username}/tweets")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetFeedResponseDTO> getUserTweets(@PathVariable("username") String userName) {
		return userServ.getUserTweets(userName);
	}
	
	@GetMapping(value = "/@{username}/mentions")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweetResponseDTO> getUserMentions(@PathVariable("username") String userName) {
		return userServ.getUserMentions(userName);
	}
	
	@GetMapping(value = "/@{username}/followers")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweedleUserResponseDTO> getUserFollowers(@PathVariable("username") String userName) {
		return userServ.getUserFollowers(userName);
	}
	
	@GetMapping(value = "/@{username}/following")
	@ResponseStatus(HttpStatus.FOUND)
	public List<TweedleUserResponseDTO> getUserFollowing(@PathVariable("username") String userName) {
		return userServ.getUserFollowing(userName);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweedleUserResponseDTO postUser(@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.postUser(tUserRequestDTO);
	}
	
	@PostMapping("/@{username}/follow")
	@ResponseStatus(HttpStatus.CREATED)
	public void postUserFollow(@PathVariable("username") String username,@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		userServ.postUserFollow(username,tUserRequestDTO);
	}
	
	@PostMapping("/@{username}/unfollow")
	@ResponseStatus(HttpStatus.OK)
	public void postUserUnfollow(@PathVariable("username") String username,@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		userServ.postUserUnfollow(username,tUserRequestDTO);
	}
	
	@PatchMapping("/@{username}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TweedleUserResponseDTO patchUser(@PathVariable("username") String userName, @RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.patchUser(userName,tUserRequestDTO);
	}
	
	@DeleteMapping("/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public TweedleUserResponseDTO deleteUser(@PathVariable("username") String userName, @RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.deleteUser(userName,tUserRequestDTO);
	}
	
}
