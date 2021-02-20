package com.Assessment.SocialMedia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Assessment.SocialMedia.services.HashTagService;
import com.Assessment.SocialMedia.services.TweedleUserService;

import lombok.AllArgsConstructor;

/* Endpoints:
 * 
 * GET validate/tag/exists/{label}
 * GET validate/username/exists/@{username}
 * GET validate/username/available/@{username}
 *
 *
 * GET validate/tag/exists/{label}
 * checks whether or not a given hashtag exists
 * 
 * GET validate/username/exists/@{username}
 * checks whether or not a given username exists
 * 
 * GET validate/username/available/@{username}
 * checks whether or not a given username is available
 * 
 */

@RestController
@RequestMapping("validate")
@AllArgsConstructor
public class ValidateController {
	
	// Think about how to do this without making a ValidateService.java
	
	// Do I need a ValidateResponseDTO?
	
	private HashTagService hashTagService;
	private TweedleUserService tweedleUserService;
	
	// checks whether or not a given hashtag exists
	@GetMapping("/tag/exists/{label}")
	public boolean getHashtagExists(@PathVariable("label") String label) {
		return hashTagService.tagExists(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public boolean getUsernameExists(@PathVariable("username") String username) {
		return tweedleUserService.userExists(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean getUsernameAvailable(@PathVariable("username") String username) {
		return !tweedleUserService.userExists(username);
	}
	
}
