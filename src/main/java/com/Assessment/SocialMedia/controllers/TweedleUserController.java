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

import com.Assessment.SocialMedia.model.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.model.TweedleUserResponseDTO;
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
	
	//TODO: Should send "an error should be sent in lieu of response." Does this mean an exception?
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public TweedleUserResponseDTO getUser(@PathVariable("username") String userName) {
		return userServ.getUser(userName);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public TweedleUserResponseDTO postUser(@RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.postUser(tUserRequestDTO);
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TweedleUserResponseDTO patchUser(@PathVariable("username") String userName, @RequestBody TweedleUserRequestDTO tUserRequestDTO) {
		return userServ.patchUser(userName,tUserRequestDTO);
	}
	
}
